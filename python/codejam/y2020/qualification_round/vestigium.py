'''
2021.3.13

N x N 행렬에서 k, r, c 구하기
k: 좌상 - 우하 대각선 상의 값들의 합
r: 중복된 값이 있는 행의 수
c: 중복된 값이 있는 열의 수

(input)
T: 테스트케이스의 수
N: 행렬의 크기
N행 N열의 값

(output)
Case #x: k r c

(solution 1)
행렬은 2차 배열에 담고, k는 뭐 그냥 구하면 되고,
r과 c는 각 행과 열 탐색하면서 dict 이용하여 중복값 체크.
'''

def solve(N, mat):
    k, r, c = 0, 0, 0
    s = set()
    for i in range(N):
        k += mat[i][i]
        s.clear()
        for j in range(N):
            if mat[i][j] in s:
                r += 1
                break
            s.add(mat[i][j])
        s.clear()
        for j in range(N):
            if mat[j][i] in s:
                c += 1
                break
            s.add(mat[j][i])
    return '{} {} {}'.format(k, r, c)

T = int(input())
for i in range(T):
    N = int(input())
    mat = []
    for j in range(N):
        mat.append([int(v) for v in input().split()])
    result = solve(N, mat)
    print('Case #{}: {}'.format(i+1, result))