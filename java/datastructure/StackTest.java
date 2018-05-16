package com.datastructure;

import static org.junit.Assert.*;

import org.junit.Test;

public class StackTest {

    @Test(expected=StackOverflowException.class)
    public void test() {
        // 스택 생성하고 1,2,3 추가
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        
        // 크기가 3인지 확인
        assertTrue(3 == stack.size());
        
        // 후입선출로 pop되는지 확인
        assertTrue(3 == stack.pop());
        assertTrue(2 == stack.pop());
        assertTrue(1 == stack.pop());
        
        // 스택 최대치 넘게 항목 삽입하면 StackOverflowException이 발생하는지 확인
        for (int i=0; i<Stack.DEFAULT_MAX_SIZE; i++) {
            stack.push(i);
        }
        stack.push(Stack.DEFAULT_MAX_SIZE);
    }

}
