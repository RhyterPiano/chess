package server.handlers;


import server.dataaccess.MySQLAuthDAO;
import server.service.Serializer;
import server.service.UserService;
import server.service.results.ErrorResult;

public abstract class Handlers {
    protected static MySQLAuthDAO authDAO = new MySQLAuthDAO();
    protected static UserService userService = new UserService();
    protected static Serializer serializer = new Serializer();

    Handlers () {

    }

    ErrorResult checkAuth(String authToken) {
        if (authDAO.getAuth(authToken) == null) {
            return new ErrorResult("Error: unauthorized");
        }
        return null;
    }
}
