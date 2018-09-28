import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Main {
    public static void main(String[] args) {
        long start, end;
        Random rand = new Random();
        Integer[] a;
        MinMaxHeap<Integer> mmh;

        System.out.println("<<Item count : 1,000>>");
        a = new Integer[1000];
        for (int i = 0; i < 1000; i ++)
            a[i] = rand.nextInt();

        start = System.currentTimeMillis();
        mmh = new MinMaxHeap<>(a);
        end = System.currentTimeMillis();
        System.out.println("MinMaxHeap : " + (end - start));

        start = System.currentTimeMillis();
        Arrays.sort(a);
        end = System.currentTimeMillis();
        System.out.println("QuickSort : " + (end - start));

        System.out.println("<<Item count : 1,000,000>>");
        a = new Integer[1000000];
        for (int i = 0; i < 1000000; i ++)
            a[i] = rand.nextInt();

        start = System.currentTimeMillis();
        mmh = new MinMaxHeap<>(a);
        end = System.currentTimeMillis();
        System.out.println("MinMaxHeap : " + (end - start));

        start = System.currentTimeMillis();
        Arrays.sort(a);
        end = System.currentTimeMillis();
        System.out.println("QuickSort : " + (end - start));

        System.out.println("<<Item count : 100,000,000>>");
        a = new Integer[100000000];
        for (int i = 0; i < 100000000; i ++)
            a[i] = rand.nextInt();

        start = System.currentTimeMillis();
        mmh = new MinMaxHeap<>(a);
        end = System.currentTimeMillis();
        System.out.println("MinMaxHeap : " + (end - start));

        start = System.currentTimeMillis();
        Arrays.sort(a);
        end = System.currentTimeMillis();
        System.out.println("QuickSort : " + (end - start));
    }
}
