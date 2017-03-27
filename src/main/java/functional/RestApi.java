package functional;

import register.RegisterAPI;

import static spark.Spark.*;

/**
 * Created by johankladder on 9-3-17 (18:09)
 */
public class RestApi {

    public static void main(String[] args) {
        port(getHerokuAssignedPort());

        get("/hello", (req, res) -> "Hello Hoppers!");

        post("/register", RegisterAPI::register);

        post("/change-password", RegisterAPI::changePassword);
    }

    private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

}
