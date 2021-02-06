# 삽입 정렬
class InsertionSort:
	def sort(self, nums):
		for i in range(1, len(nums)):
			j = i
			while j > 0 and nums[j] < nums[j-1]:
				nums[j],nums[j-1] = nums[j-1],nums[j]
				j -= 1

nums = [2,1,4,7,5,9,3,8,6]
InsertionSort().sort(nums)
print(nums)