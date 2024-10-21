package server.handlers;

public class ClearApplicationHandler extends Handlers {

    public ClearApplicationHandler() {

    }

    public void clearAll() {
        userDAO.clear();
        gameDAO.clear();
        authDAO.clear();
    }
}
