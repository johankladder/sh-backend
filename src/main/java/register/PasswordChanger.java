package register;

import helpers.*;

import helpers.database.Database;
import helpers.register.Password;
import spark.Request;
import utils.PasswordUtility;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by johankladder on 20-3-17 (21:07)
 */
public class PasswordChanger extends ParamsHandler {

    @DataField(value = "sh_user")

    @ParamField(required = true)
    public static final String OLD_PASSWORD = "old_password";

    @ParamField(required = true)
    public static final String NEW_PASSWORD = "new_password";

    @ParamField(required = true)
    public static final String EMAIL = "email";

    private PasswordUtility passwordUtils = new PasswordUtility();

    public PasswordChanger(Request request) throws ShException {
        super(request);
        tryPublish(this);
    }


    @Override
    protected void publish(HashMap<String, Object> paramsMap) throws ShException {

        byte[] obtainedSalt = new byte[0];
        byte[] obtainedPassword = new byte[0];
        try {
            // TODO: hardcoded
            ResultSet set = (ResultSet) Database.execQuery("select password, salt from sh_user where email='"
                    + paramsMap.get(EMAIL) + "'", Database.Result.RESULTSET);
            if(set.next()) {
                obtainedSalt = set.getBytes("salt");
                obtainedPassword = set.getBytes("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            boolean status = passwordUtils.authenticate((String) paramsMap.get(OLD_PASSWORD),
                    obtainedPassword, obtainedSalt);
            if (status) {
                Password newPassword = generatePassword((String) paramsMap.get(NEW_PASSWORD));
                String json = createJsonFromParamsField(createDataMap(newPassword, (String) paramsMap.get(EMAIL)));
                System.out.println(json);
            } else {
                throw new ShException();
            }

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new ChangeException();
        }
    }

    private HashMap<String, Object> createDataMap(Password password, String email) {
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put(User.PASSWORD, password.password);
        dataMap.put(User.SALT, password.salt);
        dataMap.put(EMAIL, email);

        return dataMap;
    }


    // TODO: Duplication
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