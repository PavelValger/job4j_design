package ru.job4j.tree;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.function.Predicate;

public class SimpleTree<E> implements Tree<E> {
    private final Node<E> root;

    public SimpleTree(final E root) {
        this.root = new Node<>(root);
    }

    private Optional<Node<E>> findByPredicate(Predicate<Node<E>> condition) {
        Optional<Node<E>> rsl = Optional.empty();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            var node = data.poll();
            if (condition.test(node)) {
                rsl = Optional.of(node);
                break;
            }
            data.addAll(node.getChildren());
        }
        return rsl;
    }

    public boolean isBinary() {
        return findByPredicate(t -> t.getChildren().size() > 2).isEmpty();
    }

    @Override
    public boolean add(E parent, E child) {
        var rsl = false;
        var findRoot = findBy(parent);
        if (findRoot.isPresent() && findBy(child).isEmpty()) {
            var newRoot = findRoot.get();
            rsl = newRoot.getChildren().add(new Node<>(child));
        }
        return rsl;
    }

    @Override
    public Optional<Node<E>> findBy(E value) {
        return findByPredicate(t -> t.getValue() != null && t.getValue().equals(value));
    }
}
