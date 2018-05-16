package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;

/**
 * Code Jam Korea 2012 결선 라운드 : 문제 D 창문 깨기
 * large input에 대해서도 실행가능
 * 
 * 창문수,공격수,방어수가 주어질때 창문이 깨지지 않을 확률을 담고 있는 D[][][] 맵을 구한다. D1은 강화안된 경우이고, D2는 한번 강화된 경우이다.
 * 창문이 1개일때(i=0)부터 K개일때까지(i=K-1) 순차적으로 확률을 계산하여 담는다. 
 * i개의 창문에 대하여 확률이 계산되어 있다고 가정할때 창문 한개를 추가하여(총 i+1개의 창문) 여기에 i개 창문으로의 공격/방어를 옮겨오는 모든 경우에 대한 확률을 더하는 방식으로 계산한다.
 * 즉, i개 창문에 j-p개의 공격과 k-q개의 방어가 있었다면, i+1개에 대하여 i개쪽으로 j-p공격, k-q방어 보내고 추가된 한개에 p공격,q방어를 보내는 확률을 계산하여 더하는 것이다.
 * 여기서 p>q 라면 추가된 한개 창문이 무조건 깨지므로 p<=q 인 경우에 대해서만 확률을 계산하여 그 모든 경우에 대한 합을 구하면 된다.
 * 이러한 방식으로 D1, D2를 구하고 나서 K개 창문에 N공격, M방어시의 확률을 구해야 하는데 이때 H개 강화된 창문과 그렇지 않은 창문에 대하여 구분해서 계산해야 한다.
 */
public class CodeJamKorea2012_F_D_othersolution2 {
	
	double D1[][][] = new double[203][103][103];
	double D2[][][] = new double[203][103][103];

	double Comb[][] = new double[203][203];
	double Mul[] = new double[203], M1[] = new double[203], M2[] = new double[203];
	
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		setValues();
		
		// get file
		FileReader fr = new FileReader("/Users/teuskim/codejam/D-small-practice.in.txt");
		BufferedReader br = new BufferedReader(fr);
		numberOfCases = Integer.valueOf(br.readLine());
		
		// make output
		File file = new File("/Users/teuskim/codejam/D-small-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int casenum=0; casenum<numberOfCases; casenum++){
			
			String[] strs = br.readLine().split(" ");
			int K = Integer.parseInt(strs[0]);
			int N = Integer.parseInt(strs[1]);
			int M = Integer.parseInt(strs[2]);
			int H = Integer.parseInt(strs[3]);
			
			int i, j;
			double sol = 0.0;

			for(i=0;i<=N+M;i++){
				M1[i] = 1.0;
				M2[i] = 1.0;
				for(j=0;j<i;j++){
					M1[i] *= (double)(K-H) / (double)K;
					M2[i] *= (double)(H) / (double)K;
				}
			}
			for(i=0;i<=N;i++){
				for(j=0;j<=M;j++){
					double X, Y;
					if (K-H == 0){
						if (i == 0 && j == 0) X = 1.0;
						else X = 0.0;
					}
					else X = D1[K-H-1][i][j];

					if (H == 0){
						if (N-i == 0 && M-j == 0) Y = 1.0; 
						else Y = 0.0;
					}
					else Y = D2[H-1][N-i][M-j];
					
					double[] g = new double[4];
					g[0] = Comb[N][i];
					g[1] = M1[i+j];
					g[2] = Comb[M][j];
					g[3] = M2[N-i+M-j];
					Arrays.sort(g);
					
					sol += X*Y * (g[0]*g[3]) * (g[1]*g[2]);
				}
			}
			
			String result = String.format("%.8f", (1.0-sol));
			fw.write("Case #"+(casenum+1)+": "+result+"\n");
			print("Case #"+(casenum+1)+": "+result);
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	private void setValues(){
		Comb[0][0] = 1.0;
		for(int i=1;i<=201;i++){
			Comb[i][0] = 1.0;
			for(int j=1;j<=201;j++){
				Comb[i][j] = Comb[i-1][j] + Comb[i-1][j-1];
			}
		}
		
		for(int i=0;i<200;i++){  // for K 창문수 => i
			for(int j=0;j<=100;j++){  // for N 공격수 => j
				for(int k=0;k<=100;k++){  // for M 강화수 => k
					D1[i][j][k] = 0.0;
					D2[i][j][k] = 0.0;
					if (i == 0){
						if (k - j + 1 < 0) continue;
						D2[i][j][k] = 1.0;  // 이미 한번 강화된 경우, k-j+1>=0 이면 깨지지 않는다.
						if (k - j < 0) continue;
						D1[i][j][k] = 1.0;  // k-j>=0 이면 깨지지 않는다.
						continue;
					}
					// 창문 한개씩 늘려가며 순차적으로 확률곱을 계산한다.
					Mul[j+k] = 1.0;
					for(int p=0;p<j+k;p++) Mul[j+k] *= (double)i / (double)(i+1);
					for(int p=j+k-1;p>=0;p--) Mul[p] = Mul[p+1] / (double)i;
					for(int p=0;p<=j;p++){  // 공격
						for(int q=0;q<=k;q++){  // 방어
							//현재에 p개 던지고 q개 막음
							if (q - p < 0) continue;  // 공격이 더 늘어나는 상황은 왜 skip하는걸까?
							//j-p+k-q개만큼 i를 곱하고 j+k개만큼 i+1을 나눈다
							D1[i][j][k] += D1[i-1][j-p][k-q] * ((Mul[j-p+k-q] * Comb[j][p]) * Comb[k][q]);
						}
					}
					D2[i][j][k] = 0.0;
					for(int p=0;p<=j;p++){
						for(int q=0;q<=k;q++){
							//현재에 p개 던지고 q개 막음
							if (q - p + 1 < 0) continue;
							D2[i][j][k] += D2[i-1][j-p][k-q] * ((Mul[j-p+k-q] * Comb[j][p]) * Comb[k][q]);
						}
					}
				}
			}
		}
	}
	
	public static void main(String[] args){
		print("start!");
		try{
			
			new CodeJamKorea2012_F_D_othersolution2().goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		print("end!");
	}
	
	
	/**********************
	 * code for debugging *
	 **********************/
	
	public void check(boolean isRight, String log){
		if(isRight == false){
			print("exit: "+log);
			System.exit(0);
		}
	}
	
	public static void print(String str){
		System.out.println(str);
	}
	
	public static void print(int[] arr){
		if(arr == null) print("null");
		else{
			String str = "[";
			if(arr.length > 0){
				for(int i=0; i<arr.length; i++) str += arr[i]+",";
				str = str.substring(0, str.length()-1);
			}
			str += "]";
			print(str);
		}
	}
}
