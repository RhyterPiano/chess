package server.service;


import model.AuthData;
import server.dataaccess.DataAccessException;
import server.service.requests.LoginRequest;
import server.service.requests.RegisterRequest;
import server.dataaccess.UserDAO;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    public AuthData register(RegisterRequest request) throws DataAccessException {
        userDAO.userExists(request);
        //create the user and add the data to the db
        //create the auth data, then return the register result
        return null;
    }

    public AuthData login(LoginRequest request) {
        return null;
    }

    public void logout(AuthData auth) {

    }
}
