package server.service;
import server.dataaccess.*;

public class ClearApplicationService {
    UserDAO userDAO = new UserDAO();
    GameDAO gameDAO = new GameDAO();
    AuthDAO authDAO = new AuthDAO();

    public ClearApplicationService() {

    }

    public UserDAO clearAll() {
        userDAO.clear();
        gameDAO.clear();
        authDAO.clear();
        return userDAO;
    }
}
