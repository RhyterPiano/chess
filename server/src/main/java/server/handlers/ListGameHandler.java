package server.handlers;

import model.GameData;
import server.service.GameService;
import results.ErrorResult;
import results.ListGamesResult;
import spark.Request;
import spark.Response;

import java.util.Collection;

public class ListGameHandler extends Handlers {
    GameService gameService = new GameService();

    public String listGames(Request req, Response res) {
        String authToken = req.headers("Authorization");
        if (checkAuth(authToken) != null) {
            res.status(401);
            ErrorResult error = new ErrorResult("Error, unauthorized");
            return serializer.serializeError(error);
        }
        Collection<GameData> games = gameService.listGames();
        ListGamesResult listGamesResult = new ListGamesResult(games);
        return serializer.serializeListGames(listGamesResult);
    }

}
