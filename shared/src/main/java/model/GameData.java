package model;

public record GameData (int gameID, String whiteUsername, String blackUsername, String gameName, chess.ChessGame game) {
    public GameData updateWhiteUser(String name) {
        return new GameData(gameID, name, blackUsername, gameName, game);
    }

    public GameData updateBlackUser(String name) {
        return new GameData(gameID, whiteUsername, name, gameName, game);
    }

    public GameData updateGame(chess.ChessGame chessGame) {
        return new GameData(gameID, whiteUsername, blackUsername, gameName, chessGame);
    }


}
