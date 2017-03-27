package helpers.functional;

import org.junit.BeforeClass;
import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by johankladder on 22-3-17 (20:57)
 */
public abstract class ApiDatabaseTester {

    private static ApiTest apiTest;

    @BeforeClass
    public static void setup() throws InterruptedException, ClassNotFoundException, IOException {
        apiTest = new ApiTest();
        ApiTest.setup();
        DatabaseTester.setup();
    }

    protected HttpURLConnection testUrl(String urlTest, String method, String requestBody) throws IOException {
        return apiTest.testUrl(urlTest, method, requestBody);
    }

    protected void testDatabase(String query) {

    }

}
