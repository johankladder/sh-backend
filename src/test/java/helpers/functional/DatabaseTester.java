package helpers.functional;

import helpers.database.Database;
import org.flywaydb.core.Flyway;


public class DatabaseTester {

    public static String LOCATION = "localhost";
    public static String DATABASE = "shbackend_test";
    public static String USER = "travis";
    public static String PASSWORD = "";
    public static String TIMEZONE = "UTC";

    /** Setups the database tester with default credentials for testing.
     * @throws ClassNotFoundException When an connection could not be made with the database.
     */
    public static void setup() throws ClassNotFoundException {

        Flyway flyway = new Flyway();

        // Point it to the helpers.unit.database
        flyway.setDataSource("jdbc:mysql://" + LOCATION + "/" + DATABASE + "?serverTimezone="
                + TIMEZONE, USER, PASSWORD);

        // Clear // TODO: Clears complete database
        flyway.clean();

        // Start the migration
        flyway.migrate();

        // Define test helpers.unit.database in the connector
        defineDatabaseConnector();
    }

    private static void defineDatabaseConnector() {
        Database.LOCATION = LOCATION;
        Database.USER = USER;
        Database.PASSWORD = PASSWORD;
        Database.DATABASE = DATABASE;
    }

}
