package server;

import server.handlers.*;
import spark.*;
import server.dataaccess.DB;

public class Server {
    private ClearApplicationHandler clearApplicationHandler = new ClearApplicationHandler();
    private CreateGameHandler createGameHandler = new CreateGameHandler();
    private JoinGameHandler joinGameHandler = new JoinGameHandler();
    private ListGameHandler listGameHandler = new ListGameHandler();
    private LoginHandler loginHandler = new LoginHandler();
    private LogoutHandler logoutHandler = new LogoutHandler();

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
            return 400;
        });
    }

    private void registerInit() {
        Spark.post("/user", (req, res) -> {
            return 400;
        });
    }

    private void loginInit() {
        Spark.post("/session", (req, res) -> {
            return 400;
        });
    }

    private void logoutInit() {
        Spark.delete("/session", (req, res) -> {
            return 400;
        });
    }

    private void listGamesInit() {
        Spark.get("/game", (req, res) -> {
            return 400;
        });
    }

    private void createGameInit() {
        Spark.post("/game", (req, res) -> {
            return 400;
        });
    }

    private void joinGameInit() {
        Spark.put("/game", (req, res) -> {
            return 400;
        });
    }


}
