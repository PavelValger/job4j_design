package ru.job4j.generics;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class UserStoreTest {
    @Test
    public void whenAddAndFindThenUsernameIsPetr() {
        var store = new UserStore();
        store.add(new User("1", "Petr"));
        var result = store.findById("1");
        assertThat(result.getUsername(), is("Petr"));
    }

    @Test
    public void whenAddAndFindThenUserIsNull() {
        var store = new UserStore();
        store.add(new User("1", "Petr"));
        var result = store.findById("10");
        assertNull(result);
    }

    @Test
    public void whenAddDuplicateAndFindUsernameIsPetr() {
        var store = new UserStore();
        store.add(new User("1", "Petr"));
        store.add(new User("1", "Maxim"));
        var result = store.findById("1");
        assertThat(result.getUsername(), is("Petr"));
    }

    @Test
    public void whenReplaceThenUsernameIsMaxim() {
        var store = new UserStore();
        store.add(new User("1", "Petr"));
        store.replace("1", new User("1", "Maxim"));
        var result = store.findById("1");
        assertThat(result.getUsername(), is("Maxim"));
    }

    @Test
    public void whenNoReplaceUserThenNoChangeUsername() {
        var store = new UserStore();
        store.add(new User("1", "Petr"));
        store.replace("10", new User("10", "Maxim"));
        var result = store.findById("1");
        assertThat(result.getUsername(), is("Petr"));
    }

    @Test
    public void whenDeleteUserThenUserIsNull() {
        var store = new UserStore();
        store.add(new User("1", "Petr"));
        store.delete("1");
        var result = store.findById("1");
        assertNull(result);
    }

    @Test
    public void whenNoDeleteUserThenUsernameIsPetr() {
        var store = new UserStore();
        store.add(new User("1", "Petr"));
        store.delete("10");
        var result = store.findById("1");
        assertThat(result.getUsername(), is("Petr"));
    }
}