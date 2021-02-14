'''
https://leetcode.com/problems/next-permutation/

사전순으로 나열된다고 할때 주어진 순열의 다음 순열 찾기.
'''
class Solution:
    def nextPermutation(self, nums) -> None:
        changed = False
        l = len(nums)
        for i in range(l-2, -1, -1):
            finded = False
            for j in range(l-1, i, -1):
                if nums[j] > nums[i]:
                    finded = True
                    break
            if finded:
                nums[i],nums[j] = nums[j],nums[i]
                self.reverse(nums, i+1, l-1)
                changed = True
                break
        if not changed:
            self.reverse(nums, 0, l-1)
    
    def reverse(self, nums, s: int, e: int) -> None:
        while s < e:
            nums[s],nums[e] = nums[e],nums[s]
            s += 1
            e -= 1

nums = [1,2,3,5,4]
Solution().nextPermutation(nums)
print(nums)