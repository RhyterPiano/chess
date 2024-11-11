package server.dataaccess;

import dataaccess.DatabaseManager;
import model.AuthData;

import java.util.*;

public class MySQLAuthDAO extends DAO {

    public MySQLAuthDAO() {
        try {
            configureDatabase();
        } catch (DataAccessException e) {
            System.out.println("Error in creating MySQLUserDAO");
        }
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
        var statement = "DELETE FROM authentication WHERE authtoken=?";
        executeUpdate(statement, authToken);
    }

    public void addAuthData(AuthData authData) throws DataAccessException {
        var statement = "INSERT INTO authentication (authtoken, username) VALUES (?, ?)";
        executeUpdate(statement, authData.authToken(), authData.username());
    }

    public AuthData getAuth(String authToken) {
        AuthData authData = null;
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT authtoken, username FROM authentication WHERE authtoken=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String username = rs.getString("username");
                        authData = new AuthData(authToken, username);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(String.format("Unable to read data: %s", e.getMessage()));
        }
        return authData;
    }

    @Override
    public void clear() {
        var statement = "TRUNCATE authentication";
        try {
            executeUpdate(statement);
        }
        catch (DataAccessException e) {
            System.out.println("Error in MySQLUserDAO.clear");
        }
    }

}
