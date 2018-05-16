package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Code Jam Korea 2012 결선 라운드 : 문제 D 창문 깨기
 * small input에 대해서만 실행 가능
 * 
 * 창문이 깨지지 않을 확률을 구하여 1-p로 창문이 깨질 확률을 계산한다.
 * 창문갯수가 0일때부터 K개일때까지, i와 i+1에 대한 관계식을 이용하여 순차적으로 d[][][]를 계산한 후 마지막 값인 d[K][M][N]이 창문이 깨지지 않을 확률이라고 보고 있다.
 * 그러나 d[i+1][nextdefend][useattack + k]을 구하는 과정인 그 관계식이 도저히 이해가 되지 않는다.
 * 
 * TODO: 추후 다시 확인해보자. 
 * Floyd-Warshall 알고리즘과 모양이 유사해 보이는데, 해당 방법을 이해하면 문제 해결시 구사할 수 있는 무기가 하나 더 늘 수 있다는 생각이 든다.
 */
public class CodeJamKorea2012_F_D_othersolution {
	
	private int K,N,M,H;
	private int ptri[][] = new int[256][256];
	private double d[][][];
	
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		calcptri();
		
		// get file
		FileReader fr = new FileReader("/Users/teuskim/codejam/test.in.txt");
		BufferedReader br = new BufferedReader(fr);
		numberOfCases = Integer.valueOf(br.readLine());
		
		// make output
		File file = new File("/Users/teuskim/codejam/test.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int casenum=0; casenum<numberOfCases; casenum++){

			String[] strs = br.readLine().split(" ");
			K = Integer.parseInt(strs[0]);  // 창문수
			N = Integer.parseInt(strs[1]);  // 악당수 ( 돌던지는 수 )
			M = Integer.parseInt(strs[2]);  // 일꾼수 ( 창문 강화 수 )
			H = Integer.parseInt(strs[3]);  // 이미 한번 강화된 창문 수
			d = new double[25][32][32];  // d[K][M][N] => K개 창문에 M번 강화하고 N번 돌던져서 하나도 안깨질 확률

			double ans = 0;
			d[0][0][0] = 1;
			for(int i = 0;i < K; i++)
			{
				for(int j = 0;j <= M; j ++)
				{
					for(int k = 0;k <= N; k ++)
					{
						for(int nextdefend = j; nextdefend <= M; nextdefend ++)
						{
							int totaldefend = (nextdefend - j) + (i < H ? 1 : 0);
							for(int useattack = 0; useattack <= totaldefend && useattack + k <= N; useattack ++)
							{
								double temp = d[i][j][k]
										* ptri[M-j][nextdefend-j] * Math.pow(1.0/K, nextdefend - j)
										* ptri[N-k][useattack] * Math.pow(1.0/K, useattack);
								d[i+1][nextdefend][useattack + k] += temp;
								
//								if(i+1==3 && nextdefend==1 && useattack+k==1)
//								if(i==0 && j==0 && k==0)
								print("d["+(i+1)+"]["+nextdefend+"]["+(useattack+k)+"] += d["+i+"]["+j+"]["+k+"]"
										+" * "+(M-j)+"C"+(nextdefend-j)+" * (1/"+K+")^"+(nextdefend-j)
										+" * "+(N-k)+"C"+(useattack)+" * (1/"+K+")^"+(useattack)
										+" ==> "+temp);
							}
						}
					}
				}
			}
			ans = 1-d[K][M][N];
			String result = String.format("%.8f", ans);
			fw.write("Case #"+(casenum+1)+": "+result+"\n");
			System.out.println("Case #"+(casenum+1)+": "+result);
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	void calcptri()
	{
		ptri[0][0] = 1;
		for(int i = 1;i < 256;i ++)
		{
			ptri[i][0] = 1;
			for(int j = 1; j <= i; j ++)
			{
				ptri[i][j] = ptri[i-1][j-1] + ptri[i-1][j];
			}
		}
	}
	
	public static void main(String[] args){
		System.out.println("start!");
		try{
			
			new CodeJamKorea2012_F_D_othersolution().goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("end!");
	}
	
	public void check(boolean isRight, String log){
		if(isRight == false){
			System.out.println("exit: "+log);
			System.exit(0);
		}
	}
	
	public void print(String str){
		System.out.println(str);
	}
}
