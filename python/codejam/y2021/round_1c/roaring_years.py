'''
2021.5.1

연속된 자연수로 만들어진 year를 roaring year라고 한다.
현재 년도가 주어졌을때, 다가오는 roaring year를 구하는 것이 문제.

(input)
T: 테스트케이스의 수
Y: 현재년도

(output)
Case #x: y
다가오는 roaring year

(solution 1)
정리
- 시작숫자만 결정하면 다가오는 roaring year를 만들 수 있다. (이후 RY)
- Test Set 1만 고려한다면, 최대 10**6 만큼만 확인하면 되기 때문에 간단하게 해결할 수 있다.
- 어떤 자릿수가 정해졌을때 그 자릿수에 해당하는 RY의 경우의 수는 그다지 크지 않다.
- 1자릿수는 9개, 2자릿수는 90개, 3자릿수는 900개, ...
- 만약 현재년도가 x자릿수라면 RY는 x자릿수이거나, x+1자릿수에서 제일 작은수이다.
- 1자릿수로 시작하면, 1자릿수만으로 구성되거나, 1자릿수와 2자릿수가 이어져서 구성될 수 있다.
- 2자릿수 이상으로 시작하면, 그 자릿수로만 연결되어야 한다. 즉, x자릿수로 시작하면 x*n 자릿수의 숫자만 만들 수 있다.
- 현재년도의 자릿수에 대한 경우의 수는 1부터 19까지, 19가지밖에 되지 않는다.
- 1,2,3 자릿수 구하려면, 1자릿수로 시작
- 4자릿수 구하려면, 1자릿수 또는 2자릿수로 시작
- 5자릿수 구하려면, 1자릿수 또는 99로 시작
- 6자릿수 구하려면, 1자릿수 또는 2자릿수 또는 3자릿수로 시작

일단 시간이 없어서 test set 1만 해결할 수 있도록 코드 작성후 제출.
Y 최대값이 10**6 이고 자릿수로는 7이니까, 시작숫자로 1부터 999까지만 시도해보면 된다.

(solution 1 result)
test set 1만 성공.
'''

def roaring_year(start, Y):
    tmp = str(start)
    while int(tmp+str(start+1)) <= Y:
        tmp += str(start+1)
        start += 1
    tmp += str(start+1)
    return int(tmp)

def solve(Y):
    result = 10**18 + 1
    for i in range(1000):
        result = min(result, roaring_year(i, Y))
    return result

T = int(input())
for tt in range(1, T+1):
    Y = int(input())
    result = solve(Y)
    print('Case #{}: {}'.format(tt, result))