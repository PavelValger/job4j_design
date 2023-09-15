package ru.job4j.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Balancer {
    public static void split(List<ArrayList<Integer>> nodes, Iterator<Integer> source) {
        while (source.hasNext()) {
            var listIterator = nodes.iterator();
            while (source.hasNext() && listIterator.hasNext()) {
                listIterator.next().add(source.next());
            }
        }
    }
}