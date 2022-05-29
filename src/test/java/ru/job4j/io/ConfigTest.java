package ru.job4j.io;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class ConfigTest {

    @Test
    public void whenPairWithoutComment() {
        String path = "./data/pair_without_comment.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("name"), is("Petr Arsentev"));
        assertNull(config.value("surname"));
    }

    @Test
    public void whenCoupleWithComments() {
        String path = "./data/couple_with_comments.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("2"), is("Pavel"));
        assertNull(config.value("1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenPatternViolation() {
        String path = "./data/pattern_violation.properties";
        Config config = new Config(path);
        config.load();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenPatternNoKey() {
        String path = "./data/pattern_no_key.properties";
        Config config = new Config(path);
        config.load();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenPatternNoValue() {
        String path = "./data/pattern_no_value.properties";
        Config config = new Config(path);
        config.load();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenPatternNoEquation() {
        String path = "./data/pattern_no_equation.properties";
        Config config = new Config(path);
        config.load();
    }

    @Test
    public void whenTwoEquals() {
        String path = "./data/two_equals.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("name"), is("Pavel=Valger"));
    }
}