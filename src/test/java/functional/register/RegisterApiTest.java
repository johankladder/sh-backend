package functional.register;

import org.junit.BeforeClass;
import helpers.functional.ApiDatabaseTester;

import java.io.IOException;
import java.net.HttpURLConnection;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RegisterApiTest extends ApiDatabaseTester {

    /**
     * Creates the fixtures for the database. These fixtures can therefore be used in the database.
     *
     * @throws IOException When URL is not valid.
     */
    @BeforeClass
    public static void fixture() throws IOException {
        HttpURLConnection connection = testUrl("/register", "POST",
                "email=j.kladder@st.hanze.nl&username="
                        + "Johan Kladder&password=password&place=Groningen&country=Holland");

        // Fixture is correctly defined!
        assertEquals(200, connection.getResponseCode());
    }

    @Test
    public void testMissingEmail() throws IOException {
        HttpURLConnection connection = testUrl("/register", "POST",
                "email=&username=Johan Kladder&password=password&place=Groningen");

        assertEquals(422, connection.getResponseCode());
    }

    @Test
    public void testMissingUsername() throws IOException {
        HttpURLConnection connection = testUrl("/register", "POST",
                "email=j.kladder@st.hanze.nl"
                        + "&username=&password=password&place=Groningen");

        assertEquals(422, connection.getResponseCode());
    }

    @Test
    public void testMissingPassword() throws IOException {
        HttpURLConnection connection = testUrl("/register", "POST",
                "email=j.kladder@st.hanze.nl"
                        + "&username=johankladder&password=&place=Groningen");

        assertEquals(422, connection.getResponseCode());
    }

    @Test
    public void testDuplicateUser() throws IOException {
        HttpURLConnection connection = testUrl("/register", "POST",
                "email=j.kladder@st.hanze.nl&username="
                        + "Johan Kladder&password=password&place=Groningen");

        assertEquals(422, connection.getResponseCode());
    }

}
