# 퀵 정렬
class QuickSort:
	def sort(self, nums, s, e):
		if s >= e:
			return
		elif s == e-1:
			if nums[s] > nums[e]:
				nums[s],nums[e] = nums[e],nums[s]
			return
		pivot = e
		i = s
		j = e-1
		while i < j:
			while i < j and nums[i] <= nums[pivot]:
				i += 1
			while i < j and nums[j] > nums[pivot]:
				j -= 1
			if i < j:
				nums[i],nums[j] = nums[j],nums[i]
			else:
				if nums[j] <= nums[pivot]:
					j += 1
				nums[j],nums[pivot] = nums[pivot],nums[j]
				pivot = j
				break
		self.sort(nums, s, pivot-1)
		self.sort(nums, pivot+1, e)

nums = [2,1,4,7,5,9,3,8,6]
QuickSort().sort(nums, 0, len(nums)-1)
print(nums)