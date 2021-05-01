'''
2021.3.27

N개의 distinct한 수들이 주이지고, 이걸 정렬해야 하는데, 두 수의 크기 비교를 할 수가 없고,
세개의 수에서 중간값이 뭔지만 알 수 있는 상황이다.
중간값 쿼리는 T번의 테스트케이스 모두에 대해 총 Q번까지만 가능하다.
중간값 쿼리를 이용해서 정렬한 결과를 리턴하는 문제.
#interactive

(input)
T N Q: T는 테스트케이스의 수, N은 정렬해야할 수들의 갯수, Q는 총 쿼리 가능수.
(interactive input and output)
(output) 중간값 쿼리할 세개의 수에 대한 위치값. 1 ~ N 범위의 수.
(input) 중간값 쿼리 결과. 쿼리한 세개의 수 중 중간값의 위치값.
... Q/T 정도 반복 질의 후 답을 알았다면?
(output) 정렬된 결과 출력.
(input) 1 or -1. 1은 정답, -1은 오답 또는 오류발생.

(solution 1)
삼분법으로 해결하는 문제인듯.
이미 정렬되어 있는 구간에 숫자를 추가하는 로직을 반복 실행.
정렬되어 있는 전체구간을 삼등분하고, 그 구분값 2개와 추가할 수를 쿼리한다.
구분값 2개는 이미 정렬되어 있으므로, 쿼리결과에 따라 추가할 수가 세개의 구간 중 어디에 들어갈지 알 수 있다.
구간값 2개 사이에 값이 없는경우와 1개인 경우는 edge case로 다뤄야 한다. 
이때 왼쪽끝 또는 오른쪽 끝인 경우도 고려해야 한다.
'''

def solve(n, q):
    print("1 2 3")
    m = int(input())
    q -= 1
    if m == 1: 
        l = [0, 2, 1, 3, 0]
    elif m == 2:
        l = [0, 1, 2, 3, 0]
    elif m == 3:
        l = [0, 1, 3, 2, 0]
    num = 4
    left_idx, right_idx = 0, len(l)-1
    while len(l) < n+2:
        if right_idx - left_idx == 1:
            l.insert(right_idx, num)
            num += 1
            left_idx, right_idx = 0, len(l)-1
            continue
        if right_idx - left_idx == 2:
            if left_idx == 0:
                mid1_idx, mid2_idx = 1, 2
            else:
                mid1_idx, mid2_idx = left_idx, left_idx+1
        else:
            mid1_idx = left_idx + ((right_idx - left_idx + 1) // 3)
            mid2_idx = left_idx + (((right_idx - left_idx + 1) * 2) // 3)
        print(l[mid1_idx], l[mid2_idx], num)
        m = int(input())
        if m == -1:
            return -1
        q -= 1
        if m == l[mid1_idx]:
            right_idx = mid1_idx
        elif m == l[mid2_idx]:
            left_idx = mid2_idx
        else:
            left_idx, right_idx = mid1_idx, mid2_idx
    print(" ".join([str(l[i]) for i in range(1, len(l)-1)]))
    m = int(input())
    if m == -1:
        return -1
    return q

tmp = input().split()
T, N, Q = int(tmp[0]), int(tmp[1]), int(tmp[2])
for tt in range(1, T+1):
    Q = solve(N, Q)
    if Q == -1:
        break