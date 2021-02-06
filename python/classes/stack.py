# linked list를 이용한 스택
class LinkedListStack:
    
    class Node:
        def __init__(self, item, next):
            self.item = item
            self.next = next

    def __init__(self):
        self.first = None
    
    def push(self, item):
        oldfirst = self.first
        self.first = self.Node(item, oldfirst)
    
    def pop(self):
        if self.is_empty():
            raise Exception('Stack is empty')
        item = self.first.item
        self.first = self.first.next
        return item
    
    def is_empty(self):
        return self.first is None

# array를 이용한 스택
# python의 list는 append()와 pop()으로 스택처럼 사용할 수 있다.
class ArrayStack:
    
    def __init__(self):
        self.list = []
        self.top = 0

    def push(self, item):
        self.list.append(item)
        self.top += 1

    def pop(self):
        if self.is_empty():
            raise Exception('Stack is empty')
        self.top -= 1
        item = self.list[self.top]
        return item

    def is_empty(self):
        return self.top == 0


# stack = LinkedListStack()
stack = ArrayStack()
stack.push(1)
stack.push(2)
stack.push(3)
while not stack.is_empty():
    print(stack.pop())
stack.pop()