package server.dataaccess;

import model.*;

import java.util.HashMap;
import java.util.HashSet;

public class DB {
    //Temporarily used to store the data that I will later use.
    private HashMap<String, UserData> users = new HashMap<>();
    private HashMap<Integer, GameData> games = new HashMap<>();
    private HashSet<AuthData> authTokens = new HashSet<>();

    public HashMap<String, UserData> getUsers() {
        return users;
    }

    public HashMap<Integer, GameData> getGames() {
        return games;
    }

    public HashSet<AuthData> getAuthData() {
        return authTokens;
    }

    public void addUser(UserData user) {
        users.put(user.username(), user);
    }

    public void addGame(GameData game) {
        games.put(game.gameID(), game);
    }

    public void addAuth(AuthData authData) {
        authTokens.add(authData);
    }

    public DB() {
        //create the database with all the necessary data
    }
}
