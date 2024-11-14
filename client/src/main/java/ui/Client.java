package ui;

import chess.ChessGame;
import model.GameData;
import requests.*;
import results.*;
import ui.network.ServerFacade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Client {
    ServerFacade serverFacade;
    private boolean loggedIn;
    private HashMap<Integer, GameData> listOfGames;

    public Client() {
        serverFacade = new ServerFacade("http://localhost:8080");
        loggedIn = false;
    }

    public void run() {

    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            if (loggedIn) {
                return switch (cmd) {
                    case "help" -> help();
                    case "logout" -> logout();
                    case "create" -> createGame(params);
                    case "list" -> listGames();
                    case "play" -> playGame(params);
                    case "observe" -> observeGame(params);
                    default -> help();
                };
            } else {
                return switch (cmd) {
                    case "help" -> help();
                    case "quit" -> "quit";
                    case "login" -> login(params);
                    case "register" -> register(params);
                    default -> help();
                };
            }

        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    private String observeGame(String... params) {
        return "not implemented";
    }

    private String playGame(String... params) {
        int gameNumber = Integer.parseInt(params[0]);
        int gameID = listOfGames.get(gameNumber).gameID();
        String teamColor = params[1];
        ChessGame.TeamColor color;
        switch(teamColor) {
            case "white" -> color = ChessGame.TeamColor.WHITE;
            case "black" -> color = ChessGame.TeamColor.BLACK;
            default -> {
                return "Invalid color, please try again\n";
            }

        }
        try {
            JoinGameRequest request = new JoinGameRequest(color, gameID);
            serverFacade.joinGame(request);
            return String.format("Congradulations! You are now in game %d playing as the %s color", gameNumber, color);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private String listGames() {
        try {
            listOfGames = new HashMap<>();
            ListGamesResult result = serverFacade.listGames();
            StringBuilder stringBuilder = new StringBuilder();
            int i = 1;
            for (GameData gameData : result.games()) {
                stringBuilder.append(String.format("%d. %s\n", i, gameData.gameName()));
                listOfGames.put(i, gameData);
                i++;
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private String createGame(String... params) {
        try {
            CreateGameRequest request = new CreateGameRequest(params[0]);
            serverFacade.createGame(request);
            return String.format("Successfully created game '%s.'", request.gameName());
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private String logout() {
        try {
            serverFacade.logout();
            loggedIn = false;
            return "Successfully logged out!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String help() {
        if(loggedIn) {
            return """
                    -help
                    -logout
                    -create <game>
                    -list
                    -play <id> [WHITE|BLACK]
                    -observe <id>
                    """;
        }
        return """
                -help
                -quit
                -login <username> <password>
                -register <username> <password> <email>
                """;
    }

    public String login(String... params) {
       try {
           LoginRequest request = new LoginRequest(params[0], params[1]);
           serverFacade.login(request);
           loggedIn = true;
           return String.format("Logged in as %s.", request.username());
       } catch (Exception e) {
           return String.format("Failed to log in. %s\n", e.getMessage());
       }
    }

    public String register(String... params) {
        try {
            RegisterRequest request = new RegisterRequest(params[0], params[1], params[2]);
            serverFacade.register(request);
            loggedIn = true;
            return String.format("Registered user %s, you are now logged in!", request.username());
        } catch (Exception e) {
            return String.format("Falled to register. %s", e.getMessage());
        }
    }




}
