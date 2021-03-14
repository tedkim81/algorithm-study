'''
https://leetcode.com/problems/combination-sum/

Solution
Dynamic Programming(동적 계획법)의 구현.
하지만 해당 문제의 조건 범위에서는 동적계획법을 쓰지 않아도 동작하고, 동적계획법을 쓰면 오히려 느리고 메모리 사용량도 크다.
그리고 메모이제이션을 할때 value가 아닌 reference를 저장하게 되는 경우 버그 가능성이 높아진다.
반성하는 차원에서 남겨두자.

Solution2
Depth First Search(깊이 우선 탐색)의 구현.
'''
class Solution:
    def solve(self, candidates, start_idx, target):
        num = candidates[start_idx]
        if num > target:
            return []
        elif num == target:
            return [[num]]
        
        if target in self.cache and start_idx in self.cache[target]:
            return self.cache[target][start_idx]
        
        result = []
        list1 = self.solve(candidates, start_idx, target-num)
        for l in list1:
            result.append([num]+l)
        if start_idx+1 < len(candidates):
            next_idx = start_idx+1
            if candidates[next_idx] > num:
                list2 = self.solve(candidates, next_idx, target)
                for l in list2:
                    result.append(l[:])
        
        if target not in self.cache:
            self.cache[target] = {}
        self.cache[target][start_idx] = result
        
        return self.cache[target][start_idx]
    
    def combinationSum(self, candidates, target):
        candidates.sort()
        self.cache = {}
        return self.solve(candidates, 0, target)

class Solution2:
    def combinationSum(self, candidates, target):
        def dfs(candidates, target, cur_path, ans):
            if target < 0: return
            if target == 0:
                ans.append( cur_path )
                return
            for index, num in enumerate(candidates):
                dfs( candidates[index:], target - num, cur_path + [num], ans )
                
        ans = []
        dfs ( candidates, target, [], ans )
        return ans

candidates = [3,12,9,11,6,7,8,5,4]
target = 15
result = Solution().combinationSum(candidates, target)
print(result)