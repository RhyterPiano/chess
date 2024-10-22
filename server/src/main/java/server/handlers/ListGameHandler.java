package server.handlers;

import server.service.GameService;
import server.service.results.ErrorResult;
import server.service.results.ListGamesResult;
import spark.Request;
import spark.Response;

public class ListGameHandler extends Handlers {
    GameService gameService = new GameService();

    public String listGames(Request req, Response res) {
        String authToken = req.headers("Authorization");
        if (checkAuth(authToken) != null) {
            res.status(401);
            ErrorResult error = new ErrorResult("Error, unauthorized");
            return serializer.serializeError(error);
        }
        ListGamesResult listGamesResult = gameService.listGames();

    }

}
