import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.valueOf(st.nextToken());
        int K = Integer.valueOf(st.nextToken());

        int[][] map = new int[N + 1][N + 1];
        int[][] rectDP = new int[N + 1][N + 1];
        int[] smallX = new int[K + 1];
        int[] bigX = new int[K + 1];
        int[] smallY = new int[K + 1];
        int[] bigY = new int[K + 1];
        int[] expandable = new int[K + 1];

        for (int i = 1; i <= K; i++) {
            smallX[i] = Integer.MAX_VALUE;
            smallY[i] = Integer.MAX_VALUE;
            bigX[i] = Integer.MIN_VALUE;
            bigY[i] = Integer.MIN_VALUE;
        }

        for (int y = 1; y <= N; y++) {
            String str = br.readLine();
            for (int x = 1; x <= str.length(); x++) {
                if ('.' != str.charAt(x - 1)) {
                    int rectNumber = str.charAt(x - 1) - '0';
                    map[y][x] = rectNumber;
                    if (x < smallX[rectNumber]) smallX[rectNumber] = x;
                    if (x > bigX[rectNumber]) bigX[rectNumber] = x;
                    if (y < smallY[rectNumber]) smallY[rectNumber] = y;
                    if (y > bigY[rectNumber]) bigY[rectNumber] = y;
                }
            }
        }

        for (int rect = 1; rect <= K; rect++) {
            for (int y = 1; y <= N; y++) {
                for (int x = 1; x <= N; x++) {
                    if (rect <= map[y][x])
                        rectDP[y][x] = 1 + Math.min(rectDP[y - 1][x - 1], Math.min(rectDP[y - 1][x], rectDP[y][x - 1]));
                    else
                        rectDP[y][x] = 0;
                    if (rectDP[y][x] > expandable[rect]
                    && x - rectDP[y][x] + 1 <= smallX[rect]
                    && x >= bigX[rect]
                    && y - rectDP[y][x] + 1 <= smallY[rect]
                    && y >= bigY[rect])
                        expandable[rect] = rectDP[y][x];
                }
            }
        }

        for (int i = 1; i <= K; i++) {
            if (smallX[i] == Integer.MAX_VALUE)
                System.out.println(1 + " " + expandable[i]);
            else
                System.out.println((Math.max(bigX[i] - smallX[i], bigY[i] - smallY[i]) + 1) + " " + expandable[i]);
        }
    }
}