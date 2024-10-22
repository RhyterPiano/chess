package server.dataaccess;

import model.UserData;
import org.eclipse.jetty.server.Authentication;
import server.service.requests.RegisterRequest;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class UserDAO extends DAO {

    public UserDAO() {
    }

    public void createUser(RegisterRequest request) throws DataAccessException {
        userExists(request);
        UserData user = new UserData(request.username(), request.password(), request.email());
        addUser(user);
    }

    public UserData getUser(String username){
        return db.getUser(username);
    }

    public HashMap<String, UserData> getUsers() {
        return db.getUsers();
    }

    public void addUser(UserData user) {
        db.addUser(user);
    }

    public void userExists(RegisterRequest request) throws DataAccessException {
        if (db.getUsers().containsKey(request.username())) {
            throw new DataAccessException("Error: Username already exists");
        }
    }

    @Override
    public void clear() {
        db.setUsers(new HashMap<>());
    }
}
