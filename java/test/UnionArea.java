package com.example.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UnionArea {
	
	public static class Rect{
		int x1,y1,x2,y2;
		public Rect(int x1,int y1, int x2, int y2){
			this.x1=x1; this.y1=y1; this.x2=x2; this.y2=y2;
		}
	}
	
	public static class Pair<T> implements Comparable<Pair<T>>{
		int first;
		T second;
		public Pair(int first, T second){
			this.first=first; this.second=second;
		}
		
		@Override
		public int compareTo(Pair<T> o) {
			return this.first-o.first;
		}
	}

	private int unionArea(List<Rect> rects){
		if(rects.isEmpty()) return 0;
		
		List<Pair<Pair<Integer>>> events = new ArrayList<Pair<Pair<Integer>>>();
		List<Integer> ys = new ArrayList<Integer>();
		for(int i=0; i<rects.size(); i++){
			Rect rect = rects.get(i);
			ys.add(rect.y1);  ys.add(rect.y2);
			events.add(new Pair<Pair<Integer>>(rect.x1, new Pair<Integer>(1, i)));
			events.add(new Pair<Pair<Integer>>(rect.x2, new Pair<Integer>(-1, i)));
		}
		Collections.sort(events);
		Collections.sort(ys);
		
		int ret = 0;
		int[] count = new int[ys.size()];
		for(int i=0; i<events.size()-1; i++){
			int x = events.get(i).first, delta = events.get(i).second.first, rectangle = events.get(i).second.second;
			int y1 = rects.get(rectangle).y1, y2 = rects.get(rectangle).y2;
			for(int j=0; j<ys.size(); j++)
				if(ys.get(j) >= y1 && ys.get(j) < y2)
					count[j] += delta;
			int cutLength = 0;
			for(int j=0; j<ys.size()-1; j++)
				if(count[j] > 0)
					cutLength += ys.get(j+1) - ys.get(j);
			ret += cutLength * (events.get(i+1).first-x);
		}
		return ret;
	}
	
	public static void main(String[] args){
		List<Rect> rects = new ArrayList<Rect>();
		rects.add(new Rect(0,0,2,2));
		rects.add(new Rect(0,1,3,3));
		int ret = new UnionArea().unionArea(rects);
		System.out.println("union rect : "+ret);
	}
}
