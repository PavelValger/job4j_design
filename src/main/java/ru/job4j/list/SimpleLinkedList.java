package ru.job4j.list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SimpleLinkedList<E> implements LinkList<E> {
    private Node<E> first;
    private Node<E> last;
    private int size = 0;
    private int modCount = 0;

    private static class Node<E> {
        private Node<E> prev;
        private E item;
        private Node<E> next;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(E value) {
        final Node<E> prev = last;
        final Node<E> newNode = new Node<>(prev, value, null);
        last = newNode;
        if (prev == null) {
            first = newNode;
        } else {
            prev.next = newNode;
        }
        size++;
        modCount++;
    }

    @Override
    public E get(int index) {
        Objects.checkIndex(index, size);
        Node<E> cursor = first;
        if (index < (size / 2)) {
            for (int i = 0; i < index; i++) {
                cursor = cursor.next;
            }
        } else {
            cursor = last;
            for (int i = size - 1; i > index; i--) {
                cursor = cursor.prev;
            }
        }
        return cursor.item;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int index = 0;
            private Node<E> cursor = first;
            private final int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException(
                            "The collection has undergone a structural change"
                    );
                }
                return index < size;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("There are no elements");
                }
                if (index == 0) {
                    index++;
                    return first.item;
                }
                index++;
                cursor = cursor.next;
                return cursor.item;
            }
        };
    }
}
