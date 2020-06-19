/**
 * 2020.4.4
 *
 * 0과 1로 구성된 B 비트의 데이터가 저장되어 있고, 그게 뭔지 맞춰야 한다.
 * 쓸데없이 보안이 강해서 한번에 값 하나만 확인할 수 있다.
 * 1부터 B까지의 수를 보내면, 그 위치의 값이 뭔지 알려준단다.
 * 최대 150번까지 확인하고, 전체 데이터가 뭔지 알아내면 된다.
 * 그런데 문제는..!
 * 1번째, 11번째, 21번째,..에 값을 보내주기 전에 데이터가 변화한다.
 * 각각 동일하게 25%의 확률로 아래 중 한가지가 실행된다.
 * - 반전: 0은 1로, 1은 0으로 변경됨
 * - 순서뒤집기: 뒤에서부터 앞으로 순서 뒤집기
 * - 반전하고 순서뒤집기
 * - 그냥 내비둠
 * 위의 4개 중 뭐가 실행됐는지는 알려주지 않는다.(치사하다..)
 * #interactive
 *
 * (input)
 * T B: T는 테스트케이스의 수, B는 비트수(데이터 크기)
 *
 * (interactive input and output)
 * (output) 데이터의 위치
 * (input) 그 위치의 값. 0 또는 1
 * 최대 150회 반복
 * (output) B비트의 데이터. 정답 추정값.
 * (input) 답이 맞으면 Y, 아니면 N
 *
 * (solution 1)
 * 1번째에 데이터가 바뀌는건 신경쓰지 않아도 된다.
 * 최초 데이터를 구하는게 아니라 현재 데이터를 구하면 되는거니까.
 * 따라서 Test set 1은 B=10 이므로 데이터 바뀌는걸 신경쓰지 않아도 된다.
 * 점수가 1점밖에 안되는 이유가 있다..;
 * 반복 없이 질의하면, Test set 2는 1번 바뀌고, Test set 3는 9번 바뀐다.
 * B는 최대 100이고, 질의는 최대 150회니까, 최대 50번 반복 질의할 수 있다.
 * 바뀌는 타이밍에 어떻게 바뀌었는지 확인하지 않고 넘어갔다가
 * 나중에 확인하는 방식이 가능할까? => 불가능(증명은 skip;;)
 * 질의를 해서 얻은 값은 저장하고 있고, 바뀌는 타이밍에는 몇번의 추가질의를 통해
 * 어떻게 바뀌었는지 확인하여 저장한 데이터를 변경해 준다.
 * 질의를 150회 꽉꽉 채워서 한다고 하면, 데이터의 변경 체크는 최대 14번 해야하고,
 * 확인용 질의는 50회니까 한번 체크하는데에 3~4회정도 질의를 사용할 수 있다.
 * 뒤집혔는지를 확인하려면 앞부분 값과 그에 대응하는 뒷부분 값을 알아야 한다.
 * 따라서 질의는, 1, B, 2, B-1,... 순서로 하자.
 * 그리고 중요한 발견 한가지.
 * 만약 처음과 끝이 (0,0) 이었는데 바뀌는 타이밍이었다면?
 * - 반전: (1,1)
 * - 뒤집기: (0,0)
 * - 반전+뒤집기: (1,1)
 * - 내비둠: (0,0)
 * 이 경우, (1,0) 또는 (0,1)로는 바뀌지 않는다. 즉, 하나만 질의해보면 된다..!
 * 만약 처음과 끝이 (0,1) 이었는데 바뀌는 타이밍이었다면?
 * - 반전: (1,0)
 * - 뒤집기: (1,0)
 * - 반전+뒤집기: (0,1)
 * - 내비둠: (0,1)
 * 즉, 바뀌는 타이밍일때 앞뒤가 (0,0) 또는 (1,1)인 위치 확인하여 
 * 경우의 수를 반으로 줄이고, (1,0) 또는 (0,1)인 위치 확인하면
 * 2회 질의로 바뀐 방식을 확인할 수 있다!!
 *
 * (solution 1 result)
 * 한번에 성공은 못했으나 한참의 디버깅 끝에 간신히 성공..;;
 * 대칭되는 앞과 뒤의 값 2개를 한쌍으로 질의하는데,
 * 둘 중 하나를 질의하고 나서 순서가 바뀌면 나머지 하나의 위치가 바뀌는 문제.
 * 둘 중 하나만 아는 상태를 check_and_change에서 고려하지 않은 문제.
 * 이 두가지 문제 때문에 시간을 너무 많이 소비했다.
 */

#include <stdio.h>
#include <iostream>
#include <algorithm>

using namespace std;

int B;
int bb[101];
int qcnt;

// test
int testbb[101];
int testqcnt;

// test
void print_bits(int arr[], string txt) {
	for (int i=1; i<=B; i++) {
		cout << arr[i];
	}
	cout << " <= " << txt << endl;
}

int req_and_recv(int pos) {
	cout << pos << endl;
	int bbb;
	cin >> bbb;
	qcnt++;
	return bbb;
}

