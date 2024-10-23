package service;

import chess.ChessGame;
import org.eclipse.jetty.server.AsyncRequestLogWriter;
import org.eclipse.jetty.server.Authentication;
import org.junit.jupiter.api.*;
import server.dataaccess.DataAccessException;
import server.service.GameService;
import server.service.requests.CreateGameRequest;
import server.service.requests.JoinGameRequest;
import server.service.results.CreateGameResult;

import java.util.ArrayList;
import java.util.HashSet;

public class GameServiceTest {
    private GameService gameService = new GameService();
    private static CreateGameRequest createGameRequest = new CreateGameRequest("Test Game");

    @BeforeEach
    public void setUp() {
        gameService.clear();
    }

    @Test
    public void createGameSuccess() {
        CreateGameResult result = gameService.createGame(createGameRequest);
        Assertions.assertNotNull(result);
    }

    @Test
    public void createGameFail() {
        CreateGameRequest badCreateGameRequest = new CreateGameRequest(null);
        CreateGameResult result = gameService.createGame(badCreateGameRequest);
        Assertions.assertNull(result);
    }

    @Test
    public void joinGameSuccess(){
        CreateGameResult game = gameService.createGame(createGameRequest);
        int gameID = game.gameID();
        JoinGameRequest joinRequest = new JoinGameRequest(ChessGame.TeamColor.WHITE, gameID);
        Assertions.assertDoesNotThrow(() -> {
            gameService.joinGame(joinRequest, "User");
        });
    }

    @Test
    public void joinGameFail(){
        JoinGameRequest joinRequest = new JoinGameRequest(ChessGame.TeamColor.WHITE, 0);
        Assertions.assertThrows(DataAccessException.class, () -> {
            gameService.joinGame(joinRequest, "User");
        });
    }

    @Test
    public void listGamesSuccess() {
        gameService.createGame(createGameRequest);
        Assertions.assertNotNull(gameService.listGames());
    }

    @Test
    public void listGamesEmpty() {
        Assertions.assertEquals(new ArrayList<>(), gameService.listGames());
    }
}
