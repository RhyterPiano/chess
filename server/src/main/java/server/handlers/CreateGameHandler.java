package server.handlers;

import server.service.GameService;
import server.service.requests.CreateGameRequest;
import server.service.results.CreateGameResult;
import server.service.results.ErrorResult;
import spark.Request;
import spark.Response;

public class CreateGameHandler extends Handlers {
    GameService gameService = new GameService();


    public String createGame(Request req, Response res) {
        String authToken = req.headers("Authorization");
        if (checkAuth(authToken) != null) {
            res.status(401);
            ErrorResult error = new ErrorResult("Error, unauthorized");
            return serializer.serializeError(error);
        }
        CreateGameRequest createGameRequest = serializer.deserializeCreateGame(req);
        CreateGameResult createGameResult = gameService.createGame(createGameRequest);
        return serializer.serializeCreateGame(createGameResult);
    }
}
