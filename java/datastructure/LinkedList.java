package com.datastructure;

/**
 * 단순 연결 리스트
 */
public class LinkedList<T> {

	private class Node {
		T val;
		Node next;
	}
	private Node header;
	private int size;
	
	public LinkedList() {
		header = new Node();
		size = 0;
	}
	
	public boolean add(int pos, T val) {
		if (pos < 0 || pos > size) {
			return false;
		}
		Node dest = header;
		for (int i=0; i<pos; i++) {
			dest = dest.next;
		}
		Node inserted = new Node();
		inserted.val = val;
		inserted.next = dest.next;
		dest.next = inserted;
		size++;
		return true;
	}
	
	public boolean addLast(T val) {
		return add(size, val);
	}
	
	public T remove(int pos) {
		if (pos < 0 || pos >= size) {
			return null;
		}
		Node dest = header.next;
		Node preDest = null;
		for (int i=0; i<pos; i++) {
			preDest = dest;
			dest = dest.next;
		}
		preDest.next = dest.next;
		T retVal = dest.val;
		dest = null;
		size--;
		return retVal;
	}
	
	public T get(int pos) {
		if (pos < 0 || pos >= size) {
			return null;
		}
		Node dest = header.next;
		for (int i=0; i<pos; i++) {
			dest = dest.next;
		}
		return dest.val;
	}
	
	public int size() {
		return size;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for (int i=0; i<size; i++) {
			sb.append(get(i));
			if (i < size-1) {
				sb.append(',');
			}
		}
		sb.append(']');
		return sb.toString();
	}
	
}
