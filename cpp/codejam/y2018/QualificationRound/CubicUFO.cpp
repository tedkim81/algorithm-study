/**
 * 2019.1.8
 *
 * https://codingcompetitions.withgoogle.com/codejam/round/00000000000000cb/00000000000079cc
 *
 * 한 모서리의 길이가 1km인 정육면체 형태의 UFO가 토론토 위에 떴단다.
 * (얘들 요즘 문제가 다 인런식인듯..;;)
 * x,y,z축이 있는 3차원 공간 안에 있는데, 
 * 위아래를 나타내는 축은 y축이다.
 * UFO는 (0,0,0)을 중심으로 위치하고 있고, 
 * 토론토 땅바닥은 y=-3이면서 x와 z축으로 이루어진 평면에 평행한 평면이다.
 * 이 정도면 우주전쟁이 날법도 한데, 정부는 UFO에게 자기들의 요구(?)를 들어주면 일단 봐주겠단다.
 * (0,0,0)을 중심으로 마음대로 회전해서 땅에 투영된 그림자의 크기가 A가 되도록 하면 되는 것이다.
 * 회전하고 나서 정육면체의 인접한 3개의 면의 중심점들을 output으로 출력해 주면 된다.
 * #이분법
 *
 * (input)
 * T: 테스트케이스의 수
 * A: 요구되는 그림자의 크기
 *
 * (output)
 * 정육면체의 인접한 3개의 면의 각각의 중심 좌표를 한줄에 하나씩 출력
 * 출력값 자체의 오차 및 크기 A에 대한 오차는 플러스마이너스 백만분의 일이다.
 *
 * (solution 1)
 * x축 또는 z축 중에 아무거나 하나를 골라서(여기서는 x축으로 하자), 
 * 45도 회전시키면 그림자의 한변은 1로 유지, 다른 한변은 루트2가 되고, 
 * 이때가 x축 기준으로만 돌리는 조건에서는 최대값이 된다.
 * 그리고, x축 기준으로 45도 돌린 상태에서, z축을 기준으로 45도를 돌리면 정육각형 모양의 그림자가 되고, 
 * 삼각함수 써가면서 계산해본 결과 이때의 정육각형의 넓이는 루트3.
 * 문제의 Limits에서 Test set 1에서 A의 최대값 1.414213은 루트2의 근사값이고, 
 * Test set 2에서 A의 최대값 1.732050은 루트3의 근사값이다.
 * 따라서, 1.0 <= A <= 1.414213 인 경우 x축을 기준으로 돌리고, 
 * 1.414213 < A <= 1.732050 인 경우 x축 45도 돌린 상태에서 z축을 돌리면 된다. 
 * 그렇게 x축과 z축을 몇도 돌리는지 계산하고, 면의 중심점들을 그만큼 돌려서 좌표를 구한다. 
 * (이거 완전 수학문젠데..?)
 * Test set 1: 
 *   x축 기준 0도일때 넓이 1, 45도일때 넓이 1.414213. 순증가이다. 
 *   이분법으로 100번정도 반복하여 회전각을 구하자.
 *   이때는 그림자가 사각형이라 넓이 계산이 편하다.
 * Test set 2: 
 *   z축 기준 0도일때 넓이 1.414213, 45도일때 넓이 1.732050. 순증가이다. 
 *   이분법으로 100번정도 반복하여 회전각을 구하자.
 *   육각형이라 넓이 계산이 어렵긴 하지만, 높이가 1.414213으로 일정하고 좌우대칭이라는 특징을 이용하면 
 *   결국은 이것도 사각형 넓이 구하는 것과 같은 방식이 된다. 
 *   (좌측 삼각형을 띄어서 우측에 붙이면 사각형이 된다.)
 * 이분법 구현은 재귀가 아닌 반복문으로 구현하는게 편할듯.(100번이면 충분하니까. 아무리 큰 수라도 2로 100번 나눈다면?)
 *
 * (solution 2)
 * 구현을 하다보니, 위의 방식에 오류가 있다. 
 * 구현하면서 테스트하다보니 Test set 2에서 회전각이 45도일때 최대가 아니었다. 
 * 회전함에 따라 그림자의 크기가 변하는 축에 대하여, 그 너비가 가장 클때가 그림자의 넓이도 가장 커지는데,
 * 그 가장 큰 너비를 가지는 두 꼭지점을 잇는 선이 xz평면과 이루는 각을 시작으로 0도까지 줄여가면 
 * 그림자의 넓이가 순증가하고 0도일때가 최대값이 된다는 것을 전제로 깔고 다시 정리해 보자.
 * Test set 1 ( 1.0 <= A <= 1.414213 ):
 *   정육면체가 똑바로 떠있을때 그림자 넓이는 1이고, x축 중심으로 돌릴때 가장 긴 선은 길이가 루트2이고 
 *   평면과 이루는 각도는 45도( atan(1) )이다. 
 *   45도를 시작으로 0도까지 회전시키면 그림자의 넓이는 점점 커지고,
 *   0도일때 최대값인 루트2(1.414213..)가 된다. 
 *   이 과정을 이분법으로 구현하여 넓이가 A에 가장 가까울때의 각도를 구한다.
 *   그러면 회전각은 (45 - 그 각도)가 되고, yz평면에서 점들을 회전각만큼 회전시킨다.
 * Test set 2 ( 1.414213 < A <= 1.732050 ):
 *   일단 yz평면에서 점들을 45도 회전시키고 시작. 이때 그림자 넓이는 루트2. 
 *   z축 중심으로 돌릴때 가장 긴 선은 길이가 루트3이고, 평면과 이루는 각은 atan(루트2) 이다.
 *   이 각을 시작으로 0도까지 회전시키면 그림자의 넓이는 점점 커지고, 
 *   0도일때 최대값인 루트3(1.732050..)이 된다.
 *   이 과정을 이분법으로 구현하여 넓이가 A에 가장 가까울때의 각도를 구한다. 
 *   회전각은 (시작각 - 그 각도)
 *   xz평면에서 점들을 회전각만큼 회전시키면 끄읕.
 *
 * (solution 3)
 * 구현을 하다보니 또 오류가 있다.. ㅠ.ㅠ
 * Test set 2에서 그림자의 넓이가 최대가 되는 경우는 가장 긴 선이 평면과 0도가 될때가 아니었다. 
 * (대강 19도 정도일때가 최대임. 테스트 하다가 발견함.)
 * 시작각도와 끝각도 사이에서 그림자의 넓이가 순증가함이 전제가 되어야 이분법을 사용할 수 있기 때문에,
 * 시작각도를 0으로 설정하고,
 * 그림자가 최대가 될때의 각도, 즉 그림자가 정육각형이 될때의 각도를 구해서 끝각도로 설정해야 한다.
 * 그림 그려가며 계산해보니 정육각형이 될때 한변의 길이가 (루트2/루트3) 이다.
 * 따라서 끝각도는, acos(루트2/루트3) 이다.
 *
 * (solution 3 result)
 * 어렵사리 한참만에 submit을 했는데, Test set 1에 대해서는 통과했으나, 
 * Test set 2에서 "Runtime error"라고 판정을 받았다. 그런데.. 뭐가 문젠지 못찾겠다.. 
 * 차라리 Wrong answer면 문제를 다시 들여다 보겠는데, Runtime error는 아무리 디버깅 해봐도 안나는데..
 * 컴파일러 버전의 문제일까..?
 *
 * 나중에 다시 확인해 보자..
 *
 * 2019.2.9 한달 정도가 지났다. 그 사이 코드잼은 또 리뉴얼 과정을 거쳤는지 UI가 많이 바뀌었다.
 * 이 문제 푸는데 시간을 너무 까먹는거 같아서 일단 넘어갔었는데, 이제는 다시 풀어봐야겠다 싶어서 
 * 코드를 다시 submit 해봤더니 글쎄.. 성공했다.. 뭐냐 얘.. Runtime error라는 판정자체가 버그였나보다..
 * 그리고 다른 솔루션을 살짝 컨닝해보니, 훨씬 더 간단하게 풀 수 있었다.
 * x축이나 z축으로 돌리는게 아니라, 
 * x축과 z축 중간에(45도) 있는 축으로 회전시키면 그림자를 정육각형까지 만들 수 있다.
 * 즉, 그 축을 중심으로 몇도 돌리면 되는지를 이분법으로 구하면, 
 * Test set 1과 Test set 2를 구분할 필요 없이 답을 구할 수 있다.
 */

