package server.dataaccess;

import model.UserData;

import java.util.Collection;
import java.util.HashSet;

public class UserDAO implements DAO {
    private Collection<UserData> users = new HashSet<>();

    UserDAO() {
    }

    public void createUser() throws DataAccessException {
        //creates a new user
    }

    public UserData getUser(String username) throws DataAccessException{
        //get the user data
        return new UserData(username, "password", "email@email.com");
    }

    @Override
    public void clear(){
        //clears the database
    }
}
