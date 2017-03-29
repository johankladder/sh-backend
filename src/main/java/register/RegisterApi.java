package register;

import helpers.ShException;
import spark.Request;
import spark.Response;

public class RegisterApi {

    /**
     * Api method for registering an user.
     * @param request Request containing post information
     * @param response Default object
     * @return status 200 or 403
     */
    public static Object register(Request request, Response response) {

        try {
            new User(request);
            response.status(200);
        } catch (ShException ex) {
            response.status(422);
        }

        return response;
    }

    /**
     * Api method for changing password of user.
     * @param request Request containing post information
     * @param response Default object
     * @return status 200 or 403
     */
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
