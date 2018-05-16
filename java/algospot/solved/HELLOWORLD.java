package com.teuskim.solved;
import java.util.Scanner;

public class HELLOWORLD {
	
	public void goodluck(){
		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		while(cases-- > 0) {
			String name = sc.next();
			System.out.println("Hello, " + name + "!");
		}
	}
	
	public static void main(String[] args) {
		new HELLOWORLD().goodluck();
	}
}