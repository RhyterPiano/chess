package ui;

import chess.ChessGame;
import model.GameData;
import ui.network.ServerMessageHandler;
import websocket.messages.ServerMessage;

import static chess.EscapeSequences.*;
import java.util.Scanner;

public class Repl implements ServerMessageHandler {
    private Client client;
    private ChessBoard chessBoardPrinter = new ChessBoard();

    public void main() {
        run();
    }

    public Repl() {
        client = new Client(this);
    }

    public void run() {
        System.out.println("Welcome to Chess! Sign in or register to start!");
        System.out.print(client.help());
        System.out.println();

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            String line = scanner.nextLine();

            try {
                result = client.eval(line);
                System.out.print(result);
                System.out.println();
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }


    @Override
    public void notify(ServerMessage serverMessage) {
        System.out.println(serverMessage.getMessage());
    }

    @Override
    public void loadGame(ServerMessage loadGameMessage) {
        GameData gameData = loadGameMessage.getGame();
        chess.ChessBoard board = gameData.game().getBoard();
        ChessGame.TeamColor teamColor = client.getTeamColor();
        chessBoardPrinter.printBoard(teamColor, board);

        System.out.println(client.help());
    }

    @Override
    public void notifyError(ServerMessage errorMessage) {
        System.out.println(errorMessage.getErrorMessage());
    }
}
