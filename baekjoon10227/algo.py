def find_rank(rank, arr):
    if len(arr) == 1:
        return arr[0]

    bigger = list()
    smaller = list()
    pivot = arr.pop()
    for i in arr:
        smaller.append(i) if i < pivot else bigger.append(i)

    if len(smaller) == rank:
        return pivot
    elif len(smaller) > rank:
        return find_rank(rank, smaller)
    else:
        return find_rank(rank - len(smaller) - 1, bigger)


def find_answer():
    sub_mat = list()
    for i in range(1, R * C + 1):
        if ans[i] == 1:
            return i
        else:
            for temp_x in range(x[i] - (W // 2), x[i] + (W // 2) + 1):
                for temp_y in range(y[i] - (H // 2), y[i] + (H // 2) + 1):
                    if (W // 2) <= temp_x < C - (W // 2)\
                            and (H // 2) <= temp_y < R - (H // 2)\
                            and check[temp_x][temp_y] == 0:
                        check[temp_y][temp_x] = 1
                        sub_mat.clear()
                        for item in mat[temp_x - (W // 2): temp_x + (W // 2) + 1]:
                            sub_mat.extend(item[temp_y - (H // 2): temp_y + (H // 2) + 1])
                        answer = find_rank((H * W) // 2, sub_mat)
                        ans[answer] = 1
                        if ans[i] == 1:
                            return i


a = [int(i) for i in input().split()]
R = a[0]
C = a[1]
H = a[2]
W = a[3]

mat = [[0] * C for i in range(R)]
check = [[0] * C for i in range(R)]
ans = [0] * (R * C + 1)
x = [0] * (R * C + 1)
y = [0] * (R * C + 1)

for r in range(R):
    a = [int(i) for i in input().split()]
    for c in range(C):
        mat[c][r] = a[c]
        x[a[c]] = c
        y[a[c]] = r

print(find_answer())