package helpers.database.parsers;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by johankladder on 27-3-17 (18:06)
 */
public class JsonParser implements SQLParser {

    @Override
    public String parse(Object data, TYPE type) {
        JSONObject jsonObject = createJsonObject((String) data);
        return parseJsonObjectToSQL(jsonObject, type);
    }

    private JSONObject createJsonObject(String data) {
        return new JSONObject(data);
    }

    private String parseJsonObjectToSQL(JSONObject object, TYPE type) {
        String tableName = getTableName(object);
        HashMap<String, Object> data = getData(object, tableName);
        String sqlString = createSQLString(data, type, tableName);
        return sqlString;
    }

    private String getTableName(JSONObject object) {
        return object.keys().next();
    }

    private HashMap<String, Object> getData(JSONObject data, String key) {
        HashMap<String, Object> dataMap = new HashMap<>();

        JSONObject json = (JSONObject) data.get(key);
        JSONObject tableData = (JSONObject) json.get("data");

        Iterator<String> keys = tableData.keys();

        while (keys.hasNext()) {
            String dataKey = keys.next();
            dataMap.put(dataKey, tableData.get(dataKey));
        }

        return dataMap;

    }

    private String createSQLString(HashMap<String, Object> data, TYPE type, String tableName) {
        switch (type) {
            case INSERT:
                return createInsertSQL(data, tableName);
            default:
                return null;
        }
    }

    private String createInsertSQL(HashMap<String, Object> data, String tableName) {
        String prefix = "INSERT INTO " + tableName + " (";

        String values = " VALUES (";

        Iterator<Map.Entry<String, Object>> iterator = data.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            prefix += next.getKey() + ", ";
            values += next.getValue() + ", ";
        }

        // Clean up:
        prefix = StringUtils.trim(prefix);
        values = StringUtils.trim(values);
        prefix = StringUtils.removeEnd(prefix, ",");
        values = StringUtils.removeEnd(values, ",");

        // Closure:
        prefix += ")";
        values += ")";

        return prefix + values;

    }
}
