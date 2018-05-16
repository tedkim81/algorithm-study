package com.datastructure;

/**
 * 스택
 */
public class Stack<T> {

    private DoublyCircularLinkedList<T> list;
    private int size;
    private int maxSize;
    
    public static final int DEFAULT_MAX_SIZE = 100;
    
    public Stack() {
        list = new DoublyCircularLinkedList<T>();
        size = 0;
        maxSize = DEFAULT_MAX_SIZE;
    }
    
    public void push(T val) {
        if (size == maxSize) {
            throw new StackOverflowException();
        }
        list.addLast(val);
        size++;
    }
    
    public T pop() {
        if (size == 0) {
            return null;
        }
        size--;
        return list.removeLast();
    }
    
    public int size() {
        return size;
    }
}
