/**
 * 2018.12.28
 *
 * string과 관련된 작업들에 대한 연습
 */

#include <stdio.h>
#include <string>
#include <iostream>
#include <sstream>

using namespace std;

void test1() {
	// string split
	string s = "Hello world, guys!";
	stringstream ss(s);
	string w;
	while (getline(ss, w, ' ')) {
		cout << w << endl;
		cout << "w sizeof: " << sizeof(w) << endl;  // 24
	}
	cout << "s sizeof: " << sizeof(s) << endl;  // 24
	cout << "s.size(): " << s.size() << endl;  // 18
}

void test2() {
	// create string
	stringstream ss;
	ss << "aa " << "bb " << "cc" << endl;
	ss << "11" << " " << "22" << " " << "33";
	cout << ss.str() << endl;

	// print full double
	ss.str("");
	ss.precision(10);
	double a = 1.22334455;
	ss << a;
	cout << ss.str() << endl;
	cout << a << endl;
	cout.precision(10);
	cout << a << endl;
}

void test3() {
	char aa[10];
	cin >> aa;
	cout << aa[0] << endl;
}

void test4() {
	string aa = "Hello world!!";
	if (aa[2] == 'l') {
		cout << "OK!" << endl;
	} else {
		cout << "Oops.." << endl;
	}
}

void test5() {
	double a = 1.23456789;
	char b[100];
	sprintf(b, "%.10f", a);
	string c = b;
	cout << b << endl;
}

void test6() {
	cout << "== 문자열 정렬 ==" << endl;
	string a[4] = {"cdefg", "bcdef", "defge", "abcedf"};
	sort(a, a+4);
	for (int i=0; i<4; i++) {
		cout << a[i] << endl;
	}
	cout << "== char array for string ==" << endl;
	char b[4][6] = {"cdefg", "bcdef", "defge", "abcde"};
	// sort(b, b+4); => error!
	for (int i=0; i<4; i++) {
		cout << b[i] << endl;
	}
}

int main() {
	test1();
}