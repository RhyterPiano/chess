package server.websocket;

import java.util.concurrent.ConcurrentHashMap;

import chess.ChessMove;
import com.google.gson.Gson;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import server.Server;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;

public class ConnectionManager {
    public ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, ArrayList<String>> gameList = new ConcurrentHashMap<>();
    private Gson serializer = new Gson();

    public void add(String visitorName, Session session, int gameID) {
        var connection = new Connection(visitorName, session);
        connections.put(visitorName, connection);
        ArrayList<String> inGame = new ArrayList<>();
        if (gameList.containsKey(gameID)) {
            inGame = gameList.get(gameID);
        }
        inGame.add(visitorName);
        gameList.put(gameID, inGame);
    }

    public void remove(int gameID, String username) {
        connections.remove(username);
        if (gameList.get(gameID) !=  null) {
            for (String user : gameList.get(gameID)) {
                if (user.equals(username)) {
                    gameList.get(gameID).remove(user);
                    return;
                }
            }
        }
    }

    public void alertJoin(int gameID, String username, String color) throws IOException {
        if (gameList.get(gameID) != null) {
            for (String user : gameList.get(gameID)) {
                Connection c = connections.get(user);
                if (c.session.isOpen()) {
                    String message = String.format("%s has joined the game as %s", username, color);
                    sendNotification(c, message);
                }
            }
        }
    }

    public void alertLeaveGame(int gameID, String username) throws IOException {
        if (gameList.get(gameID) != null) {
            for (String user : gameList.get(gameID)) {
                Connection c = connections.get(user);
                if (c.session.isOpen()) {
                    String message = String.format("%s has left the game", username);
                    sendNotification(c, message);
                }
            }
        }
    }

    public void alertGameUpdate(int gameID, GameData gameData) throws IOException {
        if (gameList.get(gameID) != null) {
            for (String user : gameList.get(gameID)) {
                Connection c = connections.get(user);
                if (c.session.isOpen()) {
                    ServerMessage gameUpdate = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
                    gameUpdate.setGame(gameData);

                    c.send(serializer.toJson(gameUpdate));
                }
            }
        }
    }

    public void alertNotification(String username, int gameID, ChessMove move, String condition) throws IOException {
        if (gameList.get(gameID) != null) {
            for (String user : gameList.get(gameID)) {
                Connection c = connections.get(user);
                if (c.session.isOpen()) {
                    String message = String.format("%s has made a move! They performed the move %s", username, move.toString());
                    if (condition != null) {
                        message = message + String.format("\nThe game is now in %s!", condition);
                    }
                    sendNotification(c, message);
                }
            }
        }
    }

    public void sendAlert(String username, String otherUser, int gameID, String condition) throws IOException {
        if (gameList.get(gameID) != null) {
            for (String user : gameList.get(gameID)) {
                Connection c = connections.get(user);
                if (c.session.isOpen()) {
                    String message = String.format("%s has put %s in %s\n", username, otherUser, condition);
                    if (!condition.equals("Check!")) {
                        message = message + "The game is now over";
                    }
                    sendNotification(c, message);
                }
            }
        }
    }

    public void alertResign(String username, String winner, int gameID) throws IOException {
        if (gameList.get(gameID) != null) {
            for (String user : gameList.get(gameID)) {
                Connection c = connections.get(user);
                if (c.session.isOpen()) {
                    String message = String.format("%s has resigned, and the game is now over. %s wins!", username, winner);
                    sendNotification(c, message);
                }
            }
        }
    }

    public void sendNotification(Connection c, String message) throws IOException {
        ServerMessage notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        notification.addMessage(message);
        c.send(serializer.toJson(notification));
    }

}
