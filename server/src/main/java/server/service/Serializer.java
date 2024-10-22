package server.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import server.service.requests.*;
import server.service.results.ErrorResult;
import server.service.results.LoginResult;
import spark.Request;

public class Serializer {

    public LoginRequest deserializeLogin(Request request) {
        return new Gson().fromJson(request.body(), LoginRequest.class);
    }

    public String serializeError(ErrorResult error) {
        return new Gson().toJson(error);
    }

    public String serializeLogin(LoginResult loginResult) {
        return new Gson().toJson(loginResult);
    }

}
