package functional.hello;

import org.junit.Test;
import helpers.functional.ApiTest;
import spark.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import static org.junit.Assert.assertEquals;

/**
 * Created by johankladder on 22-3-17 (19:45)
 */
public class HelloApiTest extends ApiTest {

    @Test
    public void testHello() throws IOException {
        HttpURLConnection connection = testUrl("/hello", "GET", null);

        assertEquals(200, connection.getResponseCode());

        InputStream inputStream = connection.getInputStream();

        assertEquals("Hello Hoppers!", IOUtils.toString(inputStream));
    }
}
