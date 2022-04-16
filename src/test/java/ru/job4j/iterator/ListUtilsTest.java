package ru.job4j.iterator;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ListUtilsTest {

    @Test
    public void whenAddBefore() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 3));
        ListUtils.addBefore(input, 1, 2);
        assertThat(input, is(List.of(1, 2, 3)));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenAddBeforeWithInvalidIndex() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 3));
        ListUtils.addBefore(input, 3, 2);
    }

    @Test
    public void whenAddAfterLast() {
        List<Integer> input = new ArrayList<>(Arrays.asList(0, 1, 2));
        ListUtils.addAfter(input, 2, 3);
        assertThat(input, is(List.of(0, 1, 2, 3)));
    }

    @Test
    public void whenAddAfterMiddle() {
        List<Integer> input = new ArrayList<>(Arrays.asList(0, 1, 2));
        ListUtils.addAfter(input, 1, 3);
        assertThat(input, is(List.of(0, 1, 3, 2)));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenAddAfterLastWithInvalidIndex() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 3));
        ListUtils.addAfter(input, 2, 2);
    }

    @Test
    public void whenRemoveIf() {
        List<Integer> input = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4));
        ListUtils.removeIf(input, t -> t % 2 == 0);
        assertThat(input, is(List.of(1, 3)));
    }

    @Test(expected = NullPointerException.class)
    public void whenRemoveIfFilterIsNull() {
        List<Integer> input = new ArrayList<>();
        ListUtils.removeIf(input, null);
    }

    @Test
    public void whenReplaceIf() {
        List<Integer> input = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4));
        ListUtils.replaceIf(input, t -> t % 2 != 0, 101);
        assertThat(input, is(List.of(0, 101, 2, 101, 4)));
    }

    @Test(expected = NullPointerException.class)
    public void whenReplaceIfFilterIsNull() {
        List<Integer> input = new ArrayList<>();
        ListUtils.replaceIf(input, null, 101);
    }

    @Test
    public void whenRemoveAll() {
        List<Integer> source = new ArrayList<>(Arrays.asList(0, 1, 0, 1, 2, 3, 4));
        List<Integer> clear = new ArrayList<>(List.of(0, 2, 4));
        ListUtils.removeAll(source, clear);
        assertThat(source, is(List.of(1, 1, 3)));
    }

    @Test(expected = NullPointerException.class)
    public void whenRemoveAllNull() {
        List<String> source = new ArrayList<>(Arrays.asList("1", "2"));
        List<String> clear = new ArrayList<>();
        clear.add("1");
        clear.add(null);
        ListUtils.removeAll(source, clear);
    }
}