package com.java.ssg;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("=== 프로그램 시작 ===");

		Scanner sc = new Scanner(System.in);
		int articleId = 0;

		while (true) {
			System.out.printf("명령어 입력 : ");
			String command = sc.nextLine();

			if (command.equals("article write")) {
				
				articleId++;
				
				System.out.printf("제목 : ");
				String title = sc.nextLine();
				
				System.out.printf("내용 : ");
				String content = sc.nextLine();
				
				System.out.printf("%d번 글이 생성되었습니다.\n", articleId);
				
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

}
