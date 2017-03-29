package helpers.fixtures;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by johankladder on 27-3-17 (14:53)
 */
public class FixturesHolder {

    private String tableName = null;

    private Fixture[] fixtures = null;

    private LinkedHashMap fixturesMap;

    private ArrayList<Fixture> fixturesList = null;

    public FixturesHolder(LinkedHashMap fixturesMap) {
        this.fixturesMap = fixturesMap;
        construct();
    }

    private void construct() {
        obtainTableName(fixturesMap);
        obtainFixtures(fixturesMap);
    }

    private void obtainTableName(LinkedHashMap fixturesMap) {
        fixturesMap.forEach((tableName, fixtureInformation) -> {
            this.tableName = (String) tableName;
        });
    }

    private void obtainFixtures(LinkedHashMap fixturesMap) {
        fixturesList = new ArrayList<>();
        if (tableName != null) {
            ArrayList extractedFixtures = (ArrayList) fixturesMap.get(tableName);
            for(Object fixture : extractedFixtures) {
                obtainFixture((LinkedHashMap) fixture);
            }
        }
    }

    private void obtainFixture(LinkedHashMap fixtureMap) {
        fixtureMap.forEach((fixtureName, fixtureData) -> {
            Fixture createdFixture = new Fixture();
            createdFixture.defineData((LinkedHashMap) fixtureData);
            fixturesList.add(createdFixture);
        });
    }

    public String getTableName() {
        return tableName;
    }

    public ArrayList<Fixture> getFixtures() {
        return fixturesList;
    }
}