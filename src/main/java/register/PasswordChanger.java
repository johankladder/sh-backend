package register;

import helpers.CreationException;
import helpers.DataField;
import helpers.ParamField;
import helpers.ParamsHandler;
import helpers.ShException;
import helpers.database.Database;
import helpers.register.Password;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import spark.Request;

import utils.PasswordUtility;

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
            ResultSet set = (ResultSet) Database.execQuery("select password, salt from sh_user "
                    + "where email='" + paramsMap.get(EMAIL) + "'", Database.Result.RESULTSET);
            if (set.next()) {
                obtainedSalt = set.getBytes("salt");
                obtainedPassword = set.getBytes("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        boolean status = false;
        try {
            status = passwordUtils.authenticate((String) paramsMap.get(OLD_PASSWORD),
                    obtainedPassword, obtainedSalt);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new CreationException();
        }
        if (status) {
                    // TODO: Store new password
                    Password newPassword = generatePassword((String) paramsMap.get(NEW_PASSWORD));
                } else {
                    throw new ShException();
            }
    }


    // TODO: Duplication
    private Password generatePassword(String password) throws CreationException {
        try {
            byte[] salt = passwordUtils.generateSalt();
            byte[] pass = passwordUtils.createPassword(password, salt);

            return new Password(salt, pass);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new CreationException();
        }
    }
}
