package ui;

import static chess.EscapeSequences.*;
import java.util.Scanner;

public class Repl {
    private Client client;

    public void main() {
        run();
    }

    public Repl() {
        client = new Client();
    }

    public void run() {
        System.out.println("Welcome to Chess! Sign in or register to start!");
        System.out.print(client.help());

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


}
