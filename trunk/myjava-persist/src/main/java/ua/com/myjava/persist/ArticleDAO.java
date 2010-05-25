package ua.com.myjava.persist;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ua.com.myjava.model.Article;



public class ArticleDAO extends HibernateDaoSupport {

	private ua.com.myjava.article.ArticleService articleService;

	public ua.com.myjava.article.ArticleService getArticleService() {
		return articleService;
	}

	public void setArticleService(
			ua.com.myjava.article.ArticleService articleService) {
		this.articleService = articleService;
	}

	public ArticleDAO() {
	}

	public void save(Article article) {
		getHibernateTemplate().save(article);
	}

	public Article load(Integer id) {
		Article article = (Article) getHibernateTemplate().load(Article.class,
				id);
		article.setText(articleService.getArticle(article.getFilename()));

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

}
