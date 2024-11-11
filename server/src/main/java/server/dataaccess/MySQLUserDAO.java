package server.dataaccess;

import model.UserData;
import requests.RegisterRequest;
import dataaccess.DatabaseManager;

public class MySQLUserDAO extends DAO {

    public MySQLUserDAO() {
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
            CREATE TABLE IF NOT EXISTS users (
              `username` varchar(256) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(256) NOT NULL,
              INDEX(username)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
        };
        return createStatements;
    }




    public UserData getUser(String username) throws DataAccessException {
        UserData user = null;
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT password, email FROM users WHERE username=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String password = rs.getString("password");
                        String email = rs.getString("email");
                        user = new UserData(username, password, email);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return user;
    }

    public void addUser(UserData user) throws DataAccessException {
        var statement = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        executeUpdate(statement, user.username(), user.password(), user.email());
    }

    public void userExists(RegisterRequest request) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM users WHERE username=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, request.username());
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        throw new DataAccessException("User already exists");
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void clear() {
        var statement = "TRUNCATE users";
        try {
            executeUpdate(statement);
        }
        catch (DataAccessException e) {
            System.out.println("Error in MySQLUserDAO.clear");
        }

    }
}
