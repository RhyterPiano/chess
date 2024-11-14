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
    private String authToken;


    public ServerFacade(String url) {
        serverURL = url;
    }

    public void register(RegisterRequest request) throws Exception {
        var path = "/user";
        RegisterResult result = makeRequest("POST", path, request, RegisterResult.class, false);
        authToken = result.authToken();
    }

    public void login(LoginRequest request) throws Exception {
        var path = "/session";
        LoginResult result = makeRequest("POST", path, request, LoginResult.class, false);
        authToken = result.authToken();
    }

    public void logout() throws Exception {
        var path = "/session";
        makeRequest("DELETE", path, null, null, true);
        authToken = null;
    }

    public void createGame(CreateGameRequest request) throws Exception {
        var path = "/game";
        makeRequest("POST", path, request, null, true);
    }

    public ListGamesResult listGames() throws Exception {
        var path = "/game";
        return makeRequest("GET", path, null, ListGamesResult.class, true);
    }

    public void joinGame(JoinGameRequest request) throws Exception {
        var path = "/game";
        makeRequest("PUT", path, request, null, true);
    }

    public void clearAll() throws Exception {
        var path = "/db";
        makeRequest("DELETE", path, null, null, false);
    }








    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass, Boolean needsAuth) throws Exception {
        try {
            URL url = (new URI(serverURL + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);
            if (needsAuth) {
                http.addRequestProperty("Authorization", authToken);
            }

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
