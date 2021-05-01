'''
2021.4.10

M장의 숫자 카드가 주어지고, 숫자는 모두 소수이다.
두 그룹으로 나눠서, 왼쪽 그룹의 숫자합과 오른쪽 그룹의 숫자곱이 같도록 하되, 그 값이 최대가 되도록 해야 한다.
그 최대값은 얼마인가?

(input)
T: 테스트케이스의 수
M: 소수의 갯수
M행의 P N: P는 소수, N은 해당 소수의 갯수

(output)
Case #x: y
그룹 나눴을때 왼쪽그룹의 숫자합(=오른쪽 그룹의 숫자곱)의 최대값,
or 그룹 못나누면 0

(solution 1)
부분합과 부분곱을 미리 구한 후,
각 소수를 합 또는 곱 그룹에 속하도록 하면서 탐색하면서,
남은 소수들에 대하여 합이 남은거 전부 곱한거보다 크거나, 곱이 남은거 전부 합한거보다 큰 경우 가지치기.

(solution 1 result)
Test Set 1은 통과. Test Set 2는 Time Limit Exceeded.
'''

def solve(sum_val, mul_val, p_list, idx, all_sums, all_muls):
    if idx > len(p_list)-1:
        return sum_val if sum_val == mul_val else 0
    if sum_val > mul_val * all_muls[idx] or mul_val > sum_val + all_sums[idx]:
        return 0
    return max(
        solve(sum_val+p_list[idx], mul_val, p_list, idx+1, all_sums, all_muls),
        solve(sum_val, mul_val*p_list[idx], p_list, idx+1, all_sums, all_muls)
    )

T = int(input())
for tt in range(1, T+1):
    M = int(input())
    p_list = []
    for i in range(M):
        tmp = input().split()
        for j in range(int(tmp[1])):
            p_list.append(int(tmp[0]))
    p_list.reverse()
    all_sums = p_list[:]
    all_muls = p_list[:]
    for i in range(len(p_list)-2, -1, -1):
        all_sums[i] += all_sums[i+1]
        all_muls[i] *= all_muls[i+1]

    result = solve(0, 1, p_list, 0, all_sums, all_muls)

    print('Case #{}: {}'.format(tt, result))

# p_list = [2, 2, 3, 5, 5, 7, 11]
# all_sums = p_list[:]
# all_muls = p_list[:]
# for i in range(len(p_list)-2, -1, -1):
#     all_sums[i] += all_sums[i+1]
#     all_muls[i] *= all_muls[i+1]
# print(all_sums)
# print(all_muls)
# print(solve(0, 1, p_list, 0, all_sums, all_muls))