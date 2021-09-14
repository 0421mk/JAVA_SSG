package com.java.ssg.controller;

import java.util.Scanner;

import com.java.ssg.dto.Member;

public class MemberController extends Controller {
	
	// 일단 App에서 Member과 관련된 요소들을 싹다 호출해주고 오류 제거.
	
	private String command = null;
	Scanner sc = null;
	
	public MemberController(Scanner sc) {
		this.sc = sc;
	}
	
	public void doAction(String command) {
		this.command = command;
		
		if(command.equals("member join")) {
			doJoin();
		} else if(command.startsWith("member login")) {
			doLogin();
		} else {
			System.out.println("멤버 명령어가 존재하지 않습니다.");
			return;
		}
	}
	
	public void doJoin() {
		if (isLogined()) {
			System.out.println("로그아웃 후 이용해주세요.");
			return;
		}

		String loginId;
		String loginPw;
		String loginPwConfirm;

		System.out.printf("아이디 : ");
		loginId = sc.nextLine();

		if (getMemberByLoginId(loginId) != null) {
			System.out.println("이미 존재하는 아이디입니다.");
			return;
		}

		while (true) {
			System.out.printf("비밀번호 : ");
			loginPw = sc.nextLine();

			System.out.printf("비밀번호 확인 : ");
			loginPwConfirm = sc.nextLine();

			if (loginPw.equals(loginPwConfirm) == false) {
				System.out.println("비밀번호를 확인해주세요.");
				continue;
			}

			break;
		}

		System.out.printf("이름 : ");
		String userName = sc.nextLine();

		Member member = new Member(loginId, loginPw, userName);
		members.add(member);

		System.out.printf("환영합니다. %s님이 가입하셨습니다.\n", member.userName);
	}

	public void doLogin() {
		if (isLogined()) {
			System.out.println("로그아웃 후 이용해주세요.");
			return;
		}

		String loginId;
		String loginPw;

		System.out.printf("아이디 : ");
		loginId = sc.nextLine();

		System.out.printf("비밀번호 : ");
		loginPw = sc.nextLine();

		Member member = getMemberByLoginId(loginId);

		if (member == null) {
			System.out.println("아이디를 확인해주세요.");
			return;
		}

		if (member.loginPw.equals(loginPw) == false) {
			System.out.println("비밀번호를 확인해주세요.");
			return;
		}

		loginedMember = member;
		System.out.printf("로그인 성공! %s님 환영합니다!\n", loginedMember.userName);
	}

	public void doLogout() {
		if (isLogined() == false) {
			System.out.println("로그인 후 이용해주세요.");
			return;
		}

		System.out.printf("%s님이 로그아웃되었습니다.\n", loginedMember.userName);
		loginedMember = null;
	}

	public String getMemberNameByArticleId(int articleId) {
		String memberName = null;

		for (Member member : members) {
			if (member.id == articleId) {
				memberName = member.userName;
			}
		}

		return memberName;
	}

	public void makeTestMemberData() {
		System.out.println("테스트를 위한 멤버 데이터를 생성합니다.");

		members.add(new Member("admin", "admin", "관리자"));
		members.add(new Member("user2", "user2", "유저2"));
		members.add(new Member("user3", "user3", "유저3"));
	}

	public Member getMemberByLoginId(String loginId) {

		for (Member member : members) {
			if (member.loginId.equals(loginId)) {
				return member;
			}
		}

		return null;

	}

	public boolean isLogined() {
		return loginedMember != null;
	}
}
