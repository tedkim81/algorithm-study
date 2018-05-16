package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

/**
 * Copy this file and do coding hardly!
 */
public class CodeJamKorea2012_Q_B_othersolution {
	
	private String op, A, B, C;
	private Pair<Integer, Pair<Pair<String, String>, String>>[][] DP = new Pair[600][2];
	private int mode;
	private int offset = 300;
	
	private void dfs(int a, int b, int c, int carry){
		Pair<Integer, Pair<Pair<String, String>, String>> res = DP[a+offset][carry];
		if(res.first == -1){
			res.first = 0;
			if (a < 0 && b < 0) {
				 if (mode == 0) {
					 if (c < 0) {
						 if (carry == 0) {
							 res.first = 1;
							 res.second.first.first = res.second.first.second = res.second.second = "";
						 }
					 }
					 else if (c == 0 && carry == 1) {
						 if (C.charAt(c) == '1' || C.charAt(c) == '?') {
							 res.first = 1;
							 res.second.first.first = res.second.first.second = "";
							 res.second.second = "1";
						 }
					 }
				 }
				 else {
					 if (carry == 0 && c < 0) {
						 res.first = 1;
						 res.second.first.first = res.second.first.second = res.second.second = "";
					 }
				 }
				 return;
			}
			int i, j, k;
			for(i=0; i<10; i++){
				if (a < 0 && i != 0) continue;
				if (a == 0 && i == 0 && A.length() != 1) continue;
				if (a >= 0 && A.charAt(a) != '?' && A.charAt(a) - '0' != i) continue;
				for(j=0; j<10; j++){
					if (b < 0 && j != 0) continue;
					if (b == 0 && j == 0 && B.length() != 1) continue;
					if (b >= 0 && B.charAt(b) != '?' && B.charAt(b) - '0' != j) continue;
					for(k=0; k<10; k++){
						if (c < 0 && k != 0) continue;
						if (c == 0 && k == 0 && C.length() != 1) continue;
						if (c >= 0 && C.charAt(c) != '?' && C.charAt(c) - '0' != k) continue;
						
						if (mode == 0) {
							if ((i + j + carry) % 10 != k) continue;
							dfs(a - 1, b - 1, c - 1, (i + j + carry) / 10);
						}
						else{
							if ((i - carry + 10 - j) % 10 != k) continue;
							dfs(a - 1, b - 1, c - 1, ((i - carry + 10 - j) / 10)==0?1:0);  // TODO: !((i - carry + 10 - j) / 10) 이 의미하는 바는?
						}
						Pair<Integer, Pair<Pair<String, String>, String>> cmp = 
								mode == 0 ? DP[a - 1 + offset][(i + j + carry) / 10] : DP[a - 1 + offset][((i - carry + 10 - j) / 10)==0?1:0];  // TODO: !((i - carry + 10 - j) / 10)
						if (cmp.first == 0) continue;
						String AC = cmp.second.first.first, BC = cmp.second.first.second, CC = cmp.second.second;
						if (a >= 0) AC += (char)(i + '0');
						if (b >= 0) BC += (char)(j + '0');
						if (c >= 0) CC += (char)(k + '0');
						
						if (res.first == 0 || res.second.first.first.compareTo(AC) > 0 ||
					              (res.second.first.first.equals(AC) && res.second.first.second.compareTo(BC) > 0) ||
					              (res.second.first.first.equals(AC) && res.second.first.second.equals(BC) && res.second.second.compareTo(CC) > 0)) {
							res.first = 1;
							res.second.first.first = AC; res.second.first.second = BC; res.second.second = CC;
						}
					}
				}
			}
		}
	}
	
	private String process(int op) {
		int i, j;
		for(i=0; i<600; i++) for(j=0; j<2; j++) DP[i][j] = new Pair<Integer, Pair<Pair<String,String>,String>>(-1, new Pair<Pair<String,String>, String>(new Pair<String, String>("", ""), ""));
		int a = A.length() - 1, b = B.length() - 1, c = C.length() - 1;
		mode = op;
		dfs(a, b, c, 0);
		Pair<Integer, Pair<Pair<String, String>, String>> res = DP[a + offset][0];
		return res.second.first.first + (op == 0 ? " + " : " - ") + res.second.first.second + " = " + res.second.second;
	}
	
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
			A = strs[0];
			B = strs[2];
			C = strs[4];
			
			String result;
			if(strs[1].equals("+")) result = process(0);
			else result = process(1);
			
			fw.write("Case #"+(i+1)+": "+result+"\n");
			System.out.println("Case #"+(i+1)+": "+result);
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	public static class Pair<A, B> {
	    private A first;
	    private B second;

	    public Pair(A first, B second) {
	    	super();
	    	this.first = first;
	    	this.second = second;
	    }

	    public int hashCode() {
	    	int hashFirst = first != null ? first.hashCode() : 0;
	    	int hashSecond = second != null ? second.hashCode() : 0;

	    	return (hashFirst + hashSecond) * hashSecond + hashFirst;
	    }

	    public boolean equals(Object other) {
	    	if (other instanceof Pair) {
	    		Pair otherPair = (Pair) other;
	    		return 
	    		((  this.first == otherPair.first ||
	    			( this.first != null && otherPair.first != null &&
	    			  this.first.equals(otherPair.first))) &&
	    		 (	this.second == otherPair.second ||
	    			( this.second != null && otherPair.second != null &&
	    			  this.second.equals(otherPair.second))) );
	    	}

	    	return false;
	    }

	    public String toString()
	    { 
	           return "(" + first + ", " + second + ")"; 
	    }

	    public A getFirst() {
	    	return first;
	    }

	    public void setFirst(A first) {
	    	this.first = first;
	    }

	    public B getSecond() {
	    	return second;
	    }

	    public void setSecond(B second) {
	    	this.second = second;
	    }
	}
	
	public static void main(String[] args){
		System.out.println("start!");
		try{
			
			new CodeJamKorea2012_Q_B_othersolution().goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("end!");
	}
}
