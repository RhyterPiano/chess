package server.service;


import model.AuthData;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;
import requests.LoginRequest;
import requests.RegisterRequest;
import results.LoginResult;
import results.RegisterResult;
import server.dataaccess.MySQLAuthDAO;
import server.dataaccess.DataAccessException;
import server.dataaccess.MySQLUserDAO;

public class UserService {
    private MySQLUserDAO userDAO = new MySQLUserDAO();
    private MySQLAuthDAO authDAO = new MySQLAuthDAO();

    public UserService() {
    }

    public RegisterResult register(RegisterRequest request) throws DataAccessException {
        try {
            userDAO.userExists(request);
        } catch (DataAccessException e) {
            throw new DataAccessException("User Already Exists");
        }
        UserData userData = new UserData(request.username(), hashPassword(request.password()), request.email());
        if (request.password() == null || userData.email() == null) {
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
        if (!BCrypt.checkpw(request.password(), userData.password())) {
            throw new DataAccessException("Error: Incorrect password");
        }
        String authToken = authDAO.createAuth();
        AuthData authData = new AuthData(authToken, request.username());
        authDAO.addAuthData(authData);
        return new LoginResult(authData.username(), authData.authToken());
    }

    public void logout(String authToken) throws DataAccessException{
        if (authDAO.getAuth(authToken) == null) {
            throw new DataAccessException("The authToken does not exist");
        }
        authDAO.removeAuth(authToken);
    }

    private String hashPassword(String clearTextPassword) {
        return BCrypt.hashpw(clearTextPassword, BCrypt.gensalt());
    }

    public void clear() {
        userDAO.clear();
        authDAO.clear();
    }
}
