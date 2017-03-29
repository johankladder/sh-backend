package register;

import helpers.*;
import helpers.database.Database;
import helpers.database.parsers.JsonParser;
import helpers.database.parsers.SQLParser;
import helpers.register.Password;
import org.apache.commons.lang3.StringUtils;
import spark.Request;
import utils.PasswordUtility;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by johankladder on 12-3-17 (18:58)
 * <p>
 * // TODO: API tests
 */
public class User extends ParamsHandler {

    @DataField(value = "sh_user")

    @ParamField(required = true)
    public final static String USERNAME = "username";

    @ParamField(required = true)
    public final static String PASSWORD = "password";

    @ParamField(required = true)
    public final static String EMAIL = "email";

    @ParamField(required = false)
    public final static String PLACE = "place";

    @ParamField(required = false)
    public final static String COUNTRY = "country";

    final static String SALT = "salt";

    public static JsonParser parser = new JsonParser();


    private PasswordUtility passwordUtils = new PasswordUtility();

    public User(Request request) throws ShException {
        super(request);
        tryPublish(this);
    }

    @Override
    protected void publish(HashMap<String, Object> paramsMap) throws ShException {
            checkUsername((String) paramsMap.get(USERNAME));
            checkPassword((String) paramsMap.get(PASSWORD));
            checkEmail((String) paramsMap.get(EMAIL));
            Password hashedPassword = generatePassword((String) paramsMap.get(PASSWORD));

        try {
            Database.connect();
            checkDuplication((String) paramsMap.get(EMAIL));
            PreparedStatement preparedStatement = Database.getPreparedStatement(
              "INSERT INTO sh_user (" + USERNAME + "," + EMAIL + "," + PLACE + ","
              + COUNTRY + "," + PASSWORD + "," + SALT + ") VALUES (?,?,?,?,?,?)"
            );

            preparedStatement.setString(1, (String) paramsMap.get(USERNAME));
            preparedStatement.setString(2, (String) paramsMap.get(EMAIL));
            preparedStatement.setString(3, (String) paramsMap.get(PLACE));
            preparedStatement.setString(4, (String) paramsMap.get(COUNTRY));
            preparedStatement.setBytes(5, hashedPassword.password);
            preparedStatement.setBytes(6, hashedPassword.salt);

            preparedStatement.execute();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void checkUsername(String username) throws CreationException {
        if (StringUtils.isEmpty(username)) {
            throw new CreationException();
        }
    }

    private void checkPassword(String password) throws CreationException {
        if (StringUtils.isEmpty(password)) {
            throw new CreationException();
        }
    }

    private void checkEmail(String email) throws CreationException {
        if (StringUtils.isEmpty(email)) {
            throw new CreationException();
        }
    }

    // TODO: Hardcoded SQL String
    private void checkDuplication(String email) throws SQLException, ShException {
           ResultSet result = (ResultSet)
                   Database.execQuery("SELECT * FROM sh_user WHERE email='" + email + "'", Database.Result.RESULTSET);

           if(result.next()) {
               throw new ShException();
           }

    }

    // TODO: Duplication in passwordchanger
    private Password generatePassword(String password) throws CreationException {

        try {
            byte[] salt = passwordUtils.generateSalt();
            byte[] pass = passwordUtils.getEncryptedPassword(password, salt);

            return new Password(salt, pass);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new CreationException();
        }
    }

}
