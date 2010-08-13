package ua.com.myjava.webapp.controllers.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.com.myjava.model.Article;
import ua.com.myjava.model.Comment;

/**
 * User: root
 * Date: 10.08.2010
 * Time: 21:46:12
 */
/**
 * User: abogoley
 * Date: 09.08.2010
 */
public class AddCommentValidator implements Validator {
    @Override
    public boolean supports(Class clazz) {
        return clazz.equals(Comment.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Article article = (Article) target;
        ValidationUtils.rejectIfEmpty(errors, "name", "addComment.errors.nameEmpty");
        ValidationUtils.rejectIfEmpty(errors, "email", "addComment.errors.emailEmpty");
        ValidationUtils.rejectIfEmpty(errors, "text", "addComment.errors.textEmpty");
    }
}