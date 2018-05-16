package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

/**
 * Round 1B 2010 : Problem B. Picking Up Chicks
 * 2013년 1월 19일 오후 12:31:55
 * 닭들이 일렬로 이동하는데 시작점이 각각 다르고 도착점이 같으며 각자의 속도가 있다. 
 * N마리중 최소 K마리 이상이 T시간 이내에 들어오도록 하기 위해 빠른 닭이 느린 닭을 추월하도록 도와주는(swap) 작업은 최소 몇번을 해야할까를 묻는 문제이다.
 * 
 * 문제를 이해하는데는 약30분정도, 해결방법을 생각하는데는 약30분정도, 첫코딩에는 약1시간 정도 걸렸다. 그리고.. 틀렸다..
 * input에서 4번째 케이스에 B가 1000000000이라 시간안에 들어갈 수 있는 닭이 없는데 K가 0이라서 output이 0으로 나온것을 보고
 * 시간안에 들어갈 수 있는 닭이 없을때 IMPOSSIBLE 을 출력하도록 수정했다. ( 정말 치명적인 실수였다. )
 * 그리고도 문제가 해결되지 않아 오류를 더 찾았고 추가적으로 수정했다.
 * 
 * 결국 해답을 보며 내 코드의 문제를 찾아보려 했고 설명부분을 읽을때까지는 문제를 찾을 수 없었다.
 * 그러다가 해답의 코드예제를 보고 K에 대한 나의 실수를 찾을 수 있었다. K가 0이면 output은 무조건 0이 된다..
 * 
 * 문제는 가능하면 수학적으로 접근하자. 0이라는 수는 일상적인 직관에 오해를 불러오는 경우가 꽤 있는듯 하다.
 * 들어올수 있는 닭이 하나도 없어서 불가능이라고 생각한 것은 그저 일상적인 직관에 의한 결과였다. K가 0이라면 조건은 참이 된다는 것을 잠시 잊었다.
 * 쉽게 풀 수 있는 문제였으나 한가지 착각이 오답으로 만들었다. 주의하자.
 */
public class CodeJam2010_1B_B {
	
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
			
			String[] strs = br.readLine().split(" ");
			int N = Integer.parseInt(strs[0]);
			int K = Integer.parseInt(strs[1]);
			int B = Integer.parseInt(strs[2]);
			int T = Integer.parseInt(strs[3]);
			
			Chick.b = B;
			
			List<Chick> list = new ArrayList<Chick>();
			String[] xs = br.readLine().split(" ");
			String[] vs = br.readLine().split(" ");
			for(int j=0; j<N; j++){
				Chick chick = new Chick(Integer.parseInt(xs[j]), Integer.parseInt(vs[j]));
				list.add(chick);
			}

			TreeSet<Chick> set = new TreeSet<Chick>();
			for(int j=0; j<N; j++){
				Chick chick = list.get(j);
				if(chick.isSuccess(T) == false) continue;
				for(int k=j+1; k<N; k++){
					Chick chick2 = list.get(k);
					if(chick.compareTime(chick2) < 0 && chick2.isSuccess(T) == false)
						chick.swapCnt++;
				}
				set.add(chick);
			}
			
			String result;
//			if(set.size() > 0 && set.size() >= K){    // 이게 치명적인 실수였다!!
			if(set.size() >= K){
				int swapCnt = 0;
				Iterator<Chick> it = set.iterator();
				for(int j=0; j<K; j++){
					Chick chick = it.next();
					swapCnt += chick.swapCnt;
				}
				result = ""+swapCnt;
			}
			else{
				result = "IMPOSSIBLE";
			}
			
			fw.write("Case #"+(i+1)+": "+result+"\n");
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	public static class Chick implements Comparable<Chick> {
		public static int b;
		public int v;
		public int x;
		public int swapCnt;
		
		public Chick(int x, int v){
			this.x = x;
			this.v = v;
			this.swapCnt = 0;
		}
		
		public int compareTime(Chick o) {
			int result = (int)((b-this.x)*(long)o.v - (b-o.x)*(long)this.v);
			return result;
		}
		
		@Override
		public int compareTo(Chick o) {
			if(this.swapCnt == o.swapCnt)
				return -1;
			return this.swapCnt - o.swapCnt;
		}

		public boolean isSuccess(int t){
			return v*t+x >= b;
		}
	}
	
	public static void main(String[] args){
		System.out.println("start!");
		try{
			
			goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("end!");
	}
}
