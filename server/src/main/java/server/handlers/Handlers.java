package server.handlers;


import server.dataaccess.AuthDAO;
import server.dataaccess.GameDAO;
import server.dataaccess.UserDAO;

public abstract class Handlers {
    protected static UserDAO userDAO = new UserDAO();
    protected static GameDAO gameDAO = new GameDAO();
    protected static AuthDAO authDAO = new AuthDAO();

    Handlers () {

    }
}
