package com.java.ssg;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.java.ssg.controller.ArticleController;
import com.java.ssg.controller.Controller;
import com.java.ssg.controller.MemberController;
import com.java.ssg.util.Util;

public class App {
	
	public void start() {
		System.out.println("=== 프로그램 시작 ===");

		Scanner sc = new Scanner(System.in);
		
		ArticleController articleController = new ArticleController(sc);
		MemberController memberController = new MemberController(sc);
		Controller controller = null;

		articleController.makeTestArticleData();
		memberController.makeTestMemberData();

		while (true) {
			System.out.printf("명령어 입력 : ");
			String command = sc.nextLine();

			String[] commandBits = command.split(" ");
			String controllerName = commandBits[0];

			if (controllerName.equals("article")) {
				controller = articleController;
			} else if (controllerName.equals("member")) {
				controller = memberController;
			} else {
				System.out.println("존재하지 않는 명령어입니다.");
				continue;
			}

			if (command.equals("system exit")) {
				System.out.println("프로그램을 종료합니다.");
				break;
			}

			controller.doAction(command);
		}

		sc.close();
		System.out.println("=== 프로그램 종료 ===");

	}

}
