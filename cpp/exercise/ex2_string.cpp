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
	}
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

int main() {
	test2();
}