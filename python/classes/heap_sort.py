# 힙 정렬
class HeapSort:
	def left_child(self, parent):
		return parent * 2 + 1

	def parent(self, node):
		return (node - 1) // 2

	def downheap(self, nums, node, tail):
		while self.left_child(node) <= tail:
			child = self.left_child(node)
			if child+1 <= tail and nums[child+1] > nums[child]:
				child += 1
			if nums[node] >= nums[child]:
				break
			nums[node],nums[child] = nums[child],nums[node]
			node = child

	def upheap(self, nums, node):
		while self.parent(node) >= 0:
			parent = self.parent(node)
			if nums[node] <= nums[parent]:
				break
			nums[node],nums[parent] = nums[parent],nums[node]
			node = parent

	def heapify(self, nums):
		for node in range(1, len(nums)):
			self.upheap(nums, node)

	def sort(self, nums):
		self.heapify(nums)
		for poped in range(len(nums)):
			tail = len(nums) - 1 - poped
			nums[0],nums[tail] = nums[tail],nums[0]
			self.downheap(nums, 0, tail-1)

nums = [2,1,4,7,5,9,3,8,6]
HeapSort().sort(nums)
print(nums)