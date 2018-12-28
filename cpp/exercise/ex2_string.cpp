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

int main() {
	// string split
	string s = "Hello world, guys!";
	stringstream ss(s);
	string w;
	while (getline(ss, w, ' ')) {
		cout << w << endl;
	}
}