#include <stdio.h>
#include <iostream>
#include <string>
#include <sstream>
#include <math.h>

using namespace std;

double PI = 3.141592653589793238463;

double radian(double degree) {
	return degree * PI / 180;
}

double degree(double rad) {
	return rad * 180 / PI;
}

double get_r(double p1, double p2) {
	return sqrt(p1*p1 + p2*p2);
}

double get_curr_rad(double p1, double p2) {
	double curr_rad;
	if (p1 > -0.0000001 && p1 < 0.0000001) {
		if (p2 > 0) curr_rad = radian(90);
		else curr_rad = radian(270);
	} else {
		curr_rad = atan(p2/p1);
	}
	return curr_rad;
}

double rotate_axis1(double p1, double p2, double diff_rad) {
	return get_r(p1, p2) * cos(get_curr_rad(p1, p2) + diff_rad);
}

double rotate_axis2(double p1, double p2, double diff_rad) {
	return get_r(p1, p2) * sin(get_curr_rad(p1, p2) + diff_rad);
}

string get_result(double pts[3][3]) {
	stringstream ss;
	ss.precision(10);
	for (int i=0; i<3; i++) {
		ss << pts[i][0] << " " << pts[i][1] << " " << pts[i][2] << endl;
	}
	return ss.str();
}

