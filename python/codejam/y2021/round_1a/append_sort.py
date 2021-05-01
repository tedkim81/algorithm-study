'''
2021.4.10

주어진 순열을 정렬해야 하는데, 값들의 위치를 바꿀수는 없고, 
값 뒤에 숫자를 넣어서 억지로 큰수가 되어 정렬된 상태가 되게 만들어야 한다.
숫자를 최소 몇번 넣어야 (strictly 하게) 정렬된 상태를 만들 수 있나?

(input)
T: 테스트케이스의 수
N: 순열의 항목수
X1 X2 ... : 순열의 값들

(output)
Case #x: y
y는, 숫자 append 하는 최소 횟수

(solution 1)
현재와 이전 숫자 비교하여 현재 숫자 처리하면서 끝까지 진행.
비교 케이스들
- 이전숫자 < 현재숫자: skip
- 이전숫자 >= 현재숫자
  + [이전숫자의 현재숫자 자릿수만큼] < 현재숫자: 남은자릿수 0추가
  + [이전숫자의 현재숫자 자릿수만큼] == 현재숫자: 이전숫자에 +1
  + [이전숫자의 현재숫자 자릿수만큼] > 현재숫자: [남은자릿수+1] 0추가
위 과정을 거치면서 추가되는 자릿수는 별도로 계산한다.

(solution 1 result)
Test Set 1 통과. Test Set 2는 Wrong Answer.
왜 실패하는지 현재로서는 모르겠다. 나중에 analysis를 확인해 보자.
'''

def solve(N, X):
    cnt = 0
    for i in range(1, N):
        if int(X[i-1]) < int(X[i]):
            continue
        prev_sz = len(X[i-1])
        curr_sz = len(X[i])
        xx = X[i-1][:curr_sz]
        if xx < X[i]:
            for j in range(prev_sz-curr_sz):
                X[i] += '0'
                cnt += 1
        elif xx == X[i]:
            if prev_sz == curr_sz:
                X[i] += '0'
                cnt += 1
            else:
                if X[i-1][-1] == '9':
                    for j in range(prev_sz-curr_sz+1):
                        X[i] += '0'
                        cnt += 1
                else:
                    X[i] = str(int(X[i-1])+1)
                    cnt += prev_sz - curr_sz
        else:
            for j in range(prev_sz-curr_sz+1):
                X[i] += '0'
                cnt += 1
    return cnt

T = int(input())
for tt in range(1, T+1):
    N = int(input())
    X = input().split()
    result = solve(N, X)
    print('Case #{}: {}'.format(tt, result))