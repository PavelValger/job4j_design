package ru.job4j.generics;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class RoleStoreTest {
    @Test
    public void whenAddAndFindThenUsernameIsPetr() {
        var store = new RoleStore();
        store.add(new Role("1", "Actor"));
        var result = store.findById("1");
        assertThat(result.getRoleName(), is("Actor"));
    }

    @Test
    public void whenAddAndFindThenUserIsNull() {
        var store = new RoleStore();
        store.add(new Role("1", "Actor"));
        var result = store.findById("10");
        assertNull(result);
    }

    @Test
    public void whenAddDuplicateAndFindUsernameIsPetr() {
        var store = new RoleStore();
        store.add(new Role("1", "Actor"));
        store.add(new Role("1", "Director"));
        var result = store.findById("1");
        assertThat(result.getRoleName(), is("Actor"));
    }

    @Test
    public void whenReplaceThenUsernameIsMaxim() {
        var store = new RoleStore();
        store.add(new Role("1", "Actor"));
        store.replace("1", new Role("1", "Director"));
        var result = store.findById("1");
        assertThat(result.getRoleName(), is("Director"));
    }

    @Test
    public void whenNoReplaceUserThenNoChangeUsername() {
        var store = new RoleStore();
        store.add(new Role("1", "Actor"));
        store.replace("10", new Role("10", "Director"));
        var result = store.findById("1");
        assertThat(result.getRoleName(), is("Actor"));
    }

    @Test
    public void whenDeleteUserThenUserIsNull() {
        var store = new RoleStore();
        store.add(new Role("1", "Actor"));
        store.delete("1");
        var result = store.findById("1");
        assertNull(result);
    }

    @Test
    public void whenNoDeleteUserThenUsernameIsPetr() {
        var store = new RoleStore();
        store.add(new Role("1", "Actor"));
        store.delete("10");
        var result = store.findById("1");
        assertThat(result.getRoleName(), is("Actor"));
    }
}