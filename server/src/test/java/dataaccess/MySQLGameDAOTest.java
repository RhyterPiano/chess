package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.dataaccess.DataAccessException;
import server.dataaccess.MySQLGameDAO;

import java.util.LinkedHashMap;

public class MySQLGameDAOTest {
    MySQLGameDAO gameDAO = new MySQLGameDAO();
    ChessGame chessGame = new ChessGame();
    GameData gameData = new GameData(1, null, null, "name", chessGame);

    @BeforeEach
    public void setUp() throws DataAccessException {
        chessGame = new ChessGame();
        gameDAO.addGame(gameData);
        gameDAO.clear();
    }

    @Test
    public void testCreateGame() {
        int gameID = gameDAO.createGame("myGame");
        Assertions.assertNotNull(gameDAO.getGame(gameID));
    }

    @Test
    public void testCreateGameNull() {
        int gameID = gameDAO.createGame(null);
        Assertions.assertDoesNotThrow( () -> {
            gameDAO.getGame(gameID);
        });
    }
    
    @Test
    public void testListGames() throws DataAccessException {
        gameDAO.addGame(gameData);
        int game = gameDAO.createGame("newGame");
        LinkedHashMap<Integer, GameData> expected = new LinkedHashMap<>();
        expected.put(1, gameDAO.getGame(1));
        expected.put(game, gameDAO.getGame(game));
        Assertions.assertEquals(expected, gameDAO.listGames());
    }

    @Test
    public void testListGamesEmpty() {
        Assertions.assertEquals(new LinkedHashMap<>(), gameDAO.listGames());
    }



}
