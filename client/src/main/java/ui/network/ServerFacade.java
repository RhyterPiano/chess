package ui.network;

import chess.ChessGame;
import com.google.gson.Gson;
import requests.*;
import results.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
public class ServerFacade {
    public final String serverURL;

    public ServerFacade(String url) {
        serverURL = url;
    }

    public void register(RegisterRequest request) throws Exception {
        var path = "/user";
        makeRequest("POST", path, request, RegisterResult.class);
    }

    public void login(LoginRequest request) throws Exception {
        var path = "/session";
        makeRequest("POST", path, request, LoginResult.class);
    }

    public void logOut() throws Exception {
        var path = "/session";
        makeRequest("DELETE", path, null, null);
    }

    public void createGame(CreateGameRequest request) throws Exception {
        var path = "/game";
        makeRequest("POST", path, request, null);
    }

    public ListGamesResult listGames() throws Exception {
        var path = "/game";
        return makeRequest("GET", path, null, ListGamesResult.class);
    }

    public void joinGame(JoinGameRequest request) throws Exception {
        var path = "/game";
        makeRequest("PUT", path, request, null);
    }








    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) throws Exception {
        try {
            URL url = (new URI(serverURL + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            createBody(request, http);
            http.connect();
            checkSuccess(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            throw ex;
        }
    }



    private void createBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null)
            http.addRequestProperty("Content-Type", "application/json");
        String reqData = new Gson().toJson(request);
        try (OutputStream reqBody = http.getOutputStream()) {
            reqBody.write(reqData.getBytes());
        } catch (IOException e) {
            throw e;
        }
    }

    private void checkSuccess(HttpURLConnection http) throws IOException {
        var status = http.getResponseCode();
        if (!(status / 100 == 2)) {
            throw new IOException("checkSuccess status not in 200s");
        }
    }

    private <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }
}
