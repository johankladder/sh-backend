package functional.register;

import helpers.functional.ApiDatabaseTester;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by johankladder on 27-3-17 (21:19)
 */
public class PasswordChangerTest extends ApiDatabaseTester {

    @BeforeClass
    public static void fixture() throws IOException {

        // Add user in the correct way:
        HttpURLConnection connection = testUrl("/register", "POST", "email=j.kladder@st.hanze.nl&username=" +
                "Johan Kladder&password=password&place=Groningen&country=Holland");

        // Fixture is correctly defined!
        assertEquals(200, connection.getResponseCode());
    }


    @Test
    public void testChangeCorrect() throws IOException {
        HttpURLConnection connection = testUrl("/change-password", "POST", "email=j.kladder@st.hanze.nl&old_password=" +
                "password&new_password=password1");

        assertEquals(200, connection.getResponseCode());
    }

    @Test
    public void testChangeFault() throws IOException {
        HttpURLConnection connection = testUrl("/change-password", "POST", "email=j.kladder@st.hanze.nl&old_password=" +
                "fault&new_password=password1");

        assertEquals(403, connection.getResponseCode());
    }


}