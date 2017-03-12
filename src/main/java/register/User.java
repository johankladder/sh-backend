package register;

import helpers.CreationException;
import helpers.ParamField;
import helpers.ParamsHandler;
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
        paramsMap.forEach((key, value) -> System.out.println(value));
    }


}
