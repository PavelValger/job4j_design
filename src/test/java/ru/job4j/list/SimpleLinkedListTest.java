package ru.job4j.list;

import org.hamcrest.core.Is;
import org.junit.Test;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import static org.junit.Assert.*;

public class SimpleLinkedListTest {
    @Test
    public void whenAddAndGet() {
        LinkList<Integer> list = new SimpleLinkedList<>();
        list.add(1);
        list.add(2);
        assertThat(list.get(0), Is.is(1));
        assertThat(list.get(1), Is.is(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenGetFromOutOfBoundThenExceptionThrown() {
        LinkList<Integer> list = new SimpleLinkedList<>();
        list.add(1);
        list.add(2);
        list.get(2);
    }

    @Test
    public void whenAddIterHasNextTrue() {
        LinkList<Integer> list = new SimpleLinkedList<>();
        list.add(1);
        var it = list.iterator();
        assertTrue(it.hasNext());
    }

    @Test
    public void whenAddIterNextOne() {
        LinkList<Integer> list = new SimpleLinkedList<>();
        list.add(1);
        var it = list.iterator();
        assertThat(it.next(), Is.is(1));
    }

    @Test
    public void whenEmptyIterHashNextFalse() {
        LinkList<Integer> list = new SimpleLinkedList<>();
        var it = list.iterator();
        assertFalse(it.hasNext());
    }

    @Test
    public void whenAddIterMultiHasNextTrue() {
        LinkList<Integer> list = new SimpleLinkedList<>();
        list.add(1);
        var it = list.iterator();
        assertTrue(it.hasNext());
        assertTrue(it.hasNext());
    }

    @Test
    public void whenAddIterNextOneNextTwo() {
        LinkList<Integer> list = new SimpleLinkedList<>();
        list.add(1);
        list.add(2);
        var it = list.iterator();
        assertThat(it.next(), Is.is(1));
        assertThat(it.next(), Is.is(2));
    }

    @Test
    public void whenAddIterNextOneNextTwoAndNull() {
        LinkList<Integer> list = new SimpleLinkedList<>();
        list.add(1);
        list.add(2);
        list.add(null);
        var it = list.iterator();
        assertTrue(it.hasNext());
        assertThat(it.next(), Is.is(1));
        assertTrue(it.hasNext());
        assertThat(it.next(), Is.is(2));
        assertTrue(it.hasNext());
        assertNull(it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void whenGetIteratorTwiceThenEveryFromBegin() {
        LinkList<Integer> list = new SimpleLinkedList<>();
        list.add(1);
        list.add(2);
        var first = list.iterator();
        assertTrue(first.hasNext());
        assertThat(first.next(), Is.is(1));
        assertTrue(first.hasNext());
        assertThat(first.next(), Is.is(2));
        assertFalse(first.hasNext());
        var second = list.iterator();
        assertTrue(second.hasNext());
        assertThat(second.next(), Is.is(1));
        assertTrue(second.hasNext());
        assertThat(second.next(), Is.is(2));
        assertFalse(second.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void whenGetIteratorFromEmptyListThenNextThrowException() {
        LinkList<Integer> list = new SimpleLinkedList<>();
        list.iterator().next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenRemoveAfterGetIteratorThenMustBeException() {
        LinkList<Integer> list = new SimpleLinkedList<>();
        var iterator = list.iterator();
        list.add(0);
        iterator.next();
    }
}