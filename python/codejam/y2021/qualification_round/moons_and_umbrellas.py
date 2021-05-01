'''
2021.3.27

그믐달과 접힌 우산을 그리는 화가가 있고, CJ와 JC에 저작권을 갖고 있는 트롤이 있다.
트롤이, 그믐달은 C와 같고 접힌 우산은 J와 같으니, 그림에서 CJ와 JC가 있으면 저작권료를 내란다.
CJ의 저작권료는 X, JC의 저작권료는 Y
화가는 아직 그림을 그리는 중이라 남은 부분에 뭘 그릴지 결정해야 하는데,
화가의 결정에 따른, 저작권료의 최소값은 얼마인가?

(input)
T: 테스트케이스의 수
X Y S: X는 CJ에 대한 저작권료, Y는 JC에 대한 저작권료, S는 C,J,? 로 구성된 문자열.
?는 화가가 아직 그림을 그리지 않은 빈공간을 의미한다.
X와 Y는 음수가 가능하다. 이 경우 화가가 광고비 명목으로 돈을 받는 것이라 가정한다.

(output)
Case #x: y
y는 저작권료 최소값

(solution 1)
?를 C 또는 J로 치환할때, 
    비용 최소화를 위해 둘 중 한경우만 고려해도 되는 경우들이 있다.
    ? 뒤에 C 또는 J가 있는 경우, 그 뒤에 있을 ?에 영향을 주지 않으므로, 한놈 정하고 넘어갈 수 있다.
    ?가 연속적으로 몰려 있는 경우, 그 앞과 뒤 값의 경우의 수 9가지(값이 없는 경우 포함)에 대해 최선의 경우를 바로 만들 수 있다.
?구간들을 먼저 탐색하면서 최선의 선택을 하도록 C or J를 넣고, 그 뒤에 전체적으로 비용을 계산한다.
if X >= 0 and Y >= 0:
    ?구간은 앞글자 반복
elif X < 0 or Y < 0:
    if X + Y < 0:
        C,J가 번갈아 나오기
    else:
        앞글자 반복
else:
    C,J가 번갈아 나오기
'''

def solve(X, Y, l):
    i, j = 0, 0
    while i < len(l)-1:
        if l[i] != '?':
            i += 1
            continue
        j = i
        while j+1 < len(l) and l[j+1] == '?':
            j += 1
        
        fill_type = 1
        if X >= 0 and Y >= 0:
            pass
        elif X < 0 or Y < 0:
            if X+Y < 0:
                fill_type = 2
        else:
            fill_type = 2
        
        c = 'C'
        if fill_type == 1:
            if i-1 >= 0:
                c = l[i-1]
            elif j+1 < len(l):
                if l[j+1] == 'J' and X < 0:
                    c = 'C'
                elif l[j+1] == 'C' and Y < 0:
                    c = 'J'
                else:
                    c = l[j+1]
            for k in range(i, j+1):
                l[k] = c
        else:
            is_odd = ((j-i+1)%2 == 1)
            if i-1 >= 0:
                c = 'J' if l[i-1] == 'C' else 'C'
            elif j+1 < len(l):
                if l[j+1] == 'C':
                    if is_odd:
                        c = 'J' if Y < 0 else 'C'
                    else:
                        c = 'C' if X < 0 else 'J'
                else:
                    if is_odd:
                        c = 'C' if X < 0 else 'J'
                    else:
                        c = 'J' if Y < 0 else 'C'    
            else:
                c = 'C' if X < Y else 'J'
                for k in range(i, j+1):
                    l[k] = c
                    c = 'C' if c == 'J' else 'J'
        i = j + 1

    result = 0
    for i in range(len(l)-1):
        if l[i] == 'C' and l[i+1] == 'J':
            result += X
        elif l[i] == 'J' and l[i+1] == 'C':
            result += Y

    return result

T = int(input())
for tt in range(1, T+1):
    tmp = input().split()
    X, Y, l = int(tmp[0]), int(tmp[1]), list(tmp[2])
    result = solve(X, Y, l)
    print('Case #{}: {}'.format(tt, result))