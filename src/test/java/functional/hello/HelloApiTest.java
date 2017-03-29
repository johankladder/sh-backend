package functional.hello;

import helpers.functional.ApiTest;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import static org.junit.Assert.assertEquals;

import spark.utils.IOUtils;

public class HelloApiTest extends ApiTest {

    @Test
    public void testHello() throws IOException {
        HttpURLConnection connection = testUrl("/hello", "GET", null);

        assertEquals(200, connection.getResponseCode());

        InputStream inputStream = connection.getInputStream();

        assertEquals("Hello Hoppers!", IOUtils.toString(inputStream));
    }
}
