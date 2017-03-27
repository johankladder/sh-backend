package helpers.database;

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

    public static void connect() throws ClassNotFoundException, SQLException {
        // This will load the MySQL driver, each DB has its own driver
        Class.forName("com.mysql.jdbc.Driver");
        // Setup the connection with the DB
        connection = DriverManager
                .getConnection("jdbc:mysql://" + LOCATION +"/"+ DATABASE+"?serverTimezone=UTC&"
                        + "user="+USER+"&password=" + PASSWORD);
    }

    public static ResultSet execQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();

        return statement.executeQuery(query);
    }

    public static void cleanUp() throws SQLException {
        connection.close();
    }
}
