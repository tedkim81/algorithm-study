package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;

/**
 * Code Jam Korea 2012 본선 라운드 : 문제 A 생존자
 * 
 * P와 S의 합으로 정렬을 해야한다는 것을 깨닫는 것이 핵심이다.
 * 정렬 없이 모든 조합을 확인해보는것도 방법이겠으나 그러면 수행시간이 너무 오래 걸리므로 (지수시간), 다항시간에 문제를 해결할 수 있도록
 * 음식의 순서를 적절히 만들어보려는 노력을 해야하는 것이다.
 * 어떤 음식을 나중에 먹는게 좋을까? 쉽게 생각하면 유효기간이 긴 음식을 나중에 먹는게 좋겠다고 생각할 수 있다.
 * 그러나 먼저 먹은 음식 중에 생존기간이 맨마지막 음식의 (유효기간+생존기간) 보다 긴 음식이 있다면 이것은 최대한 나중에 먹는것이 유리했을 것이다.
 * 그렇다면 생존기간으로 정렬하게 되면? 맨마지막 음식의 (유효기간+생존기간) 보다 긴 유효기간을 가진 음식이 있다면, 더 나중에 먹는것이 오래 산다. 따라서 이것도 답이 아니다.
 * 따라서 (유효기간+생존기간)으로 정렬을 하면 어떤 음식의 유효기간 또는 생존기간이 다음 음식의 (유효기간+생존기간)보다 클 경우가 없으므로 위의 문제되는 경우들은 발생하지 않는다.
 * 
 * 그리고 (유효기간+생존기간)이 가장 큰 값이 마지막에 와야 한다는 것을 귀류법으로 증명해보려면,
 * 해당값이 더 작은 음식을 마지막보다 뒤에 넣었을때가 앞에 넣었을때보다 더 살 수 있는 경우가 있을 수 없다는 것을 증명하면 된다.
 */
public class CodeJamKorea2012_1_A_othersolution {
	
	private int n;
	private PS[] data = new PS[1010];
	private boolean[] dynamic = new boolean[1000010];
	
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
			
			n = Integer.parseInt(br.readLine());
			int j,k;
			for(j=0; j<n; j++){
				String[] strs = br.readLine().split(" ");
				data[j] = new PS();
				data[j].p = Integer.parseInt(strs[0]);
				data[j].s = Integer.parseInt(strs[1]);
			}
			Arrays.sort(data, 0, n);  // 여기가 핵심!
			Arrays.fill(dynamic, false);
			
			dynamic[0] = true;
			int sum = 0;
			for(j=0; j<n; j++){
				for (k=Math.min(sum, data[j].p); k>=0; k--) {
					if (dynamic[k]) dynamic[k+data[j].s] = true;
				}
				sum += data[j].s;
			}
			
			for (j=sum; j>=0; j--) if (dynamic[j]) break;
			
			fw.write("Case #"+(i+1)+": "+j+"\n");
			System.out.println("Case #"+(i+1)+": "+j);
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	private class PS implements Comparable<PS> {
		private int p;
		private int s;
		
		@Override
		public int compareTo(PS o) {
			return (this.p+this.s) - (o.p+o.s);
		}

		@Override
		public boolean equals(Object obj) {
			if(obj instanceof PS == false) return false;
			PS other = (PS) obj;
			if(this.p == other.p && this.s == other.s) return true;
			return false;
		}

	}
	
	public static void main(String[] args){
		System.out.println("start!");
		try{
			
			new CodeJamKorea2012_1_A_othersolution().goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("end!");
	}
}
