package ui;

import ui.network.ServerFacade;

import java.util.Arrays;

public class Client {
    ServerFacade serverFacade;

    public Client() {
        serverFacade = new ServerFacade("http://localhost:8080");
    }

    public void run() {

    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "help" -> help();
                case "quit" -> "quit";
                case "login" -> login();
                case "register" -> register();
                default -> help();
            };
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public String help() {
        return """
                -help
                -quit
                -login <username> <password>
                -register <username> <password> <email>
                """;
    }

    public String login(String... params) {
        return "login";
    }

    public String register(String... params) {
        return "register";
    }


}
