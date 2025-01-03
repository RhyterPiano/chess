package server.handlers;

import requests.LoginRequest;
import requests.RegisterRequest;
import results.ErrorResult;
import results.LoginResult;
import results.RegisterResult;
import server.dataaccess.DataAccessException;
import spark.Request;
import spark.Response;

public class LoginHandler extends Handlers {

    public String loginUser(Request request, Response response) {
        LoginRequest loginRequest = serializer.deserializeLogin(request);
        try {
            LoginResult loginResult = userService.login(loginRequest);
            return serializer.serializeLogin(loginResult);
        } catch (DataAccessException e) {
            response.status(401);
            ErrorResult error = new ErrorResult("Error: User not Found");
            return serializer.serializeError(error);
        }
    }

    public String registerUser(Request req, Response res) {
        RegisterRequest registerRequest = serializer.deserializeRegister(req);
        try {
            RegisterResult registerResult = userService.register(registerRequest);
            LoginResult result = new LoginResult(registerResult.username(), registerResult.authToken());
            return serializer.serializeLogin(result);
        } catch (DataAccessException e) {
            ErrorResult error;
            if (e.getMessage().contains("info")) {
                res.status(400);
                error = new ErrorResult("Error: Insufficient Data Provided");
            } else {
                res.status(403);
                error = new ErrorResult("Error: Already Taken");
            }
            return serializer.serializeError(error);
        }
    }
}
