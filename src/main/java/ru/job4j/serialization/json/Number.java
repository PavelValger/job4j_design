package ru.job4j.serialization.json;

public class Number {
    private final String state;

    public Number(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Number{"
                + "state='" + state + '\''
                + '}';
    }
}