string solve(double A) {
	double pts[3][3] = {
		{0.5, 0, 0},
		{0, 0.5, 0},
		{0, 0, 0.5}
	};
	if (A == 1) {
		return get_result(pts);
	}
	double max_rad, hi_rad, lo_rad, curr_rad, area, tmp1, tmp2;
	if (A <= sqrt(2.0)) {  // 1.000000 <= A <= 1.414213
		max_rad = radian(45) + 0.0000001;
		hi_rad = max_rad;
		lo_rad = radian(0) - 0.0000001;
		for (int i=0; i<100; i++) {
			curr_rad = (hi_rad + lo_rad) / 2;
			area = sqrt(2.0) * cos(curr_rad);
			if (area < A) {
				hi_rad = curr_rad;
			} else {
				lo_rad = curr_rad;
			}
		}
		double diff_rad = max_rad - curr_rad;
		for (int i=1; i<3; i++) {
			tmp1 = rotate_axis1(pts[i][1], pts[i][2], diff_rad);
			tmp2 = rotate_axis2(pts[i][1], pts[i][2], diff_rad);
			pts[i][1] = tmp1;
			pts[i][2] = tmp2;
		}
	} else {  // 1.414213 < A <= 1.732050
		for (int i=1; i<3; i++) {
			tmp1 = rotate_axis1(pts[i][1], pts[i][2], radian(45));
			tmp2 = rotate_axis2(pts[i][1], pts[i][2], radian(45));
			pts[i][1] = tmp1;
			pts[i][2] = tmp2;
		}
		hi_rad = acos(sqrt(2.0)/sqrt(3.0)) + 0.0000001;
		lo_rad = radian(0) - 0.0000001;
		for (int i=0; i<100; i++) {
			curr_rad = (hi_rad + lo_rad) / 2;
			area = ((sqrt(2.0) * sin(curr_rad) / 2) + cos(curr_rad)) * sqrt(2.0);
			if (area > A) {
				hi_rad = curr_rad;
			} else {
				lo_rad = curr_rad;
			}
		}
		for (int i=0; i<3; i++) {
			tmp1 = rotate_axis1(pts[i][0], pts[i][1], curr_rad);
			tmp2 = rotate_axis2(pts[i][0], pts[i][1], curr_rad);
			pts[i][0] = tmp1;
			pts[i][1] = tmp2;
		}
	}
	return get_result(pts);
}

int main() {
	int T;
	cin >> T;
	for (int i=0; i<T; i++) {
		double A;
		cin >> A;
		string result = solve(A);
		cout << "Case #" << i+1 << ": " << endl << result;
	}
	return 0;
}
