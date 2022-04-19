package ru.job4j.list;

import java.util.*;

public class SimpleArrayList<T> implements List<T> {
    private T[] container;
    private int size = 0;
    private int modCount = 0;

    public SimpleArrayList(int capacity) {
        this.container = (T[]) new Object[capacity];
    }

    private void copyOf() {
        if (container.length == 0) {
            container = (T[]) new Object[3];
        }
        container =  Arrays.copyOf(container, container.length * 2);
    }

    @Override
    public void add(T value) {
        if (size == container.length) {
            copyOf();
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
        return new Iterator<>() {
            private int index = 0;
            private final int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return index < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return container[index++];
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SimpleArrayList<?> that = (SimpleArrayList<?>) o;
        return size == that.size && Arrays.equals(container, that.container);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        result = 31 * result + Arrays.hashCode(container);
        return result;
    }
}
