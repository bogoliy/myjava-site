package ua.com.myjava.persist;

import java.io.File;
import java.io.Reader;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ngram.NGramTokenFilter;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ua.com.myjava.model.Article;

public class ArticleDAO extends HibernateDaoSupport {
	private static final int WINDOW = 10;
	Logger log = Logger.getLogger(ArticleDAO.class.toString());

	public ArticleDAO() {
	}

	public void save(Article article) {
		getHibernateTemplate().save(article);
	}
  
	public Article load(Integer id) { 

		Article article = (Article) getHibernateTemplate().get(Article.class,
				id);

		return article;
	}

	@SuppressWarnings("unchecked")
	public List<Article> getArticles() {
		return (List<Article>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session arg0)
							throws HibernateException, SQLException {
						Query query = getSession().createQuery("from Article");
						return new ArrayList<Article>(query.list());
					}
				});
	}

	@SuppressWarnings("unchecked")
	public List<Article> getArticles(String searchQuery) {
		BooleanQuery query = new BooleanQuery();
		try {
			query = buildNGramQuery(searchQuery, query);
			query = buildSnowballQuery(searchQuery, query);
		} catch (ParseException e) {
			throw new RuntimeException("Unable to parse query: " + searchQuery,
					e);
		} catch (Exception e) {
			throw new RuntimeException("Unable to parse query: " + searchQuery,
					e);
		}
		FullTextSession ftSession = Search
				.getFullTextSession(this.getSession());

		org.hibernate.Query hibQuery = ftSession.createFullTextQuery(query,
				Article.class);
		hibQuery.setFirstResult(0).setMaxResults(WINDOW);
		return new ArrayList<Article>(hibQuery.list());
	}

	private BooleanQuery buildNGramQuery(String search, BooleanQuery query)
			throws Exception {
		Reader reader = new StringReader(search);
		StandardAnalyzer analyzer = new StandardAnalyzer(new File(
				"stopwords.txt"));
		TokenStream stream = analyzer.tokenStream("articleText", reader);
		NGramTokenFilter ngramFilter = new NGramTokenFilter(stream, 3, 3);
		Token token = new Token();
		token = ngramFilter.next(token);
		while (token != null) {
			if (token.termLength() != 0) {
				String term = new String(token.termBuffer(), 0, token
						.termLength());
				// add it to the query by creating a TermQuery
				query.add(new TermQuery(new Term("articleText", term)),
						Occur.SHOULD);
			}
			System.out.println("*" + token.toString());
			System.out.println("**" + query.toString());
			token = ngramFilter.next(token);

		}
		return query;
	}

	private BooleanQuery buildSnowballQuery(String search, BooleanQuery query)
			throws Exception {
		Reader reader = new StringReader(search);
		StandardAnalyzer analyzer = new StandardAnalyzer(new File(
				"stopwords.txt"));
		TokenStream stream = analyzer.tokenStream("title_stemmer", reader);
		SnowballFilter snowballFilter = new SnowballFilter(stream,
				new org.tartarus.snowball.ext.RussianStemmer());
		Token token = new Token();
		token = snowballFilter.next(token);
		String term = new String(token.termBuffer(), 0, token.termLength());
		// add it to the query by creating a TermQuery
		query.add(new TermQuery(new Term("title_stemmer", term)), Occur.SHOULD);

		return query;
	}

}
