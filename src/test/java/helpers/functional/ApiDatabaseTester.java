package helpers.functional;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.junit.BeforeClass;

public abstract class ApiDatabaseTester {

    private static ApiTest apiTest;

    /** Setups the Api tester and the database tester
     *  @throws ClassNotFoundException When DatabaseTester cannot be setup correctly
     *  @see ApiTest#setup()
     *  @see DatabaseTester#setup()
     */
    @BeforeClass
    public static void setup() throws ClassNotFoundException {
        apiTest = new ApiTest();
        ApiTest.setup();
        DatabaseTester.setup();
    }

    protected static HttpURLConnection testUrl(String urlTest, String method, String requestBody)
            throws IOException {
        return apiTest.testUrl(urlTest, method, requestBody);
    }

}
