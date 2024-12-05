package ui;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import requests.*;
import results.*;
import ui.network.ServerFacade;
import ui.network.WebSocketFacade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Client {
    private ServerFacade serverFacade;
    private WebSocketFacade webSocketFacade;
    private boolean loggedIn;
    private boolean inGame = false;
    private HashMap<Integer, GameData> listOfGames;
    private ChessBoard chessBoardPrinter = new ChessBoard();
    private AuthData authData;
    private ChessGame.TeamColor teamColor;
    private int gameID;
    private chess.ChessBoard board = new chess.ChessBoard();


    public Client(Repl repl) {
        serverFacade = new ServerFacade("http://localhost:8080");
        webSocketFacade = new WebSocketFacade("http://localhost:8080", repl);
        loggedIn = false;
    }

    public void run() {

    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            if (inGame) {
                return switch (cmd) {
                    case "help" -> help();
                    case "redraw" -> redrawBoard();
                    case "leave" -> leave();
                    case "move" -> makeMove(params);
                    case "resign" -> resign();
                    case "show" -> showMoves();
                    default -> String.format("Unrecognized command. Here are your options:\n%s", help());
                };
            }
            else if (loggedIn) {
                return switch (cmd) {
                    case "help" -> help();
                    case "logout" -> logout();
                    case "create" -> createGame(params);
                    case "list" -> listGames();
                    case "play" -> playGame(params);
                    case "observe" -> observeGame(params);
                    default -> String.format("Unrecognized command. Here are your options:\n%s", help());
                };
            } else {
                return switch (cmd) {
                    case "help" -> help();
                    case "quit" -> "quit";
                    case "login" -> login(params);
                    case "register" -> register(params);
                    default -> String.format("Unrecognized command. Here are your options:\n%s", help());
                };
            }

        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public String help() {
        if (inGame) {
            return """
                    -help
                    -redraw (Redraws the board)
                    -leave
                    -move <starting position, ending position>
                    -resign
                    -show <position>
                    """;
        } else if (loggedIn) {
            return """
                    -help
                    -logout
                    -create <game>
                    -list
                    -play <id> [WHITE|BLACK]
                    -observe <id> [WHITE|BLACK]
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
           String authToken = serverFacade.login(request);
           loggedIn = true;
           String username = request.username();
           this.authData = new AuthData(authToken, username);
           return String.format("Logged in as %s.\n%s\n", request.username(), help());
       } catch (Exception e) {
           String errorMessage = "";
           if(e.getMessage().contains("Index")) {
               errorMessage = String.format("Login command didn't recieve sufficient data");
           } else if (e.getMessage().contains("User")) {
               errorMessage = "User and password do not match";
           }
           return String.format("Failed to log in. %s\n", errorMessage);
       }
    }

    public String register(String... params) {
        try {
            RegisterRequest request = new RegisterRequest(params[0], params[1], params[2]);
            String authToken = serverFacade.register(request);
            loggedIn = true;
            String username = request.username();
            this.authData = new AuthData(authToken, username);
            return String.format("Registered user %s, you are now logged in!", request.username());
        } catch (Exception e) {
            String errorMessage = "";
            if(e.getMessage().contains("Taken")) {
                errorMessage = "Username already taken";
            } else if (e.getMessage().contains("Index")) {
                errorMessage = "Insufficient data given to register command";
            }
            return String.format("Falled to register. %s\n", errorMessage);
        }
    }

    private String logout() {
        try {
            serverFacade.logout();
            loggedIn = false;
            this.authData = null;
            return String.format("Successfully logged out!\n%s", help());
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private String observeGame(String... params) {
        try {
            int gameNumber = Integer.parseInt(params[0]);
            this.gameID = listOfGames.get(gameNumber).gameID();
            String teamColor = params[1];
            switch(teamColor) {
                case "white" -> this.teamColor = ChessGame.TeamColor.WHITE;
                case "black" -> this.teamColor = ChessGame.TeamColor.BLACK;
                default -> {
                    return "Unrecognized color, please try again\n";
                }
            };
            webSocketFacade.connectGame(this.authData, this.gameID);
            this.inGame = true;

            return String.format("Now observing game %d from the %s color pov", gameNumber, this.teamColor);

        } catch (Exception e) {
            return "Invalid game ID given\n";
        }
    }

    private String playGame(String... params) {
        try {
        int gameNumber = Integer.parseInt(params[0]);
        this.gameID = listOfGames.get(gameNumber).gameID();
        String teamColor = params[1];
        switch(teamColor) {
            case "white" -> this.teamColor = ChessGame.TeamColor.WHITE;
            case "black" -> this.teamColor = ChessGame.TeamColor.BLACK;
            default -> {
                return "Invalid color, please try again\n";
            }

        }
            JoinGameRequest request = new JoinGameRequest(this.teamColor, this.gameID);
            serverFacade.joinGame(request);

            webSocketFacade.connectGame(this.authData, this.gameID);
            inGame = true;

            return String.format("Congradulations! You are now in game %d playing as the %s color\n", gameNumber, this.teamColor);
        } catch (Exception e) {
            String errorMessage = "Unrecognized game id\n";
            if (e.getMessage().contains("Index")) {
                errorMessage = "Please select a game number and a team color:\n";
            } else if (e.getMessage().contains("null")) {
                errorMessage = "Game not found. Please use 'list' and provide index to refer to a game\n";
            } else if (e.getMessage().contains("Taken")) {
                errorMessage = "Position is already taken. Please choose another or create a new game\n";
            }
            return errorMessage;
        }
    }

    private String listGames() {
        try {
            listOfGames = new HashMap<>();
            ListGamesResult result = serverFacade.listGames();
            StringBuilder stringBuilder = new StringBuilder();
            int i = 1;
            for (GameData gameData : result.games()) {
                stringBuilder.append(String.format("%d. %s: ", i, gameData.gameName()));
                String whiteUsername = "none";
                String blackUsername = "none";
                if(gameData.whiteUsername() != null) {
                    whiteUsername = gameData.whiteUsername();
                }
                stringBuilder.append(String.format("White = %s, ", whiteUsername));
                if(gameData.blackUsername() != null) {
                    blackUsername = gameData.blackUsername();
                }
                stringBuilder.append(String.format("Black = %s", blackUsername));
                stringBuilder.append("\n");
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
            return String.format("Successfully created game '%s.'\n", request.gameName());
        } catch (Exception e) {
            String errorMessage = "";
            if(e.getMessage().contains("Index")) {
                errorMessage = "no game name provided\n";
            } else {
                errorMessage = "please try again\n";
            }
            return errorMessage;
        }
    }

    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

    public String redrawBoard() {
        chessBoardPrinter.printBoard(teamColor, board);
        return "";
    }

    public String leave() {
        try {
            webSocketFacade.leaveGame(authData, gameID);
            this.gameID = -1;

        } catch (IOException e) {
            return "Error when trying to leave the game:(\n";
        }

        inGame = false;
        return "Successfully left the game!\n" + help();
    }

    public String makeMove(String... params) {
        return "not implemented";
    }

    public String resign() {
        return "not implemented";
    }

    public String showMoves(String... params) {
        return "not implemented";
    }

    public void setBoard(chess.ChessBoard board) {
        this.board = board;
    }

    public void printBoard() {
        chessBoardPrinter.printBoard(teamColor, board);
    }
}
