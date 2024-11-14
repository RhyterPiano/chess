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
    }

}
