package utils;

import helpers.DataField;

/**
 * Created by johankladder on 20-3-17 (19:46)
 */
public class DataFieldJSONBuilder {

    private String jsonString;

    public DataFieldJSONBuilder() {

    }

    public void appendDataField(String key, Object param) {
        System.out.println(key + " " + param);
    }

    public String getJsonString() {
        return jsonString;
    }
}
