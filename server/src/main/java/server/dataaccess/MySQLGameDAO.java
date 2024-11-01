package server.dataaccess;

import chess.ChessGame;
import dataaccess.DatabaseManager;
import model.AuthData;
import model.GameData;
import org.eclipse.jetty.server.Authentication;
import server.service.Serializer;

import java.util.*;

public class MySQLGameDAO extends DAO {
    private final Random random = new Random();
    private final Serializer serializer = new Serializer();
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
        try {
            addGame(game);
        } catch (DataAccessException e) {
            System.out.println("Error in createGame function in MySQLGameDAO.java");
        }
        return gameID;
    }

    public GameData getGame(int gameID) {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM games WHERE gameid=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String whiteUserName = rs.getString("whiteusername");
                        String blackUserName = rs.getString("blackusername");
                        String gameName = rs.getString("gamename");
                        String json = rs.getString("chessgame");
                        ChessGame chessGame = serializer.deserializeChessGame(json);
                        return new GameData(gameID, whiteUserName, blackUserName, gameName, chessGame);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    public LinkedHashMap<Integer, GameData> listGames() {
        LinkedHashMap<Integer, GameData> games = new LinkedHashMap<>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM games";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int gameID = rs.getInt("gameid");
                        String whiteUserName = rs.getString("whiteusername");
                        String blackUserName = rs.getString("blackusername");
                        String gameName = rs.getString("gamename");
                        String json = rs.getString("chessgame");
                        ChessGame chessGame = serializer.deserializeChessGame(json);
                        games.put(gameID, new GameData(gameID, whiteUserName, blackUserName, gameName, chessGame));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(String.format("Unable to read data: %s", e.getMessage()));
        }
        return games;
    }

    public void addGame(GameData game) throws DataAccessException {
        var statement = "INSERT INTO games (gameid, whiteusername, blackusername, gamename, chessgame) VALUES (?, ?, ?, ?, ?)";
        int gameID = game.gameID();
        String whiteUsername = game.whiteUsername();
        String blackUsername = game.blackUsername();
        String gameName = game.gameName();
        String chessGame = serializer.serializeChessGame(game.game());
        executeUpdate(statement, gameID, whiteUsername, blackUsername, gameName, chessGame);
    }

    public void updateGame(int gameID, GameData game) {
        var statement = "UPDATE games SET whiteusername = ?, blackusername = ?, gamename = ?, chessgame = ? WHERE gameid = ?";
        String chessGame = serializer.serializeChessGame(game.game());
        try {
            executeUpdate(statement, game.whiteUsername(), game.blackUsername(), game.gameName(), chessGame, gameID);
        } catch (DataAccessException e) {
            System.out.println("Error in updateGame in MySQLGameDAO.java");
        }

    }

    @Override
    public void clear() {
        var statement = "TRUNCATE games";
        try {
            executeUpdate(statement);
        }
        catch (DataAccessException e) {
            System.out.println("Error in MySQLGameDAO.clear");
        }
    }
}
