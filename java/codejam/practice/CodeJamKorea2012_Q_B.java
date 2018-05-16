package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Code Jam Korea 2012 예선 라운드 : 문제 B 계산식 복원
 * 덧셈 또는 뺄셈 등식들에서 일부 자릿수들을 알아볼 수 없을때(?표시) 가능한 값들을 채워서 등식을 완성하는 문제(사전순)
 * 2013년 2월 7일 오전 12:58:36
 * 
 * 예선 문제라고 얕봤다가 호되게 당한 문제이다. 문제를 이해하고 완전탐색을 이용한 small input 을 해결하기까지는 그리 오래 걸리지는 않았다.
 * 그러나 large input을 해결하기 위해 이틀을 소비했다. 해결전략은 각 자릿수들의 계산결과에서 올림/내림이 발생하는 경우와 그렇지 않은 경우 두가지에 
 * 대하여 재귀호출을 하기로 결정했고 이는 옳은 판단이었다. 그러나 각 자릿수의 값을 구하는 과정에서 각각의 경우를 조건식으로 계속 분기하여
 * 각각의 상황에 맞게 재귀호출을 하려고 했다. 세개의 숫자가 ? 이거나 그렇지 않은 경우 둘로 나뉘므로 기본 2*2*2=8 가지에 덧셈/뺄셈 여부,
 * 올림/내림 발생여부까지 해서 32갈래로 나뉘었다. 여기까진 미친척하고 했다. 그럴 수도 있다고 생각했다. 그러나.. 두자리 이상일때 맨앞자리는 0이 올 수
 * 없다는 조건에서 멘붕이 찾아왔다. 여기까지 벌써 하루를 넘게 소비하고 난 뒤였다. 
 * 이번에도 새삼스레 깨달았다. 집착하지 말아야 한다. 아니다 싶으면 빨리 다른 방법을 찾아봐야 하는 것이다.
 * 
 * 연습장을 꺼내들고 문제의 정의를 수식으로 표현한 뒤 부분문제를 나눠봤다. 아랫자리부터 탐색을 하는데 현재 탐색중인 위치 이후의 탐색결과는
 * 올림/내림여부에 대해서만 종속적이고 그 외에는 분리가 될 수 있었다. 그렇다, 동적계획법으로 해결이 가능한 문제였던 것이다.
 * 일단 메모이제이션을 하기 전에 재귀호출만을 이용해 문제를 해결해 보고자 했다. 쉽게 해결할 수 있을듯 보였지만 여기서도 좀 헤맸다.
 * 단순히 재귀호출이 한번 일어나는 것이 아니라 두번의 재귀호출을 하고 그 결과를 비교하여 더 사전적으로 앞선 결과를 반환해야 하는데
 * 처음에는 이를 착각하여 올림/내림이 발생하지 않는 결과를 우선하도록 했었다. 이 문제를 해결하고 small input 을 정답처리한후
 * 동적계획법을 적용하여 large input 을 해결하려 했으나 문제가 발생했다. 첫번째 재귀호출이 성공했을때는 a,b,c 배열이 변경되어 두번째 재귀호출이
 * 정상동작을 하지 못했기 때문이었다. 그래서 opCheck의 리턴타입을 boolean에서 결과객체로 변경했다. 
 * 그러나 계속 오답이었고 결국 다른 참가자의 솔루션(cpp) 파일을 java파일로 옮겨쓴뒤 결과를 구한후 내 결과와 비교하여 문제점을 파악했다.
 * ?? - ?? = ?? 의 결과는 20 - 10 = 10 이어야 하는데 내 결과에서는 10 - 11 = 99 였다. base case 가 잘못됐었던 것이다.
 * base case 에서 carry 가 넘어오는 경우는 실패처리를 했다. 그리고 정답을 처리되었다.
 * 
 * 이번 문제로 시간을 굉장히 많이 소모하긴 했지만 나름 유익했다. 재귀호출에 어느 정도 익숙해진 느낌이다.
 * 재귀 호출시 주의할 점이다.
 * 1. 부분문제로 나누고 나누어진 부분문제의 결과는 그 부분문제에 해당하는 결과여야 한다. 
 * 이번에 opCheck의 base case에서 전체결과를 리턴하고 중간호출자들은 그 결과를 그냥 포워딩하는 식으로 했었다가 낭패를 봤다.
 * 메모이제이션 적용시 부분문제의 결과 대신 전체결과가 저장되는 바람에 잘못된 결과를 반환했었다.
 * 2. base case 에서의 동작을 신중하게 검토해야 한다.
 */
public class CodeJamKorea2012_Q_B {
	
	private int[] a;
	private int[] b;
	private int[] c;
	private int maxLength;
	private char op;
	private Map<Integer, ResCls> map;
	
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		FileReader fr = new FileReader("/Users/teuskim/codejam/B-large-practice.in.txt");
		BufferedReader br = new BufferedReader(fr);
		numberOfCases = Integer.valueOf(br.readLine());
		
