package server.dataaccess;

import chess.ChessGame;
import model.GameData;
import org.eclipse.jetty.server.Authentication;

import java.util.*;

public class GameDAO extends DAO {
    private final Random random = new Random();
    public GameDAO() {
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
