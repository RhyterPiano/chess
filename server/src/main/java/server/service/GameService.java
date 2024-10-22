package server.service;

import chess.ChessGame;
import model.GameData;
import model.UserData;
import server.dataaccess.DataAccessException;
import server.dataaccess.GameDAO;
import server.service.requests.CreateGameRequest;
import server.service.requests.JoinGameRequest;
import server.service.results.CreateGameResult;
import server.service.results.ListGamesResult;

import java.util.*;

public class GameService {
    GameDAO gameDAO = new GameDAO();

    public GameService() {

    }

    public CreateGameResult createGame(CreateGameRequest request) {
        int gameID = gameDAO.createGame(request.gameName());
        return new CreateGameResult(gameID);
    }

    public void joinGame(JoinGameRequest request, String userName) throws DataAccessException {
        GameData game = gameDAO.getGame(request.gameID());
        game = addUser(request.playerColor(), game, userName);
        gameDAO.updateGame(request.gameID(), game);
    }

    private GameData addUser(ChessGame.TeamColor teamColor, GameData game, String userName) throws DataAccessException {
        switch (teamColor) {
            case WHITE: {
                if (game.whiteUsername() == null) {
                    return game.updateWhiteUser(userName);
                } else {
                    throw new DataAccessException("User Already Exists");
                }
            }
            case BLACK: {
                if (game.blackUsername() == null) {
                    return game.updateBlackUser(userName);
                } else {
                    throw new DataAccessException("User Already Exists");
                }
            }
        }
        return null;
    }

    public Collection<GameData> listGames() {
        LinkedHashMap<Integer, GameData> games = gameDAO.listGames();
        ArrayList<GameData> gameList = new ArrayList<>();
        for (GameData game : games.values()) {
            gameList.add(game.updateGame(null));
        }
        return gameList;
    }
}
