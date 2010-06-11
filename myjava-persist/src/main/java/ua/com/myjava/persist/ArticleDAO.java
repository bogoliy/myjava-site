package ua.com.myjava.persist;

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
import org.apache.lucene.analysis.ru.RussianStemFilter;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.SearchFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ua.com.myjava.model.Article;
import org.apache.solr.analysis.SnowballPorterFilterFactory;

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
		org.apache.lucene.search.Query luceneQuery;
		try {
			luceneQuery = buildNGramQuery(searchQuery);
		} catch (ParseException e) {
			throw new RuntimeException("Unable to parse query: " + searchQuery,
					e);
		} catch (Exception e) {
			throw new RuntimeException("Unable to parse query: " + searchQuery,
					e);
		}
		FullTextSession ftSession = Search
				.getFullTextSession(this.getSession());

		org.hibernate.Query query = ftSession.createFullTextQuery(luceneQuery,
				Article.class);
		log.info("Search completed" );
		return new ArrayList<Article>(query.list());
	}

	private org.apache.lucene.search.Query buildNGramQuery(String search)
			throws Exception {
		Reader reader = new StringReader(search);
		Analyzer analyzer = new StandardAnalyzer();
		TokenStream stream = analyzer.tokenStream("articleText", reader);
		NGramTokenFilter ngramFilter = new NGramTokenFilter(stream, 3, 3);
		Token token = new Token();
		token = ngramFilter.next(token);
		BooleanQuery query = new BooleanQuery();
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

	@SuppressWarnings("unchecked")
	public List<Article> getArticlesWithSameRoot(String searchQuery) {
		org.apache.lucene.search.Query luceneQuery;
		try {
			luceneQuery = buildSnowballQuery(searchQuery);
		} catch (ParseException e) {
			throw new RuntimeException("Unable to parse query: " + searchQuery,
					e);
		} catch (Exception e) {
			throw new RuntimeException("Unable to parse query: " + searchQuery,
					e);
		}
		FullTextSession ftSession = Search
				.getFullTextSession(this.getSession());

		org.hibernate.Query query = ftSession.createFullTextQuery(luceneQuery,
				Article.class);

		return new ArrayList<Article>(query.list());

	}

	private org.apache.lucene.search.Query buildSnowballQuery(String search)
			throws Exception {
		Reader reader = new StringReader(search);
		Analyzer analyzer = new StandardAnalyzer();
		TokenStream stream = analyzer.tokenStream("title_stemmer", reader);
		SnowballFilter snowballFilter = new SnowballFilter(stream,
				new org.tartarus.snowball.ext.RussianStemmer());
		Token token = new Token();
		token = snowballFilter.next(token);
		BooleanQuery query = new BooleanQuery();
		String term = new String(token.termBuffer(), 0, token.termLength());
		// add it to the query by creating a TermQuery
		query.add(new TermQuery(new Term("title_stemmer", term)), Occur.SHOULD);

		return query;
	}

}
