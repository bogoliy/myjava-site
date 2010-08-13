package ua.com.myjava.persist;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.springframework.orm.hibernate3.HibernateCallback;
import ua.com.myjava.model.Article;
import ua.com.myjava.model.Comment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: abogoley
 * Date: 09.08.2010
 * Time: 21:12:07
 */
public class CommentDAO extends GenericDAO<Comment, Long> {
    public CommentDAO() {
        super(Comment.class);
    }

    public List<Comment> getComments(int articleId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Comment.class);
        criteria.add(Expression.eq("articleId", articleId));
        return (List<Comment>) getHibernateTemplate().findByCriteria(criteria);
    }
}
