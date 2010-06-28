package ua.com.myjava.webapp.security;

import java.sql.SQLException;
import java.util.List;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;


/**
 * Extends the baseline Spring Security JdbcDaoImpl and implements change password functionality.
 */
public class CustomJdbcDaoImpl extends JdbcDaoImpl implements IChangePassword {
	
	public void changePassword(String username, String password) {
		getJdbcTemplate().update(
			"UPDATE USER SET USER_PASSWORD = ? WHERE USER_LOGIN = ?",	password, username
		);
	}

}
