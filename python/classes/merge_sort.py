# 병합 정렬
class MergeSort:
	def sort(self, nums, s, e, nums_tmp):
		if s == e:
			return
		mid = (s + e) // 2
		self.sort(nums, s, mid, nums_tmp)
		self.sort(nums, mid+1, e, nums_tmp)
		i = s
		j = mid+1
		k = s
		while k <= e:
			if i <= mid and (j > e or nums[i] <= nums[j]):
				nums_tmp[k] = nums[i]
				i += 1
			else:
				nums_tmp[k] = nums[j]
				j += 1
			k += 1
		nums[s:e+1] = nums_tmp[s:e+1]

nums = [2,1,4,7,5,9,3,8,6]
nums_tmp = nums[:]
MergeSort().sort(nums, 0, len(nums)-1, nums_tmp)
print(nums)