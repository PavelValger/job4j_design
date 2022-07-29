package ru.job4j.serialization.json;

public class Number {
    private String state;

    public Number(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    @Override
    public String toString() {
        return "Number{"
                + "state='" + state + '\''
                + '}';
    }
}
