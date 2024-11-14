package ui.network;

import chess.ChessGame;
import com.google.gson.Gson;
import requests.*;
import results.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.stream.Collectors;

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

    public CreateGameResult createGame(CreateGameRequest request) throws Exception {
        var path = "/game";
        return makeRequest("POST", path, request, CreateGameResult.class, true);
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

            if (!method.equals("GET")) {
                createBody(request, http);
            }

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
        int status = http.getResponseCode();
        if (!(status / 100 == 2)) {
            String errorMsg;
            try (InputStream errorStream = http.getErrorStream()) {
                errorMsg = new BufferedReader(new InputStreamReader(errorStream))
                        .lines().collect(Collectors.joining("\n"));
            } catch (Exception e) {
                errorMsg = "Unable to retrieve error details.";
            }
            throw new IOException("Request failed with status: " + status + "\nDetails: " + errorMsg);
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
