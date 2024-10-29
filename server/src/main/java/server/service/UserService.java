package server.service;


import model.AuthData;
import model.UserData;
import server.dataaccess.AuthDAO;
import server.dataaccess.DataAccessException;
import server.service.requests.*;
import server.dataaccess.UserDAO;
import server.service.results.*;

public class UserService {
    private UserDAO userDAO = new UserDAO();
    private AuthDAO authDAO = new AuthDAO();

    public UserService() {
    }

    public RegisterResult register(RegisterRequest request) throws DataAccessException {
        userDAO.userExists(request);
        UserData userData = new UserData(request.username(), request.password(), request.email());
        if (userData.password() == null || userData.email() == null) {
            throw new DataAccessException("Insufficient register info");
        }
        String authToken = authDAO.createAuth();
        AuthData authData = new AuthData(authToken, request.username());
        authDAO.addAuthData(authData);
        userDAO.addUser(userData);
        return new RegisterResult(authData.username(), authData.authToken());
    }

    public LoginResult login(LoginRequest request) throws DataAccessException {
        UserData userData = userDAO.getUser(request.username());
        if (userData == null) {
            throw new DataAccessException("Error: User does not exist");
        }
        if (!userData.password().equals(request.password())) {
            throw new DataAccessException("Error: Incorrect password");
        }
        String authToken = authDAO.createAuth();
        AuthData authData = new AuthData(authToken, request.username());
        authDAO.addAuthData(authData);
        return new LoginResult(authData.username(), authData.authToken());
    }

    public void logout(String authToken) throws DataAccessException{
        authDAO.removeAuth(authToken);

    }

    public void clear() {
        userDAO.clear();
        authDAO.clear();
    }
}
