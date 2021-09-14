package com.java.ssg;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.java.ssg.dto.Article;
import com.java.ssg.dto.Member;
import com.java.ssg.util.Util;

public class App {
	List<Article> articles;
	List<Member> members;
	static Member loginedMember = null;

	App() {
		articles = new ArrayList<>();
		members = new ArrayList<>();
	}

	public void start() {
		System.out.println("=== 프로그램 시작 ===");

		Scanner sc = new Scanner(System.in);

		makeTestArticleData();
		makeTestMemberData();

		while (true) {
			System.out.printf("명령어 입력 : ");
			String command = sc.nextLine();

			if (command.equals("article write")) {

				if (isLogined() == false) {
					System.out.println("로그인 후 이용해주세요.");
					continue;
				}

				System.out.printf("제목 : ");
				String title = sc.nextLine();

				System.out.printf("내용 : ");
				String content = sc.nextLine();

				Article article = new Article(title, content, loginedMember.id);
				articles.add(article);

				System.out.printf("%d번 글이 생성되었습니다.\n", article.id);

			} else if (command.equals("member join")) {

				if (isLogined()) {
					System.out.println("로그아웃 후 이용해주세요.");
					continue;
				}

				String loginId;
				String loginPw;
				String loginPwConfirm;

				System.out.printf("아이디 : ");
				loginId = sc.nextLine();

				if (getMemberByLoginId(loginId) != null) {
					System.out.println("이미 존재하는 아이디입니다.");
					continue;
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

			} else if (command.equals("member login")) {

				if (isLogined()) {
					System.out.println("로그아웃 후 이용해주세요.");
					continue;
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
					continue;
				}

				if (member.loginPw.equals(loginPw) == false) {
					System.out.println("비밀번호를 확인해주세요.");
					continue;
				}

				loginedMember = member;
				System.out.printf("로그인 성공! %s님 환영합니다!\n", loginedMember.userName);

			} else if (command.equals("member logout")) {

				if (isLogined() == false) {
					System.out.println("로그인 후 이용해주세요.");
					continue;
				}

				System.out.printf("%s님이 로그아웃되었습니다.\n", loginedMember.userName);
				loginedMember = null;

			} else if (command.startsWith("article list")) {

				command = command.substring("article list".length()).trim();
				List<Article> printArticle = null;

				if (articles.size() == 0) {
					System.out.println("글이 존재하지 않습니다.");
					continue;
				}

				if (command.length() == 0) {

					printArticle = articles;

				} else {

					List<Article> searchArticles = new ArrayList<>();

					for (Article article : articles) {
						if (article.title.contains(command)) {
							searchArticles.add(article);
						}
					}

					if (searchArticles.size() == 0) {
						System.out.println("요청하신 검색 결과가 존재하지 않습니다.");
						continue;
					}

					printArticle = searchArticles;

				}

				for (Article article : printArticle) {
					printList(article);
				}

			} else if (command.startsWith("article modify")) {

				if (isLogined() == false) {
					System.out.println("로그인 후 이용해주세요.");
					continue;
				}

				command = command.substring("article modify".length()).trim();

				if (Util.isNumeric(command) == false) {
					System.out.println("한칸 띄고 숫자만 입력해주세요.");
					continue;
				}

				int commandNum = Integer.parseInt(command);
				Article foundArticle = getArticleById(commandNum);

				if (foundArticle == null) {
					System.out.printf("%d번 게시물은 존재하지 않습니다.\n", commandNum);
					continue;
				}

				System.out.printf("제목 : ");
				String title = sc.nextLine();

				System.out.printf("내용 : ");
				String content = sc.nextLine();

				String date = Util.getNowDateStr();

				foundArticle.title = title;
				foundArticle.content = content;
				foundArticle.date = date;

				System.out.println("게시물 수정을 완료했습니다.");

			} else if (command.startsWith("article detail")) {

				command = command.substring("article detail".length()).trim();

				if (Util.isNumeric(command) == false) {
					System.out.println("한칸 띄고 숫자만 입력해주세요.");
					continue;
				}

				int commandNum = Integer.parseInt(command);
				Article foundArticle = getArticleById(commandNum);

				if (foundArticle == null) {
					System.out.printf("%d번 게시물은 존재하지 않습니다.\n", commandNum);
					continue;
				}

				foundArticle.increseHit();

				printDetail(foundArticle);

			} else if (command.startsWith("article delete")) {

				if (isLogined() == false) {
					System.out.println("로그인 후 이용해주세요.");
					continue;
				}

				command = command.substring("article delete".length()).trim();

				if (Util.isNumeric(command) == false) {
					System.out.println("한칸 띄고 숫자만 입력해주세요.");
					continue;
				}

				int commandNum = Integer.parseInt(command);
				Article foundArticle = getArticleById(commandNum);

				if (foundArticle == null) {
					System.out.printf("%d번 게시물은 존재하지 않습니다.\n", commandNum);
					continue;
				}

				articles.remove(foundArticle);
				System.out.printf("%d번 게시물이 삭제되었습니다.\n", foundArticle.id);

			} else if (command.equals("system exit")) {
				System.out.println("system exit");
				break;
			} else {
				System.out.println("존재하지 않는 명령어입니다.");
			}
		}

		sc.close();
		System.out.println("=== 프로그램 종료 ===");

	}

	public void makeTestArticleData() {
		System.out.println("테스트를 위한 게시물 데이터를 생성합니다.");

		articles.add(new Article("제목1", "내용1", 1));
		articles.add(new Article("제목2", "내용2", 2));
		articles.add(new Article("제목3", "내용3", 3));
	}

	public void makeTestMemberData() {
		System.out.println("테스트를 위한 멤버 데이터를 생성합니다.");

		members.add(new Member("admin", "admin", "관리자"));
		members.add(new Member("user2", "user2", "유저2"));
		members.add(new Member("user3", "user3", "유저3"));
	}

	public Article getArticleById(int id) {

		for (Article article : articles) {

			if (article.id == id) {
				return article;
			}

		}

		return null;

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

	public void printList(Article article) {

		System.out.printf("글 ID : %d\n", article.id);
		System.out.printf("제목 : %s\n", article.title);
		System.out.printf("날짜 : %s\n", article.date);
		System.out.printf("조회수 : %d\n", article.hit);
		System.out.printf("작성자 : %s\n", article.memberId);
		System.out.println("===========");

	}
	
	public void printDetail(Article article) {

		System.out.printf("글 ID : %d\n", article.id);
		System.out.printf("제목 : %s\n", article.title);
		System.out.printf("내용 : %s\n", article.content);
		System.out.printf("날짜 : %s\n", article.date);
		System.out.printf("조회수 : %d\n", article.hit);
		System.out.printf("작성자 : %s\n", article.memberId);
		System.out.println("===========");

	}

}
