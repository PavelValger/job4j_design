package ru.job4j.iterator;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Класс описывает работу методов ListIterator.
 * @author PAVEL VALGER
 * @version 1.0
 */
public class ListUtils {
    /**
     * Метод вставляет указанный элемент в указанную позицию в списке list.
     * Сдвигает элемент, находящийся в данный момент в этой позиции и
     * любые последующие элементы вправо.
     * @param list список со значениями.
     * @param index позиция вставки элемента.
     * @param value значение элемента.
     * @param <T> обобщенный тип.
     * @exception IndexOutOfBoundsException если index находится вне диапазона list.
     */
    public static <T> void addBefore(List<T> list, int index, T value) {
        Objects.checkIndex(index, list.size());
        var iterator = list.listIterator();
        while (iterator.hasNext()) {
            if (iterator.nextIndex() == index) {
                iterator.add(value);
                break;
            }
            iterator.next();
        }
    }

    /**
     * Метод вставляет указанный элемент в позицию после индекса в списке list.
     * Сдвигает элемент, находящийся в данный момент в этой позиции (если таковой имеется) и
     * любые последующие элементы вправо.
     * @param list список со значениями.
     * @param index позиция вставки элемента.
     * @param value значение элемента.
     * @param <T> обобщенный тип.
     * @exception IndexOutOfBoundsException если index находится вне диапазона list.
     */
    public static <T> void addAfter(List<T> list, int index, T value) {
        Objects.checkIndex(index, list.size());
        var iterator = list.listIterator(list.size());
        while (iterator.hasPrevious()) {
            if (iterator.previousIndex() == index) {
                iterator.add(value);
                break;
            }
            iterator.previous();
        }
    }

    /**
     * Метод удаляет все элементы списка list, которые удовлетворяют заданному Predicate.
     * @param list список со значениями.
     * @param filter принимает Predicate.
     * @param <T> обобщенный тип.
     * @exception NullPointerException если Predicate не имеет функции.
     */
    public static <T> void removeIf(List<T> list, Predicate<T> filter) {
        Objects.requireNonNull(filter);
        var iterator = list.listIterator();
        while (iterator.hasNext()) {
            if (filter.test(iterator.next())) {
                iterator.remove();
            }
        }
    }

    /**
     * Метод заменяет каждый элемент списка list результатом применения Predicate к этому элементу
     * @param list список со значениями.
     * @param filter принимает Predicate.
     * @param value значение элемента.
     * @param <T> обобщенный тип.
     */
    public static <T> void replaceIf(List<T> list, Predicate<T> filter, T value) {
        Objects.requireNonNull(filter);
        var iterator = list.listIterator();
        while (iterator.hasNext()) {
            if (filter.test(iterator.next())) {
                iterator.set(value);
            }
        }
    }

    /**
     * Метод удаляет из списка list все его элементы, содержащиеся в elements.
     * @param list список со значениями.
     * @param elements список со значениями для исключения из list.
     * @param <T> обобщенный тип.
     */
    public static <T> void removeAll(List<T> list, List<T> elements) {
        var iterator = list.listIterator();
        while (iterator.hasNext()) {
            T cursor = iterator.next();
            for (T element : elements) {
                if (element == cursor) {
                    iterator.remove();
                    break;
                }
            }
        }
    }
}
