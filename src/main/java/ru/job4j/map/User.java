package ru.job4j.map;

import java.text.SimpleDateFormat;
import java.util.*;

public class User {
    private String name;
    private int children;
    private Calendar birthday;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public User(String name, int children, Calendar birthday) {
        this.name = name;
        this.children = children;
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return children == user.children
                && Objects.equals(name, user.name) && Objects.equals(birthday, user.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, children, birthday);
    }

    @Override
    public String toString() {
        return "User{"
                + "name='" + name + '\''
                + ", children=" + children
                + ", birthday=" + format.format(birthday.getTime())
                + '}';
    }

    public static void main(String[] args) {
        Calendar birthday = new GregorianCalendar(1992, Calendar.MAY, 4);
        User pavel = new User("Pavel", 1, birthday);
        User paul = new User("Pavel", 1, birthday);
        Map<User, Object> map = new HashMap<>();
        map.put(pavel, new Object());
        map.put(paul, new Object());
        for (User user : map.keySet()) {
            Object value = map.get(user);
            System.out.println(user + " = " + value);
        }
    }
}
