package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Code Jam Korea 2012 본선 라운드 : 문제 C 모자 쓴 아이들
 * 계단에 모자쓴 아이들이 서있을때 뒤에서부터 질문하여 i번째 아이가 자기 모자 색깔을 맞출 수 있는 경우의 수를 구하는 문제
 * 2013년 2월 21일 오전 12:26:39
 * 
 * 문제이해 5분, 해결방법 2시간, 코딩/제출 2시간, 그리고.. 또 실패!
 * i번째에서 모자 색깔을 맞출 수 있는 경우에 대한 조건을 구했다.
 * i=1일때, b1 == B || w1 == W 
 * i>1일때, (b1 == B-1 && w1 < W-1) || (w1 == W-1 && b1 < B-1) => i-1번째에서 못맞추고 i번째에서 맞출수 있는 경우 
 * ( 첫번째절에서, 검정색이 B-1개가 보이는데 i번째가 검정색이라면 i-1에서 모자색깔을 맞췄을 것이다. )
 * i의 앞부분과 뒷부분에 나올 수 있는 경우의 수를 곱하여 답을 구하고자 했다.
 * 
 * 그러나 이것은 잘못된 방법이었다. 위의 알고리즘으로 test output은 운 좋게 일치했지만 small output은 값들이 너무 작게 나왔다.
 * 위 알고리즘에서 i>1일때 모자색깔을 맞출 수 있는 조건은 i-1번째에서 못맞추는 경우만 반영되어 있다. 이것이 일반화 가능한지 불가능한지는 생각해보지 않은 것이다.
 * 그리고 최소한 b1 == B-1 || w1 == W-1 인 경우가 만족되어야 하는데, 이 경우 조건이 너무 협소하여 경우의 수가 너무 작아진다는 것을 알아차렸어야 했다.
 * b1 < B-1 && w1 < W-1 인 경우 맞출 수 있는 경우가 있을까? 물론 있다.
 * 예를 들어, B=3, W=3, K=4, I=3 인 경우는 존재한다. ( 검정,흰,검정,흰 )
 * 
 * 한참을 헤매다가 위의 예에 대하여 ( 검정,흰,검정,흰 ) 그림을 그려놓고 뒤에서부터 현재 상황에 대하여 수식으로 표현해 봤다.
 * i=1일때, K-i+1개의 모자의 구성 조건은, 0<=b1<=3, o<=w1<=3, b1+w1<=4
 * i=2일때, b2나 w2가 3이 아니면 되니까, 0<=b2<=2, o<=w2<=2, b1+w1<=3
 * 아! 여기서 몇가지 깨달았다. 
 * 1. i번째 아이에게 있어서 뒤에 있는 아이들로부터 얻을 수 있는 정보는 앞에 있는 아이들로부터 얻을 수 있는 정보와 독립적이다.
 * 2. 뒤로부터의 정보를 통해 K-i+1개 모자의 구성 조건을 좀더 좁힐 수 있다.
 * 이 두가지 깨달음을 통해 조건을 일반화 시킬 수 있었다.
 * i번째 아이까지 기회가 온 경우 앞에서부터 K-i+1개 모자의 구성 조건은,  0 <= bi <= B-i+1,  0 <= wi <= W-i+1,  bi+wi = K-i+1
 * i번째 아이가 모자 색깔을 맞추기 위한 조건은, bi == B-i+1 || wi == W-i+1
 * i번째 아이의 뒤에 있는 아이들은 모두 검정색 또는 흰색 모자를 쓸 수 있다.
 * i번째 아이의 앞에 있는 아이들은 검정색 모자를 B-i+1개 쓰고 있거나 흰색 모자를 W-i+1개 쓰고 있다.
 * 위 조건들을 이용하여 정답을 구할 수 있었다.
 * 
 * 직관에 의존하여 해결책을 찾으려고 하면 생각의 흐름이 dead lock 에 빠질 수 있다.
 * 이 문제를 해결하는데 있어서 가장 중요했던 부분은 그림을 그리고 각 상황에 대하여 수식으로 표현한 것이었다.
 */
public class CodeJamKorea2012_1_C {
	
	private int mod = 32749;
	private int[][] combMap = new int[2001][2001];
//	private int[][] splMap = new int[2001][2001];
	
