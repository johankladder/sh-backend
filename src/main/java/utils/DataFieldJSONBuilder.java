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
        if(param != null) {
            dataObject.put(key, param);
        } else {
            dataObject.put(key, JSONObject.NULL);
        }
    }

    // TODO: Hardcoded key
    public String getJsonString() {
        JSONObject root = new JSONObject();
        root.put("data", dataObject);
        JSONObject base = new JSONObject();
        base.put(dataField, root);
        return base.toString();
    }
}
