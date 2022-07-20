package ru.job4j.serialization.json;

import java.util.Arrays;

public class Auto {
    private final boolean move;
    private final int age;
    private final String colour;
    private final Number number;
    private final String[] characteristic;

    public Auto(boolean move, int age, String colour, Number number, String[] characteristic) {
        this.move = move;
        this.age = age;
        this.colour = colour;
        this.number = number;
        this.characteristic = characteristic;
    }

    @Override
    public String toString() {
        return "Auto{"
                + "move=" + move
                + ", age=" + age
                + ", colour='" + colour + '\''
                + ", number=" + number
                + ", characteristic=" + Arrays.toString(characteristic)
                + '}';
    }
}
