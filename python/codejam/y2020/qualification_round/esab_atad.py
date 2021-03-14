'''
2021.3.14

크기 B인 비트열이 주어지고, 한번에 한 비트만 질의하여 확인할 수 있는 상황에서,
최대 150번까지만 확인하고 뭔지 맞추는 문제.
다만, 1, 11, 21,... 번째 질의에서 아래 4가지 중 하나가 25%의 확률로 실행되는데, 
뭐가 실행되었는지 알려주지 않는다.
- 반전: 0은 1로, 1은 0으로.
- 순서뒤집기: 앞은 뒤로, 뒤는 앞으로.
- 반전 + 순서뒤집기
- 그냥 내비두기
#interactive

(input)
T B: 테스트케이스의 수, 비트의 크기
(interactive input and output)
(output) 질의해서 값을 확인하고자 하는 비트의 위치(1 ~ B)
(input) 비트값. 0 또는 1
최대 150회 반복
(output) 예상되는 비트열
(input) 정답 여부. Y 또는 N

(solution 1)
10번 확인하면 바뀌고, 또 10번 확인하면 바뀌고..
최초 10번에서는 앞5개, 뒤5개 값 확인.
이후 10번 확인턴에서는, 처음 3번을 이용해 어떻게 변환됐는지 확인한다.
앞과 뒤의 대응되는 위치에 있는 값들을 이용하는데,
step1) 원래 0,0 또는 1,1 이었던 경우 어떤게 변했는지 확인하여 4개의 변환케이스 중 후보 2개를 추린다.
step2) 원래 0,1 또는 1,0 이었던 경우에서 값 하나만 확인하여 변환케이스 후보 2개 중 진짜를 찾는다.
이때까지 찾은 값들을 해당 방식으로 변환한다.
변환케이스)
0 => 반전: 0은 1로, 1은 0으로.
1 => 순서뒤집기: 앞은 뒤로, 뒤는 앞으로.
2 => 반전 + 순서뒤집기
3 => 그냥 내비두기

(memo)
분명히 제대로 작성한 것 같은데 interactive_runner로 테스트를 해보면 실패가 랜덤하게 발생했다.(성공하기도 하고 실패하기도 하고)
몇번의 테스트 결과에서는, 10번째 비트만 달랐다.
문제는, result에 left쪽을 하나 더 담은 상태에서 순서뒤집기가 발생하면 right쪽이 하나 더 많아지게 된다는 것을 고려하지 못해서 발생한 것이었다.
'''

def solve(B):
    result = [None] * B
    left_idx = 0
    checks = {}

    def check_and_convert():
        nonlocal result, left_idx
        if 'same' not in checks:
            for left in range(left_idx+1):
                right = B - 1 - left
                if result[left] is not None and result[right] is not None and result[left] == result[right]:
                    checks['same'] = [left, result[left]]
                    break
        if 'diff' not in checks:
            for left in range(left_idx+1):
                right = B - 1 - left
                if result[left] is not None and result[right] is not None and result[left] != result[right]:
                    checks['diff'] = [left, result[left]]
                    break
        ii = 0
        convert_type = 3
        if 'same' in checks:
            print(checks['same'][0]+1)
            bit_for_same = input()
            ii += 1
            if checks['same'][1] == bit_for_same:
                if 'diff' in checks:
                    print(checks['diff'][0]+1)
                    bit_for_diff = input()
                    ii += 1
                    if checks['diff'][1] == bit_for_diff:
                        convert_type = 3
                    else:
                        convert_type = 1
                else:
                    convert_type = 3
            else:
                if 'diff' in checks:
                    print(checks['diff'][0]+1)
                    bit_for_diff = input()
                    ii += 1
                    if checks['diff'][1] == bit_for_diff:
                        convert_type = 2
                    else:
                        convert_type = 0
                else:
                    convert_type = 0
        else:
            print(checks['diff'][0]+1)
            bit_for_diff = input()
            ii += 1
            if checks['diff'][1] == bit_for_diff:
                if 'same' in checks:
                    print(checks['same'][0]+1)
                    bit_for_same = input()
                    ii += 1
                    if checks['same'][1] == bit_for_same:
                        convert_type = 3
                    else:
                        convert_type = 2
                else:
                    convert_type = 3
            else:
                if 'same' in checks:
                    print(checks['same'][0]+1)
                    bit_for_same = input()
                    ii += 1
                    if checks['same'][1] == bit_for_same:
                        convert_type = 1
                    else:
                        convert_type = 0
                else:
                    convert_type = 0

        if 'same' not in checks or 'diff' not in checks:
            right_idx = B - 1 - left_idx
            if result[left_idx] is None or result[right_idx] is None:
                result[left_idx] = None
                result[right_idx] = None
                left_idx -= 1
        
        if convert_type == 0:
            for i in range(B):
                if result[i] is not None:
                    result[i] = '1' if result[i]=='0' else '0'
        elif convert_type == 1:
            result = result[::-1]
        elif convert_type == 2:
            for i in range(B):
                if result[i] is not None:
                    result[i] = '1' if result[i]=='0' else '0'
            result = result[::-1]

        if 'same' in checks:
            checks['same'][1] = result[checks['same'][0]]
        if 'diff' in checks:
            checks['diff'][1] = result[checks['diff'][0]]
        
        return ii

    def query():
        nonlocal left_idx
        right_idx = B - 1 - left_idx
        if result[left_idx] is None:
            idx = left_idx
        elif result[right_idx] is None:
            idx = right_idx
        else:
            left_idx += 1
            right_idx -= 1
            if result[left_idx] is None:
                idx = left_idx
            elif result[right_idx] is None:
                idx = right_idx
        print(idx+1)
        result[idx] = input()
        if left_idx == (B//2)-1 and result[left_idx] is not None and result[right_idx] is not None:
            return True
        return False

    def submit_result():
        print(''.join(result))
        yn = input()
        return yn == 'Y'

    i = 1
    while i <= 150:
        if i > 10 and (i % 10) == 1:
            ii = check_and_convert()
            i += ii
            continue
        if query():
            break
        i += 1
    return submit_result()

tmp = input().split()
T, B = int(tmp[0]), int(tmp[1])
for tt in range(1, T+1):
    if not solve(B):
        break
