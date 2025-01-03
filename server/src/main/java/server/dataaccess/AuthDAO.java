package server.dataaccess;

import model.AuthData;

import java.util.*;

public class AuthDAO extends DAO {

    public AuthDAO() {
    }

    public String createAuth() {
        return UUID.randomUUID().toString();
    }

    public void removeAuth(String authToken) throws DataAccessException {
        AuthData authData = db.getAuth(authToken);
        if (authData == null) {
            throw new DataAccessException("Error, unauthorized");
        }
        db.removeAuth(authData);

    }

    public void addAuthData(AuthData authData) {
        db.addAuth(authData);
    }

    public HashMap<String, AuthData>  getAuthData() {
        return db.getAuthData();
    }

    public AuthData getAuth(String authToken) {
        return db.getAuth(authToken);
    }

    @Override
    public void clear() {
        db.setAuthTokens(new HashMap<>());
    }

}
