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

import java.util.Collection;
import java.util.Random;

public class GameService {
    GameDAO gameDAO = new GameDAO();

    public GameService() {

    }

    public CreateGameResult createGame(CreateGameRequest request) {
        int gameID = gameDAO.createGame(request.gameName());
        return new CreateGameResult(gameID);
    }

    public void joinGame(JoinGameRequest request, String userName) throws DataAccessException {
        gameDAO.getGame(request.gameID());
        GameData game = gameDAO.getGame(request.gameID());
        addUser(request.playerColor(), game, userName);
        gameDAO.updateGame(request.gameID(), game);
    }

    private void addUser(ChessGame.TeamColor teamColor, GameData game, String userName) throws DataAccessException {
        switch (teamColor) {
            case WHITE: {
                if (game.whiteUsername() == null) {
                    game = game.updateWhiteUser(userName);
                } else {
                    throw new DataAccessException("User Already Exists");
                }
            }
            case BLACK: {
                if (game.blackUsername() == null) {
                    game = game.updateBlackUser(userName);
                } else {
                    throw new DataAccessException("User Already Exists");
                }
            }

        }
    }

    public ListGamesResult listGames() {
        return gameDAO.listGames();
    }
}
