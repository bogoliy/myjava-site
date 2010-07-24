package ua.com.myjava.webapp.controllers;

import java.util.HashMap;
import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import ua.com.myjava.model.Article;
import ua.com.myjava.persist.ArticleDAO;
import ua.com.myjava.webapp.fckeditor.FCKeditorWrapper;

public class ArticleListController implements Controller {
    Logger log = Logger.getLogger(ArticleListController.class.toString());
    ArticleDAO articleDAO;

    public ArticleDAO getArticleDAO() {
        return articleDAO;
    }

    public void setArticleDAO(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    public ModelAndView handleRequest(HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        String searchString = request.getParameter("serachString");
        String findAdditionalResults = request
                .getParameter("additionalResults");

        List<Article> articles = null;
        log.info("Starting controller work with search string = "
                + searchString);
        if (searchString == null)
            articles = articleDAO.getArticles();
        else {
            if (findAdditionalResults != null
                    && findAdditionalResults.equals("true")) {
                articles = articleDAO.getArticles(searchString);
                log.info("Retreiving results with the same root");
            } else {
                articles = articleDAO.getArticles(searchString);
                log.info("Executing search");
            }

        }

        Map<String, Object> props = new HashMap<String, Object>();
        props.put("editor", new FCKeditorWrapper(request));
        props.put("articles", articles);

        return new ModelAndView("articleList", props);
    }
}
