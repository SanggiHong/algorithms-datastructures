import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    private static Range[] ranges;
    private static List<Integer>[] subordinates;
    private static MinMax[] segmentTree;
    private static int[] remainder;
    private static int leaf;

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokens;

        tokens = new StringTokenizer(reader.readLine());
        int company = Integer.parseInt(tokens.nextToken());

        while (0 < company--) {
            tokens = new StringTokenizer(reader.readLine());
            int N = Integer.parseInt(tokens.nextToken());

            subordinates = new List[N + 1];

            for (int i = 1; i <= N; i++)
                subordinates[i] = new LinkedList<>();

            tokens = new StringTokenizer(reader.readLine());
            for (int i = 2; i <= N; i++)
                subordinates[Integer.parseInt(tokens.nextToken())].add(i);

            ranges = new Range[N + 1];
            for (int i = 1; i <= N; i++)
                ranges[i] = new Range(0, 0);
            euilerTour(1, 1);

            int height = (int) (Math.ceil(Math.log(N) / Math.log(2)));
            leaf = (int) Math.pow(2, height);
            segmentTree = new MinMax[leaf * 2];
            remainder = new int[leaf * 2];

            tokens = new StringTokenizer(reader.readLine());
            for (int i = 1; i <= N; i++) {
                int salary = Integer.parseInt(tokens.nextToken());
                segmentTree[leaf + ranges[i].start - 1] = new MinMax(salary, salary);
            }

            for (int i = leaf + N; i < leaf * 2; i++)
                segmentTree[i] = new MinMax(Integer.MAX_VALUE, Integer.MIN_VALUE);

            for (int i = leaf - 1; i > 0; i--) {
                segmentTree[i] = new MinMax(Math.min(segmentTree[i * 2].min, segmentTree[i * 2 + 1].min),
                        Math.max(segmentTree[i * 2].max, segmentTree[i * 2 + 1].max));
            }

            tokens = new StringTokenizer(reader.readLine());
            int Q = Integer.parseInt(tokens.nextToken());
            while (0 < Q--) {
                tokens = new StringTokenizer(reader.readLine());
                int number;
                switch(tokens.nextToken()) {
                    case "Q":
                        number = Integer.parseInt(tokens.nextToken());
                        int min = getMin(1, 1, leaf, ranges[number].start, ranges[number].end);
                        int max = getMax(1, 1, leaf, ranges[number].start, ranges[number].end);
                        System.out.println(Integer.toString(max - min));
                        break;
                    case "R":
                        number = Integer.parseInt(tokens.nextToken());
                        increaseSalary(1, 1, leaf, ranges[number].start, ranges[number].end, Integer.parseInt(tokens.nextToken()));
                        break;
                }
            }
        }
    }

    static void increaseSalary(int node, int left, int right, int start, int end, int change) {
        propagate(node);

        if (right < start || end < left)
            return;

        if (start <= left && right <= end) {
            remainder[node] = change;
            propagate(node);
            return;
        }

        increaseSalary(node * 2, left, (left + right) / 2, start, end, change);
        increaseSalary(node * 2 + 1, (left + right) / 2 + 1, right, start, end, change);

        segmentTree[node].min = Math.min(segmentTree[node * 2].min, segmentTree[node * 2 + 1].min);
        segmentTree[node].max = Math.max(segmentTree[node * 2].max, segmentTree[node * 2 + 1].max);
    }

    static void propagate(int node) {
        if (remainder[node] == 0)
            return;

        segmentTree[node].min = segmentTree[node].min + remainder[node];
        segmentTree[node].max = segmentTree[node].max + remainder[node];
        if (node < leaf) {
            remainder[node * 2] = remainder[node * 2] + remainder[node];
            remainder[node * 2 + 1] = remainder[node * 2 + 1] + remainder[node];
        }
        remainder[node] = 0;
    }

    static int getMin(int node, int left, int right, int start, int end) {
        propagate(node);

        if (right < start || end < left)
            return Integer.MAX_VALUE;
        if (start <= left && right <= end)
            return segmentTree[node].min;

        return Math.min(
                getMin(node * 2, left, (left + right) / 2, start, end),
                getMin(node * 2 + 1, (left + right) / 2 + 1, right, start, end) );
    }

    static int getMax(int node, int left, int right, int start, int end) {
        propagate(node);

        if (right < start || end < left)
            return Integer.MIN_VALUE;
        if (start <= left && right <= end)
            return segmentTree[node].max;

        return Math.max(
                getMax(node * 2, left, (left + right) / 2, start, end),
                getMax(node * 2 + 1, (left + right) / 2 + 1, right, start, end) );
    }

    static int euilerTour(int employee, int start) {
        ranges[employee].start = start;
        ranges[employee].end = start;

        for (Integer subordinate : subordinates[employee])
            ranges[employee].end = euilerTour(subordinate, ranges[employee].end + 1);

        return ranges[employee].end;
    }

    static class Range {
        int start;
        int end;

        public Range(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    static class MinMax {
        int min;
        int max;

        public MinMax(int min, int max) {
            this.min = min;
            this.max = max;
        }
    }
}
