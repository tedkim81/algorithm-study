package gcj.y2013.round1a;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Problem A. Bullseye
 * 2x^2 + (2r-1)x <= t 라는 부등식을 찾는데에 꽤 많은 시간을 소비하고 나서, 만족하는 x를 찾기위해 근의 공식을 사용하려 하다가 결국 오답처리 되었다.
 * 틀리고 나서보니 당연히 이분법을 사용했어야 했는데 아쉽다..
 * 이분법을 사용함에 있어서도 여전히 주의할 사항은 값을 계산할때 값의 표현범위를 넘어가 버리는 일이 없도록 하는 것이다.
 * 위 식은 그대로 사용하게 되면 x^2에서 범위를 초과한다. 따라서 !( 2x + (2r-1) > t/x ) 로 변형하여 사용해야 한다.
 */
public class A {
	
	private long r,t;
	private long maxr;
	
	private void goodluck() throws Exception {
		// ready variables
		String path = "/Users/teuskim/Documents/workspace/android-src/CodeJam/src/";
		int numberOfCases;
		maxr = (long)Math.pow(10, 18) + 1;
		
		// get file
		Scanner sc = new Scanner(new File(path+"A-large-practice.in.txt"));
		numberOfCases = sc.nextInt();
		
		// make output
		File file = new File(path+"A-large-practice.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int casenum=0; casenum<numberOfCases; casenum++){
			
			r = sc.nextLong();
			t = sc.nextLong();
			
			String result = ""+getResult();
			fw.write("Case #"+(casenum+1)+": "+result+"\n");
			print("Case #"+(casenum+1)+": "+result);
		}
		fw.close();
	}
	
	private long getResult(){
		long min = 1;
		long max = maxr;
		while(min+1 < max){
			long mid = (min+max) / 2;
			if(2*mid + (2*r-1) > t/mid) max = mid;
			else min = mid;
		}
		return min;
	}
	
	public static void main(String[] args){
		print("start!");
		try{
			
			new A().goodluck();
			
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
