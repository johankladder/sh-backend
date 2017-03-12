package register;

import helpers.CreationException;
import helpers.ParamField;
import helpers.ParamsHandler;
import org.apache.commons.lang3.StringUtils;
import spark.Request;

import java.util.HashMap;

/**
 * Created by johankladder on 12-3-17 (18:58)
 */
public class User extends ParamsHandler {

    @ParamField
    public final static String USERNAME = "username";

    @ParamField
    public final static String PASSWORD = "password";

    @ParamField
    public final static String EMAIL = "email";

    User(Request request) throws CreationException {
        super(request);
        tryPublish(this);
    }

    @Override
    protected void publish(HashMap<String, String> paramsMap) throws CreationException {
        checkUsername(paramsMap.get(USERNAME));
        checkPassword(paramsMap.get(PASSWORD));
        checkEmail(paramsMap.get(EMAIL));
    }

    private void checkUsername(String username) throws CreationException {
        if(StringUtils.isEmpty(username)) {
            throw new CreationException();
        }
    }

    private void checkPassword(String password) throws CreationException {
        if(StringUtils.isEmpty(password)) {
            throw new CreationException();
        }
    }

    private void checkEmail(String email) throws CreationException {
        if(StringUtils.isEmpty(email)) {
            throw new CreationException();
        }
    }


}
