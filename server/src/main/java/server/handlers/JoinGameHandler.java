package server.handlers;

import server.dataaccess.DataAccessException;
import server.service.GameService;
import requests.JoinGameRequest;
import results.ErrorResult;
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
            ErrorResult error;
            if (e.getMessage().contains("User")) {
                res.status(403);
                error = new ErrorResult("Error: Already Taken");
            } else {
                res.status(400);
                error = new ErrorResult("Error: Null teamColor");
            }
            return serializer.serializeError(error);
        }
    }


}
