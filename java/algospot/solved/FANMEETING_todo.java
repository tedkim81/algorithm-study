package com.teuskim.solved;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 시간초과 발생
 * 1. 자바로 구현하니 기본적으로 느린데다, 
 * 2. 문제를 분할할때 subList를 그대로 사용할 수가 없어서 new ArrayList 로 생성하는 과정이 O(n) 만큼 더 시간을 소모한다.
 * 
 * 방법을 다시 찾아보자.
 */
public class FANMEETING_todo {
	
	public void goodluck(){
		Scanner sc = new Scanner(System.in);
		int cases = sc.nextInt();
		while(cases-- > 0) {
			
			String members = sc.next();
			String fans = sc.next();
			String result = ""+getCnt(members, fans);
			System.out.println(result);
		}
	}
	
	private int getCnt(String members, String fans){
		int N = members.length();
		int M = fans.length();
		List<Integer> A = new ArrayList<Integer>(N);
		List<Integer> B = new ArrayList<Integer>(M);
		for(int i=0; i<N; i++) A.add(members.charAt(i)=='M' ? 1 : 0);
		for(int i=0; i<M; i++) B.add(fans.charAt(M-i-1)=='M' ? 1 : 0);
		List<Integer> C = karatsuba(A, B);
		int cnt = 0;
		for(int i=N-1; i<M; i++)
			if(C.get(i) == 0)
				cnt++;
		return cnt;
	}
	
	private void normalize(List<Integer> num){
		num.add(0);
		for(int i=0; i<num.size()-1; i++){
			int numi = num.get(i);
			if(numi < 0){
				int borrow = (Math.abs(numi) + 9) / 10;
				num.set(i+1, num.get(i+1)-borrow);
				num.set(i, numi+(borrow*10));
			}
			else{
				num.set(i+1, num.get(i+1)+(numi/10));
				num.set(i, numi%10);
			}
		}
		if(num.size() > 1 && num.get(num.size()-1) == 0) num.remove(num.size()-1);
	}
	
	private List<Integer> multiply(List<Integer> a, List<Integer> b){
		int csize = a.size()+b.size()-1;
		List<Integer> c = new ArrayList<Integer>(csize);
		for(int i=0; i<csize; i++) c.add(0);
		for(int i=0; i<a.size(); i++)
			for(int j=0; j<b.size(); j++)
				c.set(i+j, c.get(i+j)+(a.get(i)*b.get(j)));
		normalize(c);
		return c;
	}
	
	private void addTo(List<Integer> a, List<Integer> b, int k){
		int totalSize = Math.max(a.size(), b.size()+k);
		int addCnt = totalSize-a.size()+1;
		for(int i=0; i<addCnt; i++) a.add(0);
		for(int i=0; i<b.size(); i++) a.set(i+k, a.get(i+k)+b.get(i));
		normalize(a);
		if(a.size() > 1 && a.get(a.size()-1) == 0) a.remove(a.size()-1);
	}
	
	private void subFrom(List<Integer> a, List<Integer> b){
		if(a.size() < b.size()){
			int addCnt = b.size() - a.size();
			for(int i=0; i<addCnt; i++) a.add(0);
		}
		for(int i=0; i<b.size(); i++){
			a.set(i, a.get(i)-b.get(i));
		}
		normalize(a);
		if(a.size() > 1 && a.get(a.size()-1) == 0) a.remove(a.size()-1);
	}
	
	private List<Integer> karatsuba(List<Integer> a, List<Integer> b){
		int an = a.size();  int bn = b.size();
		if(an < bn) return karatsuba(b, a);
		if(an == 0 || bn == 0) return new ArrayList<Integer>();
		if(an <= 50) return multiply(a, b);
		
		int half = an / 2;
		List<Integer> a0 = new ArrayList<Integer>(a.subList(0, half));
		List<Integer> a1 = new ArrayList<Integer>(a.subList(half, a.size()));
		List<Integer> b0 = new ArrayList<Integer>(b.subList(0, Math.min(b.size(), half)));
		List<Integer> b1 = new ArrayList<Integer>(b.subList(Math.min(b.size(), half), b.size()));
		
		List<Integer> z2 = karatsuba(a1, b1);
		List<Integer> z0 = karatsuba(a0, b0);
		addTo(a0, a1, 0); addTo(b0, b1, 0);
		List<Integer> z1 = karatsuba(a0, b0);
		subFrom(z1, z0); subFrom(z1, z2);
		
		List<Integer> ret = new ArrayList<Integer>();
		addTo(ret, z0, 0); addTo(ret, z1, half); addTo(ret, z2, half+half);
		return ret;
	}
	
	public static void main(String[] args) {
		new FANMEETING_todo().goodluck();
	}
}