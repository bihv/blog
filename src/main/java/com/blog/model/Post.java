package com.blog.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "posts")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;

	String title;

	@ManyToOne
	Category category;

	@ManyToOne
	User author;

	@Lob
	String content;

	Date timePost;

	Date lastUpdate;

	public Post(int id, String title, Category category, User author, String content, Date timePost, Date lastUpdate) {
		super();
		this.id = id;
		this.title = title;
		this.category = category;
		this.author = author;
		this.content = content;
		this.timePost = timePost;
		this.lastUpdate = lastUpdate;
	}

	public Post() {
		super();
	}

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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getTimePost() {
		return timePost;
	}

	public void setTimePost(Date timePost) {
		this.timePost = timePost;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

}
