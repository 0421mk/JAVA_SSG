package com.java.ssg;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.java.ssg.util.Util;

public class Main {
	static List<Article> articles;
	
	static {
		articles = new ArrayList<>();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("=== 프로그램 시작 ===");

		Scanner sc = new Scanner(System.in);
		
		makeTestData();

		while (true) {
			System.out.printf("명령어 입력 : ");
			String command = sc.nextLine();

			if (command.equals("article write")) {

				System.out.printf("제목 : ");
				String title = sc.nextLine();

				System.out.printf("내용 : ");
				String content = sc.nextLine();

				Article article = new Article(title, content);
				articles.add(article);

				System.out.printf("%d번 글이 생성되었습니다.\n", article.id);

			} else if (command.equals("article list")) {
				
				if(articles.size() == 0) {
					System.out.println("글이 존재하지 않습니다.");
					continue;
				}

				for (Article article : articles) {
					System.out.printf("글 ID : %d\n", article.id);
					System.out.printf("제목 : %s\n", article.title);
					System.out.printf("날짜 : %s\n", article.date);
					System.out.printf("조회수 : %d\n", article.hit);
					System.out.println("===========");
				}

			} else if (command.startsWith("article modify")) {

				command = command.substring("article modify".length()).trim();

				if (isNumeric(command) == false) {
					System.out.println("한칸 띄고 숫자만 입력해주세요.");
					continue;
				}

				int commandNum = Integer.parseInt(command);
				Article foundArticle = null;

				for (Article article : articles) {

					if (article.id == commandNum) {
						foundArticle = article;
						break;
					}

				}

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

				if (isNumeric(command) == false) {
					System.out.println("한칸 띄고 숫자만 입력해주세요.");
					continue;
				}

				int commandNum = Integer.parseInt(command);
				Article foundArticle = null;

				for (Article article : articles) {

					if (article.id == commandNum) {
						foundArticle = article;
						break;
					}

				}

				if (foundArticle == null) {
					System.out.printf("%d번 게시물은 존재하지 않습니다.\n", commandNum);
					continue;
				}
				
				foundArticle.increseHit();

				System.out.printf("글 ID : %d\n", foundArticle.id);
				System.out.printf("제목 : %s\n", foundArticle.title);
				System.out.printf("내용 : %s\n", foundArticle.content);
				System.out.printf("날짜 : %s\n", foundArticle.date);
				System.out.printf("조회수 : %d\n", foundArticle.hit);
				System.out.println("===========");

			} else if (command.startsWith("article delete")) {

				command = command.substring("article delete".length()).trim();

				if (isNumeric(command) == false) {
					System.out.println("한칸 띄고 숫자만 입력해주세요.");
					continue;
				}

				int commandNum = Integer.parseInt(command);
				Article foundArticle = null;

				for (Article article : articles) {

					if (article.id == commandNum) {
						foundArticle = article;
						break;
					}

				}

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

	private static boolean isNumeric(String str) {
		boolean check = str.matches("-?\\d+");

		return check;
	}
	
	private static void makeTestData() {
		System.out.println("테스트를 위한 데이터를 생성합니다.");
		
		articles.add(new Article("제목1", "내용1"));
		articles.add(new Article("제목2", "내용2"));
		articles.add(new Article("제목3", "내용3"));
	}
}

class Article {
	static int indexId = 0;
	int id;
	String title;
	String content;
	String date;
	int hit = 0;

	Article(String title, String content) {
		indexId++;
		id = indexId;
		this.title = title;
		this.content = content;
		this.date = Util.getNowDateStr();
	}
	
	void increseHit() {
		hit++;
	}
}