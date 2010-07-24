package ua.com.myjava.webapp.controllers;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import ua.com.myjava.article.ArticleHelper;
import ua.com.myjava.model.Article;
import ua.com.myjava.persist.ArticleDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: abogoley
 * Date: 24.07.2010
 * Time: 15:51:26
 */
public class AddArticleController extends SimpleFormController {
    private ArticleDAO articleDAO;
    private ArticleHelper articleHelper;

    @Override
    protected ModelAndView onSubmit(
            HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
            throws Exception {
        Article article = (Article) command;
        String fileName = System.currentTimeMillis() + ".htm";
        article.setFilename(fileName);
        articleHelper.saveInFileSystem(article);
        articleDAO.save(article);

        // default behavior: render success view
        if (getSuccessView() == null) {
            throw new ServletException("successView isn't set");
        }
        return new ModelAndView(getSuccessView(), errors.getModel());

    }


    public void setArticleDAO(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    public void setArticleHelper(ArticleHelper articleHelper) {
        this.articleHelper = articleHelper;
    }
}
