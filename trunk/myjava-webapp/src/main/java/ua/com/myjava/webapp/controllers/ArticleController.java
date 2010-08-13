package ua.com.myjava.webapp.controllers;

import javax.servlet.http.HttpServletRequest;


import javax.servlet.http.HttpServletResponse;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import ua.com.myjava.model.Article;
import ua.com.myjava.model.Comment;
import ua.com.myjava.persist.ArticleDAO;
import ua.com.myjava.persist.CommentDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleController implements Controller {

    private static final int NO_ARTICLE = 0;
    ArticleDAO articleDAO;
    CommentDAO commendDAO;
    private CommentDAO commentDAO;

    public void setCommendDAO(CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }

    public ArticleDAO getArticleDAO() {
        return articleDAO;
    }

    public void setArticleDAO(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    private ua.com.myjava.article.ArticleHelper articleHelper;


    public ua.com.myjava.article.ArticleHelper getArticleHelper() {
        return articleHelper;
    }

    public void setArticleHelper(ua.com.myjava.article.ArticleHelper articleHelper) {
        this.articleHelper = articleHelper;
    }

    public ModelAndView handleRequest(HttpServletRequest req,
                                      HttpServletResponse arg1) throws Exception {
        int id = NO_ARTICLE;
        if (req.getParameter("id") != null) {
            try {
                id = Integer.parseInt(req.getParameter("id"));
            } catch (Exception e) {
            }
        }
        Article article = articleDAO.load(id);
        if (article == null) {
            article = articleDAO.load(NO_ARTICLE);
        }
        List<Comment> comments = commentDAO.getComments(article.getId());

        article.setText(articleHelper.getArticle(article.getFilename()));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put( "article", article);
        map.put( "comments", comments);

        return new ModelAndView("article", map);
    }

    public void setCommentDAO(CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }

    public CommentDAO getCommentDAO() {
        return commentDAO;
    }
}
