package ru.job4j.map;

import org.hamcrest.core.Is;
import org.junit.Assert;
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
        simpleMap.put("100500", 100);
        simpleMap.put(user, 1000);
        Assert.assertFalse(simpleMap.put("key", 7));
        Assert.assertFalse(simpleMap.put("100500", 500));
        Assert.assertFalse(simpleMap.put(user, 800));
    }

    @Test
    public void whenAddAndGetByCorrectIndex() {
        SimpleMap<Integer, String> simpleMap = new SimpleMap<>();
        simpleMap.put(null, "void");
        simpleMap.put(1, "one");
        simpleMap.put(5, "five");
        Assert.assertEquals("void", simpleMap.get(null));
        Assert.assertEquals("one", simpleMap.get(1));
        Assert.assertEquals("five", simpleMap.get(5));
    }

    @Test
    public void whenGetEmpty() {
        SimpleMap<Integer, String> simpleMap = new SimpleMap<>();
        simpleMap.put(1, "one");
        Assert.assertNull(simpleMap.get(2));
    }

    @Test
    public void whenRemoveThenTrue() {
        SimpleMap<Integer, String> simpleMap = new SimpleMap<>();
        simpleMap.put(1, "one");
        Assert.assertTrue(simpleMap.remove(1));
    }

    @Test
    public void whenRemoveThenFalse() {
        SimpleMap<Integer, String> simpleMap = new SimpleMap<>();
        simpleMap.put(1, "one");
        Assert.assertFalse(simpleMap.remove(5));
    }

    @Test
    public void whenAddMoreLoadFactor() {
        SimpleMap<Object, String> simpleMap = new SimpleMap<>();
        Calendar birthday = new GregorianCalendar(1992, Calendar.MAY, 4);
        User user = new User("Pavel", 30, birthday);
        simpleMap.put(null, "null");
        simpleMap.put(2, "2");
        simpleMap.put(user, "user");
        simpleMap.put(4, "4");
        simpleMap.put(5, "5");
        simpleMap.put(6, "6");
        Assert.assertTrue(simpleMap.put(12, "12"));
        Assert.assertEquals("user", simpleMap.get(11));
        Assert.assertNull(simpleMap.get(3));
        Assert.assertThat(simpleMap.getCapacity(), Is.is(16));
    }

    @Test
    public void whenCheckIterator() {
        SimpleMap<Object, String> simpleMap = new SimpleMap<>();
        simpleMap.put(null, "void");
        simpleMap.put(1, "one");
        simpleMap.put(5, "five");
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
        SimpleMap<Object, String> simpleMap = new SimpleMap<>();
        var iterator = simpleMap.iterator();
        simpleMap.put(5, "five");
        iterator.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenRemoveAfterGetIteratorThenMustBeException() {
        SimpleMap<Object, String> simpleMap = new SimpleMap<>();
        simpleMap.put(5, "five");
        var iterator = simpleMap.iterator();
        simpleMap.remove(5);
        iterator.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void whenGetIteratorFromEmptyListThenNextThrowException() {
        SimpleMap<Object, String> simpleMap = new SimpleMap<>();
        simpleMap.put(5, "five");
        simpleMap.remove(5);
        var iterator = simpleMap.iterator();
        iterator.next();
    }
}