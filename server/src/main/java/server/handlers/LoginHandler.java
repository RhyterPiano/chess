package server.handlers;

import server.service.requests.*;
import server.service.results.*;
import spark.Request;
import spark.Response;
import server.service.*;

public class LoginHandler extends Handlers {
    private UserService userService = new UserService();
    private Serializer serializer = new Serializer();

    public Response loginUser(Request request, Response response) {
        LoginRequest loginRequest = serializer.serializeLogin(request);
        LoginResult loginResult = userService.login(loginRequest);
        return response;
    }
}
