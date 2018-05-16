#include <stdio.h>
#include <algorithm>
#include <queue>

using namespace std;

int n,m,b;

pair<int,int> dat[1024];

int main()
{
	int testcases;
	scanf("%d",&testcases);
	for(int testcase = 1; testcase <= testcases; testcase ++)
	{
		scanf("%d%d%d",&n,&m,&b);
		for(int i = 0; i < m; i ++)
		{
			scanf("%d%d",&dat[i].first, &dat[i].second);
		}

		typedef pair<int, pair<int,int>> cand_t;

		vector<cand_t> pq;
		for(int i = 0; i < m; i ++)
		{
			if(dat[i].first >= b)
			{
				pq.push_back(make_pair(dat[i].first, make_pair(i, -1)));
			}
			for(int j = i;j < m; j ++) 
			{
				if(dat[i].first + dat[j].first>= b)
				{
					pq.push_back(make_pair(dat[i].first + dat[j].first, make_pair(i, j)));
				}
			}
		}

		sort(pq.begin(), pq.end());

		long long ans = 0;
		int i = 0;
		while(n > 0 && i < pq.size())
		{
			int p1 = pq[i].second.first, p2 = pq[i].second.second;
			if(p2 == -1)
			{
				if(dat[p1].second == 0) {
					i ++;
					continue;
				}
				n --;
				dat[p1].second --;
				ans += pq[i].first;
			}
			else
			{
				if(dat[p1].second == 0 || dat[p2].second == 0) {
					i ++;
					continue;
				}
				if(p1 == p2 && dat[p1].second < 2) {
					i ++;
					continue;
				}
				n --;
				dat[p1].second --;
				dat[p2].second --;
				ans += pq[i].first;
			}
		}

		if(n > 0) {
			ans = -1;
		}
		printf("Case #%d: %lld\n", testcase, ans);
	}
	return 0;
}