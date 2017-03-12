package register;

import helpers.CreationException;
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
        } catch (CreationException ex) {
            response.status(422);
        }

        return response;
    }
}
