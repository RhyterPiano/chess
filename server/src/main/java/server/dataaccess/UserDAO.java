package server.dataaccess;

import model.UserData;
import requests.RegisterRequest;

import java.util.HashMap;

public class UserDAO extends DAO {

    public UserDAO() {
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
