package com.java.ssg.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.java.ssg.dto.Article;
import com.java.ssg.util.Util;

public class ArticleController extends Controller {
	
	// 일단 App에서 Article과 관련된 요소들을 싹다 호출해주고 오류 제거.
	
	private String command = null;
	Scanner sc = null;
	
	public ArticleController(Scanner sc) {
		this.sc = sc;
	}
	
	private MemberController memberController = new MemberController(sc);
	
	public void doAction(String command) {
		this.command = command;
		
		if(command.equals("article write")) {
			doWrite();
		} else if(command.startsWith("article modify")) {
			doModify();
		} else if(command.startsWith("article delete")) {
			doDelete();
		} else if(command.startsWith("article list")) {
			showList();
		} else if(command.startsWith("article detail")) {
			showDetail();
		} else {
			System.out.println("게시물 명령어가 존재하지 않습니다.");
			return;
		}
	}

	public void doWrite() {
		if (memberController.isLogined() == false) {
			System.out.println("로그인 후 이용해주세요.");
			return;
		}

		System.out.printf("제목 : ");
		String title = sc.nextLine();

		System.out.printf("내용 : ");
		String content = sc.nextLine();

		Article article = new Article(title, content, loginedMember.id);
		articles.add(article);

		System.out.printf("%d번 글이 생성되었습니다.\n", article.id);
	}

	public void showList() {
		command = command.substring("article list".length()).trim();
		List<Article> printArticle = null;

		if (articles.size() == 0) {
			System.out.println("글이 존재하지 않습니다.");
			return;
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
				return;
			}

			printArticle = searchArticles;

		}

		for (Article article : printArticle) {
			printList(article);
		}
	}

	public void doModify() {
		if (memberController.isLogined() == false) {
			System.out.println("로그인 후 이용해주세요.");
			return;
		}

		command = command.substring("article modify".length()).trim();

		if (Util.isNumeric(command) == false) {
			System.out.println("한칸 띄고 숫자만 입력해주세요.");
			return;
		}

		int commandNum = Integer.parseInt(command);
		Article foundArticle = getArticleById(commandNum);

		if (foundArticle == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", commandNum);
			return;
		}

		if (foundArticle.memberId != loginedMember.id) {
			System.out.printf("권한이 없습니다.\n");
			return;
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
	}

	public void showDetail() {
		command = command.substring("article detail".length()).trim();

		if (Util.isNumeric(command) == false) {
			System.out.println("한칸 띄고 숫자만 입력해주세요.");
			return;
		}

		int commandNum = Integer.parseInt(command);
		Article foundArticle = getArticleById(commandNum);

		if (foundArticle == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", commandNum);
			return;
		}

		foundArticle.increseHit();

		printDetail(foundArticle);
	}

	public void doDelete() {
		if (memberController.isLogined() == false) {
			System.out.println("로그인 후 이용해주세요.");
			return;
		}

		command = command.substring("article delete".length()).trim();

		if (Util.isNumeric(command) == false) {
			System.out.println("한칸 띄고 숫자만 입력해주세요.");
			return;
		}

		int commandNum = Integer.parseInt(command);
		Article foundArticle = getArticleById(commandNum);

		if (foundArticle == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", commandNum);
			return;
		}

		if (foundArticle.memberId != loginedMember.id) {
			System.out.printf("권한이 없습니다.\n");
			return;
		}

		articles.remove(foundArticle);
		System.out.printf("%d번 게시물이 삭제되었습니다.\n", foundArticle.id);
	}

	public void makeTestArticleData() {
		System.out.println("테스트를 위한 게시물 데이터를 생성합니다.");

		articles.add(new Article("제목1", "내용1", 1));
		articles.add(new Article("제목2", "내용2", 2));
		articles.add(new Article("제목3", "내용3", 3));
	}

	public Article getArticleById(int id) {

		for (Article article : articles) {

			if (article.id == id) {
				return article;
			}

		}

		return null;

	}

	public void printList(Article article) {

		System.out.printf("글 ID : %d\n", article.id);
		System.out.printf("제목 : %s\n", article.title);
		System.out.printf("날짜 : %s\n", article.date);
		System.out.printf("조회수 : %d\n", article.hit);
		System.out.printf("작성자 : %s\n", memberController.getMemberNameByArticleId(article.memberId));
		System.out.println("===========");

	}

	public void printDetail(Article article) {

		System.out.printf("글 ID : %d\n", article.id);
		System.out.printf("제목 : %s\n", article.title);
		System.out.printf("내용 : %s\n", article.content);
		System.out.printf("날짜 : %s\n", article.date);
		System.out.printf("조회수 : %d\n", article.hit);
		System.out.printf("작성자 : %s\n", memberController.getMemberNameByArticleId(article.memberId));
		System.out.println("===========");

	}

}
