# 버블 정렬
class BubbleSort:
	def sort(self, nums):
		for i in range(1, len(nums)):
			for j in range(len(nums)-i):
				if nums[j] > nums[j+1]:
					nums[j],nums[j+1] = nums[j+1],nums[j]

nums = [2,1,4,7,5,9,3,8,6]
BubbleSort().sort(nums)
print(nums)