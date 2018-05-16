package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Copy this file and do coding hardly!
 */
public class CodeJamKorea2012_Q_B_backup {
	
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		FileReader fr = new FileReader("/Users/teuskim/codejam/B-small-practice.in.txt");
		BufferedReader br = new BufferedReader(fr);
		numberOfCases = Integer.valueOf(br.readLine());
		
		// make output
		File file = new File("/Users/teuskim/codejam/B-small-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int i=0; i<numberOfCases; i++){
			
			String[] strs = br.readLine().split(" ");
			String a = strs[0];
			char op = strs[1].charAt(0);
			String b = strs[2];
			String c = strs[4];
			int n = 0;
			String abc = a+b+c;
			for(int j=0; j<abc.length(); j++){
				if(abc.charAt(j) == '?') n++;
			}
			
			result = null;
			opCheck(a, b, c, op, new int[n]);
			
			fw.write("Case #"+(i+1)+": "+result+"\n");
			fw.flush();
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	private String result;
	private boolean opCheck(String a, String b, String c, char op, int[] d){
//		System.out.println(a+" "+op+" "+b+" = "+c+" , size:"+d.length);
		if(d.length == 0){
			BigDecimal ba = new BigDecimal(a);
			BigDecimal bb = new BigDecimal(b);
			BigDecimal bc = new BigDecimal(c);
			if((op == '+' && ba.add(bb).equals(bc))
					|| (op == '-' && ba.subtract(bb).equals(bc))){
				result = ba+" "+op+" "+bb+" = "+bc;
				return true;
			}
			else
				return false;
		}
		
		for(int i=48; i<58; i++){
			String[] tmp = a.split("\\?", 2);
			String tmpA, tmpB, tmpC;
			if(tmp.length > 1){
				if(i == 48 && tmp[0].length() == 0 && tmp[1].length() > 0) continue;
				tmpA = tmp[0] + (char)i + tmp[1];
				tmpB = b;
				tmpC = c;
			}
			else{
				tmpA = a;
				tmp = b.split("\\?", 2);
				if(tmp.length > 1){
					if(i == 48 && tmp[0].length() == 0 && tmp[1].length() > 0) continue;
					tmpB = tmp[0] + (char)i + tmp[1];
					tmpC = c;
				}
				else{
					tmpB = b;
					tmp = c.split("\\?", 2);
					if(i == 48 && tmp[0].length() == 0 && tmp[1].length() > 0) continue;
					tmpC = tmp[0] + (char)i + tmp[1];
				}
			}
			int[] tmpD;
			if(d.length > 1)
				tmpD = Arrays.copyOfRange(d, 1, d.length);
			else
				tmpD = new int[0];
			if(opCheck(tmpA, tmpB, tmpC, op, tmpD))
				return true;
		}
		return false;
	}
	
	//======================================================================
	
	private void goodluck2() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		FileReader fr = new FileReader("/Users/teuskim/codejam/test.in.txt");
		BufferedReader br = new BufferedReader(fr);
		numberOfCases = Integer.valueOf(br.readLine());
		
		// make output
		File file = new File("/Users/teuskim/codejam/test.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int i=0; i<numberOfCases; i++){
			
			String[] strs = br.readLine().split(" ");
			char op = strs[1].charAt(0);
			int[] a = getArr(strs[0]);
			int[] b = getArr(strs[2]);
			int[] c = getArr(strs[4]);
			
			opCheck(a, b, c, 0, op, false);
			
			String result = "";
			for(int j=0; j<a.length; j++) result += a[a.length-j-1];
			result += " "+op+" ";
			for(int j=0; j<b.length; j++) result += b[b.length-j-1];
			result += " = ";
			for(int j=0; j<c.length; j++) result += c[c.length-j-1];
			
			fw.write("Case #"+(i+1)+": "+result+"\n");
			fw.flush();
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	private int[] getArr(String str){
		int[] arr = new int[str.length()];
		for(int j=0; j<arr.length; j++){
			char ch = str.charAt(j);
			if(ch == '?') arr[arr.length-j-1] = -1;
			else arr[arr.length-j-1] = (int)ch - 48;
		}
		return arr;
	}

	private void setValue(int[] a, int va, int[] b, int vb, int[] c, int vc, int idx, char op){
		if(a.length > idx) a[idx] = va;
		if(b.length > idx) b[idx] = vb;
		if(c.length > idx) c[idx] = vc;
	}
	
	private int getValue(int[] arr, int idx){
		if(arr.length > idx){
			return arr[idx];
		}
		return 0;
	}
	
	private boolean notBeZero(int[] arr, int idx){
		if(arr.length-1 == idx)
			return true;
		return false;
	}
	
	/**
	 * 뒷자리부터 체크하는 함수
	 * 중단. 새로운 방법 모색 필요.
	 * 어떻게든 답을 구해보려 했으나 모든 경우의 수를 코딩하기는 사실상 불가능하다는 것을 깨달았다. 
	 * 맨 앞자리에 0이 올수 없다는 조건을 충족시키기 위해 setValue 하기전에 조건문 추가하다가 포기..
	 * 다른 방법을 찾아보자!
	 * 
	 * @param a 첫번째 연산수 역순 배열
	 * @param b 두번째 연산수 역순 배열
	 * @param c 연산 결과 역순 배열
	 * @param idx 현재 인덱스
	 * @param op 연산자 ( + , - )
	 * @param d 올림/내림 발생여부
	 * @return 가능하다면 true
	 */
	private boolean opCheck(int[] a, int[] b, int[] c, int idx, char op, boolean d){
		if(idx == Math.max(a.length, Math.max(b.length, c.length))){
			return true;
		}
		// 올림/내림이 발생하지 않는 경우와 발생하는 경우 두번에 대해서만 재귀호출한다.
		if(getValue(a,idx) == -1){
			if(getValue(b,idx) == -1){
				if(getValue(c,idx) == -1){  // a[idx] == -1, b[idx] == -1, c[idx] == -1
					if(op == '+'){
						if(d == false){
							if(notBeZero(a, idx))
								if(notBeZero(b, idx)) setValue(a, 1, b, 1, c, 2, idx, op);
								else setValue(a, 1, b, 0, c, 1, idx, op);
							else
								if(notBeZero(b, idx) || notBeZero(c, idx)) setValue(a, 0, b, 1, c, 1, idx, op);
								else setValue(a, 0, b, 0, c, 0, idx, op);
							if(opCheck(a, b, c, idx+1, op, false)) return true;
							
							if(notBeZero(c, idx)) setValue(a, 2, b, 9, c, 1, idx, op);
							else setValue(a, 1, b, 9, c, 0, idx, op);
							if(opCheck(a, b, c, idx+1, op, true)) return true;
						}
						else{
							if(notBeZero(a, idx))
								if(notBeZero(b, idx)) setValue(a, 1, b, 1, c, 3, idx, op);
								else setValue(a, 1, b, 0, c, 2, idx, op);
							else
								if(notBeZero(b, idx)) setValue(a, 0, b, 1, c, 2, idx, op);
								else setValue(a, 0, b, 0, c, 1, idx, op);
							if(opCheck(a, b, c, idx+1, op, false)) return true;
							
							if(notBeZero(c, idx)) setValue(a, 1, b, 9, c, 1, idx, op);
							else setValue(a, 0, b, 9, c, 0, idx, op);
							if(opCheck(a, b, c, idx+1, op, true)) return true;
						}
					}
					else{  // op == '-'
						if(d == false){
							if(notBeZero(b, idx))
								if(notBeZero(c, idx)) setValue(a, 2, b, 1, c, 1, idx, op);
								else setValue(a, 1, b, 1, c, 0, idx, op);
							else
								if(notBeZero(c, idx)) setValue(a, 1, b, 0, c, 1, idx, op);
								else setValue(a, 0, b, 0, c, 0, idx, op);
							if(opCheck(a, b, c, idx+1, op, false)) return true;
							
							if(notBeZero(a, idx)) setValue(a, 1, b, 2, c, 9, idx, op);
							else setValue(a, 0, b, 1, c, 9, idx, op);
							if(opCheck(a, b, c, idx+1, op, true)) return true;
						}
						else{
							if(notBeZero(b, idx))
								if(notBeZero(c, idx)) setValue(a, 3, b, 1, c, 1, idx, op);
								else setValue(a, 2, b, 1, c, 0, idx, op);
							else
								if(notBeZero(c, idx)) setValue(a, 2, b, 0, c, 1, idx, op);
								else setValue(a, 1, b, 0, c, 0, idx, op);
							if(opCheck(a, b, c, idx+1, op, false)) return true;
							
							if(notBeZero(a, idx)) setValue(a, 1, b, 1, c, 9, idx, op);
							else if(notBeZero(b, idx)) setValue(a, 0, b, 1, c, 8, idx, op);
							else setValue(a, 0, b, 0, c, 9, idx, op);
							if(opCheck(a, b, c, idx+1, op, true)) return true;
						}
					}
				}
				else{  // a[idx] == -1, b[idx] == -1, c[idx] >= 0
					if(op == '+'){
						if(d == false){
							setValue(a, 0, b, getValue(c,idx), c, getValue(c,idx), idx, op);
							if(opCheck(a, b, c, idx+1, op, false)) return true;
							
							setValue(a, (getValue(c,idx)+1)%10, b, 9, c, getValue(c,idx), idx, op);
							if(opCheck(a, b, c, idx+1, op, true)) return true;
						}
						else{
							setValue(a, 0, b, (getValue(c,idx)+9)%10, c, getValue(c,idx), idx, op);
							if(opCheck(a, b, c, idx+1, op, false)) return true;
							setValue(a, getValue(c,idx), b, 9, c, getValue(c,idx), idx, op);
							if(opCheck(a, b, c, idx+1, op, true)) return true;
						}
					}
					else{  // op == '-'
						if(d == false){
							setValue(a, getValue(c,idx), b, 0, c, getValue(c,idx), idx, op);
							if(opCheck(a, b, c, idx+1, op, false)) return true;
							if(getValue(c,idx) > 0){
								setValue(a, 0, b, 10-getValue(c,idx), c, getValue(c,idx), idx, op);
								if(opCheck(a, b, c, idx+1, op, true)) return true;
							}
						}
						else{
							if(getValue(c,idx)+1 < 10){
								setValue(a, getValue(c,idx)+1, b, 0, c, getValue(c,idx), idx, op);
								if(opCheck(a, b, c, idx+1, op, false)) return true;
								setValue(a, 0, b, 9-getValue(c,idx), c, getValue(c,idx), idx, op);
								if(opCheck(a, b, c, idx+1, op, true)) return true;
							}
							else{
								setValue(a, (getValue(c,idx)+1)%10, b, 0, c, getValue(c,idx), idx, op);
								if(opCheck(a, b, c, idx+1, op, true)) return true;
							}
						}
					}
				}
			}
			else{  
				if(getValue(c,idx) == -1){  // a[idx] == -1, b[idx] >= 0, c[idx] == -1
					if(op == '+'){
						if(d == false){
							setValue(a, 0, b, getValue(b,idx), c, getValue(b,idx), idx, op);
							if(opCheck(a, b, c, idx+1, op, false)) return true;
							setValue(a, (10-getValue(b,idx))%10, b, getValue(b,idx), c, 0, idx, op);
							if(opCheck(a, b, c, idx+1, op, true)) return true;
						}
						else{
							if(getValue(b,idx)+1 < 10){
								setValue(a, 0, b, getValue(b,idx), c, getValue(b,idx)+1, idx, op);
								if(opCheck(a, b, c, idx+1, op, false)) return true;
								setValue(a, 9-getValue(b,idx), b, getValue(b,idx), c, 0, idx, op);
								if(opCheck(a, b, c, idx+1, op, true)) return true;
							}
							else{
								setValue(a, 0, b, getValue(b,idx), c, (getValue(b,idx)+1)%10, idx, op);
								if(opCheck(a, b, c, idx+1, op, true)) return true;
							}
						}
					}
					else{  // op == '-'
						if(d == false){
							setValue(a, getValue(b,idx), b, getValue(b,idx), c, 0, idx, op);
							if(opCheck(a, b, c, idx+1, op, false)) return true;
							setValue(a, 0, b, getValue(b,idx), c, (10-getValue(b,idx))%10, idx, op);
							if(opCheck(a, b, c, idx+1, op, true)) return true;
						}
						else{
							if(getValue(b,idx)+1 < 10){
								setValue(a, getValue(b,idx)+1, b, getValue(b,idx), c, 0, idx, op);
								if(opCheck(a, b, c, idx+1, op, false)) return true;
								setValue(a, 0, b, getValue(b,idx), c, (9-getValue(b,idx))%10, idx, op);
								if(opCheck(a, b, c, idx+1, op, true)) return true;
							}
							else{
								setValue(a, 0, b, getValue(b,idx), c, 9-getValue(b,idx), idx, op);
								if(opCheck(a, b, c, idx+1, op, true)) return true;
							}
						}
					}
				}
				else{  // a[idx] == -1, b[idx] >= 0, c[idx] >= 0
					if(op == '+'){
						if(d == false){
							if(getValue(c,idx) >= getValue(b,idx)){
								setValue(a, getValue(c,idx)-getValue(b,idx), b, getValue(b,idx), c, getValue(c,idx), idx, op);
								if(opCheck(a, b, c, idx+1, op, false)) return true;
							}
							else{
								setValue(a, 10+getValue(c,idx)-getValue(b,idx), b, getValue(b,idx), c, getValue(c,idx), idx, op);
								if(opCheck(a, b, c, idx+1, op, true)) return true;
							}
						}
						else{
							if(getValue(c,idx) >= getValue(b,idx)){
								setValue(a, (getValue(c,idx)-getValue(b,idx)+9)%10, b, getValue(b,idx), c, getValue(c,idx), idx, op);
								if(opCheck(a, b, c, idx+1, op, false)) return true;
							}
							else{
								setValue(a, 9+getValue(c,idx)-getValue(b,idx), b, getValue(b,idx), c, getValue(c,idx), idx, op);
								if(opCheck(a, b, c, idx+1, op, true)) return true;
							}
						}
					}
					else{  // op == '-'
						if(d == false){
							if(getValue(c,idx)+getValue(b,idx) < 10){
								setValue(a, getValue(c,idx)+getValue(b,idx), b, getValue(b,idx), c, getValue(c,idx), idx, op);
								if(opCheck(a, b, c, idx+1, op, false)) return true;
							}
							else{
								setValue(a, getValue(c,idx)+getValue(b,idx)-10, b, getValue(b,idx), c, getValue(c,idx), idx, op);
								if(opCheck(a, b, c, idx+1, op, true)) return true;
							}
						}
						else{
							if(getValue(c,idx)+getValue(b,idx) < 10){
								setValue(a, (getValue(c,idx)+getValue(b,idx)+1)%10, b, getValue(b,idx), c, getValue(c,idx), idx, op);
								if(opCheck(a, b, c, idx+1, op, false)) return true;
							}
							else{
								setValue(a, getValue(c,idx)+getValue(b,idx)-9, b, getValue(b,idx), c, getValue(c,idx), idx, op);
								if(opCheck(a, b, c, idx+1, op, true)) return true;
							}
						}
					}
				}
			}
		}
		else{
			if(getValue(b,idx) == -1){
				if(getValue(c,idx) == -1){  // a[idx] >= 0, b[idx] == -1, c[idx] == -1
					if(op == '+'){
						if(d == false){
							setValue(a, getValue(a,idx), b, 0, c, getValue(a,idx), idx, op);
							if(opCheck(a, b, c, idx+1, op, false)) return true;
							setValue(a, getValue(a,idx), b, (10-getValue(a,idx))%10, c, 0, idx, op);
							if(opCheck(a, b, c, idx+1, op, true)) return true;
						}
						else{
							if(getValue(a,idx)+1 < 10){
								setValue(a, getValue(a,idx), b, 0, c, getValue(a,idx)+1, idx, op);
								if(opCheck(a, b, c, idx+1, op, false)) return true;
								setValue(a, getValue(a,idx), b, 9-getValue(a,idx), c, 0, idx, op);
								if(opCheck(a, b, c, idx+1, op, true)) return true;
							}
							else{
								setValue(a, getValue(a,idx), b, 0, c, (getValue(a,idx)+1)%10, idx, op);
								if(opCheck(a, b, c, idx+1, op, true)) return true;
							}
						}
					}
					else{  // op == '-'
						if(d == false){
							setValue(a, getValue(a,idx), b, 0, c, getValue(a,idx), idx, op);
							if(opCheck(a, b, c, idx+1, op, false)) return true;
							if(getValue(a,idx)+1 < 10){
								setValue(a, getValue(a,idx), b, getValue(a,idx)+1, c, 9, idx, op);
								if(opCheck(a, b, c, idx+1, op, true)) return true;
							}
						}
						else{
							if(getValue(a,idx) > 0){
								setValue(a, getValue(a,idx), b, 0, c, getValue(a,idx)-1, idx, op);
								if(opCheck(a, b, c, idx+1, op, false)) return true;
								setValue(a, getValue(a,idx), b, getValue(a,idx), c, 9, idx, op);
								if(opCheck(a, b, c, idx+1, op, true)) return true;
							}
							else{
								setValue(a, getValue(a,idx), b, 0, c, 9, idx, op);
								if(opCheck(a, b, c, idx+1, op, true)) return true;
							}
						}
					}
				}
				else{  // // a[idx] >= 0, b[idx] == -1, c[idx] >= 0
					if(op == '+'){
						if(d == false){
							if(getValue(c,idx) >= getValue(a,idx)){
								setValue(a, getValue(a,idx), b, getValue(c,idx)-getValue(a,idx), c, getValue(c,idx), idx, op);
								if(opCheck(a, b, c, idx+1, op, false)) return true;
							}
							else{
								setValue(a, getValue(a,idx), b, 10+getValue(c,idx)-getValue(a,idx), c, getValue(c,idx), idx, op);
								if(opCheck(a, b, c, idx+1, op, true)) return true;
							}
						}
						else{
							if(getValue(c,idx) >= getValue(a,idx)){
								setValue(a, getValue(a,idx), b, (getValue(c,idx)-getValue(a,idx)+9)%10, c, getValue(c,idx), idx, op);
								if(opCheck(a, b, c, idx+1, op, false)) return true;
							}
							else{
								setValue(a, getValue(a,idx), b, 9+getValue(c,idx)-getValue(a,idx), c, getValue(c,idx), idx, op);
								if(opCheck(a, b, c, idx+1, op, true)) return true;
							}
						}
					}
					else{  // op == '-'
						if(d == false){
							if(getValue(a,idx) >= getValue(c,idx)){
								setValue(a, getValue(a,idx), b, getValue(a,idx)-getValue(c,idx), c, getValue(c,idx), idx, op);
								if(opCheck(a, b, c, idx+1, op, false)) return true;
							}
							else{
								setValue(a, getValue(a,idx), b, 10+getValue(a,idx)-getValue(c,idx), c, getValue(c,idx), idx, op);
								if(opCheck(a, b, c, idx+1, op, true)) return true;
							}
						}
						else{
							if(getValue(a,idx) >= getValue(c,idx)){
								setValue(a, getValue(a,idx), b, (getValue(a,idx)-getValue(c,idx)+9)%10, c, getValue(c,idx), idx, op);
								if(opCheck(a, b, c, idx+1, op, false)) return true;
							}
							else{
								setValue(a, getValue(a,idx), b, 9+getValue(a,idx)-getValue(c,idx), c, getValue(c,idx), idx, op);
								if(opCheck(a, b, c, idx+1, op, true)) return true;
							}
						}
					}
				}
			}
			else{
				if(getValue(c,idx) == -1){  // a[idx] >= 0, b[idx] >= 0, c[idx] == -1
					if(op == '+'){
						if(d == false){
							setValue(a, getValue(a,idx), b, getValue(b,idx), c, (getValue(a,idx)+getValue(b,idx))%10, idx, op);
							if(opCheck(a, b, c, idx+1, op, ((getValue(a,idx)+getValue(b,idx)) >= 10))) return true;
						}
						else{
							setValue(a, getValue(a,idx), b, getValue(b,idx), c, (getValue(a,idx)+getValue(b,idx)+1)%10, idx, op);
							if(opCheck(a, b, c, idx+1, op, ((getValue(a,idx)+getValue(b,idx)+1) >= 10))) return true;
						}
					}
					else{  // op == '-'
						if(d == false){
							setValue(a, getValue(a,idx), b, getValue(b,idx), c, (getValue(a,idx)-getValue(b,idx)+10)%10, idx, op);
							if(opCheck(a, b, c, idx+1, op, (getValue(a,idx) < getValue(b,idx)))) return true;
						}
						else{
							setValue(a, getValue(a,idx), b, getValue(b,idx), c, (getValue(a,idx)-getValue(b,idx)+9)%10, idx, op);
							if(opCheck(a, b, c, idx+1, op, (getValue(a,idx)-1 < getValue(b,idx)))) return true;
						}
					}
				}
				else{  // // a[idx] >= 0, b[idx] >= 0, c[idx] >= 0
					if(op == '+'){
						if(d == false){
							if((getValue(a,idx)+getValue(b,idx))%10 != getValue(c,idx)) return false;
							if(opCheck(a, b, c, idx+1, op, ((getValue(a,idx)+getValue(b,idx)) >= 10))) return true;
						}
						else{
							if((getValue(a,idx)+getValue(b,idx)+1)%10 != getValue(c,idx)) return false;
							if(opCheck(a, b, c, idx+1, op, ((getValue(a,idx)+getValue(b,idx)+1) >= 10))) return true;
						}
					}
					else{  // op == '-'
						if(d == false){
							if((getValue(a,idx)-getValue(b,idx)+10)%10 != getValue(c,idx)) return false;
							if(opCheck(a, b, c, idx+1, op, ((getValue(a,idx)-getValue(b,idx)) < 0))) return true;
						}
						else{
							if((getValue(a,idx)-getValue(b,idx)+9)%10 != getValue(c,idx)) return false;
							if(opCheck(a, b, c, idx+1, op, ((getValue(a,idx)-getValue(b,idx)-1) < 0))) return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	/*
	// return int 올림 1, 내림 -1, 올림/내림 없는 성공 0, 실패 -2
	private int opCheckUnit(int a, int b, int c, char op){
		if(op == '+'){
			int res = a+b;
			if(res >= 10 && res == c+10) return 1;
			else if(res < 10 && res == c) return 0;
			else return -2;
		}
		else{
			int res = a-b;
			if(res < 0 && res == c-10) return -1;
			else if(res >= 0 && res == c) return 0;
			else return -2;
		}
	}
	*/
	
	public static void main(String[] args){
		System.out.println("start!");
		try{
			
			new CodeJamKorea2012_Q_B_backup().goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("end!");
	}
}
