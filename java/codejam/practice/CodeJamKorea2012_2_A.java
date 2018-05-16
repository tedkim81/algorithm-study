package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;

/**
 * Code Jam Korea 2012 본선 2차 라운드 : 문제 A 전장
 * 아군장수 N명과 적군장수 N명이 일렬로 늘어서 각각 1:1로 싸울때 전투결과가 클수록 이길 확률이 높아진다고 할 경우 주어진 P를 이용하여 아군장수를 최적으로 줄을 세웠을때 전투결과의 최대값을 구하는 문제
 * 2013년 2월 26일 오전 3:18:59
 * 
 * 문제이해 6분, 해결방법 24분, 코딩 38분, 그리고 오답!
 * 이번엔 문제가 꽤나 쉬워 보였으나 역시 또 틀리고 말았다. 
 * 생각한 해결방법은 간단했다. 매치시킬 수 있는 가장 큰 P에 대하여 처리하고 둘 중 작은수만큼을 양측에서 제거한다. 그리고 모두 제거될때까지 반복하는 것이었다.
 * 결과물 자체만으로는 문제점을 찾을 수 없었다. 그래서 타참가자의 코드를 이용 정답인 결과물을 얻었고 비교해 본 결과 문제점을 발견할 수 있었다.
 * 무조건 가장 큰 P값을 우선으로 할 경우 이후 매치에서 작은수를 선택하는 경우가 생긴다. 첫번째 경우에서 뭘 선택하느냐에 따라 두번째 경우에서의 선택의 폭이 달라지기 때문에 탐욕법으로 선형시간에 풀 수 있는 문제는 아니었던 것이다.
 * 
 * 다시 생각한 방법은, fighter라는 그래프 상태값을 깊이우선탐색하여 최대값을 찾는 것이었다. 그리고, 생각보다 간단하게 small output은 구했다.
 * 여기서 한가지 명심하자. 문제를 처음 접했을 때는 small input에 대해서만 집중하고 small output을 꼭 먼저 구하도록 하자.
 * large input을 고려하여 알고리즘이 직관적이지 않게 되면 output이 틀릴 경우 이유를 알기가 너무 어려워지기 때문이다.
 * 최대한 직관적으로 단순한 알고리즘으로 small output을 구하고, large input을 위해 알고리즘을 변형한 후에 small output으로 검증하는 방식을 취하자.
 * 
 * large input에 대해 확인을 해보니 단순히 숫자의 범위가 커졌을 뿐 경우의 수 자체가 커지지는 않는다는 것을 알 수 있었다.
 * 따라서 long 타입을 모두 BigDecimal 로 바꾸고 large output을 구할 수 있었다.
 * 
 * 이번 문제는 꽤 쉬운 문제였다. 그러나 괜히 어렵게 생각했다가 첫 시도를 오답처리해 버리고 말았다. 
 * 다소 헷갈리는 상황에서 "이러면 맞겠지"하는 애매한 판단으로 나중에 디버깅조차 어렵게 만드는 일이 없어야 한다.
 * 증명은 절대 대충 넘기지 말자. 꼭 그 대충 넘긴 부분이 발목을 잡게 되어 있다.
 */
public class CodeJamKorea2012_2_A {
	
	private BigDecimal[][] P = new BigDecimal[4][7];
	private BigDecimal zero = new BigDecimal(0);
	
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		FileReader fr = new FileReader("/Users/teuskim/codejam/A-large-practice.in.txt");
		BufferedReader br = new BufferedReader(fr);
		numberOfCases = Integer.valueOf(br.readLine());
		
		// make output
		File file = new File("/Users/teuskim/codejam/A-large-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int i=0; i<numberOfCases; i++){
			
			String[] strs = br.readLine().split(" ");
//			int N = Integer.parseInt(strs[0]);
			BigDecimal[] fighter = {null,
				new BigDecimal(strs[1]),
				new BigDecimal(strs[2]),
				new BigDecimal(strs[3]),
				new BigDecimal(strs[4]),
				new BigDecimal(strs[5]),
				new BigDecimal(strs[6])
			};
			for(int j=0; j<3; j++){
				strs = br.readLine().split(" ");
				for(int k=0; k<3; k++){
					P[j+1][k+4] = new BigDecimal(strs[k]);
				}
			}
			
			String result = ""+fight(fighter);
			fw.write("Case #"+(i+1)+": "+result+"\n");
			System.out.println("Case #"+(i+1)+": "+result);
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	private BigDecimal fight(BigDecimal[] fighter){
		if(fighter[1].add(fighter[2]).add(fighter[3]).equals(zero) == true) return zero;
		
		BigDecimal result = new BigDecimal("-1000000000000000000");
		for(int i=1; i<=3; i++){
			for(int j=4; j<=6; j++){
				if(fighter[i].compareTo(zero) > 0 && fighter[j].compareTo(zero) > 0){
					BigDecimal min = fighter[i].min(fighter[j]);
					fighter[i] = fighter[i].subtract(min);  fighter[j] = fighter[j].subtract(min);
					result = result.max(P[i][j].multiply(min).add(fight(fighter)));
					fighter[i] = fighter[i].add(min);  fighter[j] = fighter[j].add(min);
				}
			}
		}
		return result;
	}
	
	public static void main(String[] args){
		System.out.println("start!");
		try{
			
			new CodeJamKorea2012_2_A().goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("end!");
	}
	
	public void check(boolean isRight, String log){
		System.out.println(log);
		if(isRight == false){
			System.out.println("exit!");
			System.exit(0);
		}
	}
	
	public void print(String str){
		System.out.println(str);
	}
	
	
	/* 오답처리된 첫번째 시도
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
			int[] fighter = {0,
				Integer.parseInt(strs[1]),
				Integer.parseInt(strs[2]),
				Integer.parseInt(strs[3]),
				Integer.parseInt(strs[4]),
				Integer.parseInt(strs[5]),
				Integer.parseInt(strs[6])
			};
			TreeSet<P> pset = new TreeSet<P>();
			for(int j=1; j<=3; j++){
				strs = br.readLine().split(" ");
				for(int k=4; k<=6; k++) pset.add(new P(j, k, Integer.parseInt(strs[k-4])));
			}
			
			long result = 0;
			int over = N;
			while(over > 0){
				for(P p : pset){
					if(fighter[p.our] > 0 && fighter[p.enemy] > 0){
						if(fighter[p.our] < fighter[p.enemy]){
							result += (long)p.num * fighter[p.our];
							over -= fighter[p.our];
							fighter[p.enemy] = fighter[p.enemy] - fighter[p.our];
							fighter[p.our] = 0;
						}
						else{
							result += (long)p.num * fighter[p.enemy];
							over -= fighter[p.enemy];
							fighter[p.our] = fighter[p.our] - fighter[p.enemy];
							fighter[p.enemy] = 0;
						}
					}
				}
			}
			
			fw.write("Case #"+(i+1)+": "+result+"\n");
			System.out.println("Case #"+(i+1)+": "+result);
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	private class P implements Comparable<P>{
		private int our;
		private int enemy;
		private int num;
		
		public P(int our, int enemy, int num){
			this.our = our;
			this.enemy = enemy;
			this.num = num;
		}
		
		@Override
		public int compareTo(P o) {
			return o.num-num;  // 내림차순
		}
	}
	*/
}
