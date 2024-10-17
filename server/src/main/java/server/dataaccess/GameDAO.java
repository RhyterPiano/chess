package server.dataaccess;

import model.GameData;

import java.util.*;

public class GameDAO implements DAO {
    private Map<Integer, GameData> games = new Map<Integer, GameData>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean containsKey(Object key) {
            return false;
        }

        @Override
        public boolean containsValue(Object value) {
            return false;
        }

        @Override
        public GameData get(Object key) {
            return null;
        }

        @Override
        public GameData put(Integer key, GameData value) {
            return null;
        }

        @Override
        public GameData remove(Object key) {
            return null;
        }

        @Override
        public void putAll(Map<? extends Integer, ? extends GameData> m) {

        }

        @Override
        public void clear() {

        }

        @Override
        public Set<Integer> keySet() {
            return Set.of();
        }

        @Override
        public Collection<GameData> values() {
            return List.of();
        }

        @Override
        public Set<Entry<Integer, GameData>> entrySet() {
            return Set.of();
        }
    };

    GameDAO() {
    }

    public void createGame(String gameName) {
        //generate gameID
        //create the game info and add game to the gamedata.
    }

    public Map<Integer, GameData> listGames() {
        return games;
    }

    public void updateGame(int gameID) {
        //takes the gameID an updates it
    }

    @Override
    public void clear() {
        games = new Map<Integer, GameData>(){
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean containsKey(Object key) {
                return false;
            }

            @Override
            public boolean containsValue(Object value) {
                return false;
            }

            @Override
            public GameData get(Object key) {
                return null;
            }

            @Override
            public GameData put(Integer key, GameData value) {
                return null;
            }

            @Override
            public GameData remove(Object key) {
                return null;
            }

            @Override
            public void putAll(Map<? extends Integer, ? extends GameData> m) {

            }

            @Override
            public void clear() {

            }

            @Override
            public Set<Integer> keySet() {
                return Set.of();
            }

            @Override
            public Collection<GameData> values() {
                return List.of();
            }

            @Override
            public Set<Entry<Integer, GameData>> entrySet() {
                return Set.of();
            }
        };
    }
}
