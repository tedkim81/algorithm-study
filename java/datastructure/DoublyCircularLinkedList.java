package com.datastructure;

/**
 * 이중 원형 연결 리스트
 */
public class DoublyCircularLinkedList<T> {

    class Node {
        T val;
        Node prev;
        Node next;
    }
    
    private Node header;
    private int size;
    
    public DoublyCircularLinkedList() {
        header = new Node();
        header.prev = header.next = header;
        size = 0;
    }
    
    public boolean add(int pos, T val) {
        if (pos < 0 || pos > size) {
            return false;
        }
        Node beforeNode;
        if (pos == 0) {
            beforeNode = header;
        } else {
            beforeNode = getNode(pos-1);
        }
        Node newNode = new Node();
        newNode.val = val;
        newNode.prev = beforeNode;
        newNode.next = beforeNode.next;
        if (beforeNode.next == null) {
            header.prev = newNode;
        } else {
            beforeNode.next.prev = newNode;
        }
        beforeNode.next = newNode;
        size++;
        return true;
    }
    
    public boolean addLast(T val) {
        return add(size, val);
    }
    
    public boolean addFirst(T val) {
        return add(0, val);
    }
    
    private Node getNode(int pos) {
        Node destNode = header;
        if (pos <= size/2) {
            for (int i=0; i<=pos; i++) {
                destNode = destNode.next;
            }
        } else {
            for (int i=size; i>pos; i--) {
                destNode = destNode.prev;
            }
        }
        return destNode;
    }
    
    public T get(int pos) {
        if (pos < 0 || pos >= size) {
            return null;
        }
        return getNode(pos).val;
    }
    
    public T remove(int pos) {
        if (pos < 0 || pos >= size) {
            return null;
        }
        Node destNode = getNode(pos);
        destNode.next.prev = destNode.prev;
        destNode.prev.next = destNode.next;
        T retVal = destNode.val;
        destNode = null;
        size--;
        return retVal;
    }
    
    public T removeLast() {
        return remove(size-1);
    }
    
    public T removeFirst() {
        return remove(0);
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
