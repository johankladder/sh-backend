package helpers.functional;

import functional.RestApi;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import spark.Spark;

public class ApiTest {

    private static final String HOST = "localhost";

    private static final int PORT = 4567;

    private static boolean initialized = false;

    /**
     * Setups the spark api and awaits initialisation. After it, it sets the initialized flag, so
     * it wont init again.
     * @throws InterruptedException
     */
    @BeforeClass
    public static void setup() throws InterruptedException {
        if(!initialized)
            RestApi.main(null);
            Spark.awaitInitialization();
            initialized = true;
    }

    /**
     * Tests an url and returns the http connection.
     * @param urlTest
     * @param method
     * @param requestBody
     * @return
     * @throws IOException
     */
    protected HttpURLConnection testUrl(String urlTest, String method, String requestBody)
            throws IOException {
        URL url = new URL("http://"+HOST+":" + PORT + urlTest);
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
