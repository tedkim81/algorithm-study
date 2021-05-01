'''
2021.3.27

Reversort(L):
    for i := 1 to length(L) - 1
        j := position with the minimum value in L between i and length(L), inclusive
        Reverse(L[i..j])

Reversort(L) 함수의 cost를 계산하는 문제.
Reverse()의 cost 합으로 구한다. Reverse()는 인자로 받은 배열의 길이가 cost가 된다.

(input)
T: 테스트케이스의 수
N: 배열의 크기
L[N]: 배열

(output)
Case #x: y
y: 배열 L에 대한 Reversort(L)의 cost 값

(solution 1)
Reversort(L) 구현하고 Reverse() 실행시 인자로 받은 배열의 크기 누적 합을 구한다.
'''

def solve(N, L):
    result = 0
    for i in range(N-1):
        j = L.index(min(L[i:]))
        L = L[:i] + list(reversed(L[i:j+1])) + L[j+1:]
        result += j + 1 - i
    return result

T = int(input())
for tt in range(1, T+1):
    N = int(input())
    L = [int(i) for i in input().split()]
    result = solve(N, L)
    print('Case #{}: {}'.format(tt, result))