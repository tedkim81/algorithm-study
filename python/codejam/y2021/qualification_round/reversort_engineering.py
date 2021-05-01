'''
2021.3.27

reversort.py와 배경이 같은 문제

Reversort(L):
    for i := 1 to length(L) - 1
        j := position with the minimum value in L between i and length(L), inclusive
        Reverse(L[i..j])

size N과 cost C 가 주어졌을때, 그에 해당하고 1 ~ N 숫자로 구성된 배열 L을 구하는 문제.

(input)
T: 테스트케이스의 수
N C: N은 배열크기, C는 cost

(output)
Case #x: y1 y2 ... yN

(solution 1)
L은 정렬된 배열에서 시작하고, min_C, max_C 구해서 거꾸로 시뮬레이션 한다.
gap_C를 max_C - C 로 설정하고, 시뮬레이션 과정에서 gap_C를 이용하여 j를 적절히 구하는게 중요.
'''

def solve(N, C):
    min_C = N - 1
    max_C = sum(range(2, N+1))
    if C < min_C or C > max_C:
        return "IMPOSSIBLE"
    gap_C = max_C - C
    L = list(range(1, N+1))
    for i in range(N-2, -1, -1):
        max_c_i = N - i
        subtract = min(max_c_i-1, gap_C)
        j = i + (max_c_i - subtract) - 1
        gap_C -= subtract
        L = L[:i] + list(reversed(L[i:j+1])) + L[j+1:]
    return " ".join([str(i) for i in L])

T = int(input())
for tt in range(1, T+1):
    N, C = (int(i) for i in input().split())
    result = solve(N, C)
    print('Case #{}: {}'.format(tt, result))