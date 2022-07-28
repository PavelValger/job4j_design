package ru.job4j.serialization.json;

import java.util.Arrays;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "auto")
@XmlAccessorType(XmlAccessType.FIELD)
public class Auto {
    @XmlAttribute
    private boolean move;
    @XmlAttribute
    private int age;
    @XmlAttribute
    private String colour;
    private Number number;
    @XmlElementWrapper(name = "characteristices")
    @XmlElement(name = "characteristic")
    private String[] characteristic;

    public Auto() {
    }

    public Auto(boolean move, int age, String colour, Number number, String... characteristic) {
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
