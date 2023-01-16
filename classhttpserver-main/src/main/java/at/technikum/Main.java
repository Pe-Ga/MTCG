package at.technikum;

import at.technikum.application.controller.RestUserController;
import at.technikum.application.repository.InMemoryUserRepository;
import at.technikum.application.router.Router;
import at.technikum.application.service.UserService;
import at.technikum.card.ElementType;
import at.technikum.http.HttpServer;
import at.technikum.card.Card;
import at.technikum.card.MonsterType;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello World");

        Card testcard = new Card(MonsterType.Goblin, ElementType.Fire, 20);
        System.out.println(testcard.toString());

        Card test1 = new Card(MonsterType.Elve, ElementType.Normal, 67);
        System.out.println(test1.toString());

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
