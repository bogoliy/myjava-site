package ua.com.myjava.service;

import ua.com.myjava.model.Article;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * User: abogoley
 * Date: 17.08.2010
 * Time: 22:34:16
 */
@WebService
public interface ArticleService {
    public void addArticle(Article article) ;
}
