package server;

import com.google.gson.Gson;
import server.handlers.*;
import server.service.Serializer;
import server.websocket.WebSocketHandler;
import spark.*;

import java.util.HashMap;

public class Server {
    private ClearApplicationHandler clearApplicationHandler = new ClearApplicationHandler();
    private CreateGameHandler createGameHandler = new CreateGameHandler();
    private JoinGameHandler joinGameHandler = new JoinGameHandler();
    private ListGameHandler listGameHandler = new ListGameHandler();
    private LoginHandler loginHandler = new LoginHandler();
    private LogoutHandler logoutHandler = new LogoutHandler();
    private Serializer serializer = new Serializer();
    private final WebSocketHandler webSocketHandler = new WebSocketHandler();

    public int run(int desiredPort) {
        Spark.port(desiredPort);
        Spark.staticFiles.location("web");

        createRoutes();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    public void createRoutes() {
        Spark.webSocket("/ws", webSocketHandler);
        clearApplicationInit();
        registerInit();
        loginInit();
        logoutInit();
        listGamesInit();
        createGameInit();
        joinGameInit();
    }

    private void clearApplicationInit() {
        Spark.delete("/db", (req, res) -> {
            clearApplicationHandler.clearAll();
            res.type("application/json");;
            HashMap<String, Object> emptyResponse = new HashMap<>();
            return new Gson().toJson(emptyResponse);
        });
    }

    private void registerInit() {
        Spark.post("/user", (req, res) -> {
            res.type("application/json");
            return loginHandler.registerUser(req, res); //change this to be implemented when needed
        });
    }

    private void loginInit() {
        Spark.post("/session", (req, res) -> {
            res.type("application/json");
            return loginHandler.loginUser(req, res);
        });
    }

    private void logoutInit() {
        Spark.delete("/session", (req, res) -> {
            res.type("application/json");
            return logoutHandler.logoutUser(req, res);
        });
    }

    private void listGamesInit() {
        Spark.get("/game", (req, res) -> {
            res.type("application/json");
            return listGameHandler.listGames(req, res);
        });
    }

    private void createGameInit() {
        Spark.post("/game", (req, res) -> {
            res.type("application/json");
            return createGameHandler.createGame(req, res);
        });
    }

    private void joinGameInit() {
        Spark.put("/game", (req, res) -> {
            res.type("application/json");
            return joinGameHandler.joinGame(req, res);
        });
    }


}
