/**
 * 2018.12.21
 *
 * 맵, 큐, 스택, 소팅 등 이것저것 라이브러리 사용하는 방식을 연습하자.
 */

#include <stdio.h>
#include <iostream>
#include <map>
#include <unordered_map>
#include <functional>
#include <vector>
#include <list>
#include <set>
#include <queue>
#include <stack>
#include <algorithm>

using namespace std;

void test1() {
	// 맵 1: map은 key값 오름차순으로 정렬함
	cout << "----- 맵 1 -----" << endl;
	map<string, int> m;  // 키값의 오름차순으로 정렬됨
	m["bb"] = 100;
	m["aa"] = 200;
	m["cc"] = 50;
	cout << "map size: " << m.size() << endl;
	for (map<string, int>::iterator i = m.begin(); i != m.end(); i++) {  // old style
		cout << i->first << " = " << i->second << endl;
	}
	for (map<string, int>::reverse_iterator i = m.rbegin(); i != m.rend(); i++) {
		cout << i->first << " = " << i->second << endl;
	}
	// for (pair<string, int> mm : m) {  // new style --> warning occured
	// 	cout << mm.first << " = " << mm.second << endl;
	// }

	// 맵 2: 순서 개념이 없다. iterate하면 넣은 순서가 아닌 랜덤하게 나온다.
	cout << "----- 맵 2 -----" << endl;
	unordered_map<string, int> m2;
	m2["bb"] = 100;
	m2["aa"] = 200;
	m2["cc"] = 50;
	m2["dd"] = 150;
	for (unordered_map<string, int>::iterator i = m2.begin(); i != m2.end(); i++) {
		cout << i->first << " = " << i->second << endl;
	}

	// 맵 3: value 기준 오름차순으로 정렬하고 싶다면?
	cout << "----- 맵 3 -----" << endl;
	m["dd"] = 50;
	multimap<int, string> m3;
	for (map<string, int>::iterator i = m.begin(); i != m.end(); i++) {
		m3.insert(make_pair(i->second, i->first));
	}
	for (multimap<int, string>::iterator i = m3.begin(); i != m3.end(); i++) {
		cout << i->first << " = " << i->second << endl;
	}
}

void test2() {
	// 벡터: 가변길이 배열. 배열에 일정량 이상이 차면 크기를 확장하는 방식.
	cout << "----- 벡터 -----" << endl;
	vector<int> v;
	v.push_back(2);
	v.push_back(1);
	v.push_back(3);
	v.insert(v.begin()+2, 4);
	v.erase(v.begin());
	for (vector<int>::iterator i = v.begin(); i != v.end(); i++) {
		cout << *i << endl;
	}
	for (vector<int>::size_type i = 0; i < v.size(); i++) {
		cout << v[i] << endl;
	}
	// 연결리스트 (linked list)
	cout << "----- 연결리스트 -----" << endl;
	list<int> l;
	l.push_back(5);
	l.push_back(7);
	l.push_back(6);
	list<int>::iterator i = l.begin();
	i++;
	i++;
	l.insert(i, 8);
	for (i = l.begin(); i != l.end(); i++) {
		cout << *i << endl;
	}
	// Set: 중복허용하지 않는 컬렉션
	cout << "----- Set -----" << endl;
	set<int> s;
	s.insert(4);
	s.insert(3);
	s.insert(3);
	s.insert(2);
	s.insert(5);
	for (set<int>::iterator i = s.begin(); i != s.end(); i++) {
		cout << *i << endl;
	}
	s.clear();
}

void test3() {
	// 큐
	cout << "----- 큐 -----" << endl;
	queue<int> q;
	q.push(1);
	q.push(3);
	q.push(2);
	while (!q.empty()) {
		cout << q.front() << endl;
		q.pop();
	}

	// 스택
	cout << "----- 스택 -----" << endl;
	stack<int> s;
	s.push(1);
	s.push(3);
	s.push(2);
	while (!s.empty()) {
		cout << s.top() << endl;
		s.pop();
	}
}

void test4() {
	// 정렬
	int a[5] = {2, 1, 3, 5, 4};
	sort(a, a+5);
	for (int i=0; i<5; i++) {
		cout << a[i] << ",";
	}
	cout << endl;

	// 배열복제
	int b[5];
	copy(begin(a), end(a), begin(b));
	for (int i=0; i<5; i++) {
		cout << a[i] << ",";
	}
	cout << endl;

	// 특정값으로 채우기
	fill(a, a+5, 0);
	for (int i=0; i<5; i++) {
		cout << a[i] << ",";
	}
	cout << endl;
}

int main() {
	test2();
}