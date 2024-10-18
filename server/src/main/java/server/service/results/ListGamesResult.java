package server.service.results;

import server.dataaccess.GameDAO;

import java.util.Collection;

public record ListGamesResult(Collection<GameDAO> gameData) {
}
