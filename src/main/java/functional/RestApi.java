package functional;

import register.RegisterAPI;
import static spark.Spark.port;
import static spark.Spark.get;
import static spark.Spark.post;

public class RestApi {

    /**
     * Method for starting the rest api.
     * @param args Arguments for the api, currently unused.
     */
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
