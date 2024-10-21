package server.service;

import com.google.gson.Gson;
import server.service.requests.*;
import spark.Request;

public class Serializer {

    public LoginRequest serializeLogin(Request request) {
        return new Gson().fromJson(String.valueOf(request), LoginRequest.class);
    }

}
