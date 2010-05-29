package ua.com.myjava.webapp.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import ua.com.myjava.model.Article;
import ua.com.myjava.persist.ArticleDAO;

public class ArticleListController implements Controller {
	ArticleDAO articleDAO;
	

	public ArticleDAO getArticleDAO() {
		return articleDAO;
	}


	public void setArticleDAO(ArticleDAO articleDAO) {
		this.articleDAO = articleDAO;
	}


	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		List<Article> articles = articleDAO.getArticles();
		return new ModelAndView("articleList", "articles", articles);
	} 
}
