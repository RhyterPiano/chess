package ui.network;

import chess.ChessMove;
import model.AuthData;
import websocket.messages.ServerMessage;
import websocket.commands.UserGameCommand;
import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import com.google.gson.Gson;

public class WebSocketFacade extends Endpoint {

    Session session;
    ServerMessageHandler serverMessageHandler;
    Gson serializer = new Gson();

    public WebSocketFacade(String url, ServerMessageHandler serverMessageHandler) {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/ws");
            this.serverMessageHandler = serverMessageHandler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
                    switch (serverMessage.getServerMessageType()) {
                        case NOTIFICATION -> serverMessageHandler.notify(serverMessage);
                        case LOAD_GAME -> serverMessageHandler.loadGame(serverMessage);
                        case ERROR -> serverMessageHandler.notifyError(serverMessage);
                        case null -> throw new RuntimeException("ServerMessageType null\n");
                    }
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    public void connectGame(AuthData user, int gameID) throws IOException {
        var userGameCommand = new UserGameCommand(UserGameCommand.CommandType.CONNECT,
                user.authToken(), gameID, null, null);
        this.session.getBasicRemote().sendText(serializer.toJson(userGameCommand));
    }

    public void leaveGame(AuthData user, int gameID) throws IOException {
        var userGameCommand = new UserGameCommand(UserGameCommand.CommandType.LEAVE,
                user.authToken(), gameID, null, null);
        this.session.getBasicRemote().sendText(serializer.toJson(userGameCommand));
    }

    public void resign(AuthData user, int gameID) throws IOException {
        var userGameCommand = new UserGameCommand(UserGameCommand.CommandType.RESIGN,
                user.authToken(), gameID, null, null);
        this.session.getBasicRemote().sendText(serializer.toJson(userGameCommand));
    }

    public void makeMove(AuthData user, int gameID, ChessMove move) throws IOException {
        var userGameCommand = new UserGameCommand(UserGameCommand.CommandType.MAKE_MOVE,
                user.authToken(), gameID, move, null);
        this.session.getBasicRemote().sendText(serializer.toJson(userGameCommand));
    }


}
