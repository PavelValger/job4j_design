package ru.job4j.serialization.json;

import java.util.Arrays;

public class Auto {
    private boolean move;
    private int age;
    private String colour;
    private Number number;
    private String[] characteristic;

    public Auto(boolean move, int age, String colour, Number number, String... characteristic) {
        this.move = move;
        this.age = age;
        this.colour = colour;
        this.number = number;
        this.characteristic = characteristic;
    }

    public boolean isMove() {
        return move;
    }

    public int getAge() {
        return age;
    }

    public String getColour() {
        return colour;
    }

    public Number getNumber() {
        return number;
    }

    public String[] getCharacteristic() {
        return characteristic;
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
