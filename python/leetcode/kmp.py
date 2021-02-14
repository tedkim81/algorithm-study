'''
https://leetcode.com/problems/implement-strstr/

문자열 검색 알고리즘인 KMP 알고리즘의 구현.
'''
class Solution:
    def strStr(self, haystack: str, needle: str) -> int:
        if len(needle) == 0:
            return 0
        if len(needle) > len(haystack):
            return -1
        pi = [0] * len(needle)
        j = 0
        for i in range(1, len(needle)):
            if needle[i] == needle[j]:
                j += 1
                pi[i] = j
            else:
                while j > 0 and needle[i] != needle[j]:
                    j = pi[j-1]
                if needle[i] == needle[j]:
                    j += 1
                    pi[i] = j
        j = 0
        for i in range(len(haystack)):
            if haystack[i] == needle[j]:
                if j == len(needle)-1:
                    return i-j
                else:
                    j += 1
            else:
                while j > 0 and haystack[i] != needle[j]:
                    j = pi[j-1]
                if haystack[i] == needle[j]:
                    j += 1
        return -1