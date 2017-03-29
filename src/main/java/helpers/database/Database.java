package helpers.database;

import helpers.ShException;
import java.sql.*;

/**
 * Created by johankladder on 12-3-17 (20:47)
 */
public class Database {

    public static String LOCATION = "localhost";
    public static String DATABASE = "database";
    public static String USER = "shbackend";
    public static String PASSWORD = "sh-password";

    private static Connection connection;

    public enum Result {
        RESULTSET,
        NONE
    }

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


    public static void cleanUp() throws SQLException {
        connection.close();
    }
}
