package com.java.ssg.dto;

import com.java.ssg.util.Util;

public class Member {
	public static int indexId = 0;
	public int id;
	public String loginId;
	public String loginPw;
	public String userName;
	public String date;

	public Member(String loginId, String loginPw, String userName) {
		indexId++;
		id = indexId;
		this.loginId = loginId;
		this.loginPw = loginPw;
		this.userName = userName;
		this.date = Util.getNowDateStr();
	}
}
