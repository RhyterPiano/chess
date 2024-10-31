package server.dataaccess;

import model.AuthData;
import server.service.requests.LogoutRequest;

import java.util.*;

public class MySQLAuthDAO extends DAO {

    public MySQLAuthDAO() {
    }

    @Override
    protected String[] createStatements() {
        final String[] createStatements = {
                """
                CREATE TABLE IF NOT EXISTS authentication (
                  `authtoken` varchar(256) NOT NULL,
                  `username` varchar(256) NOT NULL,
                  INDEX(authtoken)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
                """
        };
        return createStatements;
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
