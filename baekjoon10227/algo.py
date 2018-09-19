a = [int(i) for i in input().split()]
R = a[0]
C = a[1]
H = a[2]
W = a[3]
mat = [[0]*C for i in range(R)]

for r in range(R):
    a = input().split()
    for c in range(C):
        mat[r][c] = int(a[c])

ans = list()
for r in range(H-1, R):
    for c in range(W-1, C):
        arr = list()
        for item in mat[r-H+1:r+1]:
            arr.extend(item[c-W+1:c+1])
        arr.sort()
        ans.append(arr[4])

print(min(ans))