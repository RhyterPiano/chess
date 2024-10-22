package server.handlers;

import chess.ChessGame;
import model.GameData;
import server.dataaccess.DataAccessException;
import server.service.GameService;
import server.service.requests.JoinGameRequest;
import server.service.results.ErrorResult;
import spark.Request;
import spark.Response;

public class JoinGameHandler extends Handlers {
    GameService gameService = new GameService();

    public String joinGame(Request req, Response res) {
        String authToken = req.headers("Authorization");
        if (checkAuth(authToken) != null) {
            res.status(401);
            ErrorResult error = new ErrorResult("Error: Unauthorized");
            return serializer.serializeError(error);
        }
        JoinGameRequest joinGameRequest = serializer.deserializeJoinGame(req);
        try {
            gameService.joinGame(joinGameRequest, authDAO.getAuth(authToken).username());
            return serializer.serializeEmpty();
        } catch (DataAccessException e) {
            res.status(403);
            ErrorResult error = new ErrorResult("Error: Already Taken");
            return serializer.serializeError(error);
        }
    }


}
