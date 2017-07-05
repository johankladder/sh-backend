package helpers.database;

import helpers.ShException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    public static String LOCATION = "localhost";
    public static String DATABASE = "shbackend";
    public static String USER = "root";
    public static String PASSWORD = "";

    private static Connection connection;

    public enum Result {
        RESULTSET,
        NONE
    }

    /**
     * Creates an new connection in this database model. After this, the connection
     * can be used to create statements with.
     * @throws ClassNotFoundException Class for name not found
     * @throws SQLException When database credentials are not correct
     */
    public static void connect() throws ClassNotFoundException, SQLException {
        // This will load the MySQL driver, each DB has its own driver
        Class.forName("com.mysql.jdbc.Driver");
        // Setup the connection with the DB
        connection = DriverManager
                .getConnection("jdbc:mysql://" + LOCATION +"/"+ DATABASE+"?serverTimezone=UTC&"
                        + "user="+USER+"&password=" + PASSWORD);
    }

    public static PreparedStatement getPreparedStatement(String sql) throws SQLException {
      return connection.prepareStatement(sql);
    }


    /**
     * Execs an hardcoded mysql query wrapped in an String object. The enum is used to either
     * return the result of the query or to return the status of execution.
     * @param query The sql query
     * @param result Enum containing query information
     * @return Result (boolean or result set)
     * @throws SQLException When query is not correct.
     * @throws ShException When given result info is not valid.
     */
    public static Object execQuery(String query, Result result) throws SQLException, ShException {
        switch (result) {
            case NONE:
                PreparedStatement statement = connection.prepareStatement(query);
                return statement.execute(query);
            case RESULTSET:
                Statement statement1 = connection.createStatement();
                return statement1.executeQuery(query);
            default:
                throw new ShException();
        }
    }

}
