/**
 * 2019.1.4
 *
 * https://codingcompetitions.withgoogle.com/codejam/round/00000000000000cb/0000000000007a30
 *
 * 1000x1000 크기의 밭에 나무 심을 영역을 직사각형으로 만들려고 한다.
 * 직사각형의 넓이는 A 이상이 되어야 한다. (1x1 영역이 A개 이상)
 * 나무 심을 영역은 1x1 영역에 구덩이 하나씩이 파져야 하는데, 
 * 이것을 두더지한테 시킨단다;;
 * 근데 두더지가 말귀를 그렇게 잘 알아듣는 애는 아니어서, 
 * "(10,10)을 파!"라고 시키면 
 * 그것을 중심으로 3x3영역, 즉 (9,9)~(11,11) 중에 하나를 지맘대로 골라서 판단다.
 * 최대 1000번을 시키게 되는데, 그 안에 직사각형 영역이 만들어져야 한다.
 * 두더지한테 땅파라고 지시할 위치들을 #interactive 하게 응답하자.
 *
 * (input)
 * T: 테스트케이스의 수
 * A: 20 또는 200
 *
 * (interactive input and output)
 * (ro1, co1) out => (ri1, ci1) in => (ro2, co2) out => (ri2, ci2) in => ...
 * input 받은 (ri1, ci1), (ri2, ci2),.. 가 최소 A개 이상이고 직사각형 형태가 되면 성공.
 * output 값은 (2,2)~(999,999) 사이의 값이어야 한다.(그렇지 않으면 (-1,-1)을 input 받고 게임 끝.)
 * 마지막에 어떤 (ro, co) output에 대하여 선택된 (ri, ci)로 직사각형이 완성되는 상황이 되면, 
 * (ri, ci) 대신에 (0, 0) input으로 주게 되고, 이번 테스트케이스는 성공이므로, 
 * 다음 테스트케이스를 위해 A를 input 받는다.
 *
 * (solution 1)
 * A가 20일때는 4x5 영역으로, A가 200일때는 8x25 영역으로 고정하고 시작하자.
 * 영역의 모양에는 답이 없다. 두더지가 어딜 파냐에 따라 모양이 달라지는게 아니라, 
 * 모양을 정해놓고 채운다는게 포인트다.
 * 4x5일때, 3x3을 4번 덮어서 모두 덮을 수 있고 이때가 최소다. 
 * 마찬가지로 8x25일때, 3x3으로 27번 덮어서 모두 덮을 수 있고 이때가 최소다.
 * 영역의 좌측상단부터 우측방향으로 빈곳을 계속 반복 탐색한다.
 * 그런식으로 모든 영역을 채울때까지 반복한다.(0,0을 받을때까지 반복한다.)
 *
 * 아.. 전략을 너무 추상적으로만 짰더니 진도가 안나간다. 좀더 전략을 구체화 해보자.
 * 일단 결과 직사각형 영역을 나타내는 맵이 필요하다. 
 * int bat[9][26] => 0:땅파기전, 1:땅판후
 * bat[2][2]에서 bat[7][24]까지를 반복 탐색하면서 땅을 파라고 시켜야하는지 확인.
 * 해당 위치의 bat이 0이면 당연히 땅을 파라고 시키는데,
 * 만약 (row가 2 or 7) or (col이 2 or 24)이면, 
 * 인접한 가장자리 지역도 bat을 확인해서 0인 위치가 있으면 땅을 파야한다.
 * (ri, ci) 입력받아서 이 위치의 bat이 0이면 1로 업데이트.
 *
 * (solution 1 result)
 * 성공!..하긴 했으나 얘도 한참 걸렸다.
 * 한참 걸린 원인 몇가지,
 * - 문제에서 A는 20 or 200인데, testing_tool.py에 LIST_OF_A = [10, 10, 10] 로 되어 있었다.
 * - 알고리즘을 너무 추상적으로만 생각한 상태에서 코딩을 시작했다.
 */

#include <stdio.h>
#include <iostream>

using namespace std;

int main() {
	int T;
	cin >> T;
	for (int i=0; i<T; i++) {
		int A;
		cin >> A;
		int bat[9][26] = {};
		int max_r, max_c;
		if (A == 200) {
			max_r = 8;
			max_c = 25;
		} else {
			max_r = 4;
			max_c = 5;
		}
		int peer_r[8] = {-1,-1,-1,0,0,1,1,1};
		int peer_c[8] = {-1,0,1,-1,1,-1,0,1};
		bool end = false;
		bool error = false;
		// int cnt = 0;
		for (int j=0; j<1000; j++) {
			for (int r=2; r<max_r; r++) {
				for (int c=2; c<max_c; c++) {
					bool out = false;
					if (bat[r][c] == 0) {
						out = true;
					} else if (r == 2 || r == max_r-1 || c == 2 || c == max_c-1) {
						for (int p=0; p<8; p++) {
							int pr = r + peer_r[p];
							int pc = c + peer_c[p];
							if (bat[pr][pc] == 0) {
								out = true;
								break;
							}
						}
					}
					if (out) {
						cout << r << " " << c << endl;
						int ri, ci;
						cin >> ri >> ci;
						if (ri == 0 && ci == 0) {
							end = true;
						} else if (ri == -1 && ci == -1) {
							end = true;
							error = true;
						} else if (bat[ri][ci] == 0) {
							bat[ri][ci] = 1;
							// cnt++;
						}
					}
					if (end) break;
				}
				if (end) break;
			}
			if (end) break;
		}
		// cerr << "end: " << end << ", dig cnt: " << cnt << endl;
		// for (int r=1; r<=8; r++) {
		// 	for (int c=1; c<=25; c++) {
		// 		cerr << bat[r][c];
		// 	}
		// 	cerr << endl;
		// }
		if (error) break;
	}
}