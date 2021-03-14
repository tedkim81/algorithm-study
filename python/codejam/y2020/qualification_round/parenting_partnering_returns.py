'''
2021.3.21

N개의 할일이 있고, 각각은 시작시간과 종료시간이 정해져 있다.
Cameron과 Jamie가 그 일들을 나눠서 해야 하는데, 시간이 겹치는 일을 한사람이 동시에 할 수는 없다.
일을 어떻게 분담할지 결정하는 문제.
문제에서 일이 주어진 순서대로, JCJC.. 이런식으로 매핑하거나,
만약 모든 일을 분담하는게 불가능하면 IMPOSSIBLE

(input)
T: 테스트케이스의 수
N: 일의 수
(N행의) S E: 시작시각(0시에서 지난 minutes)과 종료시각

(output)
Case #x: y
y: J와 C로 구성된 문자열, 또는 IMPOSSIBLE

(solution 1)
acts=[]에 (s,e)들을 담는다.
connects={}에 act들의 index 기준으로 누가 누구랑 겹치는지 정보를 담는다.
connects에 있는 애들을 먼저 jset 또는 cset 에 담는데, 겹치는 애들을 dfs로 담는다.
이 과정에서 jset 또는 cset에 담기 전에 겹치는 애가 이미 거기에 있는지 확인하여,
만약 있다면 IMPOSSIBLE
connects 다 담았으면, 나머지는 다 jset에 담으면 된다고 가정하고,
['J']*N 에서 cset에 해당하는 애들만 C로 바꾸고, 문자열로 변환하여 리턴.
'''

def solve(N, acts):
    connects = {}
    for i in range(len(acts)-1):
        for j in range(i+1, len(acts)):
            if acts[j][0] <= acts[i][0] < acts[j][1] or acts[i][0] <= acts[j][0] < acts[i][1]:
                if i not in connects:
                    connects[i] = set()
                connects[i].add(j)
                if j not in connects:
                    connects[j] = set()
                connects[j].add(i)
    
    jset, cset = set(), set()

    def dfs(i, is_j):
        if i in jset or i in cset:
            return True

        if is_j:
            s = jset
        else:
            s = cset
        
        if connects[i] & s:
            return False
        
        s.add(i)
        for a in connects[i]:
            if not dfs(a, not is_j):
                return False
        return True

    for i in connects:
        if not dfs(i, True):
            return 'IMPOSSIBLE'
    
    result = ['J'] * N
    for i in cset:
        result[i] = 'C'
    return ''.join(result)

T = int(input())
for tt in range(1, T+1):
    N = int(input())
    acts = []
    for i in range(N):
        se = input().split()
        acts.append((int(se[0]), int(se[1])))
    result = solve(N, acts)
    print('Case #{}: {}'.format(tt, result))