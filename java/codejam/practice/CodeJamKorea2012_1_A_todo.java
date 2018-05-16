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
 * Code Jam Korea 2012 본선 라운드 : 문제 A 생존자
 * 무인도에 남아있는 음식들의 유통기한 P 와 섭취시 생존기간 S 가 주어질때 최대 생존기간을 구하는 문제
 * 
 * 문제이해 2분, 해결방법 약5분, 코딩에는 디버깅에 많은 시간을 쏟는 바람에 몇시간이 걸렸다.
 * 테스트 입력에 대하여 옳은 결과를 확인하고 어느 정도의 확신을 갖고 small output을 구했으나.. 실패..
 * 시간 절약을 위해 바로 다른 참가자의 코드를 이용해 정답을 확인했다.
 * 내가 생각한 해결방법은, 유효기간으로 정렬을 하고 top에서부터 생존기간을 재귀적으로 찾아가는 것이었다. ( 아래 liveTime 메소드 참조 )
 * 그러나 이것은 문제가 있었다. 정답을 만드는 조합의 순서가 정렬된 상태를 유지할 것이라고 전제를 했으나 음식의 생존기간은 정렬되지 않았기 때문에
 * 정렬상태의 아래 음식 다음에 윗 음식이 나올 수 있는 상황도 있었던 것이다.
 * 그렇다면 내가 다시 생각할 수 있는 방법은? 일단 다시 완전탐색으로 접근을 할 것이다. 완전탐색으로 문제를 풀면 small output은 구할 수 있을 것이다.
 * 그러나 large output은 구할 수 없겠지. 그래서 시간 절약을 위해 다른 참가자의 코드를 통해 해결방식을 알게 되었다.
 * 
 * 문제 해결이 어려울때, 지금의 접근방법이 정답으로 가는 올바른 길일 것이라는 무조건적 믿음을 버려야 한다.
 * 접근방법이 잘못되었다는 것을 증명해보고 잘못되었다고 판단되면 전혀 다른 각도에서 생각할 수 있는 능력을 길러야 한다.
 * 그리고 직관적으로 해결방법을 찾을 수 없는 문제가 많다. 이러한 문제들은 수학적/논리적인 증명을 통해 방법을 찾을 수 있어야 한다.
 * 다시 말해 직관적으로 이해가 되지 않더라도 수학적/논리적인 증명을 전개할 수 있어야 한다.
 * 
 * TODO: 다시 풀어보자!
 */
public class CodeJamKorea2012_1_A_todo {
	
	private List<PS> psList;
	
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
			
			int N = Integer.parseInt(br.readLine());
			TreeSet<PS> set = new TreeSet<PS>();
			for(int j=0; j<N; j++){
				String[] strs = br.readLine().split(" ");
				set.add(new PS(Integer.parseInt(strs[0]), Integer.parseInt(strs[1])));
			}
			Iterator<PS> it = set.iterator();
			psList = new ArrayList<PS>();
			while(it.hasNext()){
				psList.add(it.next());
			}
			System.out.println("list: "+psList);
			
			int result = liveTime(0, 0);
			fw.write("Case #"+(i+1)+": "+result+"\n");
			System.out.println("Case #"+(i+1)+": "+result);
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
//	private int liveTime(int position, int time){
//		if(position == psList.size()) 
//			return time;
//		
//		int livetime = 0;
//		for(int i=position; i<psList.size(); i++){
//			PS first = psList.get(i);
//			int j = i + 1;
//			for(; j<psList.size(); j++){
//				PS next = psList.get(j);
//				if(next.p >= time+first.s) break;
//			}
//			livetime = Math.max(livetime, liveTime(j, time+first.s));
//		}
//		return livetime;
//	}
	
	private int liveTime(int position, int time){
		if(position == psList.size()) return time;
		
		PS first = psList.get(position);
		position++;
		int livetime = 0;
		if(first.p >= time) livetime = liveTime(position, time+first.s);
		livetime = Math.max(livetime, liveTime(position, time));
		return livetime;
	}
	
	private class PS implements Comparable<PS> {
		private int p;
		private int s;
		
		public PS(int p, int s){
			this.p = p;
			this.s = s;
		}
		
		@Override
		public int compareTo(PS o) {
//			if(this.p == o.p)
//				return this.s - o.s;
//			return this.p - o.p;
			
			return (this.p+this.s) - (o.p+o.s);
		}

		@Override
		public boolean equals(Object obj) {
			if(obj instanceof PS == false) return false;
			PS other = (PS) obj;
			if(this.p == other.p && this.s == other.s) return true;
			return false;
		}

		@Override
		public String toString() {
			return "("+this.p+","+this.s+")";
		}
		
	}
	
	public static void main(String[] args){
		System.out.println("start!");
		try{
			
			new CodeJamKorea2012_1_A_todo().goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("end!");
	}
}
