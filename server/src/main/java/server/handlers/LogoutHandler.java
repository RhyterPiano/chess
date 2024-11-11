package server.handlers;

import server.dataaccess.DataAccessException;
import results.ErrorResult;
import spark.Request;
import spark.Response;

public class LogoutHandler extends Handlers {

    public String logoutUser(Request req, Response res) {
        String authToken = req.headers("Authorization");
        try {
            userService.logout(authToken);
            return serializer.serializeEmpty();
        } catch (DataAccessException e) {
            res.status(401);
            ErrorResult error = new ErrorResult("Error: unauthorized");
            return serializer.serializeError(error);
        }
    }
}
