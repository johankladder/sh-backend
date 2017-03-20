package register;

import helpers.CreationException;
import helpers.DataField;
import helpers.ParamField;
import helpers.ParamsHandler;
import helpers.register.Password;
import org.apache.commons.lang3.StringUtils;
import spark.Request;
import utils.PasswordUtility;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;

/**
 * Created by johankladder on 12-3-17 (18:58)
 * <p>
 * // TODO: API tests
 */
public class User extends ParamsHandler {

    @DataField
    public final static String DATAFIELD = "users";

    @ParamField
    public final static String USERNAME = "username";

    @ParamField
    public final static String PASSWORD = "password";

    @ParamField
    public final static String EMAIL = "email";

    private PasswordUtility passwordUtils = new PasswordUtility();

    public User(Request request) throws CreationException {
        super(request);
        tryPublish(this);
    }

    @Override
    protected void publish(HashMap<String, String> paramsMap) throws CreationException {
        checkUsername(paramsMap.get(USERNAME));
        checkPassword(paramsMap.get(PASSWORD));
        checkEmail(paramsMap.get(EMAIL));

        Password hashedPassword = generatePassword(paramsMap.get(PASSWORD));


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

    private Password generatePassword(String password) {

        try {
            byte[] salt = passwordUtils.generateSalt();
            byte[] pass = passwordUtils.getEncryptedPassword(password, salt);

            return new Password(salt, pass);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        }
    }

}
