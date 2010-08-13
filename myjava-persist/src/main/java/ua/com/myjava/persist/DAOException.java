package ua.com.myjava.persist;

import org.springframework.dao.DataAccessException;

/**
 * User: abogoley
 * Date: 10.08.2010
 * Time: 21:27:37
 */
public class DAOException extends Exception {
    public DAOException(String s, DataAccessException e) {
        super(s, e);
    }
}
