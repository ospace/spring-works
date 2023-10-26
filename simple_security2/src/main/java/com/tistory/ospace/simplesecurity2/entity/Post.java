package com.tistory.ospace.simplesecurity2.entity;

import org.apache.ibatis.type.Alias;

@Alias("Post")
public class Post {
	private Integer id;
	private String title;
	private String content;
	
	public static Post of(Integer id) {
		Post ret = new Post();
		ret.setId(id);
		
		return ret;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String toString() {
		return "id["+id+"] title["+title+"] content["+content+"]";
	}
}
