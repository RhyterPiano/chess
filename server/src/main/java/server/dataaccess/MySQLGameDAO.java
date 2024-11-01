package server.dataaccess;

import chess.ChessGame;
import model.GameData;
import org.eclipse.jetty.server.Authentication;

import java.util.*;

public class MySQLGameDAO extends DAO {
    private final Random random = new Random();
    public MySQLGameDAO() {
        try {
            configureDatabase();
        } catch (DataAccessException e) {
            System.out.println("Error in creating MySQLUserDAO");
        }
    }

    @Override
    protected String[] createStatements() {
        final String[] createStatements = {
                """
                CREATE TABLE IF NOT EXISTS games (
                  `gameid` int NOT NULL,
                  `whiteusername` varchar(256),
                  `blackusername` varchar(256),
                  `gamename` varchar(256),
                  `chessgame` json,
                  INDEX(gameid)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
                """
        };
        return createStatements;
    }

    public int createGame(String gameName) {
        int gameID = Math.abs(random.nextInt());
        GameData game = new GameData(gameID, null, null, gameName, new ChessGame());
        db.addGame(game);
        return gameID;
    }

    public GameData getGame(int gameID) {
        return db.getGame(gameID);
    }

    public LinkedHashMap<Integer, GameData> listGames() {
        return db.getGames();
    }

    public void updateGame(int gameID, GameData game) {
        db.setGame(gameID, game);
    }

    @Override
    public void clear() {
        db.setGames(new LinkedHashMap<>());
    }
}
