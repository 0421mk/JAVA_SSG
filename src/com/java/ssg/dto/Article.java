package com.java.ssg.dto;

import com.java.ssg.util.Util;

public class Article {
	public static int indexId = 0;
	public int id;
	public String title;
	public String content;
	public String date;
	public int hit = 0;
	public int memberId;

	public Article(String title, String content, int memberId) {
		indexId++;
		id = indexId;
		this.title = title;
		this.content = content;
		this.date = Util.getNowDateStr();
		this.memberId = memberId;
	}
	
	public void increseHit() {
		hit++;
	}
}
