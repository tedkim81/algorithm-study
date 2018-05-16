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

int main() {
//    int a = 3;
//    printf("%d\n", a);
//    printf("%p\n", &a);
//    int *b = &a;
//    printf("%p\n", b);
//    printf("%d\n", *b);
    
//    char c[3] = {'a','b','c'};
//    printf("%p\n", c);
//    printf("%p\n", (c+1));
    
    int e = 200;
    int *d = &e;
    *d = 300;
    printf("%d\n", e);
    
    return 0;
}

//int main() {
//    freopen("/Users/teuskim/Documents/workspace/cpp-src/CppTest/CppTest/in", "r", stdin);
//    freopen("/Users/teuskim/Documents/workspace/cpp-src/CppTest/CppTest/out", "w", stdout);
//    int tt;
//    scanf("%d", &tt);
//    for (int qq=1;qq<=tt;qq++) {
//        printf("Case #%d: ", qq);
//        bool can[42];
//        for (int i = 1; i <= 16; i++) can[i] = true;
//        for (int q = 0; q < 2; q++) {
//            int row;
//            scanf("%d", &row);
//            for (int i = 1; i <= 4; i++)
//                for (int j = 1; j <= 4; j++) {
//                    int a;
//                    scanf("%d", &a);
//                    if (i != row) can[a] = false;
//                }
//        }
//        int res = -1;
//        for (int i = 1; i <= 16; i++)
//            if (can[i]) {
//                if (res != -1) {
//                    res = -2;
//                    break;
//                }
//                res = i;
//            }
//        if (res == -1) puts("Volunteer cheated!"); else
//            if (res == -2) puts("Bad magician!");
//            else printf("%d\n", res);
//    }
//    return 0;
//}

//int main() {
////    freopen("testout", "w", stdout);
////    printf("test test");
////    fclose(stdout);
//    
////    freopen("testout", "r", stdin);
//    
////    FILE* f = fopen("/Users/teuskim/testout.txt", "w");
////    fputs("test test test", f);
////    fclose(f);
//    
//    FILE* f = fopen("/Users/teuskim/testout.txt", "r");
//    char s[10];
//    if (f != NULL) {
//        fgets(s, 10, f);
//        printf("%s\n", s);
//        
//    } else {
//        printf("!!!!!!!!!");
//    }
//    
//    return 0;
//}

//int main(){
//	int T;
//	scanf("%d",&T);
//	for(int testcase = 1; testcase <= T; testcase++) {
//		set<int> v[2];
//		for(int i = 0; i < 2; i++) {
//			int r;
//			scanf("%d",&r);
//			for(int j = 1; j <= 4; j++) {
//				for(int k = 0; k < 4; k++) {
//					int val;
//					scanf("%d",&val);
//					if (j == r) {
//						v[i].insert(val);
//					}
//				}
//			}
//		}
//		vector<int> intersection;
//		set_intersection(v[0].begin(), v[0].end(), v[1].begin(), v[1].end(), back_inserter(intersection));
//		printf("Case #%d: ", testcase);
//		if (intersection.size() == 1) {
//			printf("%d\n", intersection[0]);
//		} else if (intersection.size() == 0) {
//			printf("Volunteer cheated!\n");
//		} else {
//			printf("Bad magician!\n");
//		}
//	}
//	return 0;
//}
