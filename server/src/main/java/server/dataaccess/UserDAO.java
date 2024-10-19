package server.dataaccess;

import model.UserData;
import org.eclipse.jetty.server.Authentication;
import server.service.requests.RegisterRequest;

import java.util.Collection;
import java.util.HashSet;

public class UserDAO extends DAO {
    private Collection<UserData> users = new HashSet<>();

    public UserDAO() {
    }

    public void createUser() throws DataAccessException {
        //creates a new user
    }

    public UserData getUser(String username) throws DataAccessException{
        //get the user data
        return new UserData(username, "password", "email@email.com");
    }

    public void userExists(RegisterRequest request) throws DataAccessException {
        UserData user = new UserData(request.username(), request.password(), request.email());
        if (!users.contains(user)) {
            throw new DataAccessException("Username already exists");
        }
    }

    @Override
    public void clear(){
        //clears the database
    }
}
