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

    @DataField(value = "users")

    @ParamField
    public final static String USERNAME = "username";

    @ParamField
    public final static String PASSWORD = "password";

    @ParamField
    public final static String EMAIL = "email";

    public final static String SALT = "salt";


    private PasswordUtility passwordUtils = new PasswordUtility();

    public User(Request request) throws CreationException {
        super(request);
        tryPublish(this);
    }

    @Override
    protected void publish(HashMap<String, Object> paramsMap) throws CreationException {
        checkUsername((String) paramsMap.get(USERNAME));
        checkPassword((String) paramsMap.get(PASSWORD));
        checkEmail((String) paramsMap.get(EMAIL));
        Password hashedPassword = generatePassword((String) paramsMap.get(PASSWORD));
        paramsMap.put(PASSWORD, hashedPassword.password);
        paramsMap.put(SALT, hashedPassword.salt);
        String json = createJsonFromParamsField(paramsMap);
        System.out.println(json);
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
