package server.dataaccess;

import model.AuthData;
import org.junit.jupiter.api.*;

public class MySQLAuthDAOTest {
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
