package com.codejam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Round 1C 2009 : Problem B. Center of Mass
 * 2013년 1월 31일 오후 11시 58분
 * N마리의 개똥벌레가 있고 각각은 각자의 방향을 향해 일정한 속도로 움직일때 전체의 무게중심이 중심좌표에 가장 가까울때의 시간과 거리를 구하는 문제
 * 
 * 쉬운 문제였다. 문제이해에 7분, 해결방법에 15분, 그러나 코딩 및 정답제출에는 약 1시간이 걸렸다. 오답제출도 수차례 있었다.
 * 쉬운 문제는 이제 쉽게 풀때도 되지 않았나.. 아직 많이 부족하다..
 * 1차 코딩후 예제 입력 실행결과 오답. t가 모두 -1이 나왔다. 2차 방정식의 최소값을 구하는 문제였는데 근의 공식을 사용하는 것으로 착각했다.
 * f(x)=ax^2 + bx + c 의 최소값을 구해야하는데, ax^2 + bx + c = 0 을 만족하는 x값을 구하려고 했던 것이다.
 * 최소값 구하는 방식을 머리로 생각한 후 코딩했으나 예제 입력 실행결과 오답. t와 d를 구할때 나눠야할 값들을 나누지 않았다.
 * 예제 입력에 대하여 바른 출력을 확인한 후 small input 에 대하여 실행결과 오답. NaN 발생. a가 0일때의 처리가 없었다.
 * large input 에 대하여 실행결과 오답. NaN 발생. int 를 long 으로 변경.
 * 
 * 이렇게 쉬운 문제에 오답처리가 수차례 발생해 가슴이 아프다. 민첩한 가운데에서도 신중함이 필요하다.
 * 일단 가장 중요한 것은 뇌 집중의 연속성을 유지하는 것일 듯 하다. 중간중간 인터럽트가 발생하면 분명 일부 기억이 소실될 수 있다.
 * 그리고 조급함이 생겨 신중함을 잃게 되는 것으로 보인다. 외부적인 요인에 의해서라면 어쩔 수 없지만 가능하다면 문제 풀 때 집중해서 한방에 가도록 하자.
 * 
 * http://mathworld.wolfram.com/Point-LineDistance3-Dimensional.html
 * 나중에 시간되면 여기도 한번 보자.
 */
public class CodeJam2009_1C_B {
	
	private void goodluck() throws Exception {
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
			
			int N = Integer.parseInt(br.readLine());
			long xsum, ysum, zsum, vxsum, vysum, vzsum;
			xsum=ysum=zsum=vxsum=vysum=vzsum=0;
			for(int j=0; j<N; j++){
				String[] strs = br.readLine().split(" ");
				int x = Integer.parseInt(strs[0]);
				int y = Integer.parseInt(strs[1]);
				int z = Integer.parseInt(strs[2]);
				int vx = Integer.parseInt(strs[3]);
				int vy = Integer.parseInt(strs[4]);
				int vz = Integer.parseInt(strs[5]);
				
				xsum += x; ysum += y; zsum += z;
				vxsum += vx; vysum += vy; vzsum += vz;
			}
			long a = vxsum*vxsum + vysum*vysum + vzsum*vzsum;
			long b = (xsum*vxsum + ysum*vysum + zsum*vzsum) * 2;
			long c = xsum*xsum + ysum*ysum + zsum*zsum;
			
			double t = 0;
			if(a != 0)
				t = -b / (double)a / 2.0;
			if(t < 0) t = 0;
			double d = Math.sqrt((a*t*t) + (b*t) + c) / N;
			
			String result = String.format("%.8f %.8f", d, t);
			fw.write("Case #"+(i+1)+": "+result+"\n");
		}
		fw.close();
		
		br.close();
		fr.close();
	}
	
	public static void main(String[] args){
		System.out.println("start!");
		try{
			
			new CodeJam2009_1C_B().goodluck();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("end!");
	}
}
