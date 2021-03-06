'''
2021.5.1

복권처럼, 숫자가 적힌 티켓을 판매하고 랜덤 숫자를 선택해서 당첨자를 선정한다.
숫자는 [1, K] 범위에 있고, 동점자 없이 가장 가까운 숫자가 이긴다.
N명의 티켓이 이미 팔렸고 각 숫자가 뭔지 알고 있다.
내가 마지막 구매자고 티켓 2장을 살 수 있다.
내가 당첨될 확률의 최대값을 구하는 것이 문제.

(input)
T: 테스트케이스의 수
N K: N은 이미 구매된 티켓 수량, K는 숫자의 법위 [1, K]
P[0], ... ,P[N-1]: 이미 구매된 티켓의 숫자들

(output)
Case #x: y
내가 당첨될 확률의 최대값

(solution 1)
정리
- c가 P[]에 포함된 숫자라면 이길 수 없다. P[]에 포함되지 않은 숫자여야만 이길 가능성이 생긴다.
- [1, K] 전체 구간 중 P에 포함되지 않은 값 2개를 선택해야 한다. S1, S2
- S 선택시, c가 어디부터 어디까지일때 이길 수 있는지에 대한 커버리지 구간의 크기를 계산할 수 있다.
- 커버리지 구간의 크기가 제일 큰값 2개가 각각 S1과 S2이고, 그 합을 K로 나눈게 최대확률이 된다.
- N의 최대값은 30. 구간은 최대 31개까지 있을 수 있다.

특정 구간에서 커버리지 구간의 크기 계산 방법
- 왼쪽 또는 오른쪽 끝을 포함하는 구간에서는, 그냥 그 구간의 크기가 커버리지 구간의 크기가 된다.
- 사이 구간에서는, 구간의 크기를 l이라 할때, (l-1)//2 + 1

알고리즘
- P[]를 정렬한다.
- P[] 탐색하면서 커버리지 구간 크기 계산하여 S[]에 추가한다.
- S[]를 역정렬하고, (S[0]+S[1]) / K 를 구한다.
'''

def solve(N, K, P):
    P.sort()
    S = []
    for i in range(N):
        if i == 0:
            S.append(P[i]-1)
        else:
            l = P[i]-P[i-1]-1
            ss = ((l-1)//2)+1
            S.append(ss)
            S.append(min(ss, l-ss))
    S.append(K-P[-1])
    S.sort(reverse=True)
    return (S[0]+S[1])/K

T = int(input())
for tt in range(1, T+1):
    tmp = input().split()
    N, K = int(tmp[0]), int(tmp[1])
    tmp = input().split()
    P = [int(i) for i in tmp]
    result = solve(N, K, P)
    print('Case #{}: {}'.format(tt, result))