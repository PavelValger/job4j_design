package ru.job4j.list;

import java.util.*;

public class SimpleArrayList<T> implements List<T> {
    private T[] container;
    private int size = 0;
    private int modCount = 0;

    public SimpleArrayList(int capacity) {
        this.container = (T[]) new Object[capacity];
    }

    private T[] copyOf() {
        if (container.length == 0) {
            return  (T[]) new Object[3];
        }
        return Arrays.copyOf(container, container.length * 2);
    }

    @Override
    public void add(T value) {
        if (size == container.length) {
            container = copyOf();
        }
        container[size] = value;
        size++;
        modCount++;
    }

    @Override
    public T set(int index, T newValue) {
        T rsl = get(index);
        container[index] = newValue;
        return rsl;
    }

    @Override
    public T remove(int index) {
        T rsl = get(index);
        System.arraycopy(container, index + 1, container, index,
                container.length - index - 1);
        size--;
        container[size] = null;
        modCount++;
        return rsl;
    }

    @Override
    public T get(int index) {
        Objects.checkIndex(index, size);
        return container[index];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        final int[] count = {0};
        int expectedModCount = modCount;
        return new Iterator<>() {

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return count[0] < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return container[count[0]++];
            }
        };
    }
}
