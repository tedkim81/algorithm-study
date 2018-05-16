package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Round 1A 2008 : Problem B. Milkshakes
 * 2012년 12월 31일 오후 11:05:07
 * 밀크쉐이크의 종류가 N개이고 각각은 malted/unmalted 두가지 타입이 될 수 있다.
 * 손님들은 1가지 이상의 취향이 있고, 모든 손님들을 만족시킬수 있도록 하기 위해 N가지 중 최소한으로 malted 한다.
 * 
 * 일단 영어로 된 문제 지문을 이해하는데 굉장히 오랜 시간이 걸렸다. ( at most 의 의미를 제대로 파악하지 못함, 이해하는데 거의 하루정도 소요 )
 * 문제 이해후 첫번째 접근방식으로, 첫번째와 두번째를 계산하여 결과물을 얻고 그 결과물과 세번째를 계산하여 결과물을 얻는 식의 방식으로 고민을 했다.
 * 그러나 이러한 방식으로 하면, 결과물의 경우의 수가 2개 이상이 되고 상황에 따라 너무 많아질 수도 있었다. 
 * 이때 다른 방법을 찾았어야 했으나, 많아지는 결과물이 뭔가 다시 수렴할 수도 있지 않을까 하고 집착을 했고 여기서 상당한 시간이 소모되었다.
 * 그러다가 뭔가 행렬계산처럼 한번에 풀 수 있는 방법이 있지 않을까 고민했으나 첫번째 자리 계산할때 두번째 자리의 값에 따라 첫번째 자리 값이 변경될 수 있음을 보고 다른 방법을 찾았다.
 * 
 * 결국 마지막에 문제에 대하여 제대로 접근했다.
 * 0으로 채워졌거나 혹은 값이 없는 결과물을 시작으로 해서 손님 취향을 하나하나 비교하는 것이다.
 * 결과물에 1을 추가해야 하는 상황에 다시 처음부터 확인하는 절차를 생각했었으나 일단 1을 추가하면서 한바퀴 돌고 다돌고 나서 다시 한바퀴 돌며 확인하면 될것이라고 착각(!!)했다.
 * 그러나 결과물이 바뀌면서 이전에 확인통과했던 사항이 통과하지 못하는 상황이 될 수 있고 이것이 통과불가=불가능 이 아니고 다시 변경하면 가능하다는 것을 뒤늦게 깨달았다.
 * ( 이것을 몰라서 답안제출시 incorrect 나고 원인을 몰라 정답을 인터넷에서 찾아 비교하여 문제를 확인했다. )
 * 
 * 해결방식을 찾는 과정에서 뭔가 찜찜하다 싶으면 해결하려고 집착하기보다 안된다는 것을 증명하는 방향으로 접근할 필요가 있다.
 * 시간 낭비를 줄이는 것이 중요하다.
 * 
 * 이 문제는 satisfiability problem 이라고 한다. 이것을 미리 알았더라면 문제를 이해하고 해결하는데 훨씬 수월했을 수 있다.
 * 대학수학 및 알고리즘 학습이 필요하다.
 * 실행시간? O(n) 뭐 이런 식의 표현에 익숙해지자.
 */
public class CodeJam2008_1A_B {
	
	private static void goodluck() throws Exception {
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
			
			int flavorNum = Integer.parseInt(br.readLine());
			int userCnt = Integer.parseInt(br.readLine());
			Map<Integer, Integer> map = new HashMap<Integer, Integer>();
			List<Map<Integer, Integer>> umapList = new ArrayList<Map<Integer,Integer>>();
			for(int j=0; j<userCnt; j++){
				String[] strs = br.readLine().split(" ");
				int pairCnt = Integer.parseInt(strs[0]);
				int l = 0;
				Map<Integer, Integer> umap = new HashMap<Integer, Integer>();
				for(int k=0; k<pairCnt; k++){
					int fn = Integer.parseInt(strs[++l]);  // flavors number
					int malt = Integer.parseInt(strs[++l]);  // malted:1, unmalted:0
					umap.put(fn, malt);
				}
				umapList.add(umap);
			}
			boolean impossible = false;
			for(int j=0; j<userCnt; j++){
				Map<Integer, Integer> umap = umapList.get(j);
				int fnOne = 0;  // position of 1
				boolean change = true;
				for(Entry<Integer, Integer> entry : umap.entrySet()){
					int fn = entry.getKey();
					int malt = entry.getValue();
					if(malt == 0 && map.containsKey(fn) == false){
						change = false;
						break;
					}
					else if(malt == 1){
						if(map.containsKey(fn) == false){
							fnOne = fn;
						}
						else{
							change = false;
						}
					}
				}
				if(change){
					if(fnOne > 0){
						map.put(fnOne, 1);
						j = -1;  // go first
					}
					else{
						impossible = true;
						break;
					}
				}
			}
			
			String result = "";
			if(impossible){
				result = "IMPOSSIBLE";
			}
			else{
				for(int j=1; j<=flavorNum; j++){
					if(map.containsKey(j))
						result += map.get(j)+" ";
					else
						result += "0 ";
				}
				result = result.substring(0, result.length()-1);
			}
			fw.write("Case #"+(i+1)+": "+result+"\n");
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	private static void goodluck2() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		FileReader fr = new FileReader("/Users/teuskim/codejam/B-small-practice.in.txt");
		BufferedReader br = new BufferedReader(fr);
		numberOfCases = Integer.valueOf(br.readLine());
		
		// make output
		File file = new File("/Users/teuskim/codejam/B-small-practice-2.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int i=0; i<numberOfCases; i++){
			
			int flavorNum = Integer.parseInt(br.readLine());
			int userCnt = Integer.parseInt(br.readLine());
			Map<Integer, Integer> map = new HashMap<Integer, Integer>();
			List<Map<Integer, Integer>> umapList = new ArrayList<Map<Integer,Integer>>();
			for(int j=0; j<userCnt; j++){
				String[] strs = br.readLine().split(" ");
				int pairCnt = Integer.parseInt(strs[0]);
				int l = 0;
				boolean hasZero = false;
				int fnOne = 0;
				Map<Integer, Integer> umap = new HashMap<Integer, Integer>();
				for(int k=0; k<pairCnt; k++){
					int fn = Integer.parseInt(strs[++l]);  // flavors number
					int malt = Integer.parseInt(strs[++l]);  // malted:1, unmalted:0
					umap.put(fn, malt);
					if(malt == 1){
						fnOne = fn;
					}
					else{
						hasZero = true;
					}
				}
				umapList.add(umap);
				if(fnOne > 0 && hasZero == false){
					map.put(fnOne, 1);
				}
			}
			boolean impossible = false;
			for(Map<Integer, Integer> umap : umapList){
				boolean success = false;
				for(Entry<Integer, Integer> entry : umap.entrySet()){
					int ekey = entry.getKey();
					int eval = entry.getValue();
					if((eval==0 && map.containsKey(ekey)==false)
							|| (eval==1 && map.containsKey(ekey) && map.get(ekey)==1)){
						success = true;
						break;
					}
				}
				if(success == false){
					impossible = true;
					break;
				}
			}
			
			String result = "";
			if(impossible){
				result = "IMPOSSIBLE";
			}
			else{
				for(int j=1; j<=flavorNum; j++){
					if(map.containsKey(j))
						result += map.get(j)+" ";
					else
						result += "0 ";
				}
				result = result.substring(0, result.length()-1);
			}
			fw.write("Case #"+(i+1)+": "+result+"\n");
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	public static void main(String[] args){
		try{
			
			goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
