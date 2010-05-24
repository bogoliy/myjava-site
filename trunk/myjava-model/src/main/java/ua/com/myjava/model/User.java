package ua.com.myjava.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@javax.persistence.TableGenerator(name = "USER_GEN", table = "GENERATOR_TABLE", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_VALUE", pkColumnValue = "USER")
@Entity
public class User {

	@Id
	@Column(name = "us_id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "USER_GEN")
	public int id;
 
	@Column(name = "us_name")
	public String name;

	@Column(name = "us_login")
	public String login;

	@Column(name = "us_password")
	public String password;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
