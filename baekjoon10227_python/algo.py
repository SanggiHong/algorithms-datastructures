a = [int(i) for i in input().split()]
R = a[0]
C = a[1]
H = a[2]
W = a[3]

matrix = [[0] * (C + 1) for i in range(R + 1)]
find_median_map = [[0] * (C + 1) for i in range(R + 1)]

for r in range(R):
    a = [int(i) for i in input().split()]
    for c in range(C):
        matrix[r + 1][c + 1] = a[c]

left = 1
right = R * C
pivot = None
find = False
while not find:
    low_exist = False
    zero_exist = False
    pivot = (left + right) // 2

    for r in range(1, R + 1):
        for c in range(1, C + 1):
            find_median_map[r][c] = 0 if matrix[r][c] == pivot else -1 if matrix[r][c] < pivot else 1
            find_median_map[r][c] = find_median_map[r][c - 1] + find_median_map[r][c]\
                                    - find_median_map[r - 1][c - 1] + find_median_map[r - 1][c]

    for r in range (H, R + 1):
        for c in range(W, C + 1):
            partial_sum = find_median_map[r - H][c - W] - find_median_map[r - H][c]\
                          - find_median_map[r][c - W] + find_median_map[r][c]

            if partial_sum == 0:
                zero_exist = True
            elif partial_sum < 0:
                low_exist = True

    if low_exist:
        right = pivot - 1
    elif zero_exist:
        find = True
    else:
        left = pivot + 1

print(pivot)
