package server.websocket;

import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.commands.*;
import java.io.IOException;
import java.util.Collection;

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
    private final ConnectionManager connectionManager = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
        String username;
        try {
            username = authDAO.getAuth(command.getAuthToken()).username();
        } catch (Exception e) {
            ServerMessage errorMessage = new ServerMessage(ServerMessageType.ERROR);
            errorMessage.addErrorMessage("Error: Bad AuthToken");
            session.getRemote().sendString(serializer.toJson(errorMessage));
            return;
        }

        switch (command.getCommandType()) {
            case LEAVE -> leave();
            case RESIGN -> resign();
            case CONNECT -> connect(session, username, command);
            case MAKE_MOVE -> makeMove(session, username, command);
            default -> throw new IOException("Unrecognized command type");
        }
    }

    void leave() {

    }

    void resign() {

    }

    void connect(Session session, String username, UserGameCommand command) throws IOException {
        int gameID = command.getGameID();
        GameData chessGame = gameDAO.getGame(gameID);
        if (chessGame == null) {
            ServerMessage errorMessage = new ServerMessage(ServerMessageType.ERROR);
            errorMessage.addErrorMessage("Error, the Game ID does not exist");
            session.getRemote().sendString(serializer.toJson(errorMessage));
            return;
        }
        ServerMessage message = new ServerMessage(ServerMessageType.LOAD_GAME);
        message.setGame(chessGame);
        String finalMessage = serializer.toJson(message);
        session.getRemote().sendString(finalMessage);
        connectionManager.alertJoin(gameID, username);
        connectionManager.add(username, session, command.getGameID());
    }

    void makeMove(Session session, String username, UserGameCommand command) throws IOException {
        ChessMove move = command.getMove();
        GameData gameData = gameDAO.getGame(command.getGameID());
        ServerMessage message = validatePermissions(move, username, command, gameData);
        if (message != null) {
            session.getRemote().sendString(serializer.toJson(message));
            return;
        }

        ChessGame game = gameData.game();
        try {
            game.makeMove(move);
        } catch (InvalidMoveException e) {
            throw new IOException("Validated the move but it still failed. Check validation process!");
        }
        gameData = gameData.updateGame(game);
        gameDAO.updateGame(gameData.gameID(), gameData);

        connectionManager.alertGameUpdate(gameData.gameID(), gameData);




    }

    ServerMessage validatePermissions(ChessMove move, String username, UserGameCommand command, GameData gameData) {
        ChessGame chessGame = gameData.game();
        ChessGame.TeamColor color;
        if (gameData.whiteUsername().equals(username)) {
            color = ChessGame.TeamColor.WHITE;
        } else if (gameData.blackUsername().equals(username)) {
            color = ChessGame.TeamColor.BLACK;
        } else {
            ServerMessage errorMessage = new ServerMessage(ServerMessageType.ERROR);
            errorMessage.addErrorMessage("Error: Piece does not belong to the caller");
            return errorMessage;
        }
        if (chessGame.getTeamTurn() == color) {
            Collection<ChessMove> moves = chessGame.validMoves(move.getStartPosition());
            if (moves.contains(move)) {
                return null;
            }
        }
        ServerMessage errorMessage = new ServerMessage(ServerMessageType.ERROR);
        errorMessage.addErrorMessage("Error: Not the caller's turn");
        return errorMessage;
    }


}


