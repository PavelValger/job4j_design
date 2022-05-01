package ru.job4j.map;

public interface MapHash<K, V> extends Iterable<K> {
    boolean put(K key, V value);

    V get(K key);

    boolean remove(K key);
}
