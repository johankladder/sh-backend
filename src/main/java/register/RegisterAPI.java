package register;

import helpers.ShException;
import spark.Request;
import spark.Response;

/**
 * Created by johankladder on 12-3-17 (18:55)
 */
public class RegisterAPI {

    public static Object register(Request request, Response response) {

        try {
            new User(request);
            response.status(200);
        } catch (ShException ex) {
            response.status(422);
        }

        return response;
    }

    public static Object changePassword(Request request, Response response) {
        try {
            new PasswordChanger(request);
            response.status(200);
        } catch (ShException ex) {
            response.status(403);
        }

        return response;
    }
}
