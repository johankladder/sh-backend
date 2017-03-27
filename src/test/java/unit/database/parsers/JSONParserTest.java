package unit.database.parsers;

import functional.register.RegisterApiTest;
import helpers.database.parsers.JsonParser;
import helpers.database.parsers.SQLParser;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import spark.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertTrue;

/**
 * Created by johankladder on 27-3-17 (16:56)
 */
public class JSONParserTest {

    private static final String DATABASE_INSERT_JSON = "/unit/database/database_insert_json.json";

    private String insertJSON = null;

    private SQLParser sqlParser = null;

    @Before
    public void setup() throws IOException {
        InputStream resource = RegisterApiTest.class.getResourceAsStream(DATABASE_INSERT_JSON);
        String jsonTxt = IOUtils.toString(resource);
        insertJSON = new JSONObject(jsonTxt).toString();
        sqlParser = new JsonParser();
    }

    @Test
    public void testSqlFromJSON() {

        String expectedInsertSQL = sqlParser.parse(insertJSON, SQLParser.TYPE.INSERT);
        String[] columns = new String[]{
                "column1",
                "column2",
                "column3"
        };

        String[] values = new String[]{
                "value1",
                "value2",
                "value3"
        };

        String[] foundColumns = StringUtils.split(extractEntries(expectedInsertSQL)[0], ",");
        String[] foundValues = StringUtils.split(extractEntries(expectedInsertSQL)[1], ",");

        testEntry(columns, foundColumns);
        testEntry(values, foundValues);
    }

    private void testEntry(String[] expected, String[] actual) {

        for (String entry : expected) {
            boolean found = false;
            for (String actuals : actual) {
                if (entry.equals(StringUtils.trim(actuals))) {
                    found = true;
                }
            }
            assertTrue(found);
        }
    }

    private String[] extractEntries(String sql) {
        String[] entries = StringUtils.substringsBetween(sql, "(", ")");
        return entries;
    }

}
