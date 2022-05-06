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
        return Math.abs(hashCode ^ (hashCode >>> 16));
    }

    private int indexFor(int hash) {
        return hash % (table.length - 1);
    }

    private void expand() {
        if ((float) count / capacity >= LOAD_FACTOR) {
            var interim = table;
            capacity *= 2;
            table = new MapEntry[capacity];
            count = 0;
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

    private boolean removeTrue(int indexTable) {
        table[indexTable] = null;
        count--;
        modCount++;
        return true;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCount() {
        return count;
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
        V rsl = null;
        int indexTable = index(key);
        if (table[indexTable] != null && indexTable != 0
                && table[indexTable].key.equals(key)) {
            rsl = table[indexTable].value;
        }
        if (indexTable == 0 && table[indexTable] != null) {
            rsl = table[indexTable].value;
        }
        return rsl;
    }

    @Override
    public boolean remove(K key) {
        boolean rsl = false;
        int indexTable = index(key);
        if (table[indexTable] != null && indexTable != 0
                && table[indexTable].key.equals(key)) {
            rsl = removeTrue(indexTable);
        }
        if (table[indexTable] != null && indexTable == 0) {
            rsl = removeTrue(indexTable);
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
