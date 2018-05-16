package gcj.y2013.qround;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Qualification Round 2013 : Problem C. Fair and Square
 */
public class C_backup2 {
	
	private long A,B;
	private boolean[] checked = new boolean[10000001];
	
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		Scanner sc = new Scanner(new File("/Users/teuskim/codejam/C-large-1.in.txt"));
		numberOfCases = sc.nextInt();
		
		// make output
		File file = new File("/Users/teuskim/codejam/C-large-1.out.txt");
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
		int result = 0;
		for(int i=1; i<=10000000; i++){
			if(checked[i]){
				result++;
				continue;
			}
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
			
			new C_backup2().goodluck();
			
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
