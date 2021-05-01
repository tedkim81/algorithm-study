'''
2021.4.26

바늘시계의 시침, 분침, 초침이 똑같은 길이로 부러져서 구분할 수 없고, 
시간 구분자도 없어져서 어디가 위인지도 알 수 없는 상황에,
각 바늘에 대하여, 어떤 임의의 축을 중심으로 상대적인 각도가 주어졌을때 몇시 몇분 몇초인지 구하는 문제.
상대적인 각도는 tick 단위로 주어지는데, 1 tick은 (1/12) * (10**(-9))

(input)
T: 테스트케이스의 수
A B C: 각 바늘의 tick 단위의 각도

(output)
Case #x: h m s n
몇시 몇분 몇초 몇나노초
n은 Test set 1과 2에서는 0

(solution 1)
일단 Test set 1과 2만 고려해서 풀어보자.
몇시 몇분 몇초의 총 경우의 수는 43200개(12*60*60)
모든 경우 탐색하여 시침 분침 초침의 각도를 계산하여, A B C와 비교하여 같은걸 찾는다.
비교할때는 값들을 오름차순 정렬한후 각 바늘의 사이각들을 비교한다.

(solution 1 result)
sample case만 통과하고 test set 1은 실패.
test set 1과 2에서는 성공하는 코드라고 생각하는데 문제점을 발견하지 못함.
다행히 이제 analysis에서 Test Data를 제공해줘서 그걸로 문제점을 발견.
부동소수점을 제대로 고려하지 못한게 문제였다. hd를 구할때 120으로 나누어 떨어지지 않는 경우가 있었던 것.
Decimal 적용하여 다시 시도해보니 Test set 1과 2는 성공.
'''

from decimal import Decimal

def solve(A, B, C):
    l1 = [A, B, C]
    l1.sort()
    aa = l1[0]
    for i in range(3):
        l1[i] -= aa
    tpd = Decimal(12 * (10 ** 10)) # ticks per degree
    hs = [Decimal(h) for h in range(12)]
    ms = [Decimal(m) for m in range(60)]
    ss = [Decimal(s) for s in range(60)]
    for h in hs:
        for m in ms:
            for s in ss:
                sd = s * 6 * tpd
                md = ((m * 6) + (s / 10)) * tpd
                hd = ((h * 30) + (m / 2) + (s / 120)) * tpd
                l2 = [sd, md, hd]
                for i in range(3):
                    l3 = [v-l2[i] if v >= l2[i] else v-l2[i]+(360 * tpd) for v in l2]
                    l3.sort()
                    if l1 == l3:
                        return '{} {} {} 0'.format(h, m, s)
    return '0 0 0 0'

T = int(input())
for tt in range(1, T+1):
    tmp = input().split()
    A, B, C = int(tmp[0]), int(tmp[1]), int(tmp[2])
    result = solve(A, B, C)
    print('Case #{}: {}'.format(tt, result))

# f = open('ts1_output.txt', 'r')
# corrects = [s.strip() for s in f.readlines()]
# f.close()

# f = open('ts1_input.txt', 'r')
# T = int(f.readline())
# for tt in range(1, T+1):
#     tmp = f.readline().split()
#     A, B, C = int(tmp[0]), int(tmp[1]), int(tmp[2])
#     result = solve(A, B, C)
#     result_line = 'Case #{}: {}'.format(tt, result)
#     if result_line != corrects[tt-1]:
#         print('{}: {} vs {}'.format(tmp, result_line, corrects[tt-1]))
# f.close()