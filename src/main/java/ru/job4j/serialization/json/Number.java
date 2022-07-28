package ru.job4j.serialization.json;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "number")
public class Number {
    @XmlAttribute
    private String state;

    public Number() {
    }

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
