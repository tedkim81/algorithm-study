package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;

/**
 * Copy this file and do coding hardly!
 */
public class CodeJamKorea2012_1_C_othersolution {
	
	private int MOD = 32749;
	private int B, W, k, x;

	int[][] comb = new int[2010][2010];
	int[][] dynamic = new int[2010][2010];
	
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		
		comb[0][0] = 1;
		for (int i=1; i<=2000; i++) {
			comb[i][0] = 1;
			for (int j=1; j<=i; j++) comb[i][j] = (comb[i-1][j-1] + comb[i-1][j]) % MOD;
		}
		
		// get file
		FileReader fr = new FileReader("/Users/teuskim/codejam/C-small-practice.in.txt");
		BufferedReader br = new BufferedReader(fr);
		numberOfCases = Integer.valueOf(br.readLine());
		
		// make output
		File file = new File("/Users/teuskim/codejam/C-small-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int m=0; m<numberOfCases; m++){
			
			String[] strs = br.readLine().split(" ");
			B = Integer.parseInt(strs[0]);
			W = Integer.parseInt(strs[1]);
			k = Integer.parseInt(strs[2]);
			x = Integer.parseInt(strs[3]);
			
			x = k-x+1;
			if (B > k) B = k;
			if (W > k) W = k;

			// sum(b, 1~x-1) = B - (k-x), sum(w, 1~x-1) = x-1 - (B - (k-x)) = k-B-1 or
			// sum(w, 1~x-1) = W - (k-x), sum(b, 1~x-1) = k-W-1

			for(int j=0; j<dynamic.length; j++) Arrays.fill(dynamic[j], 0);

			if (range(0, x-1, B - (k-x)) && range(0, x-1, k-B-1)) dynamic[B - (k-x)][k-B] = comb[x-1][k-B-1];
			if (range(0, x-1, W - (k-x)) && range(0, x-1, k-W-1)) dynamic[k-W][W - (k-x)] = (dynamic[k-W][W - (k-x)] + comb[x-1][W - (k-x)]) % MOD;

			for (int i=x; i<k; i++) {
				int b, w;
				for (b=0; b<=i; b++) {
					w = i - b;

					if (b < B - (k-i)+1 && w < W - (k-i)+1) dynamic[b+1][w] = (dynamic[b+1][w] + dynamic[b][w]) % MOD;
					if (b < B - (k-i)+1 && w < W - (k-i)+1) dynamic[b][w+1] = (dynamic[b][w+1] + dynamic[b][w]) % MOD;
				}
			}

			int answer = 0;
			for (int i=0; i<=k; i++) if (i <= B && k-i <= W) answer = (answer + dynamic[i][k-i]) % MOD;
			
			String result = ""+answer;
			fw.write("Case #"+(m+1)+": "+result+"\n");
			System.out.println("Case #"+(m+1)+": "+result);
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	private boolean range(int l, int r, int x) { return l<=x && x<=r; }
	
	public static void main(String[] args){
		System.out.println("start!");
		try{
			
			new CodeJamKorea2012_1_C_othersolution().goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("end!");
	}
}
