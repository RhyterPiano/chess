package server.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import server.handlers.CreateGameHandler;
import server.service.requests.*;
import server.service.results.*;
import spark.Request;

import java.util.HashMap;

public class Serializer {

    public LoginRequest deserializeLogin(Request request) {
        return new Gson().fromJson(request.body(), LoginRequest.class);
    }

    public RegisterRequest deserializeRegister(Request request) {
        return new Gson().fromJson(request.body(), RegisterRequest.class);
    }

    public CreateGameRequest deserializeCreateGame(Request request) {
        return new Gson().fromJson(request.body(), CreateGameRequest.class);
    }

    public JoinGameRequest deserializeJoinGame(Request request) {
        return new Gson().fromJson(request.body(), JoinGameRequest.class);
    }

    public String serializeError(ErrorResult error) {
        return new Gson().toJson(error);
    }

    public String serializeLogin(LoginResult loginResult) {
        return new Gson().toJson(loginResult);
    }

    public String serializeCreateGame(CreateGameResult createGameResult) {
        return new Gson().toJson(createGameResult);
    }

    public String serializeListGames(ListGamesResult listGamesResult) {
        String result = new Gson().toJson(listGamesResult);
        return result;
    }

    public String serializeEmpty() {
        HashMap<String, Object> emptyResponse = new HashMap<>();
        return new Gson().toJson(emptyResponse);
    }
}
