'''
2021.3.27

100명의 선수가 각각 10000개의 퀴즈를 푼다.
i번째 선수의 skill level은 Si, j번째 퀴즈의 difficulty level은 Qj.
S와 Q 값들은 [-3.0, 3.0] 범위에서 랜덤하게 정해졌다.
퀴즈를 맞출 확률은 sigmoid 함수인 f에 대한, f(Si - Qj) 값이다.
그런데, 100명의 선수 중에 사기꾼이 한명 있다.
사기꾼은 퀴즈를 풀기전에 동전을 던져서 앞면이 나오면 정상적으로 문제를 풀지만,
뒷면이 나오면 인터넷에서 정답을 검색하여 문제를 푼다고 한다.
100명의 선수가 10000개의 퀴즈를 푼 결과 각 퀴즈의 정답여부(1 또는 0)가 주어질때,
사기꾼을 찾아내는 것이 문제.

(input)
T: 테스트케이스의 수
P: 사기꾼을 제대로 찾은 확률의 커트라인(백분율). 예) T=50, P=86이면, 43번 이상 사기꾼을 맞춰야 통과.
1과 0으로 구성된 10000자 문자열 100줄.

(output)
Case #x: y
y는 사기꾼의 번호(첫번째 선수가 1)

(solution 1)
각 퀴즈들에 대해 정답률을 구한후, 정답률이 낮은 순으로(어려운 문제 순으로) 정렬한다.
각 선수별로 기준선을 0부터 10000까지 옮기면서 분산값을 구한다.
    기준선의 좌측은 y=0 함수에 대한 분산, 우측은 y=1 함수에 대한 분산.
분산값이 가장 큰 놈이 사기꾼.

(solution 1 result)
실패. 일부 어려운 퀴즈 구간에서는 분산값이 클 수 있으나, 쉬운 문제 구간에서 압도적으로 분산이 작아진다.
sample case에서 59번 선수가 분산값이 가장 작았다..

(solution 2)
퀴즈를 어려운 문제 순으로 정렬을 하고, 어려운 문제 2000개 정도에 대해서만 정답률이 가장 높은게 사기꾼이라 가정한다.

(solution 2 result)
test set 1은 통과하지만 test set 2에서는 Wrong Answer.
Si가 매우 높은 경우 사기를 안쳐도 정답률이 매우 높을 수 있기 때문에 안되는듯.

(solution 3)
어려운 문제 구간에서 0과 1 변화의 횟수가 제일 많은 경우가 사기꾼이라 가정한다.
'''

def solve(marks):
    size_q = len(marks[0])
    size_p = len(marks)
    q_map = {}
    for i in range(size_q):
        correct_cnt = 0
        for mk in marks:
            if mk[i] == '1':
                correct_cnt += 1
        q_map[i] = correct_cnt / size_p
    q_sorted = sorted(q_map.items(), key=(lambda x: x[1]))

    player_idx = 0
    max_correct = -1
    for p_idx in range(size_p):
        mark_part = []
        for q_idx, dummy in q_sorted:
            mark_part.append(marks[p_idx][q_idx])
            if len(mark_part) > 2000:
                break
        p_correct = mark_part.count('1')
        if p_correct > max_correct:
            player_idx = p_idx
            max_correct = p_correct
    return player_idx+1

def solve2(marks):
    size_q = len(marks[0])
    size_p = len(marks)
    q_map = {}
    for i in range(size_q):
        correct_cnt = 0
        for mk in marks:
            if mk[i] == '1':
                correct_cnt += 1
        q_map[i] = correct_cnt / size_p
    q_sorted = sorted(q_map.items(), key=(lambda x: x[1]))

    player_idx = 0
    max_change = -1
    for p_idx in range(size_p):
        mk = marks[p_idx]
        ii = 0
        curr = 0
        change = 0
        correct = 0
        for q_idx, dummy in q_sorted:
            if mk[q_idx] == '1':
                correct += 1
            if mk[q_idx] != curr:
                change += 1
                curr = mk[q_idx]
            ii += 1
            if ii > 1000:
                break
        if correct > 600 and change > max_change:
            player_idx = p_idx
            max_change = change
    return player_idx+1

T = int(input())
P = int(input())
for tt in range(1, T+1):
    marks = []
    for i in range(100):
        marks.append(input())
    result = solve2(marks)
    print('Case #{}: {}'.format(tt, result))

# f = open("cheating_detection_sample.txt", "r")
# T = int(f.readline())
# P = int(f.readline())
# marks = []
# for i in range(100):
#     marks.append(f.readline()[:10000])
# f.close()
# print(solve2(marks))