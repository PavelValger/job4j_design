package ru.job4j.map;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import java.util.Calendar;
import java.util.ConcurrentModificationException;
import java.util.GregorianCalendar;
import java.util.NoSuchElementException;

public class SimpleMapTest {

    @Test
    public void whenAdd() {
        SimpleMap<Integer, String> simpleMap = new SimpleMap<>();
        Assert.assertTrue(simpleMap.put(null, "void"));
        Assert.assertTrue(simpleMap.put(1, "one"));
        Assert.assertTrue(simpleMap.put(5, "five"));
    }

    @Test
    public void whenAddFalse() {
        SimpleMap<Object, Integer> simpleMap = new SimpleMap<>();
        Calendar birthday = new GregorianCalendar(1992, Calendar.MAY, 4);
        User user = new User("Pavel", 30, birthday);
        simpleMap.put("key", 5);
        Assert.assertTrue(simpleMap.put("100", 100));
        Assert.assertTrue(simpleMap.put(user, 800));
        Assert.assertFalse(simpleMap.put("key", 7));
        Assert.assertFalse(simpleMap.put("100", 500));
        Assert.assertFalse(simpleMap.put(user, 900));
    }

    @Test
    public void whenAddAndGetByCorrectIndex() {
        SimpleMap<Integer, String> simpleMap = new SimpleMap<>();
        simpleMap.put(null, "void");
        Assert.assertTrue(simpleMap.put(1, "one"));
        Assert.assertTrue(simpleMap.put(5, "five"));
        Assert.assertEquals("void", simpleMap.get(null));
        Assert.assertEquals("one", simpleMap.get(1));
        Assert.assertEquals("five", simpleMap.get(5));
    }

    @Test
    public void whenGetDifferentKeySimilarIndex() {
        SimpleMap<Object, String> simpleMap = new SimpleMap<>();
        Calendar birthday = new GregorianCalendar(1992, Calendar.MAY, 4);
        User user = new User("Pavel", 30, birthday);
        simpleMap.put(user, "void");
        Assert.assertNull(simpleMap.get(11));
    }

    @Test
    public void whenGetEmpty() {
        SimpleMap<Integer, String> simpleMap = new SimpleMap<>();
        simpleMap.put(1, "one");
        Assert.assertNull(simpleMap.get(2));
    }

    @Test
    public void whenGetNullEmptyMap() {
        SimpleMap<Integer, String> simpleMap = new SimpleMap<>();
        Assert.assertNull(simpleMap.get(null));
    }

    @Test
    public void whenRemoveThenTrue() {
        SimpleMap<Integer, String> simpleMap = new SimpleMap<>();
        simpleMap.put(null, "null");
        simpleMap.put(1, "one");
        Assert.assertTrue(simpleMap.remove(null));
        Assert.assertTrue(simpleMap.remove(1));
        Assert.assertThat(simpleMap.getCount(), Is.is(0));
    }

    @Test
    public void whenRemoveThenFalse() {
        SimpleMap<Object, String> simpleMap = new SimpleMap<>();
        Calendar birthday = new GregorianCalendar(1992, Calendar.MAY, 4);
        User user = new User("Pavel", 30, birthday);
        simpleMap.put(user, "void");
        simpleMap.put(1, "one");
        Assert.assertFalse(simpleMap.remove(5));
        Assert.assertFalse(simpleMap.remove(11));
    }

    @Ignore
    @Test
    public void whenAddMoreLoadFactor() {
        SimpleMap<Object, String> simpleMap = new SimpleMap<>();
        Calendar birthday = new GregorianCalendar(1992, Calendar.MAY, 4);
        User user = new User("Pavel", 30, birthday);
        simpleMap.put(null, "null");
        Assert.assertTrue(simpleMap.put(2, "2"));
        Assert.assertTrue(simpleMap.put(3, "3"));
        Assert.assertTrue(simpleMap.put(user, "user"));
        Assert.assertTrue(simpleMap.put(5, "5"));
        Assert.assertTrue(simpleMap.put(6, "6"));
        Assert.assertTrue(simpleMap.put(13, "13"));
        Assert.assertThat(simpleMap.getCapacity(), Is.is(16));
        Assert.assertThat(simpleMap.getCount(), Is.is(7));
    }

    @Test
    public void whenCheckIterator() {
        SimpleMap<Object, String> simpleMap = new SimpleMap<>();
        simpleMap.put(null, "void");
        Assert.assertTrue(simpleMap.put(1, "one"));
        Assert.assertTrue(simpleMap.put(5, "five"));
        var iterator = simpleMap.iterator();
        Assert.assertTrue(iterator.hasNext());
        Assert.assertNull(iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(1, iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(5, iterator.next());
        Assert.assertFalse(iterator.hasNext());
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenAddAfterGetIteratorThenMustBeException() {
        SimpleMap<Integer, String> simpleMap = new SimpleMap<>();
        var iterator = simpleMap.iterator();
        simpleMap.put(5, "five");
        iterator.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenRemoveAfterGetIteratorThenMustBeException() {
        SimpleMap<Integer, String> simpleMap = new SimpleMap<>();
        simpleMap.put(5, "five");
        var iterator = simpleMap.iterator();
        simpleMap.remove(5);
        iterator.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void whenGetIteratorFromEmptyListThenNextThrowException() {
        SimpleMap<Integer, String> simpleMap = new SimpleMap<>();
        simpleMap.put(5, "five");
        simpleMap.remove(5);
        var iterator = simpleMap.iterator();
        iterator.next();
    }
}