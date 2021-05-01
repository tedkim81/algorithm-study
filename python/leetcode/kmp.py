'''
https://leetcode.com/problems/implement-strstr/

문자열 검색 알고리즘인 KMP 알고리즘의 구현.
'''
class Solution:

    # 좀더 직관적으로 작성한 코드.
    # 하지만 strStr2에 비해 느리다.(leetcode에서의 submit 결과)
    # 분석을 해보니 반복 횟수는 동일하다.
    # 그렇다면 +연산 및 비교연산 때문이란 얘긴데, 차이가 생각보다 크다..
    def strStr(self, haystack: str, needle: str) -> int:
        if not needle:
            return 0
        if len(needle) > len(haystack):
            return -1
        
        # loop = 0
        pi = [0] * len(needle)
        begin, matched = 1, 0
        while begin+matched < len(needle):
            # loop += 1
            if needle[begin+matched] == needle[matched]:
                matched += 1
                pi[begin+matched-1] = matched
            elif matched > 0:
                begin += matched - pi[matched-1]
                matched = pi[matched-1]
            else:
                begin += 1
                matched = 0
        # print("loop1: {}".format(loop))
        
        begin, matched = 0, 0
        while begin+matched < len(haystack):
            # loop += 1
            if haystack[begin+matched] == needle[matched]:
                matched += 1
                if matched == len(needle):
                    return begin
            elif matched > 0:
                begin += matched - pi[matched-1]
                matched = pi[matched-1]
            else:
                begin += 1
                matched = 0
        # print("loop2: {}".format(loop))
        return -1

    # 이전에 문제풀때 작성했던 코드.
    # 다시 봤는데 코드가 잘 이해되지 않아 strStr으로 다시 작성.
    # 하지만 이게 strStr 보다 빠르다.
    def strStr2(self, haystack: str, needle: str) -> int:
        if len(needle) == 0:
            return 0
        if len(needle) > len(haystack):
            return -1

        # loop = 0
        pi = [0] * len(needle)
        j = 0
        for i in range(1, len(needle)):
            # loop += 1
            if needle[i] == needle[j]:
                j += 1
                pi[i] = j
            else:
                while j > 0 and needle[i] != needle[j]:
                    # loop += 1
                    j = pi[j-1]
                if needle[i] == needle[j]:
                    j += 1
                    pi[i] = j
        # print("loop1: {}".format(loop))

        j = 0
        for i in range(len(haystack)):
            # loop += 1
            if haystack[i] == needle[j]:
                if j == len(needle)-1:
                    return i-j
                else:
                    j += 1
            else:
                while j > 0 and haystack[i] != needle[j]:
                    # loop += 1
                    j = pi[j-1]
                if haystack[i] == needle[j]:
                    j += 1
        # print("loop2: {}".format(loop))
        return -1

# result = Solution().strStr("aaandnnnnfnnfnfffjdjdjdjd", "nnfnnfnf")
# print(result)

haystack = "a" * 10000
needle = "a" * 1000 + "b"
Solution().strStr(haystack, needle)
Solution().strStr2(haystack, needle)