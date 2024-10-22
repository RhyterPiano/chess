package server.handlers;

import server.dataaccess.DataAccessException;
import server.service.requests.*;
import server.service.results.*;
import spark.Request;
import spark.Response;
import server.service.*;

public class LoginHandler extends Handlers {
    private UserService userService = new UserService();
    private Serializer serializer = new Serializer();

    public String loginUser(Request request, Response response) {
        LoginRequest loginRequest = serializer.deserializeLogin(request);
        try {
            LoginResult loginResult = userService.login(loginRequest);
            return serializer.serializeLogin(loginResult);
        } catch (DataAccessException e) {
            response.status(401);
            ErrorResult error = new ErrorResult("User not found");
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
            res.status(403);
            ErrorResult error = new ErrorResult("Already Taken");
            return serializer.serializeError(error);
        }
    }
}
