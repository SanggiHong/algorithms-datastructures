#include <iostream>
#include <vector>
#include <queue>

struct item {
	int number;
	int cost;
};

std::vector<item> graph[100001];
bool visit[100001];

item find_farthest(int start) {
	std::queue<item> queue;
	item answer = { 0 };
	item temp;
	
	for (int i = 1; i <= 100000; i++)
		visit[i] = false;

	visit[start] = true;
	temp.number = start;
	temp.cost = 0;
	queue.push(temp);
	while (!queue.empty())
	{
		if (answer.cost < queue.front().cost)
		{
			answer.cost = queue.front().cost;
			answer.number = queue.front().number;
		}

		for (unsigned int i = 0; i < graph[queue.front().number].size(); i++)
		{
			temp = graph[queue.front().number].at(i);
			if (!visit[temp.number])
			{
				visit[temp.number] = true;
				temp.cost = temp.cost + queue.front().cost;
				queue.push(temp);
			}
		}

		queue.pop();
	}

	return answer;
}

bool set_route(int x, int y, int pre)
{
	if (x == y)
		return true;

	for (unsigned int i = 0; i < graph[x].size(); i++)
	{
		if (graph[x].at(i).number != pre && set_route(graph[x].at(i).number, y, x))
		{
			graph[x].at(i).cost = 0;
			return true;
		}
	}

	return false;
}

int main() {
	int a, b, c, size;
	int x, y;
	item answer;
	item temp;
	while (true)
	{
		std::cin >> size;
		if (size == 0)
			break;

		for (int i = 0; i < size - 1; i++)
		{
			std::cin >> a >> b >> c;
			temp.number = b;
			temp.cost = c;
			graph[a].push_back(temp);

			temp.number = a;
			temp.cost = c;
			graph[b].push_back(temp);
		}

		x = find_farthest(1).number;
		y = find_farthest(x).number;

		set_route(x, y, 0);
		set_route(y, x, 0);

		answer = find_farthest(x);

		std::cout << answer.cost << '\n';

		for (int i = 1; i <= size; i++)
			graph[i].clear();
	}
}