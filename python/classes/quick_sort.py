# 퀵 정렬
class QuickSort:
	def sort(self, nums, s, e):
		if s >= e:
			return
		pivot = e
		i = s
		j = e-1
		while i < j:
			while i < j and nums[i] <= nums[pivot]:
				i += 1
			while i < j and nums[j] >= nums[pivot]:
				j -= 1
			if i >= j:
				break
			nums[i],nums[j] = nums[j],nums[i]
		if nums[i] >= nums[pivot]:
			nums[i],nums[pivot] = nums[pivot],nums[i]
			pivot = i
		self.sort(nums, s, pivot-1)
		self.sort(nums, pivot+1, e)

nums = [2,1,4,7,5,9,3,8,6]
QuickSort().sort(nums, 0, len(nums)-1)
print(nums)