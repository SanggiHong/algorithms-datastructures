#include <iostream>

int R, C, H, W;
int mat[3001][3001];
int check[3001][3001] = { 0 };
int ans[9000001];
int x[9000001];
int y[9000001];
int sub_mat[9000001];

int find_rank(int rank, int* arr, int left, int right) {
	if (left == right)
		return arr[left];

	int pivot = arr[right];
	int i = left;
	int j = right - 1;

	while (i <= j)
	{
		if (arr[i] > pivot)
		{
			int temp = arr[i];
			arr[i] = arr[j];
			arr[j] = temp;
			j = j - 1;
		}
		else
		{
			i = i + 1;
		}
	}
	int temp = arr[i];
	arr[i] = arr[right];
	arr[right] = temp;

	if (i == rank)
		return pivot;
	else if (i > rank)
		return find_rank(rank, arr, left, j);
	else
		return find_rank(rank, arr, i + 1, right);
}

int find_answer() {
	for (int i = 1; i <= R * C; i++)
	{
		if (ans[i] == 1)
			return i;

		for (int temp_x = x[i] - (W / 2); temp_x <= x[i] + (W / 2); temp_x++)
		{
			for (int temp_y = y[i] - (H / 2); temp_y <= y[i] + (H / 2); temp_y++)
			{
				if ((W / 2) <= temp_x && temp_x < C - (W / 2)
					&& (H / 2) <= temp_y && temp_y < R - (H / 2)
					&& check[temp_x][temp_y] == 0)
				{
					check[temp_x][temp_y] = 1;
					int index = 0;
					for (int j = temp_x - (W / 2); j <= temp_x + (W / 2); j++)
						for (int k = temp_y - (H / 2); k <= temp_y + (H / 2); k++)
						{
							sub_mat[index] = mat[j][k];
							index = index + 1;
						}
					int answer = find_rank(H * W / 2, sub_mat, 0, H * W - 1);
					ans[answer] = 1;
					if (ans[i] == 1)
						return i;
				}
			}
		}
	}
}

int main() {
	int input;
	std::cin >> R >> C >> H >> W;
	for (int r = 0; r < R; r++)
	{
		for (int c = 0; c < C; c++)
		{
			std::cin >> input;
			mat[c][r] = input;
			x[input] = c;
			y[input] = r;
		}
	}

	std::cout << find_answer();
}