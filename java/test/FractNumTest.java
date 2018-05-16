package com.example.test;

import java.util.Map.Entry;
import java.util.TreeMap;


public class FractNumTest {
	
	private class FractNum {
		
	}

	public static void main(String[] args){
		TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
		map.put(3, 2);
		map.put(2, 4);
		map.put(1, 6);
		for(Entry<Integer, Integer> entry : map.entrySet()){
			System.out.println(entry.getKey()+", "+entry.getValue());
		}
	}
	
	/* BigDecimal 이용한 분수구현. 너무 느리다.
	private class FractNum {
		private BigDecimal numerator;
		private BigDecimal denominator;
		
		public FractNum(int nume, int deno){
			numerator = new BigDecimal(nume);
			denominator = new BigDecimal(deno);
		}
		
		public FractNum(String key){
			String[] strs = key.split(",");
			numerator = new BigDecimal(strs[0]);
			denominator = new BigDecimal(strs[1]);
		}
		
		public void mult(int nume, int deno){
			numerator = numerator.multiply(new BigDecimal(nume));
			denominator = denominator.multiply(new BigDecimal(deno));
		}
		
		public void add(int nume, int deno){
			BigDecimal bignume = new BigDecimal(nume);
			BigDecimal bigdeno = new BigDecimal(deno);
			numerator = numerator.multiply(bigdeno).add(bignume.multiply(denominator));
			denominator = denominator.multiply(bigdeno);
		}
		
		public void add(FractNum fn){
			numerator = numerator.multiply(fn.denominator).add(fn.numerator.multiply(denominator));
			denominator = denominator.multiply(fn.denominator);
		}
		
		public String getValue(){
			return numerator.divide(denominator, 8, BigDecimal.ROUND_HALF_UP).toString();
		}
		
		public String getKey(){
			return numerator+","+denominator;
		}
	}
	*/
}
