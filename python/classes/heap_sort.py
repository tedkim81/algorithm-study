# 힙 정렬
class HeapSort:
	def left_child(self, parent):
		return parent * 2 + 1

	def parent(self, node):
		return (node - 1) // 2

	def downheap(self, node, tail):
		while self.left_child(node) <= tail:
			child = self.left_child(node)
			if child+1 <= tail and self.nums[child+1] > self.nums[child]:
				child += 1
			if self.nums[node] >= self.nums[child]:
				break
			self.nums[node],self.nums[child] = self.nums[child],self.nums[node]
			node = child

	def upheap(self, node):
		while self.parent(node) >= 0:
			parent = self.parent(node)
			if self.nums[node] <= self.nums[parent]:
				break
			self.nums[node],self.nums[parent] = self.nums[parent],self.nums[node]
			node = parent

	def heapify(self):
		for node in range(1, len(self.nums)):
			self.upheap(node)

	def sort(self, nums):
		self.nums = nums
		self.heapify()
		for poped in range(len(nums)):
			tail = len(nums) - 1 - poped
			nums[0],nums[tail] = nums[tail],nums[0]
			self.downheap(0, tail-1)

nums = [2,1,4,7,5,9,3,8,6]
HeapSort().sort(nums)
print(nums)