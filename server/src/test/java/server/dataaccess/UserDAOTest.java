package server.dataaccess;

import model.UserData;
import org.eclipse.jetty.server.Authentication;
import org.junit.jupiter.api.*;
import server.service.requests.RegisterRequest;
import server.dataaccess.*;

public class UserDAOTest {
    private UserDAO userDAO;
    private UserDAO expected;

    @BeforeEach
    public void setUp() {
        userDAO = new UserDAO();
        expected = new UserDAO();
    }

    @Test
    public void testClear() {
        userDAO.addUser(new UserData("username", "password", "email@example.com"));
        userDAO.clear();

        Assertions.assertEquals(new DB().getUsers(), userDAO.getUsers());
    }

    @Test
    public void testAddUser() {
        UserData user = new UserData("username", "password", "email@example.com");
        userDAO.addUser(user);

        Assertions.assertNotNull(userDAO.getUser("username"));
        Assertions.assertEquals(user, userDAO.getUser("username"));
    }

    @Test
    public void testUserExists() {
        userDAO.addUser(new UserData("username", "password", "email@example.com"));
        RegisterRequest request = new RegisterRequest("username", "password", "email@example.com");

        Assertions.assertThrows(DataAccessException.class, () -> {
            userDAO.userExists(request);
        }, "Expected exception was not thrown");
    }

    @Test
    public void testUserNotExists() {
        userDAO.addUser(new UserData("username", "password", "email@example.com"));
        RegisterRequest request = new RegisterRequest("name", "password", "test@email.com");
        Assertions.assertDoesNotThrow(() -> {
            userDAO.userExists(request);
        });
    }

    @Test
    public void testCreateUserSuccess() {
        RegisterRequest request = new RegisterRequest("username", "password", "email@example.com");
        RegisterRequest testRequest = new RegisterRequest("name", "password", "email@email.com");
        userDAO.addUser(request.toUserData());
        Assertions.assertDoesNotThrow(() -> {
            userDAO.createUser(testRequest);
        });
        expected.addUser(request.toUserData());
        expected.addUser(testRequest.toUserData());
        Assertions.assertEquals(expected.getUsers(), userDAO.getUsers());
    }

    @Test
    public void testCreateUserExists() {
        RegisterRequest request = new RegisterRequest("username", "password", "email@example.com");
        RegisterRequest testRequest = new RegisterRequest("username", "password", "email@example.com");
        userDAO.addUser(request.toUserData());
        Assertions.assertThrows(DataAccessException.class, () -> {
            userDAO.createUser(testRequest);
        }, "Username already exists");
        expected.addUser(request.toUserData());
        Assertions.assertEquals(expected.getUsers(), userDAO.getUsers());
    }
}
