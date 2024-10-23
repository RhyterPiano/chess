package service;

import org.eclipse.jetty.server.Authentication;
import org.junit.jupiter.api.*;
import server.dataaccess.DataAccessException;
import server.dataaccess.UserDAO;
import server.service.ClearApplicationService;
import server.service.GameService;
import server.service.UserService;
import server.service.requests.CreateGameRequest;
import server.service.requests.RegisterRequest;

import java.util.ArrayList;
import java.util.HashMap;

public class ClearApplicationServiceTest {
    private ClearApplicationService clearApplicationService = new ClearApplicationService();
    private UserService userService = new UserService();
    private GameService gameService = new GameService();

    @Test
    public void testClearAllEmpty() {
        UserDAO userDAO = clearApplicationService.clearAll();
        Assertions.assertEquals(new ArrayList<>(), gameService.listGames());
        Assertions.assertEquals( new HashMap<>(), userDAO.getUsers());
    }

    @Test
    public void testClearAllFull() throws DataAccessException {
        CreateGameRequest createGameRequest = new CreateGameRequest("Test Game");
        gameService.createGame(createGameRequest);
        RegisterRequest registerRequest = new RegisterRequest("User", "Password", "email@email.com");
        userService.register(registerRequest);
        UserDAO userDAO = clearApplicationService.clearAll();
        Assertions.assertEquals(new ArrayList<>(), gameService.listGames());
        Assertions.assertEquals( new HashMap<>(), userDAO.getUsers());
    }

    @Test
    public void testClearAllRepeated() {
        clearApplicationService.clearAll();
        clearApplicationService.clearAll();
        clearApplicationService.clearAll();
        UserDAO userDAO = clearApplicationService.clearAll();
        Assertions.assertEquals(new ArrayList<>(), gameService.listGames());
        Assertions.assertEquals( new HashMap<>(), userDAO.getUsers());
    }
}
