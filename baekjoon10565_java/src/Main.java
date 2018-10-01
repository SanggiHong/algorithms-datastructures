import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    private static int[] salary;
    private static int[] min;
    private static int[] max;
    private static List<Integer>[] subordinates;
    private static int[] supervisors;

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        StringTokenizer tokens = new StringTokenizer(reader.readLine());
        int company = Integer.parseInt(tokens.nextToken());
        for (int i = 0; i < company; i++) {
            tokens = new StringTokenizer(reader.readLine());
            int N = Integer.parseInt(tokens.nextToken());
            salary = new int[N + 1];
            min = new int[N + 1];
            max = new int[N + 1];
            subordinates = new List[N + 1];
            supervisors = new int[N + 1];

            for (int j = 1; j <= N; j++)
                subordinates[j] = new LinkedList<>();

            tokens = new StringTokenizer(reader.readLine());
            for (int j = 2; j <= N; j++) {
                int number = Integer.parseInt(tokens.nextToken());
                subordinates[number].add(j);
                supervisors[j] = number;
            }


            tokens = new StringTokenizer(reader.readLine());
            for (int j = 1; j <= N; j++)
                salary[j] = Integer.parseInt(tokens.nextToken());

            tokens = new StringTokenizer(reader.readLine());
            int Q = Integer.parseInt(tokens.nextToken());
            for (int j = 0; j < Q; j++) {
                tokens = new StringTokenizer(reader.readLine());
                Queue<Integer> queue = new LinkedList<Integer>();
                switch (tokens.nextToken()) {
                    case "Q":
                        MinMax temp = getMinMax(Integer.parseInt(tokens.nextToken()));
                        System.out.println(temp.max - temp.min);
                        break;
                    case "R":
                        int number = Integer.parseInt(tokens.nextToken());
                        int amount = Integer.parseInt(tokens.nextToken());
                        queue.add(number);
                        while (!queue.isEmpty()) {
                            salary[queue.peek()] = salary[queue.peek()] + amount;
                            if (min[queue.peek()] != 0) {
                                min[queue.peek()] = min[queue.peek()] + amount;
                                max[queue.peek()] = max[queue.peek()] + amount;
                            }

                            queue.addAll(subordinates[queue.peek()]);
                            queue.remove();
                        }

                        while (supervisors[number] != 0 && min[supervisors[number]] != 0) {
                            boolean done = true;
                            number = supervisors[number];
                            min[number] = salary[number];
                            max[number] = salary[number];
                            for (int k : subordinates[number])
                            {
                                if (min[k] == 0)
                                    continue;
                                if (min[number] > min[k])
                                    min[number] = min[k];
                                if (max[number] < max[k])
                                    max[number] = max[k];
                            }
                        }
                        break;
                }
            }
        }
    }

    static MinMax getMinMax(int number) {
        if (subordinates[number].size() == 0) {
            min[number] = salary[number];
            max[number] = salary[number];
            return new MinMax(salary[number], salary[number]);
        }

        if (min[number] != 0)
            return new MinMax(min[number], max[number]);

        MinMax result = new MinMax(salary[number], salary[number]);
        for (Integer i : subordinates[number]) {
            MinMax temp = getMinMax(i);
            if (temp.min < result.min)
                result.min = temp.min;
            if (temp.max > result.max)
                result.max = temp.max;
        }
        min[number] = result.min;
        max[number] = result.max;
        return result;
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
