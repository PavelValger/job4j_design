package ru.job4j.serialization.json;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class Convert {
    public static void main(String[] args) {
        JSONObject jsonNumber = new JSONObject("{\"state\":\"x103cx\"}");
        List<String> list = new ArrayList<>();
        list.add("AT");
        list.add("4WD");
        JSONArray jsonCharacteristic = new JSONArray(list);
        final Auto auto = new Auto(true, 2, "red",
                new Number("x103cx96RUS"), "Transmission", "Drive");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("move", auto.isMove());
        jsonObject.put("age", auto.getAge());
        jsonObject.put("colour", auto.getColour());
        jsonObject.put("number", jsonNumber);
        jsonObject.put("characteristic", jsonCharacteristic);
        System.out.println(jsonObject);
        System.out.println(new JSONObject(auto));
    }
}
