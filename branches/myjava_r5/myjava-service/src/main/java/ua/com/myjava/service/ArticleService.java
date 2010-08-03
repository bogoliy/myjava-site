package ua.com.myjava.service;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ua.com.myjava.model.Article;
import ua.com.myjava.persist.ArticleDAO;
import ua.com.myjava.service.model.ArticleWraper;

@WebService
public class ArticleService {

	private ArticleDAO articleDAO;

	@WebMethod
	public ArticleWraper[] getRecentArticles() {
		List<ArticleWraper> articles = new ArrayList<ArticleWraper>();
		for (Article article : getArticleDAO().getArticles()) {
			articles.add(new ArticleWraper(article));
		}
		return articles.toArray(new ArticleWraper[] {});
	}

	private ArticleDAO getArticleDAO() {
		if (articleDAO == null) {
			ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
					new String[] { "application-context.xml" });
			BeanFactory factory = (BeanFactory) appContext;
			articleDAO = (ArticleDAO) factory.getBean("articleDAO");
		}
		return articleDAO;
	}
}
