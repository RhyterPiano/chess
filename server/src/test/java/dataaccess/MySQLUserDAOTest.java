package dataaccess;

import model.UserData;
import org.junit.jupiter.api.*;
import server.dataaccess.DataAccessException;
import server.dataaccess.MySQLUserDAO;
import server.dataaccess.UserDAO;
import requests.RegisterRequest;

public class MySQLUserDAOTest {
    private MySQLUserDAO userDAO = new MySQLUserDAO();
    private UserDAO expected;

    @BeforeEach
    public void setUp() {
        userDAO.clear();
    }

    @Test
    public void testClear() throws DataAccessException {
        userDAO.addUser(new UserData("username", "password", "email@example.com"));
        userDAO.clear();
        Assertions.assertEquals(null, userDAO.getUser("username"));
    }

    @Test
    public void testAddUser() throws DataAccessException  {
        UserData user = new UserData("username", "password", "email@example.com");
        userDAO.addUser(user);
        Assertions.assertNotNull(userDAO.getUser("username"));
        Assertions.assertEquals(user, userDAO.getUser("username"));
    }

    @Test
    public void testAddUserFail() throws DataAccessException  {
        UserData user = new UserData("username", "password", "email@example.com");
        UserData user2 = new UserData("username", null, "email@example.com");
        userDAO.addUser(user);
        Assertions.assertThrows(DataAccessException.class, () -> {
            userDAO.addUser(user2);
        });

    }

    @Test
    public void testUserExists() throws DataAccessException {
        userDAO.addUser(new UserData("username", "password", "email@example.com"));
        RegisterRequest request = new RegisterRequest("username", "password", "email@example.com");
        Assertions.assertThrows(DataAccessException.class, () -> {
            userDAO.userExists(request);
        }, "Expected exception was not thrown");
    }

    @Test
    public void testUserNotExists() throws DataAccessException {
        userDAO.addUser(new UserData("username", "password", "email@example.com"));
        RegisterRequest request = new RegisterRequest("name", "password", "test@email.com");
        Assertions.assertDoesNotThrow(() -> {
            userDAO.userExists(request);
        });
    }

    @Test
    public void testGetUserSuccess() throws DataAccessException {
        UserData user = new UserData("username", "password", "email@example.com");
        userDAO.addUser(user);
        Assertions.assertEquals(user, userDAO.getUser(user.username()));
    }

    @Test
    public void testGetUserFail() throws DataAccessException {
        UserData user = new UserData("username", "password", "email@example.com");
        userDAO.addUser(user);
        Assertions.assertEquals(null, userDAO.getUser("bob"));
    }

}
