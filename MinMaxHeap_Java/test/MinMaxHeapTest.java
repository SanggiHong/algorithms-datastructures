import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MinMaxHeapTest {
    @Test
    public void testHeapify() {
        Integer[] a = {4, 10, 24, 3, 2, 4};
        assertEquals(6, a.length);
        MinMaxHeap<Integer> minMaxHeap = new MinMaxHeap<>(a);
        assertEquals(6, minMaxHeap.size());
        assertEquals(Integer.valueOf(2), minMaxHeap.getMin());
        assertEquals(Integer.valueOf(24), minMaxHeap.getMax());
    }

    @Test
    public void testInsertion() {
        MinMaxHeap<Integer> minMaxHeap = new MinMaxHeap<>();
        minMaxHeap.insert(4);
        minMaxHeap.insert(10);
        minMaxHeap.insert(24);
        minMaxHeap.insert(3);
        minMaxHeap.insert(2);
        minMaxHeap.insert(4);
        assertEquals(6, minMaxHeap.size());
        assertEquals(Integer.valueOf(2), minMaxHeap.getMin());
        assertEquals(Integer.valueOf(24), minMaxHeap.getMax());
    }

    @Test
    public void testPopMax() {
        Integer[] a = {4, 10, 24, 3, 2, 4};
        assertEquals(6, a.length);
        MinMaxHeap<Integer> minMaxHeap = new MinMaxHeap<>(a);
        assertEquals(6, minMaxHeap.size());
        assertEquals(Integer.valueOf(24), minMaxHeap.popMax());
        assertEquals(Integer.valueOf(10), minMaxHeap.getMax());
    }

    @Test
    public void testPopMin() {
        Integer[] a = {4, 10, 24, 3, 2, 4};
        assertEquals(6, a.length);
        MinMaxHeap<Integer> minMaxHeap = new MinMaxHeap<>(a);
        assertEquals(6, minMaxHeap.size());
        assertEquals(Integer.valueOf(2), minMaxHeap.popMin());
        assertEquals(Integer.valueOf(3), minMaxHeap.getMin());
    }

    @Test
    public void verifyWithBuiltInLibrary() {
        int N = 1000;
        assertEquals(0, N % 2);
        Integer[] a = new Integer[N];
        Random rand = new Random();

        for (int i = 0; i < N; i++)
            a[i] = rand.nextInt();
        assertEquals(N, a.length);
        MinMaxHeap<Integer> minMaxHeap = new MinMaxHeap<>(a);
        assertEquals(N, minMaxHeap.size());

        Arrays.sort(a);

        int l = 0, r = N - 1;
        for (int k = 0; k < N / 2; k++) {
            assertEquals(a[l], minMaxHeap.popMin());
            assertEquals(a[r], minMaxHeap.popMax());
            l = l + 1;
            r = r - 1;
        }
        assertEquals(0, minMaxHeap.size());
    }

}