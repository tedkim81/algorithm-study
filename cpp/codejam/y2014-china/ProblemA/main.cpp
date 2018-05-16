#include <stdio.h>
#include <vector>
#include <iterator>
#include <algorithm>
#include <set>
#include <string>
#include <iostream>
#include <list>
#include <map>
#include <deque>
#include <stack>
#include <bitset>
#include <functional>
#include <numeric>
#include <utility>
#include <sstream>
#include <iomanip>

using namespace std;

//#define FOR(i,n,m) for(int i = (int)n; i <= (int)m; i++)
//
//map <string, int> F;
//int n, m, Z;
//int a[222][222];
//int v[222];
//
//int id(string s) {
//	if (F.count(s)) return F[s];
//	F[s] = ++Z;
//	return F[s];
//}
//
//bool dfs(int i, int u) {
//	v[i] = u;
//	FOR(j, 1, Z)
//	if (a[i][j] && !v[j]) {
//		if (!dfs(j, 3 - u)) return false;
//	} else
//        if (a[i][j] && v[j] && v[j] == v[i])
//            return false;
//	return true;
//}
//
//
//int main() {
//    freopen("/Users/teuskim/Documents/workspace/cpp-src/GCJ2014CHINA/ProblemA/ProblemA/in", "r", stdin);
//    freopen("/Users/teuskim/Documents/workspace/cpp-src/GCJ2014CHINA/ProblemA/ProblemA/out2", "w", stdout);
//    
//	int Te;
//	scanf("%d", &Te);
//	for (int Ti = 1; Ti <= Te; Ti++) {
//		cin >> m;
//		memset(a, 0, sizeof(a));
//		F.clear();
//		Z = 0;
//		FOR(M, 1, m) {
//			string s, t;
//			cin >> s >> t;
//			int is = id(s);
//			int it = id(t);
//			a[is][it] = a[it][is] = true;
//            //		cout << is << ' ' << it << endl;
//		}
//		memset(v, 0, sizeof(v));
//		bool ok = true;
//		FOR(i, 1, Z) if (!v[i] && !dfs(i, 1)) {
//			ok = false;
//			break;
//		}
//		if (ok)
//            printf("Case #%d: Yes\n", Ti);
//		else
//            printf("Case #%d: No\n", Ti);
//        
//	}
//}

int main() {
    freopen("/Users/teuskim/Documents/workspace/cpp-src/GCJ2014CHINA/ProblemA/ProblemA/in", "r", stdin);
    freopen("/Users/teuskim/Documents/workspace/cpp-src/GCJ2014CHINA/ProblemA/ProblemA/out", "w", stdout);
    
    int T;
    scanf("%d", &T);
    
    for (int tt=1; tt<=T; tt++) {
        printf("Case #%d: ", tt);
        
        int M;
        scanf("%d", &M);
        
        set<string> set1, set2;
        string nleft, nright;
        bool result = true;
        for (int i=0; i<M; ++i) {
            cin >> nleft;
            cin >> nright;
            if (!(set1.find(nleft) != set1.end()) && !(set2.find(nleft) != set2.end())) {
                swap(nleft, nright);
            }
            if (set1.find(nleft) == set1.end()) {
                set2.insert(nleft);
                
                if (set2.find(nright) == set2.end()) {
                    set1.insert(nright);
                } else {
                    result = false;
                    cout << "(1: " << nleft << " , " << nright << ") ";
                }
            } else {
                if (set1.find(nright) == set1.end()) {
                    set2.insert(nright);
                } else {
                    result = false;
                    cout << "(2: " << nleft << " , " << nright << ") ";
                }
            }
        }
        if (result) {
            printf("Yes\n");
        } else {
            printf("No\n");
        }
    }
    
    return 0;
}
