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

    public LoginResult loginUser(Request request, Response response) throws DataAccessException{
        LoginRequest loginRequest = serializer.deserializeLogin(request);
        try {
            return userService.login(loginRequest);
        } catch (DataAccessException e) {
            response.status(401);
            throw e;
        }
    }
}
