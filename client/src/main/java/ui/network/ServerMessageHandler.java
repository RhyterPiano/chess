package ui.network;

import websocket.messages.ServerMessage;
import websocket.commands.UserGameCommand;

public interface ServerMessageHandler {
    void notify(ServerMessage serverMessage);

    void loadGame(ServerMessage loadGameMessage);

    void notifyError(ServerMessage errorMessage);
}
