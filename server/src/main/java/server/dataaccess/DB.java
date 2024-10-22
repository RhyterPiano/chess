package server.dataaccess;

import model.*;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class DB {
    //Temporarily used to store the data that I will later use.
    private HashMap<String, UserData> users = new HashMap<>();
    private LinkedHashMap<Integer, GameData> games = new LinkedHashMap<>();
    private HashMap<String, AuthData> authTokens = new HashMap<String, AuthData>();

    public HashMap<String, UserData> getUsers() {
        return users;
    }

    public UserData getUser(String username) {
        return users.get(username);
    }

    public LinkedHashMap<Integer, GameData> getGames() {
        return games;
    }

    public HashMap<String, AuthData> getAuthData() {
        return authTokens;
    }

    public void addUser(UserData user) {
        users.put(user.username(), user);
    }

    public void addGame(GameData game) {
        games.put(game.gameID(), game);
    }

    public GameData getGame(int gameID) {
        return games.get(gameID);
    }

    public void setGame(int gameID, GameData game) {
        games.put(gameID, game);
    }

    public void addAuth(AuthData authData) {
        authTokens.put(authData.authToken(), authData);
    }

    public AuthData getAuth(String authToken) {
        return authTokens.get(authToken);
    }

    public void removeAuth(AuthData authData) {
        authTokens.remove(authData.authToken());
    }

    public void setUsers(HashMap<String, UserData> users) {
        this.users = users;
    }

    public void setGames(LinkedHashMap<Integer, GameData> games) {
        this.games = games;
    }

    public void setAuthTokens(HashMap<String, AuthData> authTokens) {
        this.authTokens = authTokens;
    }

    public DB() {
        //create the database with all the necessary data
    }
}
