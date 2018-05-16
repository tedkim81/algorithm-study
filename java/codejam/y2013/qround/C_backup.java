package gcj.y2013.qround;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Qualification Round 2013 : Problem C. Fair and Square
 */
public class C_backup {
	
	private static final double EPS = 1e-7;
	private long A,B;
	
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		Scanner sc = new Scanner(new File("/Users/teuskim/codejam/test.in.txt"));
		numberOfCases = sc.nextInt();
		
		// make output
		File file = new File("/Users/teuskim/codejam/test.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int casenum=0; casenum<numberOfCases; casenum++){
			
			A = sc.nextLong();
			B = sc.nextLong();
			
			String result = ""+getResult();
			fw.write("Case #"+(casenum+1)+": "+result+"\n");
			print("Case #"+(casenum+1)+": "+result);
		}
		fw.close();
	}
	
	private int getResult(){
		boolean[] checked = new boolean[10000001];
		int result = 0;
		for(int i=1; i<=10000000; i++){
			if(checked[i]) continue;
			checked[i] = true;
			
			if(isPalindrome(i)){
				int end = (int)Math.max(A, Math.ceil(Math.sqrt(B)));
				if(i == 1){
					if(i >= A && i <= B) result++;
				}
				else if(i <= end){
					long tmp = (long)i*(long)i;
					while(tmp <= B){
						if(tmp >= A && tmp <= B && isPalindrome(tmp)){
							result++;
							print("tmp:"+tmp+", "+(int)Math.sqrt(tmp));
							if(tmp < checked.length) checked[(int)tmp] = true;
						}
						if(tmp > tmp*tmp) break;
						tmp *= tmp;
					}
				}
				else{
					break;
				}
			}
		}
		return result;
	}
	
	/*
	private int getResult(){
		boolean[] checked = new boolean[10000000];
		int result = 0;
		long end = (int)Math.ceil(Math.sqrt(B));
		if(end <= A) end = B;
		for(long i=A; i<=end; i++){
			if(checked[(int)(i-A)]) continue;
			checked[(int)(i-A)] = true;
			
			if(isPalindrome(i)){
				if(i == 1) result++;
				else{
					double sqrt = Math.sqrt(i);
					if(sqrt < Math.floor(sqrt)+EPS){
						if(isPalindrome((int)sqrt)){
							result++;
							print("a:"+i);
						}
					}
					long tmp = i*i;
					while(tmp <= B){
						if(isPalindrome(tmp)){
							result++;
							print("b:"+tmp);
							if(tmp-A < checked.length) checked[(int)(tmp-A)] = true;
						}
						if(tmp > tmp*tmp) break;
						tmp *= tmp;
					}
				}
			}
		}
		return result;
	}
	*/
	
	private boolean isPalindrome(long num){
		String str = ""+num;
		int end = str.length()/2 + 1;
		for(int i=0; i<end; i++){
			if(str.charAt(i) != str.charAt(str.length()-i-1)){
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args){
		print("start!");
		try{
			
			new C_backup().goodluck();
			
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
