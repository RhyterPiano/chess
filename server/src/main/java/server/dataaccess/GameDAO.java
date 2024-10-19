package server.dataaccess;

import model.GameData;
import model.UserData;
import org.eclipse.jetty.server.Authentication;

import java.util.*;

public class GameDAO extends DAO {
    //storage for Users, games, and authTokens



    public GameDAO() {
    }

    public void createGame(String gameName) {
        //generate gameID
        //create the game info and add game to the gamedata.
    }

    public Map<Integer, GameData> listGames() {
        return Map.of();
    }

    public void updateGame(int gameID) {
        //takes the gameID an updates it
    }

    @Override
    public void clear() {

    }
}
