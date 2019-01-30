/**
 * 2019.1.26
 *
 * 제목은 "와플 자르는 사람"
 * R x C 크기의 커다란 격자모양 와플을 만들어서, 그 위에 초코칩을 랜덤하게 올리고 나서,
 * 횡으로(가로로) H번 자르고, 종으로(세로로) V번 잘라서 사람들한테 나눠준단다.
 * 잘라진 조각의 크기는 같을 필요가 없는데, 잘라진 모든 조각 각각에 있는 초코칩의 수는 같아야 한단다.
 * 이게 가능하면 POSSIBLE, 불가능하면 IMPOSSIBLE 을 출력하는 문제.
 *
 * (input)
 * T: 테스트케이스의 수
 * R C H V: R은 row의 수, C는 column의 수, H는 horizontal하게 자르는 수, V는 vertical하게 자르는 수
 * R행 C열에 골뱅이(@)와 점(.)으로 와플을 형상화한 텍스트. 골뱅이(@)는 초코칩 있는거, 점(.)은 초코칩 없는거.
 * 예) R=3, C=6
 * .@@..@
 * .....@
 * @.@.@@
 *
 * (output)
 * 횡으로 H번, 종으로 V번 잘라서, 모든 조각에 초코칩이 평등하게 들어가게 할 수 있으면, POSSIBLE
 * 불가능하면, IMPOSSIBLE 출력
 *
 * (solution 1)
 * 나눠진 모든 조각에서의 초코칩의 수가 같아야 한다는 조건을 우선 생각하자.
 * 조각의 수는 (H+1)*(V+1)이고, 초코칩의 수는 @의 수. 즉, 초코칩 수는 조각 수의 "배수"가 되어야 한다.
 * 그렇지 않으면 바로 IMPOSSIBLE.
 * @의 총수는 cnt_choco_total, 한 조각에 들어가는 수는 cnt_choco = cnt_choco_total / ((H+1)*(V+1))
 * 잘린 영역 안에 초코칩이 cnt_choco 이상이 들어 있도록, 횡으로 자를 수 있는 경우들을 모두 탐색하는데,
 * 자를 횟수가 남으면 IMPOSSIBLE.
 * 종으로 잘린 좌측 조각에 초코칩이 cnt_choco만큼 들어있도록 탐색하면서, 
 * 불가능하거나 종으로 자를 횟수가 남거나 하면 IMPOSSIBLE.
 * 다 잘랐을때 횡으로 H번, 종으로 V번 잘랐다면, POSSIBLE.
 * 탐색은 재귀호출로 구현하고, 마지막 자른 위치와 남은 수를 넘겨서 부분문제화 하자.
 * 횡으로 자르는 함수는 cut_h, 종으로 자르는 함수는 cut_v
 * cut_h는 마지막 잘린 위치만 있다면 그 전에 어디 잘렸는지는 알 필요가 없는데,
 * cut_v는 cut_h에서 자른 위치들을 알아야 각 조각에 초코칩이 cnt_choco 만큼 들어가 있는지 검증할 수 있다.
 * cut_h에서 자른 위치들은 어디에 어떻게 보관할까? 어차피 자르는 수는 정해져 있으니 그냥 int h_pos_arr[100] 하면 될듯.
 *
 * (solution 1 result)
 * 성공!!
 * 하지만 코딩과 디버깅에 대략 4시간 정도가 걸렸다.. (광탈각인가..;;)
 * solution 1을 지금 다시 요약해보면,
 * 한조각에 몇개 들어가나? cnt_choco 
 * => 횡으로 자를때 잘린 영역에 초코칩 cnt_choco*(V+1)개
 * => 횡으로 다 자르고 나서, 종으로 자른다. 
 * => 종으로 자를때 잘린 영역(횡으로 잘린거 상관 없이)에 초코칩 cnt_choco*(H+1)개
 * => 종으로 잘린 영역에 횡 잘린 영역 고려하여 각 조각에 초코칩 cnt_choco개 검증
 * 꽤 간단하다. 하지만 코딩을 시작할때는 이렇게까지 정리되지 않은 상태에서 시작했고, 그래서 헤맨 것이다.
 * 코딩을 시작할때, "횡으로 자를때 종으로 자를거 신경쓸 필요 없고, 종으로 자를때 횡으로 자를거 신경쓸 필요 없다"는 것을 
 * 생각하지 못했다. 그걸 미리 생각하고 코딩했다면 시간을 단축할 수 있었을 것이다.
 * 알고리즘을 구체화 한 후에 코딩을 시작하자.
 */

