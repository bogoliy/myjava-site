package ua.com.myjava.service;

import ua.com.myjava.article.ArticleHelper;
import ua.com.myjava.model.Article;
import ua.com.myjava.persist.ArticleDAO;

import javax.jws.WebService;
import java.util.Date;

/**
 * User: abogoley
 * Date: 17.08.2010
 */
@WebService(endpointInterface = "ua.com.myjava.service.ArticleService")
public class ArticleServiceImpl implements ArticleService {
    private ArticleDAO articleDAO;
    private ArticleHelper articleHelper;

    public void addArticle(Article article) {
        article.setDate(new Date());
        String fileName = System.currentTimeMillis() + ".htm";
        article.setFilename(fileName);

      /*  try {*/
            articleHelper.saveInFileSystem(article);
            articleDAO.save(article);
       /* } catch (Exception e) {
            throw new ArticleException("Cannot store article");
        }*/
    }

    public void setArticleDAO(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    public void setArticleHelper(ArticleHelper articleHelper) {
        this.articleHelper = articleHelper;
    }
}
