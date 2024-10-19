package server.handlers;

public class ClearApplicationHandler extends Handlers {

    public ClearApplicationHandler() {
        userDAO.clear();
        gameDAO.clear();
        authDAO.clear();
    }
}