#include <stdio.h>
#include <iostream>

using namespace std;

string waffle[100];
int R, C, H, V, cnt_choco_total, cnt_choco;
int h_pos_arr[100];

int get_choco_cnt(int sr, int sc, int er, int ec) {
	int choco_cnt = 0;
	for (int i=sr; i<=er; i++) {
		for (int j=sc; j<=ec; j++) {
			if (waffle[i][j] == '@') {
				choco_cnt++;
			}
		}
	}
	return choco_cnt;
}

bool cut_v(int v_pos, int v_cnt) {
	if (v_cnt == V) {
		return true;
	}
	int choco_target_cnt = cnt_choco * (H+1);
	int next_v_pos = -1;
	for (int i=v_pos+1; i<C-1; i++) {
		choco_target_cnt -= get_choco_cnt(0, i, R-1, i);
		if (choco_target_cnt == 0) {
			next_v_pos = i;
			break;
		} else if (choco_target_cnt < 0) {
			break;
		}
	}
	if (next_v_pos < 0) {
		return false;
	}
	bool result = true;
	for (int i=0; i<H; i++) {
		int sr = h_pos_arr[i]+1;
		int er;
		if (i+1 < H) {
			er = h_pos_arr[i+1];
		} else {
			er = R-1;
		}
		if (get_choco_cnt(sr, v_pos+1, er, next_v_pos) != cnt_choco) {
			result = false;
			break;
		}
	}
	if (!result) {
		return false;
	}
	return cut_v(next_v_pos, v_cnt+1);
}

bool cut_h(int h_pos, int h_cnt) {
	if (h_cnt == H) {
		return cut_v(-1, 0);
	}
	int choco_target_cnt = cnt_choco * (V+1);
	int next_h_pos = -1;
	for (int i=h_pos+1; i<R-1; i++) {
		choco_target_cnt -= get_choco_cnt(i, 0, i, C-1);
		if (choco_target_cnt == 0) {
			next_h_pos = i;
			break;
		} else if (choco_target_cnt < 0) {
			break;
		}
	}
	if (next_h_pos < 0) {
		return false;
	}
	h_pos_arr[h_cnt] = next_h_pos;
	return cut_h(next_h_pos, h_cnt+1);
}

string solve() {
	cnt_choco_total = 0;
	for (int i=0; i<R; i++) {
		for (int j=0; j<C; j++) {
			if (waffle[i][j] == '@') {
				cnt_choco_total++;
			}
		}
	}
	if (cnt_choco_total == 0) {
		return "POSSIBLE";
	}
	int cnt_splited = (H+1) * (V+1);
	if (cnt_choco_total < cnt_splited || cnt_choco_total % cnt_splited > 0) {
		return "IMPOSSIBLE";
	}
	cnt_choco = cnt_choco_total / cnt_splited;
	if (cut_h(-1, 0)) {
		return "POSSIBLE";
	}
	return "IMPOSSIBLE";
}

int main() {
	int T;
	cin >> T;
	for (int i=0; i<T; i++) {
		cin >> R >> C >> H >> V;
		for (int j=0; j<R; j++) {
			cin >> waffle[j];
		}
		string result = solve();
		cout << "Case #" << i+1 << ": " << result << endl;
	}
}