package server.service;
import server.dataaccess.*;

public class ClearApplicationService {
    MySQLUserDAO userDAO = new MySQLUserDAO();
    GameDAO gameDAO = new GameDAO();
    MySQLAuthDAO authDAO = new MySQLAuthDAO();

    public ClearApplicationService() {

    }

    public MySQLUserDAO clearAll() {
        userDAO.clear();
        gameDAO.clear();
        authDAO.clear();
        return userDAO;
    }
}
