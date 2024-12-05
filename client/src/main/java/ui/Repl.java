package ui;

import ui.network.ServerMessageHandler;
import websocket.messages.ServerMessage;

import static chess.EscapeSequences.*;
import java.util.Scanner;

public class Repl implements ServerMessageHandler {
    private Client client;

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
        System.out.println("%s\n\n");
    }

    @Override
    public void loadGame(ServerMessage loadGameMessage) {

    }

    @Override
    public void notifyError(ServerMessage errorMessage) {

    }
}
