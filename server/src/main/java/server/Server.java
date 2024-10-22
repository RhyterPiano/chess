package server;

import com.google.gson.Gson;
import model.UserData;
import server.dataaccess.DataAccessException;
import server.handlers.*;
import server.service.Serializer;
import server.service.results.ErrorResult;
import server.service.results.LoginResult;
import spark.*;
import server.dataaccess.DB;

import java.util.HashMap;

public class Server {
    private ClearApplicationHandler clearApplicationHandler = new ClearApplicationHandler();
    private CreateGameHandler createGameHandler = new CreateGameHandler();
    private JoinGameHandler joinGameHandler = new JoinGameHandler();
    private ListGameHandler listGameHandler = new ListGameHandler();
    private LoginHandler loginHandler = new LoginHandler();
    private LogoutHandler logoutHandler = new LogoutHandler();
    private Serializer serializer = new Serializer();

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        createRoutes();
        //This line initializes the server and can be removed once you have a functioning endpoint

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    public void createRoutes() {
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
            loginHandler.toString(); //change this to be implemented when needed
            UserData userData = new UserData("name", "pass", "email@email.com");
            return new Gson().toJson(userData);
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
            logoutHandler.toString();
            HashMap<String, Object> emptyResponse = new HashMap<>();
            return new Gson().toJson(emptyResponse);
        });
    }

    private void listGamesInit() {
        Spark.get("/game", (req, res) -> {
            listGameHandler.toString();
            HashMap<String, Object> emptyResponse = new HashMap<>();
            return new Gson().toJson(emptyResponse);
        });
    }

    private void createGameInit() {
        Spark.post("/game", (req, res) -> {
            HashMap<String, Object> emptyResponse = new HashMap<>();
            return new Gson().toJson(emptyResponse);
        });
    }

    private void joinGameInit() {
        Spark.put("/game", (req, res) -> {
            HashMap<String, Object> emptyResponse = new HashMap<>();
            return new Gson().toJson(emptyResponse);
        });
    }


}
