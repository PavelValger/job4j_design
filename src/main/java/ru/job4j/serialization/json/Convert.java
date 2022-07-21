package ru.job4j.serialization.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Convert {
    public static void main(String[] args) {
        final Auto auto = new Auto(true, 2, "red",
                new Number("x103cx"), new String[]{"Transmission", "Drive"});
        final Gson gson = new GsonBuilder().create();
        System.out.println(gson.toJson(auto));
        final String autoJson =
                "{"
                        + "\"move\":false,"
                        + "\"age\":15,"
                        + "\"colour\":\"green\","
                        + "\"number\":"
                        + "{"
                        + "\"state\":\"x103cx66RUS\""
                        + "},"
                        + "\"characteristic\":"
                        + "[\"AT\",\"4WD\"]"
                        + "}";
        final Auto autoMod = gson.fromJson(autoJson, Auto.class);
        System.out.println(autoMod);
    }
}
