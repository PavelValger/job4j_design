package ru.job4j.it;

import org.junit.Test;
import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class MatrixItTest {

    @Test
    public void when4El() {
        int[][] in = {
                {1}
        };
        var it = new MatrixIt(in);
        assertThat(it.next(), is(1));
    }

    @Test
    public void whenFirstEmptyThenNext() {
        int[][] in = {
                {}, {1}
        };
        var it = new MatrixIt(in);
        assertThat(it.next(), is(1));
    }

    @Test
    public void whenFirstEmptyThenHashNext() {
        int[][] in = {
                {}, {1}
        };
        MatrixIt it = new MatrixIt(in);
        assertTrue(it.hasNext());
    }

    @Test
    public void whenRowHasDiffSize() {
        int[][] in = {
                {1}, {2, 3}
        };
        var it = new MatrixIt(in);
        assertThat(it.next(), is(1));
        assertThat(it.next(), is(2));
        assertThat(it.next(), is(3));
    }

    @Test
    public void whenFewEmpty() {
        int[][] in = {
                {1}, {}, {}, {}, {2}
        };
        var it = new MatrixIt(in);
        assertThat(it.next(), is(1));
        assertThat(it.next(), is(2));
    }

    @Test
    public void whenEmpty() {
        int[][] in = {
                {}
        };
        var it = new MatrixIt(in);
        assertFalse(it.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void whenEmptyThenNext() {
        int[][] in = {
                {}
        };
        var it = new MatrixIt(in);
        it.next();
    }

    @Test
    public void whenMultiHashNext() {
        int[][] in = {
                {}, {1}
        };
        var it = new MatrixIt(in);
        assertTrue(it.hasNext());
        it.next();
        assertFalse(it.hasNext());
    }

    @Test
    public void whenNoElements() {
        int[][] in = {{}, {}, {}};
        var it = new MatrixIt(in);
        assertFalse(it.hasNext());
    }

    @Test
    public void whenFewEmptyAndWhenRowHasDiffSize() {
        int[][] in = {
                {1}, {2, 3}, {}, {}, {}, {4}
        };
        var it = new MatrixIt(in);
        assertThat(it.next(), is(1));
        assertThat(it.next(), is(2));
        assertThat(it.next(), is(3));
        assertThat(it.next(), is(4));
    }
}