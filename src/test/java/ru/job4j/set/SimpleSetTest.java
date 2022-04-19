package ru.job4j.set;

import org.junit.Assert;
import org.junit.Test;
import java.util.Iterator;
import static org.junit.Assert.*;

public class SimpleSetTest {

    @Test
    public void whenAddNonNull() {
        SetList<Integer> set = new SimpleSet<>();
        assertTrue(set.add(1));
        assertTrue(set.contains(1));
        assertFalse(set.add(1));
    }

    @Test
    public void whenAddNull() {
        SetList<Integer> set = new SimpleSet<>();
        assertTrue(set.add(null));
        assertTrue(set.contains(null));
        assertFalse(set.add(null));
    }

    @Test
    public void whenAddAll() {
        SetList<Integer> set = new SimpleSet<>();
        assertTrue(set.add(1));
        assertTrue(set.add(2));
        assertTrue(set.add(null));
        assertFalse(set.add(2));
        assertTrue(set.contains(null));
        assertFalse(set.add(null));
    }

    @Test
    public void whenAddObjects() {
        SetList<Object> set = new SimpleSet<>();
        SetList<Object> setList = new SimpleSet<>();
        SetList<Object> simpleSet = new SimpleSet<>();
        SetList<Object> rsl = new SimpleSet<>();
        set.add(5);
        setList.add(5);
        simpleSet.add(7);
        assertTrue(rsl.add(set));
        assertFalse(rsl.add(setList));
        assertTrue(rsl.add(simpleSet));
    }

    @Test
    public void whenAddItemThenIterator() {
        SetList<Integer> set = new SimpleSet<>();
        assertTrue(set.add(1));
        assertTrue(set.add(null));
        Iterator<Integer> iterator = set.iterator();
        assertTrue(iterator.hasNext());
        Assert.assertEquals(Integer.valueOf(1), iterator.next());
        assertTrue(iterator.hasNext());
        assertNull(iterator.next());
    }
}