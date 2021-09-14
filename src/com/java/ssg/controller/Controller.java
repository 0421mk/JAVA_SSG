package com.java.ssg.controller;

import java.util.ArrayList;
import java.util.List;

import com.java.ssg.dto.Article;
import com.java.ssg.dto.Member;

public abstract class Controller {
	List<Article> articles;
	List<Member> members;
	static Member loginedMember = null;

	Controller() {
		articles = new ArrayList<>();
		members = new ArrayList<>();
	}
	
	// 공통되는 요소들은 Controller에 작성해준다.
	
	public abstract void doAction(String command);
}
