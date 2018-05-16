package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Code Jam Korea 2012 결선 라운드 : 문제 C 출근 전쟁
 * N개의 길목에 대하여 출발지에서 도착지까지 이동하는데 각 구간마다 P의 확률로 D만큼의 delay가 발생할 경우 이동 최소시간을 구하는 문제이다.
 * 2013년 3월 15일 오후 6:04:58
 * 
 * 문제를 제대로 이해하지 못하여 1시간 정도 헤매다가 solution을 확인했다.
 * 각 구간별로 이동시간 기대값을 구한후 다음 구간의 출발시간을 정한후 다시 기대값을 구하는 방식으로 도착지까지 반복하면 될 것이라 생각했다.
 * A를 지연이 없을때 걸리는 기본이동시간이라고 가정했을때 구간의 이동시간은 sigma[i=0~inf] P^i(1-P)(A+iD) 이고 이를 정리하면 A+(DP/(1-P)) 였다.
 * test input의 첫번째 케이스는 위의 식으로 답이 나오는데, 두번째 케이스에서는 0,1 구간에 66분이 나오고 따라서 1,2 구간에서는 125분에 출발하게 되고
 * 위 식을 적용하면 192분이 나온다. 그러나 답은 162이다. 무엇이 문제일까?
 * 한 가지 간과한 사실이 있었다. 위 방식이 제대로 적용되려면 각 구간이 서로 독립적이어야 한다. 그러나 실제로는 전 구간에서의 시간에 따라 다음 구간 출발시간이 정해진다.
 * test input 두번째 케이스에서 첫 구간의 기대값이 66이라 다음 구간에서 출발시간을 125분으로 생각했지만 첫 구간의 값에 따라 65분일 수도 있고 185분일 수도 있는 것이다.
 * 따라서 sigma[i=0~inf] P^i(1-P)(A+iD) 이 식을 구간별이 아닌 전체구간에 대한 식으로 변형해야 한다.
 *  => sigma[i=0~inf] P^i(1-P)(A+iD + (다음길목에서 도착지까지의 이동시간))
 * 이 식은 정리하기가 어려워서 small input에 대해서는 적당한 수(5000정도?)만큼 반복계산하여 근사값을 구하는 방식으로 풀 수 있지만 large input에 대해서는 더 고민해봐야 할 듯 하다.
 * 
 * TODO: 추후 다시 풀어보도록 하자.
 */
public class CodeJamKorea2012_F_C_othersolution {
	
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
			
			int n,m,home, office;
			String[] strs = br.readLine().split(" ");
			n = Integer.parseInt(strs[0]);
			m = Integer.parseInt(strs[1]);
			home = Integer.parseInt(strs[2]);
			office = Integer.parseInt(strs[3]);
			
			Road[] dat = new Road[103];
			for(int j=0; j<m; j++){
				strs = br.readLine().split(" ");
				dat[j] = new Road();
				dat[j].a = Integer.parseInt(strs[0]);
				dat[j].b = Integer.parseInt(strs[1]);
				dat[j].s = Integer.parseInt(strs[2]);
				dat[j].r = Integer.parseInt(strs[3]);
				dat[j].d = Integer.parseInt(strs[4]);
				dat[j].p = Integer.parseInt(strs[5]);
			}
			
			double[][] dt = new double[103][60];
			for(int j = 0; j < n; j ++)
			{
				for(int k = 0; k < 60; k ++)
				{
					dt[j][k] = -1;
				}
			}
			for(int j = 0; j < 60; j ++)
			{
				dt[office][j] = 0;
			}
			for(int j = n - 2; j >= 0; j --)
			{
				Road info = null;
				for(int k = 0 ; k < m; k ++)
				{
					if(dat[k].a == j) {
						info = dat[k];
					}
				}
				if(info == null) continue;
				double p = info.p / 100.0;
				if(info.p == 100) continue; // 진행불가
				for(int k = 0; k < 60; k ++) // 시작시간
				{
					double myest = 0;
					int adding = ((info.s - k) + 60) % 60;
					double prob = 1;
					if(dt[j+1][0] < 0 ) continue; // 불가능
					for(int l = 0; l < 5000; l ++) // 반복
					{
						myest += prob * (1-p) * (adding + info.r + l * info.d + dt[j+1][(info.s + info.r + l * info.d)%60]);
						prob *= p;
					}
//					dt[j][k] = myest / (1 - prob);
					dt[j][k] = myest;
				}
			}
			
			String result;
			if(dt[home][0] < 0) {
				result = "-1";
			}else
			{
				result = String.format("%.10f", dt[home][0]);
			}
			fw.write("Case #"+(i+1)+": "+result+"\n");
			System.out.println("Case #"+(i+1)+": "+result);
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	private class Road {
		private int a,b,s,r,d,p;
	}
	
	public static void main(String[] args){
		System.out.println("start!");
		try{
			
			new CodeJamKorea2012_F_C_othersolution().goodluck();
			
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
