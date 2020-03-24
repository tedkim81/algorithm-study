/**
 * 2020.3.24
 *
 * 알아둬야할 주요 알고리즘들 class로 구현해 두는 곳
 */
#include <stdio.h>
#include <iostream>
#include <algorithm>
#include <vector>
#include <list>
#include <set>
#include <map>

using namespace std;

class BipartiteGraph {
public:
	vector<set<int> > graph;
	map<int,int> matched;
	set<int> visited;

	BipartiteGraph(vector<set<int> > g) {
		graph = g;
	}

	bool can_match(int i) {
		set<int> s = graph[i];
		for (set<int>::iterator it = s.begin(); it != s.end(); it++) {
			if (visited.count(*it) > 0) {
				continue;
			}
			visited.insert(*it);
			if (matched.count(*it) == 0 || can_match(matched[*it])) {
				matched[*it] = i;
				return true;
			}
		}
		return false;
	}

	int maximum_match_cnt() {
		matched.clear();
		int match_cnt = 0;
		for (int i=0; i<graph.size(); i++) {
			visited.clear();
			if (can_match(i)) {
				match_cnt++;
			}
		}
		return match_cnt;
	}

	static void test() {
		vector<set<int> > g;
		set<int> s1;
		s1.insert(1);
		s1.insert(2);
		g.push_back(s1); // 0: [1,2]
		set<int> s2;
		s2.insert(0);
		g.push_back(s2); // 1: [0]
		set<int> s3;
		s3.insert(0);
		g.push_back(s3); // 2: [0]
		set<int> s4;
		g.push_back(s4); // 3: []
		
		BipartiteGraph bb = BipartiteGraph(g);
		cout << bb.maximum_match_cnt() << endl;
	}
};

int main() {
	BipartiteGraph::test();
}