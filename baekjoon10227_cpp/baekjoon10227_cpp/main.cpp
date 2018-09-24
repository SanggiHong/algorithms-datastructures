#include <iostream>

int R, C, H, W;
int matrix[3001][3001] = { 0 };
int find_median_map[3001][3001] = { 0 };

int main() {
	std::cin >> R >> C >> H >> W;

	for (int r = 1; r <= R; r++)
	{
		for (int c = 1; c <= C; c++)
			std::cin >> matrix[r][c];
	}

	int left = 1;
	int right = R * C;
	int pivot;
	bool find = false;
	while (!find)
	{
		bool low_exist = false;
		bool zero_exist = false;
		pivot = (left + right) / 2;

		for (int r = 1; r <= R; r++)
		{
			for (int c = 1; c <= C; c++)
			{
				find_median_map[r][c] = (matrix[r][c] == pivot ? 0 : matrix[r][c] < pivot ? -1 : 1);
				find_median_map[r][c] = find_median_map[r][c - 1] + find_median_map[r][c] - find_median_map[r - 1][c - 1] + find_median_map[r - 1][c];
			}
		}

		for (int r = H; r <= R; r++)
		{
			for (int c = W; c <= C; c++)
			{
				int partial_sum = find_median_map[r - H][c - W] - find_median_map[r - H][c] - find_median_map[r][c - W] + find_median_map[r][c];
				
				if (partial_sum == 0)
					zero_exist = true;
				else if (partial_sum < 0)
					low_exist = true;
			}
		}

		if (low_exist)
			right = pivot - 1;
		else if (zero_exist)
			find = true;
		else
			left = pivot + 1;
	}

	std::cout << pivot;
}