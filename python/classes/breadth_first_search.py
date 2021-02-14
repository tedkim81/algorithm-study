# 너비 우선 탐색 Breadth First Search
from queue import Queue

class BreadthFirstSearch:
	def run(self, tree):
		q = Queue()
		q.put(0)
		while not q.empty():
			node = q.get()
			print(node, end=', ')
			left_child = node * 2 + 1
			if left_child < len(tree):
				q.put(left_child)
			if left_child+1 < len(tree):
				q.put(left_child+1)

tree = list(range(20))
BreadthFirstSearch().run(tree)