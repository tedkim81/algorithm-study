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
    freopen("/Users/teuskim/Documents/workspace/cpp-src/GCJ2015/ProblemA/ProblemA/in", "r", stdin);
    freopen("/Users/teuskim/Documents/workspace/cpp-src/GCJ2015/ProblemA/ProblemA/out", "w", stdout);
    
    int tt;
    scanf("%d", &tt);
    
    int map[4][4];
    int first[4];
    int second[4];
    
    for (int t=1; t<=tt; t++) {
        printf("case #%d: ", t);
        
        int row;
        scanf("%d", &row);
        for (int i=0; i<4; i++) {
            for (int j=0; j<4; j++) {
                scanf("%d", &map[i][j]);
            }
        }
        for (int i=0; i<4; i++) {
            first[i] = map[row-1][i];
        }
        
        scanf("%d", &row);
        for (int i=0; i<4; i++) {
            for (int j=0; j<4; j++) {
                scanf("%d", &map[i][j]);
            }
        }
        for (int i=0; i<4; i++) {
            second[i] = map[row-1][i];
        }
        
        int cnt = 0;
        int num;
        for (int i=0; i<4; i++) {
            for (int j=0; j<4; j++) {
                if (first[i] == second[j]) {
                    cnt++;
                    num = first[i];
                }
            }
        }
        
        if (cnt == 1) {
            printf("%d\n", num);
        } else if (cnt > 1) {
            printf("Bad magician!\n");
        } else {
            printf("Volunteer cheated!\n");
        }
    }
    
    return 0;
}

