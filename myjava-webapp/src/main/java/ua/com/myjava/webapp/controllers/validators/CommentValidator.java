package ua.com.myjava.webapp.controllers.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.com.myjava.model.Comment;

/**
 * User: root
 * Date: 25.07.2010
 * Time: 15:45:13
 */
public class CommentValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {
        return clazz.equals(Comment.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Comment comment = (Comment) target;
        ValidationUtils.rejectIfEmpty(errors, "name", "addComment.errors.nameEmpty");
        ValidationUtils.rejectIfEmpty(errors, "email", "addComment.errors.emailEmpty");
        ValidationUtils.rejectIfEmpty(errors, "text", "addComment.errors.textEmpty");

    }
}
