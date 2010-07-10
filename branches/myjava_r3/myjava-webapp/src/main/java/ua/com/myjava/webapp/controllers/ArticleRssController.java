package ua.com.myjava.webapp.controllers;

import java.util.List;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import ua.com.myjava.model.Article;
import ua.com.myjava.persist.ArticleDAO;

public class ArticleRssController implements Controller {
	ArticleDAO articleDAO;
	

	public ArticleDAO getArticleDAO() {
		return articleDAO;
	}


	public void setArticleDAO(ArticleDAO articleDAO) {
		this.articleDAO = articleDAO;
	}
	    
	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		//List<Article> articles = articleDAO.getArticles();
		List<Article> articles = new ArrayList<Article>();
		Article article1 = new Article();
		article1.setText("article first text");
		article1.setTitle("article first title");
		article1.setId(1);
		
		Article article2 = new Article();
		article2.setText("article second text");
		article2.setTitle("article second title");
		article2.setId(2);
		
		articles.add(article1);
		articles.add(article2);
		return new ModelAndView("rss", "articles", articles);
	}
}
