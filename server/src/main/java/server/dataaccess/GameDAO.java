package server.dataaccess;

import chess.ChessGame;
import model.GameData;
import org.eclipse.jetty.server.Authentication;

import java.util.*;

public class GameDAO extends DAO {
    private Random random = new Random();
    public GameDAO() {
    }

    public void createGame(String gameName) {
        int gameID = random.nextInt();
        GameData game = new GameData(gameID, null, null, gameName, new ChessGame());
        db.addGame(game);
    }

    public HashMap<Integer, GameData> listGames() {
        return db.getGames();
    }

    public void updateGame(int gameID, GameData game) {
        // Some code to check if the game exists or not might not be a bad idea.
        db.setGame(gameID, game);
    }

    @Override
    public void clear() {
        db.setGames(new HashMap<>());
    }
}
