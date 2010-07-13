package ua.com.myjava.service.model;

import ua.com.myjava.model.Article;

public class ArticleWraper {
	private int id;
	private String title;
	private String text;

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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public ArticleWraper() {
	}

	public ArticleWraper(Article article) {
		this.id = article.getId();
		this.title = article.getTitle();
		this.text = article.getText();
	}
}
