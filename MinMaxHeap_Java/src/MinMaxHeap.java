import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class MinMaxHeap<T extends Comparable> {
    private final int DEFAULT_SIZE = 10;
    private int capacity;
    private int size = 0;
    private T[] heap;

    public MinMaxHeap() {
        heap = (T[]) new Comparable[DEFAULT_SIZE];
        capacity = DEFAULT_SIZE;
    }

    public MinMaxHeap(int size) {
        heap = (T[]) new Comparable[size];
        capacity = size;
    }

    public MinMaxHeap(T[] arr) {
        heap = arr.clone();
        capacity = arr.length;
        size = arr.length;
        for (int i = (size - 2) / 2; i >= 0; i--)
            trickleDown(i);
    }

    public MinMaxHeap(List<T> list) {
        if (list instanceof ArrayList) {
            heap = (T[]) list.toArray();
            capacity = list.size();
            size = list.size();
            for (int i = (size - 2) / 2; i >= 0; i--)
                trickleDown(i);
        } else {
            heap = (T[]) new Comparable[list.size()];
            capacity = list.size();
            for (T item : list)
                insert(item);
        }
    }

    public void insert(T item) {
        if (size >= capacity)
            while (size >= capacity)
                resize();

        heap[size] = item;
        bubbleUp(size);
        size = size + 1;
    }

    public T getMin() {
        if (size == 0)
            return null;
        return heap[0];
    }

    public T popMin() {
        T temp = heap[0];
        size = size - 1;
        heap[0] = heap[size];
        trickleDown(0);
        return temp;
    }

    public T getMax() {
        int index = getMaxIndex();
        if (index == -1)
            return null;

        return heap[index];
    }

    public T popMax() {
        int index = getMaxIndex();
        if (index == -1)
            return null;

        T temp = heap[index];
        size = size - 1;
        heap[index] = heap[size];
        trickleDown(index);

        return temp;
    }

    public int size() {
        return size;
    }

    public void clear() {
        heap = (T[]) new Comparable[capacity];
        size = 0;
    }

    private int getMaxIndex() {
        switch (size) {
            case 0:
                return -1;
            case 1:
                return 0;
            case 2:
                return 1;
            default:
                if (heap[1].compareTo(heap[2]) > 0)
                    return 1;
                return 2;
        }
    }

    private int getLevelOfTree(int index) {
        int level = 0;
        int nodeNumber = index;
        while (nodeNumber != 0) {
            if (nodeNumber % 2 == 0)
                nodeNumber = nodeNumber - 1;
            nodeNumber = nodeNumber / 2;
            level = level + 1;
        }

        return level;
    }

    private boolean isMinLevel(int index) {
        return getLevelOfTree(index) % 2 == 0;
    }

    private void trickleDown(int index) {
        if (isMinLevel(index))
            trickleDownMin(index);
        else
            trickleDownMax(index);
    }

    private void trickleDownMin(int index) {
        int compareIndex = getIndexSmallestOfChildrenAndGrandChildren(index);
        if (compareIndex != -1) {
            if (isGrandChild(index, compareIndex) && heap[index].compareTo(heap[compareIndex]) > 0) {
                swap(index, compareIndex);
                if (heap[compareIndex].compareTo(heap[(compareIndex - 1) / 2]) > 0)
                    swap(compareIndex, (compareIndex - 1) / 2);
                trickleDownMin(compareIndex);
            } else if (isChild(index, compareIndex) && heap[index].compareTo(heap[compareIndex]) > 0) {
                swap(index, compareIndex);
            }
        }
    }

    private int getIndexSmallestOfChildrenAndGrandChildren(int index) {
        if (size - 1 < 2 * index + 1)
            return -1;

        T min = heap[2 * index + 1];
        int minIndex = 2 * index + 1;

        if (2 * index + 2 <= size - 1 && min.compareTo(heap[2 * index + 2]) > 0) {
            min = heap[2 * index + 2];
            minIndex = 2 * index + 2;
        }

        if (4 * index + 3 <= size - 1 && min.compareTo(heap[4 * index + 3]) > 0) {
            min = heap[4 * index + 3];
            minIndex = 4 * index + 3;
        }

        if (4 * index + 4 <= size - 1 && min.compareTo(heap[4 * index + 4]) > 0) {
            min = heap[4 * index + 4];
            minIndex = 4 * index + 4;
        }

        if (4 * index + 5 <= size - 1 && min.compareTo(heap[4 * index + 5]) > 0) {
            min = heap[4 * index + 5];
            minIndex = 4 * index + 5;
        }

        if (4 * index + 6 <= size - 1 && min.compareTo(heap[4 * index + 6]) > 0) {
            minIndex = 4 * index + 6;
        }

        return minIndex;
    }

    private void trickleDownMax(int index) {
        int compareIndex = getIndexBiggestOfChildrenAndGrandChildren(index);
        if (compareIndex != -1) {
            if (isGrandChild(index, compareIndex) && heap[index].compareTo(heap[compareIndex]) < 0) {
                swap(index, compareIndex);
                if (heap[compareIndex].compareTo(heap[(compareIndex - 1) / 2]) < 0)
                    swap(compareIndex, (compareIndex - 1) / 2);
                trickleDownMax(compareIndex);
            } else if (isChild(index, compareIndex) && heap[index].compareTo(heap[compareIndex]) < 0) {
                swap(index, compareIndex);
            }
        }
    }

    private int getIndexBiggestOfChildrenAndGrandChildren(int index) {
        if (size - 1 < 2 * index + 1)
            return -1;

        T max = heap[2 * index + 1];
        int maxIndex = 2 * index + 1;

        if (2 * index + 2 <= size - 1 && max.compareTo(heap[2 * index + 2]) < 0) {
            max = heap[2 * index + 2];
            maxIndex = 2 * index + 2;
        }

        if (4 * index + 3 <= size - 1 && max.compareTo(heap[4 * index + 3]) < 0) {
            max = heap[4 * index + 3];
            maxIndex = 4 * index + 3;
        }

        if (4 * index + 4 <= size - 1 && max.compareTo(heap[4 * index + 4]) < 0) {
            max = heap[4 * index + 4];
            maxIndex = 4 * index + 4;
        }

        if (4 * index + 5 <= size - 1 && max.compareTo(heap[4 * index + 5]) < 0) {
            max = heap[4 * index + 5];
            maxIndex = 4 * index + 5;
        }

        if (4 * index + 6 <= size - 1 && max.compareTo(heap[4 * index + 6]) < 0) {
            maxIndex = 4 * index + 6;
        }

        return maxIndex;
    }

    private boolean isChild(int parent, int child) {
        if (2 * parent + 1 <= child && child <= 2 * parent + 2)
            return true;
        return false;
    }

    private boolean isGrandChild(int gParent, int gChild) {
        if (4 * gParent + 3 <= gChild && gChild <= 4 * gParent + 6)
            return true;
        return false;
    }

    private void swap(int index, int compareIndex) {
        T temp = heap[index];
        heap[index] = heap[compareIndex];
        heap[compareIndex] = temp;
    }

    private void bubbleUp(int index) {
        if (index < 1)
            return;

        if (isMinLevel(index)) {
            if (heap[index].compareTo(heap[(index - 1) / 2]) > 0) {
                swap(index, (index - 1) / 2);
                bubbleUpMax((index - 1) / 2);
            } else
                bubbleUpMin(index);
        } else {
            if (heap[index].compareTo(heap[(index - 1) / 2]) < 0) {
                swap(index, (index - 1) / 2);
                bubbleUpMin((index - 1) / 2);
            } else
                bubbleUpMax(index);
        }
    }

    private void bubbleUpMin(int index) {
        if (index < 3)
            return;
        if (heap[index].compareTo(heap[(index - 3) / 4]) < 0) {
            swap(index, (index - 3) / 4);
            bubbleUpMin((index - 3) / 4);
        }
    }

    private void bubbleUpMax(int index) {
        if (index < 3)
            return;
        if (heap[index].compareTo(heap[(index - 3) / 4]) > 0) {
            swap(index, (index - 3) / 4);
            bubbleUpMax((index - 3) / 4);
        }
    }

    private void resize() {
        heap = Arrays.copyOf(heap, capacity * 2);
        capacity = capacity * 2;
    }
}