	/*
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		FileReader fr = new FileReader("/Users/teuskim/codejam/C-small-practice.in.txt");
		BufferedReader br = new BufferedReader(fr);
		numberOfCases = Integer.valueOf(br.readLine());
		
		// make output
		File file = new File("/Users/teuskim/codejam/C-small-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int i=0; i<numberOfCases; i++){
			
			String[] strs = br.readLine().split(" ");
			int B = Integer.parseInt(strs[0]);
			int W = Integer.parseInt(strs[1]);
			int K = Integer.parseInt(strs[2]);
			int I = Integer.parseInt(strs[3]);
			
			int cnt = 0;
			int b1, w1, b2, w2;
			int prev = K - I;
			if(I == 1){
				b1 = B;  w1 = prev - b1;
				if(w1 >= 0 && w1 < W){
					cnt += getCount(b1, w1) % mod;
				}
				w1 = W; b1 = prev - w1;
				if(b1 >= 0 && b1 < B){
					cnt += getCount(b1, w1) % mod;
				}
			}
			else{
				b1 = B - 1;  w1 = prev - b1;
				b2 = B - b1;  w2 = W - w1 - 1;
				if(b1 >= 0 && w1 >= 0 && w1 < W-1 && b2 >= 0 && w2 >= 0){
					cnt += getCount(b1, w1) * getCount2(b2, w2, K-b1-w1-1);
				}
				w1 = W - 1;  b1 = prev - w1;
				w2 = W - w1;  b2 = B - b1 - 1;
				if(b1 >= 0 && w1 >= 0 && b1 < B-1 && b2 >= 0 && w2 >= 0){
					cnt += getCount(b1, w1) * getCount2(b2, w2, K-b1-w1-1);
				}
			}
			
			String result = ""+cnt;
			fw.write("Case #"+(i+1)+": "+result+"\n");
			System.out.println("Case #"+(i+1)+": "+result);
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	private int getCount(int b, int w){
		int big, small;
		if(b > w){
			big = b; small = w;
		}
		else{
			big = w; small = b;
		}
		if(small == 0) return 1;
		
		int cnt = 0;
		for(int i=1; i<=small; i++) cnt += comb(big+1, i) * spl(small, i);
		cnt = cnt % mod;
		return cnt;
	}
	
	private int comb(int n, int r){
		if(r == 1) return n;
		if(n == r) return 1;
		if(combMap[n][r] != 0) return combMap[n][r];
		int res = comb(n-1, r-1) + comb(n-1, r);
		combMap[n][r] = res;
		return res;
	}
	
	private int spl(int n, int r){
		if(n == r || r == 1) return 1;
		if(splMap[n][r] != 0) return splMap[n][r];
		int cnt = 1;
		for(int i=1; i<=n-r+1; i++) cnt += spl(n-i, r-1);
		splMap[n][r] = cnt;
		return cnt;
	}
	
	private int getCount2(int b, int w, int n){
		if(b == 0 || w == 0 || n == 0) return 1;
		if(n == 1) return 2;
		return getCount2(b-1, w, n-1) + getCount2(b, w-1, n-1);
	}
	*/
	
	private int B;
	private int W;
	private int K;
	private int I;
	
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		FileReader fr = new FileReader("/Users/teuskim/codejam/C-large-practice.in.txt");
		BufferedReader br = new BufferedReader(fr);
		numberOfCases = Integer.valueOf(br.readLine());
		
		// make output
		File file = new File("/Users/teuskim/codejam/C-large-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int i=0; i<numberOfCases; i++){
			
			String[] strs = br.readLine().split(" ");
			B = Integer.parseInt(strs[0]);
			W = Integer.parseInt(strs[1]);
			K = Integer.parseInt(strs[2]);
			I = Integer.parseInt(strs[3]);

			String result = ""+getCount(1);
			fw.write("Case #"+(i+1)+": "+result+"\n");
			System.out.println("Case #"+(i+1)+": "+result);
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	private int comb(int n, int r){
		if(r == 1) return n;
		if(n == r || r == 0) return 1;
		if(n < r) return 0;
		if(combMap[n][r] != 0) return combMap[n][r];
		int res = (comb(n-1, r-1) + comb(n-1, r)) % mod;
		combMap[n][r] = res;
		return res;
	}
	
	private int getCount(int i){
		int bmax = B-i+1;
		int wmax = W-i+1;
		int k = K-i+1;
		if(bmax > k) bmax = k;
		if(bmax < 0) bmax = 0;
		if(wmax > k) wmax = k;
		if(wmax < 0) wmax = 0;
		
		if(bmax+wmax < k)
			return 0;
		if(i < I && (bmax == 0 || wmax == 0))
			return 0;
		if(i == I)
			return (comb(K-I, bmax) + comb(K-I, wmax)) % mod;
		
		return (2*getCount(i+1)) % mod;
	}
	
	public static void main(String[] args){
		System.out.println("start!");
		try{
			
			new CodeJamKorea2012_1_C().goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("end!");
	}
}
