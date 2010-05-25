package ua.com.myjava.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@javax.persistence.TableGenerator(name = "ART_GEN", table = "GENERATOR_TABLE", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_VALUE", pkColumnValue = "ARTICLE")
@Entity
public class Article {
	@Id 
	@Column(name = "ar_id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ART_GEN")
	private int id;

	@Column(name = "ar_title")
	private String title;

	@Column(name = "ar_filename")
	private String filename;

	@Column(name = "ar_date")
	private Date date;

	@Column(name = "ar_text")
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	@ManyToOne
	private User user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
