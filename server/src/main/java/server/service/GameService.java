package server.service;

import chess.ChessGame;
import model.GameData;
import model.UserData;
import server.dataaccess.GameDAO;
import server.service.requests.CreateGameRequest;
import server.service.results.CreateGameResult;

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

    public void joinGame(GameData gameData) {

    }

    public Collection<GameData> listGames() {
        return null;
    }
}
