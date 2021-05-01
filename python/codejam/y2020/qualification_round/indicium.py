'''
2021.3.26

N과 K가 주어질 때, 사이즈가 N이고 trace가 K인 natural latin square 를 구하는 문제.
- natural latin square: 각 행과 각 열에 같은 수가 반복되지 않고, 1~N 사이의 수로 구성된 행렬,
- trace: 좌상-우하 대각선 상의 값의 합

(input)
T: 테스트케이스의 수
N K: N은 size, K는 trace

(output)
Case #x: POSSIBLE or IMPOSSIBLE
POSSIBLE 인 경우, 정답 행렬 출력.

(solution 1)

'''

def solve(num):
    return num

T = int(input())
for tt in range(1, T+1):
    N = int(input())
    result = solve(N)
    print('Case #{}: {}'.format(tt, result))