package ru.job4j.map;

import java.util.*;

public class SimpleMap<K, V> implements MapHash<K, V> {
    private static final float LOAD_FACTOR = 0.75f;
    private int capacity = 8;
    private int count = 0;
    private int modCount = 0;
    private MapEntry<K, V>[] table = new MapEntry[capacity];

    private static class MapEntry<K, V> {
        private final K key;
        private final V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private int hash(int hashCode) {
        return hashCode ^ (hashCode >>> 16);
    }

    private int indexFor(int hash) {
        return hash & (table.length - 1);
    }

    private void expand() {
        if ((float) count / capacity >= LOAD_FACTOR) {
            var interim = table;
            capacity *= 2;
            table = new MapEntry[capacity];
            for (MapEntry<K, V> object : interim) {
                if (object != null) {
                    put(object.key, object.value);
                }
            }
        }
    }

    private int index(K key) {
        return key == null ? 0 : indexFor(hash(key.hashCode()));
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public boolean put(K key, V value) {
        boolean rsl = false;
        expand();
        int indexTable = index(key);
        if (table[indexTable] == null) {
            table[indexTable] = new MapEntry<>(key, value);
            count++;
            modCount++;
            rsl = true;
        }
        return rsl;
    }

    @Override
    public V get(K key) {
        return table[index(key)] == null ? null : table[index(key)].value;
    }

    @Override
    public boolean remove(K key) {
        boolean rsl = false;
        if (table[index(key)] != null) {
            table[index(key)] = null;
            count--;
            modCount++;
            rsl = true;
        }
        return rsl;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<>() {
            private int index = 0;
            private final int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException(
                            "The map has undergone a structural change"
                    );
                }
                return index < count;
            }

            @Override
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("There are no elements");
                }
                while (index < table.length && table[index] == null) {
                    index++;
                }
                return table[index++].key;
            }
        };
    }
}
