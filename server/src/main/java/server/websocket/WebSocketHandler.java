package server.websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.messages.*;
import websocket.commands.*;
import java.io.IOException;
import server.dataaccess.MySQLUserDAO;
import server.dataaccess.MySQLAuthDAO;
import server.dataaccess.MySQLGameDAO;
import websocket.messages.ServerMessage;
import websocket.messages.ServerMessage.ServerMessageType;

@WebSocket
public class WebSocketHandler {
    private final ConnectionManager connections = new ConnectionManager();
    private final MySQLUserDAO userDAO = new MySQLUserDAO();
    private final MySQLAuthDAO authDAO = new MySQLAuthDAO();
    private final MySQLGameDAO gameDAO = new MySQLGameDAO();
    private final Gson serializer = new Gson();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
        String username = authDAO.getAuth(command.getAuthToken()).username();

        switch (command.getCommandType()) {
            case LEAVE -> leave();
            case RESIGN -> resign();
            case CONNECT -> connect(session, username, command);
            case MAKE_MOVE -> makeMove();
            default -> throw new IOException("Unrecognized command type");
        }
    }

    void leave() {
        ;
    }

    void resign() {
        ;
    }

    void connect(Session session, String username, UserGameCommand command) throws IOException {
        ServerMessage message = new ServerMessage(ServerMessageType.LOAD_GAME);
        int gameID = command.getGameID();
        GameData chessGame = gameDAO.getGame(gameID);
        // add the viewer to the game
        message.setMessage(chessGame);
        message.addUser("white");
        String finalMessage = serializer.toJson(message);
        session.getRemote().sendString(finalMessage);

//        ServerMessage message1 = new ServerMessage(ServerMessageType.NOTIFICATION);
//        session.getRemote().sendString(serializer.toJson(message1));
    }

    void makeMove() {
        ;
    }


}


