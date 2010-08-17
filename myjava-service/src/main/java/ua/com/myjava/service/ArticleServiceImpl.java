package ua.com.myjava.service;

import ua.com.myjava.model.Article;
import ua.com.myjava.persist.ArticleDAO;

import java.util.Date;

/**
 * User: abogoley
 * Date: 17.08.2010
 */
public class ArticleServiceImpl implements ArticleService {
    private ArticleDAO articleDAO;

    public int addArticle(Article article) throws ArticleException {
        article.setDate(new Date());
        try {
            articleDAO.save(article);
        } catch (Exception e) {
            throw new ArticleException("Cannot store article");
        }
        return article.getId();
    }

    public void setArticleDAO(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }
}
