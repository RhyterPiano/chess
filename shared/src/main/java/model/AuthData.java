package model;


public record AuthData (String authToken, String username) {
    // add function to convert to request? Error importing, not sure what causes it.
}