		// make output
		File file = new File("/Users/teuskim/codejam/B-large-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int i=0; i<numberOfCases; i++){
			
			String[] strs = br.readLine().split(" ");
			op = strs[1].charAt(0);
			a = new int[strs[0].length()];
			for(int j=0; j<a.length; j++){ char ch = strs[0].charAt(a.length-j-1); a[j] = (ch=='?') ? -1 : ((int)ch - 48); }
			b = new int[strs[2].length()];
			for(int j=0; j<b.length; j++){ char ch = strs[2].charAt(b.length-j-1); b[j] = (ch=='?') ? -1 : ((int)ch - 48); }
			c = new int[strs[4].length()];
			for(int j=0; j<c.length; j++){ char ch = strs[4].charAt(c.length-j-1); c[j] = (ch=='?') ? -1 : ((int)ch - 48); }
			maxLength = Math.max(a.length, Math.max(b.length, c.length));
			map = new HashMap<Integer, ResCls>();
			
			String result = opCheck(0, false).toString();
			
			fw.write("Case #"+(i+1)+": "+result+"\n");
			System.out.println("Case #"+(i+1)+": "+result);
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	private ResCls opCheck(int idx, boolean carry){
		if(idx == maxLength){
			if(carry == false) return new ResCls();
			else return null;
		}
		int key = (carry) ? (1000+idx) : idx;
		if(map.containsKey(key)){
			return map.get(key);
		}
		
		int si = 0; int ei = 9;
		if(idx > a.length-1){ ei = 0; }
		else if(a[idx] != -1){ si = a[idx]; ei = si; }
		else if(idx == a.length-1 && a.length > 1){ si = 1; }
		
		int sj = 0; int ej = 9;
		if(idx > b.length-1){ ej = 0; }
		else if(b[idx] != -1){ sj = b[idx]; ej = sj; }
		else if(idx == b.length-1 && b.length > 1){ sj = 1; }
		
		int sk = 0; int ek = 9;
		if(idx > c.length-1){ ek = 0; }
		else if(c[idx] != -1){ sk = c[idx]; ek = sk; }
		else if(idx == c.length-1 && c.length > 1){ sk = 1; }
		
		int ta = 0;
		int tb = 0;
		int tc = 0;
		boolean didCarry0 = false;
		boolean didCarry1 = false;
		int carry0a = 0;
		int carry0b = 0;
		int carry0c = 0;
		int carry1a = 0;
		int carry1b = 0;
		int carry1c = 0;
		
		for(int i=si; i<=ei; i++){
			for(int j=sj; j<=ej; j++){
				for(int k=sk; k<=ek; k++){
					if(didCarry0 && didCarry1) continue;
					
					if(didCarry0 == false && opCheckUnit(i, j, k, carry, false)){
						didCarry0 = true;
						carry0a = i; carry0b = j; carry0c = k;
					}
					if(didCarry1 == false && opCheckUnit(i, j, k, carry, true)){
						didCarry1 = true;
						carry1a = i; carry1b = j; carry1c = k;
					}
				}
			}
		}
//		System.out.println("carry0 idx:"+idx+", a:"+carry0a+", b:"+carry0b+", c:"+carry0c+", didCarry0:"+didCarry0);
//		System.out.println("carry1 idx:"+idx+", a:"+carry1a+", b:"+carry1b+", c:"+carry1c+", didCarry1:"+didCarry1);
		
		ResCls check0 = null;
		ResCls check1 = null;
		
		if(idx < a.length){ ta = a[idx]; }
		if(idx < b.length){ tb = b[idx]; }
		if(idx < c.length){ tc = c[idx]; }
		
		if(didCarry0){
			if(idx < a.length){ a[idx] = carry0a; }
			if(idx < b.length){ b[idx] = carry0b; }
			if(idx < c.length){ c[idx] = carry0c; }
			
			check0 = opCheck(idx+1, false);
			if(check0 != null)
				check0 = new ResCls(check0, idx, carry0a, carry0b, carry0c);
			
			if(idx < a.length){ a[idx] = ta; }
			if(idx < b.length){ b[idx] = tb; }
			if(idx < c.length){ c[idx] = tc; }
		}
		
		if(didCarry1){
			if(idx < a.length){ a[idx] = carry1a; }
			if(idx < b.length){ b[idx] = carry1b; }
			if(idx < c.length){ c[idx] = carry1c; }

			check1 = opCheck(idx+1, true);
			if(check1 != null)
				check1 = new ResCls(check1, idx, carry1a, carry1b, carry1c);
			
			if(idx < a.length){ a[idx] = ta; }
			if(idx < b.length){ b[idx] = tb; }
			if(idx < c.length){ c[idx] = tc; }
		}
		
		ResCls result = null;
		if(check0 != null) result = check0.smaller(check1);
		else if(check1 != null) result = check1;
		
		if(result != null)
			map.put(key, result);
		
		return result;
	}
	
	private int[] getCompareArray(int idx){
		int aleng = (a.length>idx) ? a.length-idx : 0;
		int bleng = (b.length>idx) ? b.length-idx : 0;
		int cleng = (c.length>idx) ? c.length-idx : 0;
		int[] arr = new int[aleng+bleng+cleng];
		int j = 0;
		if(aleng>0) for(int i=0; i<aleng; i++){ arr[j++] = a[a.length-i-1]; }
		if(bleng>0) for(int i=0; i<bleng; i++){ arr[j++] = b[b.length-i-1]; }
		if(cleng>0) for(int i=0; i<cleng; i++){ arr[j++] = c[c.length-i-1]; }
//		System.out.println("idx:"+idx+", "+arrayToString(arr));
		return arr;
	}
	
	private String arrayToString(int[] arr){
		if(arr == null) return "null";
		String str = "[";
		for(int i=0; i<arr.length; i++){
			str += arr[i]+",";
		}
		str = str.substring(0, str.length()-1)+"]";
		return str;
	}
	
	private boolean smallerLeftThanRight(int[] left, int[] right){
		boolean res = false;
		for(int i=0; i<left.length; i++){
			if(left[i] != right[i]){
				res = (left[i] < right[i]);
				break;
			}
		}
//		System.out.println("smallerLeftThanRight! left:"+arrayToString(left)+", right:"+arrayToString(right)+", result:"+res);
		return res;
	}
	
	private boolean opCheckUnit(int a0, int b0, int c0, boolean carry, boolean nextCarry){
//		System.out.println(a0+" "+op+" "+b0+" = "+c0+" , carry:"+carry+",nextCarry:"+nextCarry);
		if(op == '+'){
			if(carry == false && nextCarry == false && a0+b0 == c0) return true;
			else if(carry == false && nextCarry == true && a0+b0 == c0+10) return true;
			else if(carry == true && nextCarry == false && a0+b0+1 == c0) return true;
			else if(carry == true && nextCarry == true && a0+b0+1 == c0+10) return true;
		}
		else{
			if(carry == false && nextCarry == false && a0-b0 == c0) return true;
			else if(carry == false && nextCarry == true && 10+a0-b0 == c0) return true;
			else if(carry == true && nextCarry == false && a0-1-b0 == c0) return true;
			else if(carry == true && nextCarry == true && 10+a0-1-b0 == c0) return true;
		}
//		System.out.println("result false!!");
		return false;
	}
	
	private class ResCls {
		private List<Integer> resa;
		private List<Integer> resb;
		private List<Integer> resc;
		
		public ResCls(){
			this.resa = new ArrayList<Integer>();
			this.resb = new ArrayList<Integer>();
			this.resc = new ArrayList<Integer>();
		}
		
		public ResCls(ResCls old, int idx, int a0, int b0, int c0){
			this.resa = new ArrayList<Integer>(old.resa);
			this.resb = new ArrayList<Integer>(old.resb);
			this.resc = new ArrayList<Integer>(old.resc);
			if(a0 >= 0 && idx < a.length) this.resa.add(a0);
			if(b0 >= 0 && idx < b.length) this.resb.add(b0);
			if(c0 >= 0 && idx < c.length) this.resc.add(c0);
		}
		
		public ResCls smaller(ResCls other){
			if(other == null) return this;
			
//			System.out.println("thisa:"+this.resa+" , othera:"+other.resa);
			for(int i=0; i<resa.size(); i++){
				int thisa = this.resa.get(i);
				int othera = other.resa.get(i);
				if(thisa < othera) return this;
				else if(thisa > othera) return other;
			}
//			System.out.println("thisb:"+this.resb+" , otherb:"+other.resb);
			for(int i=0; i<resb.size(); i++){
				int thisb = this.resb.get(i);
				int otherb = other.resb.get(i);
				if(thisb < otherb) return this;
				else if(thisb > otherb) return other;
			}
//			System.out.println("thisc:"+this.resc+" , otherc:"+other.resc);
			for(int i=0; i<resc.size(); i++){
				int thisc = this.resc.get(i);
				int otherc = other.resc.get(i);
				if(thisc < otherc) return this;
				else if(thisc > otherc) return other;
			}
			return this;
		}
		
		@Override
		public String toString() {
			String result = "";
			for(int j=0; j<resa.size(); j++){ result += resa.get(j); }
			result += " "+op+" ";
			for(int j=0; j<resb.size(); j++){ result += resb.get(j); }
			result += " = ";
			for(int j=0; j<resc.size(); j++){ result += resc.get(j); }
			return result;
		}
		
	}
	
	public static void main(String[] args){
		System.out.println("start!");
		try{
			
			new CodeJamKorea2012_Q_B().goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("end!");
	}
}
