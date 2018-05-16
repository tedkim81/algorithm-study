package com.example.test;

import java.util.ArrayList;
import java.util.List;

public class RecursiveTest {
	
	public void pick(int n, List<Integer> picked, int toPick){
		if(toPick == 0){
			print(picked);
			return;
		}
		int smallest = picked.isEmpty() ? 0 : picked.get(picked.size()-1)+1;
		for(int i=smallest; i<n; i++){
			picked.add(i);
			pick(n, picked, toPick-1);
			picked.remove(picked.size()-1);
		}
	}
	
	public void print(List<Integer> list){
		for(int i : list){
			System.out.print(i+"  ");
		}
		System.out.print("\n");
	}
	
	public void combination(List<Integer> list, List<Integer> res, int m){
		if(res.size() == m){
			print(res);
			return;
		}
		int lastone = -1;
		if(res.size() > 0)
			lastone = res.get(res.size()-1);
		for(int i=0; i<list.size(); i++){
			if(list.get(i) <= lastone) continue;
			
			int a = list.remove(i);
			res.add(a);
			
			combination(list, res, m);
			
			res.remove(res.size()-1);
			list.add(i, a);
		}
	}

	public static void main(String[] args){
//		new RecursiveTest().pick(5, new ArrayList<Integer>(), 3);
		
		List<Integer> list = new ArrayList<Integer>();
		for(int i=0; i<5; i++){
			list.add(i);
		}
		new RecursiveTest().combination(list, new ArrayList<Integer>(), 3);
	}
}
