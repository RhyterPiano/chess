package ui;

import ui.network.ServerFacade;

public class Client {
    ServerFacade serverFacade;

    public Client() {
        serverFacade = new ServerFacade("http://localhost:8080");
    }

    public void run() {

    }


}
