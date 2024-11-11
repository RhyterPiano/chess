package service;

import org.junit.jupiter.api.*;
import server.dataaccess.DataAccessException;
import server.service.UserService;
import requests.LoginRequest;
import requests.RegisterRequest;
import results.LoginResult;
import results.RegisterResult;



public class UserServiceTest {
    private UserService userService = new UserService();
    private static RegisterRequest goodRegisterRequest = new RegisterRequest("New User",
            "password", "emai@example.com");
    private static RegisterRequest badRegisterRequest = new RegisterRequest(null, null, null);
    private static LoginRequest goodLoginRequest = new LoginRequest("New User", "password");

    @BeforeEach
    public void setUp() {
        userService.clear();
    }

    @Test
    public void testRegisterSuccess() throws DataAccessException {
        RegisterResult result = userService.register(goodRegisterRequest);
        RegisterResult expected = new RegisterResult("New User", result.authToken());
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testRegisterFail() {
        Assertions.assertThrows(DataAccessException.class, () -> {
            userService.register(badRegisterRequest);
        });
    }

    @Test
    public void testLoginSuccess() throws DataAccessException {
        userService.register(goodRegisterRequest);
        LoginResult result = userService.login(goodLoginRequest);
        LoginResult expected = new LoginResult("New User", result.authToken());
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testLoginFail(){
        Assertions.assertThrows(DataAccessException.class, () -> {
            userService.login(goodLoginRequest);
        });
    }


}
