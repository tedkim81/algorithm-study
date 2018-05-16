package com.example.test;

import java.util.ArrayList;
import java.util.List;

public class PermutationTest {

	/**
	 * 반복문만을 활용한 "수 나열하기" 예제
	 * n중 반복문을 사용해야 하기 때문에 n이 동적일 경우 구현이 불가하다. 따라서 재귀호출법을 사용해야 한다.
	 */
	public void arrange(){  // n=3
		List<Integer> list = new ArrayList<Integer>();
		for(int i=0; i<3; i++){
			list.add(i);
		}
		
		List<Integer> res = new ArrayList<Integer>();
		for(int i=0; i<3; i++){
			int a1 = list.remove(i);
			res.add(a1);
			for(int j=0; j<2; j++){
				int a2 = list.remove(j);
				res.add(a2);
				for(int k=0; k<1; k++){
					int a3 = list.remove(k);
					res.add(a3);
					
					print(res);
					
					list.add(k, a3);
					res.remove(res.size()-1);
				}
				list.add(j, a2);
				res.remove(res.size()-1);
			}
			list.add(i, a1);
			res.remove(res.size()-1);
		}
	}
	
	/**
	 * 재귀호출법을 사용한 "수 나열하기" 예제
	 */
	public void arrange(List<Integer> list, List<Integer> res){
		if(list.size() == 0){  // base case
			print(res);
			return;
		}
		for(int i=0; i<list.size(); i++){
			int a = list.remove(i);
			res.add(a);
			
			arrange(list, res);
			
			res.remove(res.size()-1);
			list.add(i, a);
		}
	}
	
	public void print(List<Integer> list){
		for(int i : list){
			System.out.print(i+" ");
		}
		System.out.print("\n");
	}

	public static void main(String[] args){
		List<Integer> list = new ArrayList<Integer>();
		for(int i=0; i<3; i++){
			list.add(i);
		}
		new PermutationTest().arrange(list, new ArrayList<Integer>());
	}
}
