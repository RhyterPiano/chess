package server.service;
import server.dataaccess.*;

public class ClearApplicationService {
    MySQLUserDAO userDAO = new MySQLUserDAO();
    MySQLGameDAO gameDAO = new MySQLGameDAO();
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