// test
// int req_and_recv(int pos) {
// 	if (testqcnt == 0) {
// 		for (int i=1; i<=B; i++) {
// 			testbb[i] = rand()%2;
// 			// string aa = "11101100101100110111";
// 			// testbb[i] = aa[i-1]-'0';
// 			cout << testbb[i];
// 		}
// 		cout << " <= testbb init!" << endl;
// 	}
// 	testqcnt++;	
// 	if (testqcnt%10 == 1 && testqcnt > 1) {
// 		int r = rand()%4;
// 		if (r == 0 || r == 2) {
// 			for (int i=1; i<=B; i++) {
// 				if (testbb[i] == 0) testbb[i] = 1;
// 				else if (testbb[i] == 1) testbb[i] = 0;
// 			}
// 		}
// 		if (r == 1 || r == 2) {
// 			for (int i=1; i<=B/2; i++) {
// 				int tmp = testbb[i];
// 				testbb[i] = testbb[B+1-i];
// 				testbb[B+1-i] = tmp;
// 			}
// 		}
// 		switch (r) {
// 			case 0:
// 				print_bits(testbb, "testbb complemented!");
// 				break;
// 			case 1:
// 				print_bits(testbb, "testbb reversed!");
// 				break;
// 			case 2:
// 				print_bits(testbb, "testbb complemented+reversed!");
// 				break;
// 			default:
// 				cout << "testbb stay.." << endl;
// 				break;
// 		}
// 	}
// 	qcnt++;
// 	return testbb[pos];
// }

void check_and_change() {
	int ptn1 = 0; // 0-0 or 1-1 => 1, 0-1 or 1-0 => 2
	int ptn2 = 0; 
	bool chg1, chg2;
	for (int i=1; i<=B/2; i++) {
		if (ptn1 == 0) {
			ptn1 = bb[i] == bb[B+1-i] ? 1 : 2;
			chg1 = req_and_recv(i) != bb[i];
		} else if (bb[i] == 2 && bb[B+1-i] == 2) {
			continue;
		} else if (ptn1 == 1) {
			if (bb[i] != bb[B+1-i]) {
				ptn2 = 2;
				int pos = bb[i] != 2 ? i : B+1-i;
				chg2 = req_and_recv(pos) != bb[pos];
				break;
			}
		} else { // 2
			if (bb[i] + bb[B+1-i] != 1) {
				ptn2 = 1;
				int pos = bb[i] != 2 ? i : B+1-i;
				chg2 = req_and_recv(pos) != bb[pos];
				break;
			}
		}
	}
	int chg_type;
	if (ptn1 == 1) {
		if (chg1) {
			if (ptn2 == 0) {
				chg_type = 1;
			} else {
				if (chg2) chg_type = 1; 
				else chg_type = 3;
			}
		} else {
			if (ptn2 == 0) {
				chg_type = 2;
			} else {
				if (chg2) chg_type = 2;
				else chg_type = 4;
			}
		}
	} else {
		if (chg1) {
			if (ptn2 == 0) {
				chg_type = 1;
			} else {
				if (chg2) chg_type = 1;
				else chg_type = 2;
			}
		} else {
			if (ptn2 == 0) {
				chg_type = 3;
			} else {
				if (chg2) chg_type = 3;
				else chg_type = 4;
			}
		}
	}
	if (chg_type == 1 || chg_type == 3) {
		for (int i=1; i<=B; i++) {
			if (bb[i] == 0) bb[i] = 1;
			else if (bb[i] == 1) bb[i] = 0;
		}
	}
	if (chg_type == 2 || chg_type == 3) {
		for (int i=1; i<=B/2; i++) {
			int tmp = bb[i];
			bb[i] = bb[B+1-i];
			bb[B+1-i] = tmp;
		}
	}
	// test
	// switch (chg_type) {
	// 	case 1:
	// 		print_bits(bb, "bb complemented!");
	// 		break;
	// 	case 2:
	// 		print_bits(bb, "bb reversed!");
	// 		break;
	// 	case 3:
	// 		print_bits(bb, "bb complemented+reversed!");
	// 		break;
	// 	default:
	// 		cout << "bb stay.." << endl;
	// 		break;
	// }
}

void get_or_check(int pos) {
	if (qcnt%10 == 1 && qcnt > 1) {
		check_and_change();	
	}
	if (bb[pos] == 2) {
		bb[pos] = req_and_recv(pos);
	} else if (bb[B+1-pos] == 2) {
		bb[B+1-pos] = req_and_recv(B+1-pos);
	}
}

int main() {
	int T;
	cin >> T >> B;
	for (int i=0; i<T; i++) {
		for (int j=1; j<=B; j++) {
			bb[j] = 2;
		}
		qcnt = 1;
		for (int j=1; j<=B/2; j++) {
			get_or_check(j);
			get_or_check(B+1-j);
		}
		for (int j=1; j<=B; j++) {
			cout << bb[j];
		}
		cout << endl;
		char result;
		cin >> result;
		if (result != 'Y') {
			break;
		}
	}
}

// test
// int main() {
// 	int T = 100;
// 	B = 20;
// 	int ncnt = 0;
// 	for (int i=0; i<T; i++) {
// 		for (int j=1; j<=B; j++) {
// 			bb[j] = 2;
// 		}
// 		qcnt = 1;
// 		testqcnt = 0;
// 		for (int j=1; j<=B/2; j++) {
// 			get_or_check(j);
// 			get_or_check(B+1-j);
// 		}
// 		char result = 'Y';
// 		for (int j=1; j<=B; j++) {
// 			if (bb[j] != testbb[j]) {
// 				result = 'N';
// 				break;
// 			}
// 		}
// 		print_bits(testbb, "testbb");
// 		print_bits(bb, "bb");
// 		cout << result << endl;
// 		cout << endl;
// 		if (result == 'N') {
// 			ncnt++;
// 		}
// 	}
// 	cout << "N cnt: " << ncnt << endl;
// }