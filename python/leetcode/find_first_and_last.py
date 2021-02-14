'''
https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/

정수 집합에서 이분법을 할때는 low와 high가 1차이가 날때 종료조건을 신경써야 한다.
middle 계산할때 소수점 버림이 되므로, low = mid 가 있으면 무한루프에 빠질 수 있다.
'''
class Solution:
    def searchRange(self, nums, target):
        result = [-1,-1]
        if not nums:
            return result
        s = 0
        e = len(nums)
        while s < e:
            mid = (s+e) // 2
            if target < nums[mid]:
                e = mid-1
            elif target > nums[mid]:
                s = mid+1
            else:
                e = mid
        if s == len(nums) or nums[s] != target:
            return result
        result[0] = s
        e = len(nums)
        while s < e:
            mid = (s+e) // 2
            if target < nums[mid]:
                e = mid
            else:
                s = mid+1
        result[1] = s-1
        return result