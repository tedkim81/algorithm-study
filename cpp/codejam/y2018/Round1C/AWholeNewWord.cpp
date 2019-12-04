/**
 * 2019.3.22
 *
 * https://codingcompetitions.withgoogle.com/codejam/round/0000000000007765/000000000003e064
 *
 * 길이가 L인 단어 N개가 있다. 
 * 각 단어의 영어 알파벳에는 1부터 L까지의 숫자도 매겨져 있다. 
 * 이게 순서를 의미한다.
 * 주인공의 친구가 그 L*N 개의 알파벳을 가지고 
 * 그 중에 없었던 새로운 단어를 만들고자 한다.
 * 근데 그냥 막 만들면 되는게 아니고 두가지 조건이 있는데,
 * - 새 단어의 글자수도 L
 * - 알파벳에 매겨져 있는 순서가 지켜져야 한다.
 * 이렇다. 이러한 조건을 지켜가면서 
 * 새로운 단어를 만들 수 있다면 가능한 단어 중에 하나를 출력하고,
 * 그렇지 않다면 대시(-)를 출력하란다.
 *
 * (input)
 * T: 테스트케이스의 수
 * N L: N은 단어의 수, L은 단어의 길이
 * 영대문자로 구성된 단어들 (N행)
 *
 * (output)
 * 새로운 단어를 만들 수 있다면 아무거나 하나 출력, 그렇지 않다면 대시(-) 출력
 *
 * (solution 1)
 * 문제를 그저 단순하게 생각해보면, 
 * 첫줄 첫번째 알파벳부터 순차적으로 깊이우선 탐색해서 L번째 알파벳까지 탐색하고
 * 탐색된 L개의 알파벳이 기존 단어 리스트에 있는지 확인하여 
 * 있다면 다시 돌고, 이런식으로 없는게 나올때까지, 
 * 아니면 전체 알파벳을 다 탐색할때까지 반복하고, 
 * 없는게 나왔다면 걔를 출력하고, 끝까지 안나왔다면 대시(-)를 출력하면 된다.
 * 하지만 이렇게 하면 시간복잡도가 O(N의 L제곱) 정도가 나와버리니, 
 * 이 정도면 Test set 1도 간신히 될동말동이다;;
 *
 * 이건 일단 N개의 단어를 사전순으로 정렬해 놓는 것이 그 다음을 생각하기에 수월하겠다.
 * 그리고, 이 문제에서는 "새 단어를 만들 수 없는 경우"를 빨리 찾을 수 있어야 한다. 
 * 위의 단순한 방식으로 풀때, 모든 경우를 다 탐색해야 하는 
 * 그 worst case가 바로 새 단어를 만들 수 없는 경우이기 때문.
 * 새 단어를 만들 수 없는 경우는 어떤 경우일까..
 * 만약 4개의 단어가 있는데 첫 열이 A와 B로만 구성된다면, 
 * A 뒤에 오는 단어들 집합과 B 뒤에 오는 단어들 집합이 정확히 같아야
 * 새 단어를 만들 수 없게 된다. 
 * 만약 다른게 하나라도 있다면 그 집합에다가 앞에 A 또는 B를 붙이면 새 단어가 되기 때문.
 * 아하.. 느낌 왔다.
 * 1열에서 알파벳이 하나 밖에 없으면 바로 다음 열로 넘어간다. 
 * 근데 만약 둘 이상이면? 
 * 그 다음 열부터 이루어진 문자열의 집합들이 서로 같은지를 확인하여 
 * 만약 같다면 새 단어를 만들 수 없는거고,
 * 다른게 있다면 그 문자열에다 앞에 원래 걔 것이 아니었던 알파벳을 붙여주면 되는 것이다.
 * 자아~! 이제 슈도코드 타임~!
 * -------------------------------------
 * string W[N] // 단어를 사전순으로 정렬한 후 알파벳들을 W에 담는다.
 * 
 * for 0 <= i < L-1: // 마지막열 보기전에 결판이 난다.
 *   map<char, int> mm // i열에 알파벳들이 각각 몇개씩 들어있나.
 *   for 0 <= j < N:
 *     mm[W[j][i]]=1 또는 mm[W[j][i]]++
 *   if mm.size() == 1:  // i열에 알파벳이 한가지 밖에 없으면 다음열 탐색
 *     continue
 *   else:  
 *     // 둘 이상이면 걔네를 그룹으로 나누고, 다음열부터 이어지는 문자열들이 그룹별로 정확히 같은지 확인.
 *     char cc[2000][10]
 *     for mm 탐색:
 *       if 첫그룹:
 *         cc에 i+1열부터의 값들 그대로 담기
 *       else:
 *         cc와 비교하여 다른 값 발견되면, 
 *         return (이전 그룹 앞부분)+(현재행의 i+1열 이후의 값들)
 * return "-"
 * -------------------------------------
 *
 * (solution 1 result)
 * 실패.. WA(Wrong Answer).. 
 * 뭐 딱히 확신이 있었던건 아니지만 그래도 실망스럽다..;;
 * Test set 1에서부터 실패가 났고, 
 * 얘는 그래도 뭔가 test case를 만들어보기가 쉬운 편이라 대강 입력해 봤는데,
 * 바로 문제를 발견했다.
 * AA
 * AC
 * BC
 * 이때 결과가 BC가 뜨는게 아닌가..
 * 0번째 열에서 A가 2개고 B가 1개니까 일단 답은 존재하는 상황인데, 
 * 문제는 "AC"의 "C"와 "BC"의 "B"를 붙이니 기존에 존재하는 단어가 되어 버린 것!
 * A그룹의 "A"와 "C" 중에 "A"를 고르도록 알고리즘을 수정해야 한다.
 * 이전 코드에서는 각 그룹의 행 수가 다른 경우 
 * 일일이 비교할 필요 없이 바로 답을 찾도록 했는데(주석 처리된 부분),
 * 이게 문제였다. 그룹별 행의 수가 다른 경우도 같은 경우와 마찬가지 방식으로 탐색하되,
 * 비교 대상의 크기가 서로 다를 수 있음을 코드 안에 잘 녹여야겠다.
 * 생각한대로 코드를 수정하여 꽤 자신감을 가지고 서밋을 해봤으나.. 또 다시 WA..
 * Test set 2는 몰라도 1은 통과할 줄 알았는데, 또 실패했다.
 * 이번에는 초기 입력값을 지정하고 새로운 단어를 생성할 수 없을때까지 
 * 반복실행하면서 출력하도록 디버깅해 보자.
 * 이상하다.. 문제가 없어 보이는데.. 적어도 Test set 1은 통과해야할 듯 한데..
 * ... 한참 삽질 후 ... 찾았다..
 * AA
 * AB
 * BB
 * BC
 * 이때의 결과가 AB가 뜬다.. 문제가 있었다. 
 * 현재 알고리즘은, 비교를 하다가 다른 부분을 찾았을때 
 * 서로 다르다고 판단된 두개의 행 중 어떤걸 써도 상관 없다고 전제했는데,
 * 사전순으로 더 먼저 있는 것을 사용해야 한다. 
 * 나중에 있는 것은 더 먼저 있던 녀석의 뒤에 또 나올 수도 있기 때문.
 * 자아.. 다시 알고리즘을 수정하고 리츄라이 해보자!
 * 
 * 드디어 성공!
 * 문제가 좀 쉽다고 생각했는데, 그런데도 헤맸다.. 
 * 아카이브에 올라와 있는 문제들을 다 풀었을 때쯤엔 좀 나아져 있으려나..?
 * 뭐.. 일단 목표는 있는거 다풀자 였으니 딴 생각 하지 말고 일단 다 풀어보기나 하자.
 */

