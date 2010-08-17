package ua.com.myjava.model;

import javax.persistence.Column;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.search.annotations.Field;
@XmlAccessorType(XmlAccessType.NONE)
@javax.persistence.TableGenerator(name = "USER_GEN", table = "GENERATOR_TABLE", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_VALUE", pkColumnValue = "USER")
@Entity
@Embeddable
@Table(name="user", schema="myjava")
public class User {
 
	private int id;

	private String name;

	private String login;

	private String password;

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
