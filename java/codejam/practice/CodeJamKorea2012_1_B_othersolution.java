package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * CodeJamKorea2012_1_B_othersolution.cpp 를 자바로 옯긴코드
 * 그러나 예제입력에 대한 출력이 일치하지 않는다. 뭔가 잘못 옮긴듯
 * 코드를 이해하고 해결방법을 익혀야 하겠으나.. 이 문제에 너무 많은 시간을 소비했으니 다음기회에 다시 풀어보자.
 */
public class CodeJamKorea2012_1_B_othersolution {
	
	private char[][] B = new char[111][111];
	private int[] dx = {0,1,1,1,0,-1,-1,-1};
	private int[] dy = {1,1,0,-1,-1,-1,0,1};
	private double ans = 0;
	private double[][] gat = new double[4][4];
	private double[] all = new double[4];
	
	private void goodluck() throws Exception {
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
			int N = Integer.parseInt(strs[0]);
			int M = Integer.parseInt(strs[1]);
			int K = Integer.parseInt(strs[2]);
			int S4 = Integer.parseInt(strs[3]);
			int S3 = Integer.parseInt(strs[4]);
			int S2 = Integer.parseInt(strs[5]);
			for(int ni=0; ni<N; ni++){
				String str = br.readLine();
				for(int mi=0; mi<M; mi++){
					B[ni][mi] = str.charAt(mi);
				}
			}
			
			for(int ki=1; ki<=K; ki++){
				for(int ni=0; ni<N; ni++){
					for(int mi=0; mi<M; mi++){
						if(B[ni][mi] == (char)(ki+'0') || B[ni][mi] == '?'){
							List<Double>[] P = new List[8];
							double p = 0;
							for(int d=0; d<8; d++){
								P[d] = new ArrayList<Double>();
								int x = ni;  int y = mi;  p = 1;
								P[d].add(1.0);
								for(int l=1; l<4; l++){
									x += dx[d];  y += dy[d];
									if(x<0 || y<0 || x>=N || y>=M || (B[x][y] != (char)(ki+'0') && B[x][y] != '?')){
										P[d].add(0.0);
										break;
									}
									if(B[x][y] == '?'){
										P[d].set(l-1, P[d].get(l-1) * ((K-1.0)/K));
										p *= 1.0 / K;
									}
									else{
										P[d].set(l-1, 0.0);
									}
									P[d].add(p);
								}
							}
							for(int d=0; d<8; d++){
								while(P[d].size() <= 4) P[d].add(0.0);
							}
							
							double point = 0;
							for(int d=0; d<4; d++) for(int l=0; l<4; l++) gat[d][l] = 0;
							for(int d=0; d<4; d++){
								for(int x=0; x<4; x++){
									for(int y=0; y<4; y++){
										int c = x + y + 1;
										if (c >= 4) gat[d][3] += P[d].get(x) * P[d+4].get(y);
										if (c == 3) gat[d][2] += P[d].get(x) * P[d+4].get(y);
										if (c == 2) gat[d][1] += P[d].get(x) * P[d+4].get(y);
										if (c <= 1) gat[d][0] += P[d].get(x) * P[d+4].get(y);
									}
								}
							}
							for(int l=0; l<4; l++) all[l] = 1;
							for(int d=0; d<4; d++){
								all[2] = all[2] * (gat[d][0] + gat[d][1] + gat[d][2]);
								all[1] = all[1] * (gat[d][1] + gat[d][0]);
								all[0] = all[0] * gat[d][0];
							}
							all[3] -= all[2];
							all[2] -= all[1];
							all[1] -= all[0];
							
							if (B[ni][mi] == '?') p = 1.0 / K;
							else p = 1;

							point = all[1] * S2 + all[2] * S3 + all[3] * S4;
							ans += point * p;
						}
					}
				}
			}
			
			String result = String.format("%.7f", ans);
			fw.write("Case #"+(i+1)+": "+result+"\n");
			System.out.println("Case #"+(i+1)+": "+result);
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	public static void main(String[] args){
		System.out.println("start!");
		try{
			
			new CodeJamKorea2012_1_B_othersolution().goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("end!");
	}
}
