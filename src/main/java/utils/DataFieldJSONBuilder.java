package utils;

import org.json.JSONObject;

/**
 * Created by johankladder on 20-3-17 (19:46)
 */
public class DataFieldJSONBuilder {

    private JSONObject dataObject;
    private String dataField;

    public DataFieldJSONBuilder(String datafield) {
        dataObject = new JSONObject();
        this.dataField = datafield;
    }

    public void appendDataField(String key, Object param) {
        dataObject.put(key, param);
    }

    public String getJsonString() {
        JSONObject root = new JSONObject();
        root.append(dataField, dataObject);
        return root.toString();
    }
}
