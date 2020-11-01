# 삽입 정렬
class InsertionSort:
	def sort(self, nums):
		for i in range(1, len(nums)):
			for j in range(0, i):
				if nums[i-j] < nums[i-j-1]:
					nums[i-j],nums[i-j-1] = nums[i-j-1],nums[i-j]
				else:
					break

nums = [2,1,4,7,5,9,3,8,6]
InsertionSort().sort(nums)
print(nums)