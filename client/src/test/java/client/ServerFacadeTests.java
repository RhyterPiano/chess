package client;

import org.junit.jupiter.api.*;
import requests.*;
import results.*;
import server.Server;
import ui.network.ServerFacade;


public class ServerFacadeTests {
    private ServerFacade serverFacade = new ServerFacade("http://localhost:8080");
    private static Server server;
    private RegisterRequest registerRequest = new RegisterRequest("bob", "bob", "bob");
    private LoginRequest loginBob = new LoginRequest("bob", "bob");
    private CreateGameRequest createGameRequest = new CreateGameRequest("game1");

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
    }

    @BeforeEach
    public void prep() throws Exception {
        serverFacade.clearAll();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

    @Test
    public void testRegister() throws Exception {
        Assertions.assertDoesNotThrow(() -> serverFacade.register(registerRequest));
    }

    @Test
    public void testRegisterTwice() throws Exception {
        serverFacade.register(registerRequest);
        Assertions.assertThrows(Exception.class, () -> serverFacade.register(registerRequest));
    }

    @Test
    public void testClear() throws Exception {
        serverFacade.register(registerRequest);
        serverFacade.clearAll();

    }

    @Test
    public void testLogout() throws Exception {
        serverFacade.register(registerRequest);
        serverFacade.logout();
        Assertions.assertDoesNotThrow(() -> serverFacade.login(loginBob));
    }

    @Test
    public void testLogoutNotLoggedIn() throws Exception {
        Assertions.assertThrows(Exception.class , () -> serverFacade.logout());
    }

    @Test
    public void testLogin() throws Exception {
        serverFacade.register(registerRequest);
        serverFacade.logout();
        serverFacade.login(loginBob);
        Assertions.assertDoesNotThrow(() -> serverFacade.logout());
    }

    @Test
    public void testLoginNoRegister() throws Exception {
        Assertions.assertThrows(Exception.class, () -> serverFacade.login(loginBob));
    }

    @Test
    public void testCreateGame() throws Exception {
        serverFacade.register(registerRequest);
        CreateGameResult result = serverFacade.createGame(createGameRequest);
        Assertions.assertNotNull(result);
    }

    @Test
    public void testCreateTwoGames() throws Exception {
        serverFacade.register(registerRequest);
        serverFacade.createGame(createGameRequest);
        Assertions.assertDoesNotThrow(() -> serverFacade.createGame(createGameRequest));
    }

    @Test
    public void testListOneGame() throws Exception {
        serverFacade.register(registerRequest);
        serverFacade.createGame(createGameRequest);
        Assertions.assertNotNull(serverFacade.listGames());
    }

}
