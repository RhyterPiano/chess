package server.service;


import model.AuthData;
import model.UserData;
import server.dataaccess.AuthDAO;
import server.dataaccess.DataAccessException;
import server.service.requests.*;
import server.dataaccess.UserDAO;
import server.service.results.*;

public class UserService {
    private UserDAO userDAO;
    private AuthDAO authDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    public AuthData register(RegisterRequest request) throws DataAccessException {
        userDAO.userExists(request);
        //create the user and add the data to the db
        //create the auth data, then return the register result
        return null;
    }

    public LoginResult login(LoginRequest request) {
        UserData userData = userDAO.getUser(request.username());
        if (userData != null) {
            String authToken = authDAO.createAuth();
            AuthData authData = new AuthData(authToken, request.username());
            authDAO.addAuthData(authData);
        }
        return null;
    }

    public void logout(AuthData auth) {

    }
}
