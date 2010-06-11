package ua.com.myjava.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CascadeType;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;

@javax.persistence.TableGenerator(name = "OP_GEN", table = "GENERATOR_TABLE", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_VALUE", pkColumnValue = "OPINION")
@Entity
@Embeddable
public class Opinion {

	private int id;

	private String text;

	@Id
	@Column(name = "op_id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "OP_GEN")
	public int getId() {
		return id;   
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "op_text")
	@Field
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	@Column(name = "op_date")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


	private Date date;


	private User user;

	@ManyToOne
	@JoinColumn(name = "us_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private Article article;

	@ManyToOne(cascade = { javax.persistence.CascadeType.ALL })
	@JoinColumn(name = "ar_id")
	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

}
