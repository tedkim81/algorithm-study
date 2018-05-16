package com.example.test;

import java.math.BigDecimal;
import java.util.List;

public class PrimeTest {
	
	private void prime(BigDecimal n){
		
	}

	public static void main(String[] args){
//		new PrimeTest().prime(new BigDecimal("100"));
		
		int cnt = Sieve.sieve_of_eratosthenes_count(200000000);
		System.out.println("list: "+cnt);
	}
}
