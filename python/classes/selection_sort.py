# 선택 정렬
class SelectionSort:
	def sort(self, nums):
		for i in range(len(nums)-1):
			min_idx = i
			for j in range(i+1, len(nums)):
				if nums[j] < nums[min_idx]:
					min_idx = j
			nums[i],nums[min_idx] = nums[min_idx],nums[i]

nums = [2,1,4,7,5,9,3,8,6]
SelectionSort().sort(nums)
print(nums)