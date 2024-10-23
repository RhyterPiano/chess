package service;

import org.eclipse.jetty.server.Authentication;
import org.junit.jupiter.api.*;
import server.dataaccess.DataAccessException;
import server.service.UserService;
import server.service.requests.LoginRequest;
import server.service.requests.RegisterRequest;
import server.service.results.LoginResult;
import server.service.results.RegisterResult;

public class UserServiceTest {
    private UserService userService = new UserService();
    private static RegisterRequest goodRegisterRequest = new RegisterRequest("New User",
            "password", "emai@example.com");
    private static RegisterRequest badRegisterRequest = new RegisterRequest(null, null, null);
    private static LoginRequest goodLoginRequest = new LoginRequest("New User", "password");


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
