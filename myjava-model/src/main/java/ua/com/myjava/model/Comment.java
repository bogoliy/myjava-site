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
import javax.persistence.Table;

import org.hibernate.annotations.CascadeType;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;

@javax.persistence.TableGenerator(name = "OP_GEN", table = "GENERATOR_TABLE", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_VALUE", pkColumnValue = "OPINION")
@Entity
@Embeddable
@Table(name = "comment", schema = "myjava")
public class Comment {

    private int id;

    private String text;

    private Date date;

    // private Article article;

    private String username;

    private String email;

    private int articleId;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "co_username")
    public String getUsername() {
        return username;
    }

    @Column(name = "co_email")
    public String getEmail() {
        return email;
    }

    @Id
    @Column(name = "co_id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "OP_GEN")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "co_text")
    @Field
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Column(name = "co_date")
    public Date getDate() {
        return date;
    }


    public void setDate(Date date) {
        this.date = date;
    }

/*
    @ManyToOne(cascade = {javax.persistence.CascadeType.ALL})
    @JoinColumn(name = "ar_id")
    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
*/

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    @Column(name = "ar_id")
    public int getArticleId() {
        return articleId;
    }
}
