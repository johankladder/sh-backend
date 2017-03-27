package helpers.fixtures;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by johankladder on 27-3-17 (14:49)
 */
public class Fixture {

    private HashMap<String, Object> fixtureData = null;

    public void defineData(LinkedHashMap fixtureData) {
        obtainData(fixtureData);
    }

    private void obtainData(LinkedHashMap fixtureData) {
        this.fixtureData = new HashMap<>();

        fixtureData.forEach((column, columnValue) -> {
            this.fixtureData.put((String) column, columnValue);
        });
    }

    public HashMap<String, Object> getColumns() {
        return fixtureData;
    }

    public Object getColumnData(String key) {
        return fixtureData.get(key);
    }
}
