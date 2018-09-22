#include<iostream>
#include<vector>

#define MAT(x,y) arr[size*(x-1)+(y-1)]

struct answer {
	std::vector<int> nodes;
	int total_cost;
};

int* arr;
int size;

answer get_answer_highway(int parent_number, int node_number, int cost) {
	answer max_cost_answer;
	answer temp;

	int connected = 0;
	max_cost_answer.total_cost = 0;
	max_cost_answer.nodes.clear();

	for (int i = 1; i <= size; i++)
	{
		if (i != parent_number && MAT(node_number, i) != -1)
		{
			connected = connected + 1;
			temp = get_answer_highway(node_number, i, MAT(node_number, i));
			if (temp.total_cost > max_cost_answer.total_cost)
				max_cost_answer = temp;
		}
	}

	max_cost_answer.total_cost = max_cost_answer.total_cost + cost;
	max_cost_answer.nodes.push_back(node_number);
	return max_cost_answer;
}

int get_cost_to_highway(int parent_number, int node_number, int cost, const answer answer_highway) {
	for (unsigned int i = 0; i < answer_highway.nodes.size(); i++)
	{
		if (node_number == answer_highway.nodes.at(i))
			return 0;
	}
	int max_cost = 0;
	int temp;
	for (int i = 1; i <= size; i++)
	{
		if (i != parent_number && MAT(node_number, i) != -1)
		{
			temp = get_cost_to_highway(node_number, i, MAT(node_number, i), answer_highway);
			if (max_cost < temp)
				max_cost = temp;
		}
	}

	return max_cost + cost;

}

int main() {
	std::cin >> size;
	arr = new int[size * size];
	for (int i = 0; i < size * size; i++)
		arr[i] = -1;

	int a, b, c;
	for (int i = 0; i < size - 1; i++)
	{
		std::cin >> a >> b >> c;
		MAT(a, b) = c;
		MAT(b, a) = c;
	}
	std::cin >> a;

	answer highway;
	answer temp_answer;
	for (int i = 1; i <= size; i++)
	{
		int connected_node = 0;
		for (int j = 1; j <= size; j++)
		{
			if (MAT(i, j) != -1)
				connected_node = connected_node + 1;
		}
		
		if (connected_node <= 1)
		{
			temp_answer = get_answer_highway(-1, i, 0);
			if (temp_answer.total_cost > highway.total_cost)
				highway = temp_answer;
		}
	}

	int cost = 0;
	int temp_cost;
	for (unsigned int i = 0; i < highway.nodes.size(); i++) {
		for (int j = 1; j <= size; j++) {
			if (MAT(highway.nodes.at(i), j) != -1)
			{
				temp_cost = get_cost_to_highway(highway.nodes.at(i), j, MAT(highway.nodes.at(i), j), highway);
				if (cost < temp_cost)
					cost = temp_cost;
			}
		}
	}
	
	std::cout << cost;
	return 0;
}