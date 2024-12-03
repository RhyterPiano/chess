package server.websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.messages.*;
import websocket.commands.*;
import java.io.IOException;
import server.dataaccess.MySQLUserDAO;
import server.dataaccess.MySQLAuthDAO;
import server.dataaccess.MySQLGameDAO;

@WebSocket
public class WebSocketHandler {
    private final ConnectionManager connections = new ConnectionManager();
    private final MySQLUserDAO userDAO = new MySQLUserDAO();
    private final MySQLAuthDAO authDAO = new MySQLAuthDAO();
    private final MySQLGameDAO gameDAO = new MySQLGameDAO();

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

    void connect(Session session, String username, UserGameCommand command) {
        System.out.println("Hello! It is working!\n");
    }

    void makeMove() {
        ;
    }

}
