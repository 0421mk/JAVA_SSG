package com.java.ssg;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		System.out.printf("문자열 입력 : ");
		String command = sc.nextLine();
		
		System.out.printf("정수 입력 : ");
		int intCommand = sc.nextInt();
		
		System.out.printf("입력된 문자열 : %s\n", command);
		System.out.printf("입력된 정수 : %d\n", intCommand);

	}

}
