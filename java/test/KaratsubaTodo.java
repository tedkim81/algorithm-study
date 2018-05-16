package com.example.test;

/**
 * 카라츠바의 빠른 곱셈을 자바로 구현했으나 속도가 너무 느리다.
 * ArrayList 로 최초 구현했다가 subList 사용시 ConcurrentModificationException 이 발생했고, 이 때문에 new ArrayList(subList) 로 생성해서 사용했다.
 * 그랬더니 리스트 크기가 100000 정도 되었을때 약 20초 정도 걸렸다.
 * 개선해 보기 위해 MyList 를 만들었지만 속도는 오히려 더 늦어졌다.
 * 
 * TODO: 자바로 카라츠바의 빠른 곱셈을 100000 정도의 크기까지 적당한 시간에 수행할 수 있도록 개선해 보자.
 */
public class KaratsubaTodo {
	
	// 자릿수 올림 처리
	private void normalize(MyList num){
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
		num.removeLastIfZero();
	}
	
	// 긴 자연수 곱 ( O(n^2) 의 일반방식 )
	private MyList multiply(MyList a, MyList b){
		MyList c = new MyList();
		for(int i=0; i<a.size(); i++)
			for(int j=0; j<b.size(); j++)
				c.set(i+j, c.get(i+j)+(a.get(i)*b.get(j)));
		normalize(c);
		return c;
	}
	
	// a += b*(10^k) 구현
	private void addTo(MyList a, MyList b, int k){
		for(int i=0; i<b.size(); i++) a.set(i+k, a.get(i+k)+b.get(i));
		normalize(a);
		a.removeLastIfZero();
	}
	
	// a -= b 구현
	private void subFrom(MyList a, MyList b){
		if(a.size() < b.size()){
			int addCnt = b.size() - a.size();
			for(int i=0; i<addCnt; i++) a.add(0);
		}
		for(int i=0; i<b.size(); i++){
			a.set(i, a.get(i)-b.get(i));
		}
		normalize(a);
		a.removeLastIfZero();
	}
	
	// 긴 자연수 곱 ( karatsuba 의 빠른 곱셈 )
	public static int cnt = 0;
	private MyList karatsuba(MyList a, MyList b){
		cnt++;
		int an = a.size();  int bn = b.size();
		if(an < bn) return karatsuba(b, a);
		if(an == 0 || bn == 0) return new MyList();
		if(an <= 1) return multiply(a, b);
		
		int half = an / 2;
		MyList a0 = a.subList(a.start, a.start+half);
		MyList a1 = a.subList(a.start+half, a.end);
		MyList b0 = b.subList(b.start, b.start+Math.min(b.size(), half));
		MyList b1 = b.subList(b.start+Math.min(b.size(), half), b.end);
		
		MyList z2 = karatsuba(a1, b1);
		MyList z0 = karatsuba(a0, b0);
		addTo(a0, a1, 0); addTo(b0, b1, 0);
		MyList z1 = karatsuba(a0, b0);
		subFrom(z1, z0); subFrom(z1, z2);
		
		MyList ret = new MyList();
		addTo(ret, z0, 0); addTo(ret, z1, half); addTo(ret, z2, half+half);
		return ret;
	}
	
	public static class MyList {
		public static final int MAX_SIZE = 20000;
		public int[] list;
		public int start,end;
		
		public MyList(){
			this.list = new int[MAX_SIZE];
			this.start = 0;
			this.end = 0;
		}
		
		public MyList(int[] list, int start, int end){
			this.list = list;
			this.start = start;
			this.end = end;
		}
		
		public void set(int index, int val){
			index += start;
			list[index] = val;
			if(index >= end) end = index+1;
		}
		
		public int get(int index){
			return list[index+start];
		}
		
		public void add(int val){
			list[end] = val;
			end++;
		}
		
		public void removeLastIfZero(){
			if(size() > 0 && list[end-1] == 0) end--;
		}
		
		public int size(){
			return end-start;
		}
		
		public MyList subList(int start, int end){
			int[] list2 = new int[MAX_SIZE];
			for(int i=start; i<end; i++) list2[i] = list[i];
			return new MyList(list2, start, end);
		}

		@Override
		public String toString() {
			String str = "[";
			for(int i=start; i<end; i++){
				str += list[i]+",";
			}
			str += "]";
			return str;
		}
		
	}

	public static void main(String[] args){
//		MyList a = new MyList();
//		a.add(3); a.add(2); a.add(1);
//		MyList b = new MyList();
//		b.add(6); b.add(5); b.add(4);
//		MyList c = new MyLibrary().karatsuba(a, b);
//		System.out.println(c);
		
		MyList a = new MyList();
		for(int i=0; i<10000; i++) a.add(1);
		MyList b = new MyList();
		for(int i=0; i<10000; i++) b.add(1);
		MyList c = new KaratsubaTodo().karatsuba(a, b);
		System.out.println("c.size:"+c.size()+", cnt:"+cnt);
	}
}
