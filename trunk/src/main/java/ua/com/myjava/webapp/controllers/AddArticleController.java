package ua.com.myjava.webapp.controllers;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import ua.com.myjava.article.ArticleHelper;
import ua.com.myjava.model.Article;
import ua.com.myjava.persist.ArticleDAO;
import ua.com.myjava.webapp.fckeditor.FCKeditorWrapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.springframework.web.context.support.WebApplicationObjectSupport;
/**
 * User: abogoley
 * Date: 24.07.2010
 * Time: 15:51:26
 */
public class AddArticleController extends SimpleFormController {
    Logger log = Logger.getLogger(AddArticleController.class.toString());
    private ArticleDAO articleDAO;
    private ArticleHelper articleHelper;

	protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
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

        Map<String, Object> props = new HashMap<String, Object> ();
        props.put("editor", new FCKeditorWrapper(request));
        props.put("articles", articles);

        return props;
    }

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