#include <stdio.h>
#include <iostream>
#include <map>
#include <sstream>
#include <string>
#include <algorithm>

using namespace std;

int N, L;
string W[2000];

string solve() {
	sort(W, W+N);
	char cc[2000][10];
	for (int i=0; i<L-1; i++) {
		map<char, int> mm;
		for (int j=0; j<N; j++) {
			mm[W[j][i]]++;
		}
		if (mm.size() == 1) {
			continue;
		} else {
			int row = 0;
			int ccrows = 0;
			for (pair<char, int> m : mm) {
				if (row == 0) {
					ccrows = m.second;
				} 
				// else {
				// 	if (m.second > ccrows) {
				// 		stringstream ss;
				// 		ss << W[row].substr(0, i+1) << W[row+m.second-1].substr(i+1, L-(i+1));
				// 		return ss.str();
				// 	} else if (m.second < ccrows) {
				// 		stringstream ss;
				// 		ss << W[row].substr(0, i+1) << W[row-1].substr(i+1, L-(i+1));
				// 		return ss.str();
				// 	}
				// }
				for (int ii=row; ii<row+m.second; ii++) {
					for (int jj=i+1; jj<L; jj++) {
						if (row == 0) {
							cc[ii-row][jj] = W[ii][jj];
						} else {
							if (ii-row > ccrows-1 || W[ii][jj] < cc[ii-row][jj]) {
								stringstream ss;
								ss << W[row-1].substr(0, i+1) << W[ii].substr(i+1, L-(i+1));
								return ss.str();
							} else if (W[ii][jj] > cc[ii-row][jj]) {
								stringstream ss;
								ss << W[row].substr(0, i+1) << W[ii-row].substr(i+1, L-(i+1));
								return ss.str();
							}
						}
					}
				}
				if (ccrows > m.second) {
					stringstream ss;
					ss << W[row].substr(0, i+1) << W[ccrows-1].substr(i+1, L-(i+1));
					return ss.str();
				}
				row += m.second;
			}
		}
	}
	return "-";
}

void debug() {
	cout << "debug!" << endl;
	N = 4;
	L = 2;
	W[0] = "AA";
	W[1] = "BB";
	W[2] = "CC";
	W[3] = "DD";
	int idx = 4;
	for (int i=0; i<1000; i++) {
		string result = solve();
		if (result.size() == 2) {
			W[idx++] = result;
			N++;
			for (int j=0; j<idx; j++) {
				cout << W[j] << " ";
			}
			cout << endl;
		} else {
			break;
		}
	}
	cout << "size: " << idx << endl;
}

int main() {
	int T;
	cin >> T;
	for (int i=0; i<T; i++) {
		cin >> N >> L;
		for (int j=0; j<N; j++) {
			cin >> W[j];
		}
		string result = solve();
		cout << "Case #" << i+1 << ": " << result << endl;
	}
}