package gcj.y2013.qround;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Qualification Round 2013 : Problem A. Tic-Tac-Toe-Tomek
 */
public class A {
	
	private char[][] map;
	
	private void goodluck() throws Exception {
		// ready variables
		int numberOfCases;
		
		// get file
		Scanner sc = new Scanner(new File("/Users/teuskim/codejam/A-large.in.txt"));
		numberOfCases = sc.nextInt();
		
		// make output
		File file = new File("/Users/teuskim/codejam/A-large.out.txt");
		if(file.exists() == false)
			file.createNewFile();
		FileWriter fw = new FileWriter(file);
		for(int casenum=0; casenum<numberOfCases; casenum++){
			
			map = new char[4][4];
			for(int i=0; i<4; i++){
				String line = sc.next();
				for(int j=0; j<4; j++){
					map[i][j] = line.charAt(j);
				}
			}
			
			String result = getResult();
			fw.write("Case #"+(casenum+1)+": "+result+"\n");
			print("Case #"+(casenum+1)+": "+result);
		}
		fw.close();
	}
	
	private String getResult(){
		boolean existSpace = false;
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				if(check('X', i, j)) return "X won";
				if(check('O', i, j)) return "O won";
				if(map[i][j] == '.') existSpace = true;
			}
		}
		if(existSpace) return "Game has not completed";
		return "Draw";
	}
	
	private boolean check(char who, int row, int col){
		int cnt = 0;
		for(int i=0; i<4; i++){
			if(map[row][i] == who || map[row][i] == 'T') cnt++;
		}
		if(cnt == 4) return true;
		
		cnt = 0;
		for(int i=0; i<4; i++){
			if(map[i][col] == who || map[i][col] == 'T') cnt++;
		}
		if(cnt == 4) return true;
		
		if(row == col){
			cnt = 0;
			for(int i=0; i<4; i++){
				if(map[i][i] == who || map[i][i] == 'T') cnt++;
			}
			if(cnt == 4) return true;
		}
		
		if(row == 3-col){
			cnt = 0;
			for(int i=0; i<4; i++){
				if(map[i][3-i] == who || map[i][3-i] == 'T') cnt++;
			}
			if(cnt == 4) return true;
		}
		
		return false;
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
