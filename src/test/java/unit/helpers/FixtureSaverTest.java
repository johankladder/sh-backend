package unit.helpers;

import helpers.fixtures.Fixture;
import helpers.fixtures.FixtureSaver;
import helpers.fixtures.FixturesHolder;
import org.junit.Before;
import org.junit.Test;


import java.io.FileNotFoundException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by johankladder on 27-3-17 (14:42)
 */
public class FixtureSaverTest {

    private static final String SINGLE_FIXTURE_TEST = "/unit/fixtures/single_fixture.yaml";
    private static final String MULTI_FIXTURE_TEST = "/unit/fixtures/multi_fixture.yaml";

    private FixtureSaver saver;

    @Before
    public void setup() {
        saver = new FixtureSaver();
    }

    @Test
    public void testSingleFixture() throws FileNotFoundException {

        FixturesHolder fixtureHolder = saver.createFixture(SINGLE_FIXTURE_TEST);

        assertEquals("table-name", fixtureHolder.getTableName());

        assertEquals(1, fixtureHolder.getFixtures().size());

        Fixture fixture = fixtureHolder.getFixtures().get(0);

        HashMap<String, Object> data = new HashMap<>();

        data.put("column1", "value1");
        data.put("column2", "value2");
        data.put("column3", "value3");

        testFixtures(fixture, 3, data);

    }

    @Test
    public void testMultipleFixtures() throws FileNotFoundException {

        FixturesHolder fixtureHolder = saver.createFixture(MULTI_FIXTURE_TEST);

        assertEquals("table-name", fixtureHolder.getTableName());

        assertEquals(2, fixtureHolder.getFixtures().size());

        HashMap<String, Object> data1 = new HashMap<>();
        HashMap<String, Object> data2 = new HashMap<>();

        data1.put("column1", "value1");
        data1.put("column2", "value2");
        data1.put("column3", "value3");

        data2.put("column1", "value2-1");
        data2.put("column2", null);
        data2.put("column3", 2);

        testFixtures(fixtureHolder.getFixtures().get(0), 3, data1);
        testFixtures(fixtureHolder.getFixtures().get(1), 2, data2);

    }

    private void testFixtures(Fixture fixture, int columns, HashMap<String, Object> data) {

        assertEquals(columns, fixture.getColumns().size());

        data.forEach((column, dataColumn) -> {
            assertEquals(dataColumn, fixture.getColumnData(column));
        });

    }

}