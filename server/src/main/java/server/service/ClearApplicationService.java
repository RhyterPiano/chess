package server.service;
import server.dataaccess.*;

public class ClearApplicationService {
    MySQLUserDAO userDAO = new MySQLUserDAO();
    GameDAO gameDAO = new GameDAO();
    AuthDAO authDAO = new AuthDAO();

    public ClearApplicationService() {

    }

    public MySQLUserDAO clearAll() {
        userDAO.clear();
        gameDAO.clear();
        authDAO.clear();
        return userDAO;
    }
}
