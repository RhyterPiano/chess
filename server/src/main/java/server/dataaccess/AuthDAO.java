package server.dataaccess;

import model.AuthData;

import java.util.Collection;
import java.util.HashSet;

public class AuthDAO extends DAO {
    private Collection<AuthData> authDataCollection = new HashSet<>();

    public AuthDAO() {
    }

    public void createAuth() {
        //create a new auth token
    }

    public AuthData getAuth(String authToken) {
        //find the authorization of an auth token.
        return null;
    }

    public void deleteAuth(AuthData authData) {
        //remove the auth data from the collection
    }

    @Override
    public void clear() {
    }
}
