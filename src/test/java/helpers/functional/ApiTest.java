package helpers.functional;

import functional.RestApi;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import spark.Spark;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by johankladder on 22-3-17 (19:16)
 */
public class ApiTest {

    private static final String HOST = "localhost";

    private static boolean initialized = false;

    @BeforeClass
    public static void setup() throws InterruptedException {
        if(!initialized)
            RestApi.main(null);
            Spark.awaitInitialization();
            initialized = true;
    }

    public HttpURLConnection testUrl(String urlTest, String method, String requestBody) throws IOException {
        URL url = new URL("http://"+HOST+":4567" + urlTest);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);

        if (requestBody != null) {
            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream()) {
                os.write(requestBody.getBytes("UTF-8"));
            }
        }
        connection.connect();
        return connection;
    }

    @AfterClass
    public static void stop() {
        Spark.stop();
    }

}
