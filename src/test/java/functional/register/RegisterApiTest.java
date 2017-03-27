package functional.register;

import org.junit.Before;
import org.junit.Test;
import helpers.functional.ApiDatabaseTester;
import java.io.IOException;
import java.net.HttpURLConnection;

import static org.junit.Assert.assertEquals;

/**
 * Created by johankladder on 22-3-17 (19:53)
 */
public class RegisterApiTest extends ApiDatabaseTester {

    @Before
    public void fixture() throws IOException {
        HttpURLConnection connection = testUrl("/register", "POST", "email=j.kladder@st.hanze.nl&username=" +
                "Johan Kladder&password=password&place=Groningen&country=Holland");

        assertEquals(200, connection.getResponseCode());

        // This entry is also inserted into the helpers.unit.database. No fixtures are therefore needed into the helpers.unit.database.
        // Above entry can be used to ensure testing!
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

//        assertEquals(422, connection.getResponseCode());
    }

}
