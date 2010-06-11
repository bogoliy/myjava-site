package ua.com.myjava.model;

import javax.persistence.Column;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.search.annotations.Field;

@javax.persistence.TableGenerator(name = "USER_GEN", table = "GENERATOR_TABLE", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_VALUE", pkColumnValue = "USER")
@Entity
@Embeddable
public class User {
 
	public int id;
 
	public String name;      


	public String login;

	public String password;

	@Id    
	@Column(name = "us_id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "USER_GEN")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "us_name")
	@Field
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "us_login")
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Column(name = "us_password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
