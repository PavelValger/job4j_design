package ru.job4j.list;

public interface LinkList<E> extends Iterable<E> {
    void add(E value);

    E get(int index);
}
