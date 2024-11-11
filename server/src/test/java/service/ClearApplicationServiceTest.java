package service;

import org.junit.jupiter.api.*;
import server.dataaccess.DataAccessException;
import server.dataaccess.MySQLUserDAO;
import server.service.ClearApplicationService;
import server.service.GameService;
import server.service.UserService;
import requests.CreateGameRequest;
import requests.RegisterRequest;

import java.util.ArrayList;

public class ClearApplicationServiceTest {
    private ClearApplicationService clearApplicationService = new ClearApplicationService();
    private UserService userService = new UserService();
    private GameService gameService = new GameService();

    @Test
    public void testClearAllEmpty() throws DataAccessException{
        MySQLUserDAO userDAO = clearApplicationService.clearAll();
        Assertions.assertEquals(new ArrayList<>(), gameService.listGames());
        Assertions.assertEquals( null, userDAO.getUser("User"));
    }

    @Test
    public void testClearAllFull() throws DataAccessException {
        CreateGameRequest createGameRequest = new CreateGameRequest("Test Game");
        gameService.createGame(createGameRequest);
        RegisterRequest registerRequest = new RegisterRequest("User", "Password", "email@email.com");
        userService.register(registerRequest);
        MySQLUserDAO userDAO = clearApplicationService.clearAll();
        Assertions.assertEquals(new ArrayList<>(), gameService.listGames());
        Assertions.assertEquals( null, userDAO.getUser("User"));
    }

    @Test
    public void testClearAllRepeated() throws DataAccessException {
        clearApplicationService.clearAll();
        clearApplicationService.clearAll();
        clearApplicationService.clearAll();
        MySQLUserDAO userDAO = clearApplicationService.clearAll();
        Assertions.assertEquals(new ArrayList<>(), gameService.listGames());
        Assertions.assertEquals(null, userDAO.getUser("User"));
    }
}
