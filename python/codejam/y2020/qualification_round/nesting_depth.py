'''
2021.3.13

0~9 숫자로 구성된 문자열 S에서, 
각 숫자 digit들이 그 수만큼의 깊이로 괄호안에 들어가도록 괄호가 추가된 문자열 만들어서 리턴.
01221 => 0(1(22)1)

(input)
T: 테스트케이스의 수
S: 0~9로 구성된 문자열

(output)
Case #x: ss
ss: S에 규칙에 맞도록 괄호 추가된 문자열

(solution 1)
S의 각 digit을 탐색하면서, ss를 만드는데, 
괄호 열린 숫자 p_open과 digit을 비교하여 여는 괄호 및 닫는 괄호를 추가한다.
'''

def solve(S):
    ss = ''
    p_open = 0
    for dd in S:
        d = int(dd)
        if d > p_open:
            ss += '(' * (d - p_open)
        elif d < p_open:
            ss += ')' * (p_open - d)
        p_open = d
        ss += dd
    ss += ')' * p_open
    return ss

T = int(input())
for tt in range(1, T+1):
    S = input()
    result = solve(S)
    print('Case #{}: {}'.format(tt, result))