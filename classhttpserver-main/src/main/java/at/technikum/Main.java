package at.technikum;

import at.technikum.application.controller.RestUserController;
import at.technikum.application.repository.InMemoryUserRepository;
import at.technikum.application.router.Router;
import at.technikum.application.service.UserService;
import at.technikum.http.HttpServer;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello World");

        /*
        UserService userService = new UserService(new InMemoryUserRepository());
        RestUserController restUserController = new RestUserController(userService);

        Router router = new Router();
        restUserController.listRoutes()
                .forEach(router::registerRoute);

        HttpServer httpServer = new HttpServer(router);
        httpServer.start();
        */


        /* try {
            ServerSocket server = new ServerSocket();
        } catch (IOException exception) {
            System.err.println(exception);
        } finally {
            // Optional
            System.out.println("Finally");
        } */
    }


}
