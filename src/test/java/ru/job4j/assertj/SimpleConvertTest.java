package ru.job4j.assertj;

import org.assertj.core.data.Index;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class SimpleConvertTest {

    @Test
    void checkArray() {
        SimpleConvert simpleConvert = new SimpleConvert();
        String[] array = simpleConvert.toArray("first", "second", "three", "four", "five");
        assertThat(array).hasSize(5)
                .contains("second")
                .contains("first", Index.atIndex(0))
                .containsAnyOf("zero", "second", "six")
                .doesNotContain("first", Index.atIndex(1));
    }

    @Test
    void checkList() {
        SimpleConvert simpleConvert = new SimpleConvert();
        List<String> list = simpleConvert.toList("first", "second", "three", "four", "five");
        assertThat(list).hasSize(5)
                .containsExactly("first", "second", "three", "four", "five")
                .allMatch(e -> e.length() < 7)
                .element(3)
                .isEqualTo("four");
        assertThat(list).filteredOn(e -> e.length() < 5)
                .isEqualTo(List.of("four", "five"));
    }

    @Test
    void checkSet() {
        SimpleConvert simpleConvert = new SimpleConvert();
        Set<String> set = simpleConvert.toSet("first", "second", "three", "four", "five");
        assertThat(set).hasSize(5)
                .allMatch(e -> e.length() < 7)
                .contains("five")
                .containsExactlyInAnyOrderElementsOf(
                        List.of("first", "second", "three", "four", "five"))
                .doesNotContain("zero")
                .doesNotHaveDuplicates()
                .doesNotContainNull()
                .containsAll(List.of("first", "three", "four"))
                .matches(p -> p.size() == 5)
                .filteredOn(e -> e.startsWith("f"))
                .doesNotContain("second");
    }

    @Test
    void checkMap() {
        SimpleConvert simpleConvert = new SimpleConvert();
        Map<String, Integer> map = simpleConvert.toMap("first", "second", "three", "four", "five");
        assertThat(map).hasSize(5)
                .containsKeys("first", "second", "three", "four", "five")
                .containsValues(2, 4, 1, 0, 3)
                .doesNotContainKey("zero")
                .doesNotContainValue(5)
                .containsEntry("four", 3);
    }
}