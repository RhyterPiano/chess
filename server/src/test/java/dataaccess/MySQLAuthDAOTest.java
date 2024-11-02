package dataaccess;

import model.AuthData;
import org.junit.jupiter.api.*;
import server.dataaccess.DataAccessException;
import server.dataaccess.MySQLAuthDAO;

public class MySQLAuthDAOTest {
    private MySQLAuthDAO authDAO = new MySQLAuthDAO();
    String token;
    AuthData authData;

    @BeforeEach
    public void setUp() {
        token = authDAO.createAuth();
        authData = new AuthData(token, "username");
    }

    @Test
    public void testClear() throws DataAccessException {
        String token = authDAO.createAuth();
        AuthData authData = new AuthData(token, "username");
        authDAO.addAuthData(authData);
        authDAO.clear();

        Assertions.assertEquals(null, authDAO.getAuth(token));
    }

    @Test
    public void testAddAuthDataSuccess() throws DataAccessException {
        authDAO.addAuthData(authData);
        Assertions.assertEquals(authData, authDAO.getAuth(token));
    }

    @Test
    public void testAddAuthDataFail() throws DataAccessException {
        authDAO.addAuthData(authData);
        Assertions.assertThrows(DataAccessException.class, () -> {
            AuthData authData = new AuthData(null, null);
            authDAO.addAuthData(authData);
        });
    }

    @Test
    public void testRemoveAuthSuccess() throws DataAccessException {
        authDAO.addAuthData(authData);
        authDAO.removeAuth(token);
        Assertions.assertNull(authDAO.getAuth(token));
    }

    @Test
    public void testRemoveAuthFail() throws DataAccessException {
        authDAO.addAuthData(authData);
        authDAO.removeAuth(token);
        Assertions.assertDoesNotThrow(() -> {
            authDAO.removeAuth(token);
        });
    }


}
