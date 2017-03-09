import static spark.Spark.*;

/**
 * Created by johankladder on 9-3-17 (18:09)
 */
public class RestApi {

    public static void main(String[] args) {

        get("/hello", (req, res) -> "Hello Hoppers!");

    }

}
