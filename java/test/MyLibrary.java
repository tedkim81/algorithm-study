package com.example.test;

public class MyLibrary {
	
	private int gcd(int a, int b){
		while(a > 0 && b > 0){
			if(a > b) a %= b;
			else b %= a;
		}
		return a+b;
	}
	
	public static void main(String[] args){
		int result = new MyLibrary().gcd(16, 24);
		System.out.println("result: "+result);
	}
}
