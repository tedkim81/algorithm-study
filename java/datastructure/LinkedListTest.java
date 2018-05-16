package com.datastructure;

import static org.junit.Assert.*;

import org.junit.Test;

public class LinkedListTest {

	/**
	 * 단순 연결 리스트 테스트하기
	 */
	@Test
	public void test() {
		// 연결 리스트 생성하고, 항목들(1,2,4,5) 추가
		LinkedList<Integer> list = new LinkedList<Integer>();
		list.addLast(1);
		System.out.println(list.toString());
		list.addLast(2);
		System.out.println(list.toString());
		list.addLast(4);
		System.out.println(list.toString());
		list.addLast(5);
		System.out.println(list.toString());
		
		// 항목들이 순서에 맞게 추가되었는지 테스트
		assertTrue(1 == list.get(0));
		assertTrue(2 == list.get(1));
		assertTrue(4 == list.get(2));
		assertTrue(5 == list.get(3));
		
		// 가운데에 3을 추가하여 리스트가 [1,2,3,4,5]가 되었는지 테스트
		list.add(2, 3);
		System.out.println(list.toString());
		assertTrue(3 == list.get(2));
		
		// [1,2,3,4,5]에서 2와 4를 제거
		list.remove(1); // remove 2
		System.out.println(list.toString());
		list.remove(2); // remove 4
		System.out.println(list.toString());
		
		// [1,3,5]가 되었는지 테스트
		assertTrue(3 == list.size());
		assertTrue(3 == list.get(1));
		
		// 범위를 벗어나면 null을 리턴하는지 테스트
		assertNull(list.get(3));
	}

}
