package com.java.ssg.dto;

import com.java.ssg.util.Util;

public class Article {
	public static int indexId = 0;
	public int id;
	public String title;
	public String content;
	public String date;
	public int hit = 0;

	public Article(String title, String content) {
		indexId++;
		id = indexId;
		this.title = title;
		this.content = content;
		this.date = Util.getNowDateStr();
	}
	
	public void increseHit() {
		hit++;
	}
}
