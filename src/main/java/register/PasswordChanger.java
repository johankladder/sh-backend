package register;

import helpers.*;

import helpers.register.Password;
import spark.Request;
import utils.PasswordUtility;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;

/**
 * Created by johankladder on 20-3-17 (21:07)
 */
public class PasswordChanger extends ParamsHandler {

    @DataField(value = "users")

    @ParamField(required = true)
    private static final String OLD_PASSWORD = "old_password";

    @ParamField(required = true)
    private static final String NEW_PASSWORD = "new_password";

    @ParamField(required = true)
    private static final String EMAIL = "email";

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
            boolean status = passwordUtils.authenticate((String) paramsMap.get(OLD_PASSWORD),
                    obtainedPassword, obtainedSalt);
            if (status) {
                Password newPassword = generatePassword((String) paramsMap.get(NEW_PASSWORD));
                String json = createJsonFromParamsField(createDataMap(newPassword, (String) paramsMap.get(EMAIL)));
                System.out.println(json);
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
