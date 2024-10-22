package server.handlers;

import server.dataaccess.AuthDAO;
import server.dataaccess.GameDAO;
import server.dataaccess.UserDAO;
import server.service.Serializer;
import server.service.UserService;

public abstract class Handlers {
    protected static UserDAO userDAO = new UserDAO();
    protected static GameDAO gameDAO = new GameDAO();
    protected static AuthDAO authDAO = new AuthDAO();
    protected static UserService userService = new UserService();
    protected static Serializer serializer = new Serializer();

    Handlers () {

    }
}
