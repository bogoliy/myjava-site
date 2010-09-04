package ua.com.myjava.webapp.controllers.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.com.myjava.model.Article;

/**
 * User: abogoley
 * Date: 09.08.2010
 */
public class ArticleValidator implements Validator {
    @Override
    public boolean supports(Class clazz) {
        return clazz.equals(Article.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Article article = (Article) target;
        ValidationUtils.rejectIfEmpty(errors, "title", "addArticle.errors.titleEmpty");
        ValidationUtils.rejectIfEmpty(errors, "text", "addArticle.errors.articleEmpty");
    }
}