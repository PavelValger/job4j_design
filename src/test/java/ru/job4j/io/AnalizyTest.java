package ru.job4j.io;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class AnalizyTest {

    @Test
    public void whenServerWasNotWorking() {
        Analizy analizy = new Analizy();
        String target = "unavailable.csv";
        analizy.unavailable("server.log", target);
        String rsl = analizy.recite(target);
        assertThat(rsl, is("10:57:01;10:59:01;"
                + "11:01:02;11:02:02;"));
    }

    @Test
    public void whenServerWasWorking() {
        Analizy analizy = new Analizy();
        String target = "available.csv";
        analizy.unavailable("serverOn.log", target);
        String rsl = analizy.recite(target);
        assertThat(rsl, is(""));
    }

    @Test
    public void whenServerWasCrashed() {
        Analizy analizy = new Analizy();
        String target = "crashed.csv";
        analizy.unavailable("serverOff.log", target);
        String rsl = analizy.recite(target);
        assertThat(rsl, is("10:57:01-"));
    }
}