package ru.job4j.tree;

import org.junit.Test;
import static org.junit.Assert.*;

public class SimpleTreeTest {

    @Test
    public void when6ElFindLastThen6() {
        Tree<Integer> tree = new SimpleTree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(1, 4);
        tree.add(4, 5);
        tree.add(5, 6);
        assertTrue(tree.findBy(6).isPresent());
    }

    @Test
    public void when6ElFindNotExitThenOptionEmpty() {
        Tree<Integer> tree = new SimpleTree<>(1);
        tree.add(1, 2);
        assertFalse(tree.findBy(7).isPresent());
    }

    @Test
    public void whenChildExistOnLeafThenNotAdd() {
        Tree<Integer> tree = new SimpleTree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(1, 4);
        tree.add(4, 5);
        tree.add(5, 6);
        assertFalse(tree.add(2, 6));
    }

    @Test
    public void whenAddChildIsNull() {
        Tree<Integer> tree = new SimpleTree<>(1);
        assertTrue(tree.add(1, null));
        assertTrue(tree.add(1, null));
        assertTrue(tree.add(1, null));
    }

    @Test
    public void whenAddParentIsNull() {
        Tree<Integer> tree = new SimpleTree<>(1);
        tree.add(1, null);
        assertFalse(tree.add(null, 2));
    }

    @Test
    public void whenAddAndRootIsNull() {
        Tree<Integer> tree = new SimpleTree<>(null);
        assertFalse(tree.add(null, 2));
        assertFalse(tree.add(1, 2));
    }

    @Test
    public void whenParentExistOffLeafThenNotAdd() {
        Tree<Integer> tree = new SimpleTree<>(1);
        tree.add(1, 2);
        assertFalse(tree.add(3, 4));
    }
}