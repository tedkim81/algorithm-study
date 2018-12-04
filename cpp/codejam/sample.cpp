/**
 * Codejam sample code
 * 2018.11.29
 */

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
#include <fstream>
#include <iomanip>

using namespace std;

string get_result(ifstream& infile) {
    string line;
    getline(infile, line);
    stringstream ss(line);
    int N;
    ss >> N;

    string result = "test";
    
    return result;
}

void solve(ifstream& infile, ofstream& outfile) {
    string line;
    getline(infile, line);
    stringstream ss(line);
    int T;
    ss >> T;
    for (int tt=0; tt<T; tt++) {
        
        string result = get_result(infile);
        
        cout << "Case #" << (tt+1) << ": " << result << endl;
        outfile << "Case #" << (tt+1) << ": " << result << endl;
    }
}

int main() {
    ifstream infile ("/Users/ted/Downloads/test.in.txt");
    if (infile.is_open()) {
        ofstream outfile;
        outfile.open("/Users/ted/Downloads/test.out.txt");
        solve(infile, outfile);
        outfile.close();
        infile.close();
    } else {
        cout << "Unable to open file";
    }
    return 0;
}
