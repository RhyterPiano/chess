package server.service.requests;


import model.UserData;

public record RegisterRequest(String username, String password, String email) {
    public UserData toUserData() {
        return new UserData(username, password, email);
    }
}
