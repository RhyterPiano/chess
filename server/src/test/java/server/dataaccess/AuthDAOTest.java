package server.dataaccess;

import model.AuthData;
import model.UserData;
import org.eclipse.jetty.server.Authentication;
import org.junit.jupiter.api.*;
import server.service.requests.RegisterRequest;
import server.dataaccess.*;

public class AuthDAOTest {
    private AuthDAO authDAO;
    private AuthDAO expected;
    private DB db = new DB();

    @BeforeEach
    public void setUp() {
        authDAO = new AuthDAO();
        expected = new AuthDAO();
    }

    @Test
    public void testClear() {
        String token = authDAO.createAuth();
        AuthData authData = new AuthData(token, "username");
        authDAO.addAuthData(authData);
        authDAO.clear();

        Assertions.assertEquals(db.getAuthData(), authDAO.getAuthData());
    }

//    @Test
//    public void

}
