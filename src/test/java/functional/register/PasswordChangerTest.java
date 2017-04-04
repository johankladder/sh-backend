package functional.register;

import helpers.functional.ApiDatabaseTester;
import java.io.IOException;
import java.net.HttpURLConnection;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class PasswordChangerTest extends ApiDatabaseTester {

    /**
     * Creates the fixtures for the database. These fixtures can therefore be used in the database.
     *
     * @throws IOException When URL is not valid.
     */
    @BeforeClass
    public static void fixture() throws IOException {

        // Add user in the correct way:
        HttpURLConnection connection = testUrl("/register", "POST",
                "email=j.kladder@st.hanze.nl&username=Johan Kladder"
                        + "&password=password&place=Groningen&country=Holland");

        // Fixture is correctly defined!
        assertEquals(200, connection.getResponseCode());
    }


    @Test
    public void testChangeCorrect() throws IOException {
        // Test updating password:
        HttpURLConnection connection = testUrl("/change-password",
                "POST", "email=j.kladder@st.hanze.nl&old_password="
                        + "password&new_password=password1");

        assertEquals(200, connection.getResponseCode());

        // Test if renewed password is added:
        // (Request password renewal again with new generated password from previous attempt)
        connection = testUrl("/change-password",
                "POST", "email=j.kladder@st.hanze.nl&old_password="
                        + "password1&new_password=password1");

        assertEquals(200, connection.getResponseCode());
    }

    @Test
    public void testChangeFault() throws IOException {
        HttpURLConnection connection = testUrl("/change-password", "POST",
                "email=j.kladder@st.hanze.nl&old_password="
                        + "&new_password=");

        assertEquals(403, connection.getResponseCode());
    }


}