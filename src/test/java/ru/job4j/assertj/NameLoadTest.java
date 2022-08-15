package ru.job4j.assertj;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class NameLoadTest {

    @Test
    void checkEmpty() {
        NameLoad nameLoad = new NameLoad();
        assertThatThrownBy(nameLoad::getMap)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageMatching("^.+")
                .hasMessageContaining("no data");
    }

    @Test
    void checkEmptyArray() {
        NameLoad nameLoad = new NameLoad();
        assertThatThrownBy(nameLoad::parse)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("^.+")
                .hasMessageContaining("is empty");
    }

    @Test
    void checkEqualSign() {
        NameLoad nameLoad = new NameLoad();
        String name = "qwerty";
        assertThatThrownBy(() -> nameLoad.parse(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("^.+")
                .hasMessageContaining(name)
                .hasMessageContaining("=");
    }

    @Test
    void checkNoKey() {
        NameLoad nameLoad = new NameLoad();
        String name = "=qwerty";
        assertThatThrownBy(() -> nameLoad.parse(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("^.+")
                .hasMessageContaining(name)
                .hasMessageContaining("key");
    }

    @Test
    void checkNoValue() {
        NameLoad nameLoad = new NameLoad();
        String name = "qwerty=";
        assertThatThrownBy(() -> nameLoad.parse(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("^.+")
                .hasMessageContaining(name)
                .hasMessageContaining("value");
    }

    @Test
    void whenGetMapSuccess() {
        NameLoad nameLoad = new NameLoad();
        nameLoad.parse("1=qw", "2=on", "1=erty");
        Map<String, String> value = nameLoad.getMap();
        assertThat(value.get("1")).isEqualTo("qw+erty");
        assertThat(value.get("2")).isEqualTo("on");
    }
}