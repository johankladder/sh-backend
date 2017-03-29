package functional.register;

import org.junit.BeforeClass;
import org.junit.Test;
import helpers.functional.ApiDatabaseTester;
import java.io.IOException;
import java.net.HttpURLConnection;

import static org.junit.Assert.assertEquals;

/**
 * Created by johankladder on 22-3-17 (19:53)
 */
public class RegisterApiTest extends ApiDatabaseTester {

    @BeforeClass
    public static void fixture() throws IOException {
        HttpURLConnection connection = testUrl("/register", "POST", "email=j.kladder@st.hanze.nl&username=" +
                "Johan Kladder&password=password&place=Groningen&country=Holland");

        // Fixture is correctly defined!
        assertEquals(200, connection.getResponseCode());
    }

    @Test
    public void testMissingEmail() throws IOException {
        HttpURLConnection connection = testUrl("/register", "POST", "email=&username=" +
                "Johan Kladder&password=password&place=Groningen");

        assertEquals(422, connection.getResponseCode());
    }

    @Test
    public void testMissingUsername() throws IOException {
        HttpURLConnection connection = testUrl("/register", "POST", "email=j.kladder@st.hanze.nl" +
                "&username=&password=password&place=Groningen");

        assertEquals(422, connection.getResponseCode());
    }

    @Test
    public void testMissingPassword() throws IOException {
        HttpURLConnection connection = testUrl("/register", "POST", "email=j.kladder@st.hanze.nl" +
                "&username=johankladder&password=&place=Groningen");

        assertEquals(422, connection.getResponseCode());
    }

    @Test
    public void testDuplicateUser() throws IOException {
        HttpURLConnection connection = testUrl("/register", "POST", "email=j.kladder@st.hanze.nl&username=" +
                "Johan Kladder&password=password&place=Groningen");

        assertEquals(422, connection.getResponseCode());
    }

}
