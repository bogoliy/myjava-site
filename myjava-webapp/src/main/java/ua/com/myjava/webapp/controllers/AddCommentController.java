package ua.com.myjava.webapp.controllers;

import com.thoughtworks.xstream.XStream;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import ua.com.myjava.model.Article;
import ua.com.myjava.model.Comment;
import ua.com.myjava.persist.CommentDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.logging.Logger;

/**
 * User: root
 * Date: 09.08.2010
 * Time: 21:09:36
 */
public class AddCommentController implements Controller {
    Logger log = Logger.getLogger(AddCommentController.class.toString());
    private CommentDAO commentDAO;
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String TEXT = "text";
    private static final String ARTICLEID = "articleId";


    public void setCommentDAO(CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }

    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        log.info("Returning ajax view");
        Comment comment = getComment(httpServletRequest);
        comment = commentDAO.merge(comment);
        XStream stream = new XStream();
        stream.processAnnotations(Comment.class);
        stream.toXML(comment);
        return new ModelAndView("ajax_comment", "comment", comment);
    }

    private Comment getComment(HttpServletRequest httpServletRequest) {
        String name = httpServletRequest.getParameter(NAME);
        String email = httpServletRequest.getParameter(EMAIL);
        String text = httpServletRequest.getParameter(TEXT);
        int articleId = Integer.parseInt(httpServletRequest.getParameter(ARTICLEID));
        Comment comment = new Comment();
        comment.setUsername(name);
        comment.setDate(new Date());
        comment.setEmail(email);
        comment.setText(text);
        comment.setArticleId(articleId);
        return comment;
    }
}

