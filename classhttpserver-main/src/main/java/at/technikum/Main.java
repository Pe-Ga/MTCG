package at.technikum;

import at.technikum.application.endpoints.UsersGetEndpoint;
import at.technikum.application.endpoints.UsersPostEndpoint;
import at.technikum.application.router.*;
import at.technikum.application.util.Pair;
import at.technikum.card.ElementType;
import at.technikum.game.Game;
import at.technikum.http.HttpServer;
import at.technikum.card.Card;
import at.technikum.card.MonsterType;
import at.technikum.player.Player;

import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void mainOld(String[] args) {


        System.out.println("Hello World");

        Card testcard = new Card(MonsterType.Goblin, ElementType.Fire, 20);
        System.out.println(testcard.toString());

        Card test1 = new Card(MonsterType.Elve, ElementType.Normal, 67);
        System.out.println(test1.toString());

        List<Card> deck1 = new LinkedList<Card>();
        deck1.add(new Card(MonsterType.Spell,ElementType.Fire, 1));
        deck1.add(new Card(MonsterType.Goblin,ElementType.Water, 1));
        deck1.add(new Card(MonsterType.Spell,ElementType.Normal, 1));
        deck1.add(new Card(MonsterType.Knight,ElementType.Fire, 1));

        List<Card> deck2 = new LinkedList<Card>();
        deck2.add(new Card(MonsterType.Spell,ElementType.Fire, 60));
        deck2.add(new Card(MonsterType.Goblin,ElementType.Water, 10));
        deck2.add(new Card(MonsterType.Spell,ElementType.Normal, 40));
        deck2.add(new Card(MonsterType.Knight,ElementType.Fire, 80));

        Player player1 = new Player("Peter", deck1, null);
        Player player2 = new Player("Ingo", deck2,null);

        Game game1 = new Game(player1,player2);

        System.out.println("Card won:");
        Card card_test = game1.foo(deck1,deck2);
        System.out.println(card_test.toString());








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

    public static void main(String[] args) {


        Router router1 = new Router();

        RouteIdentifier routeIdentifier;

        routeIdentifier = new RouteIdentifier("/users","GET");
        router1.registerRoute(new Pair<>(routeIdentifier, new UsersGetEndpoint()));

        routeIdentifier = new RouteIdentifier("/users","POST");
        router1.registerRoute(new Pair<>(routeIdentifier, new UsersPostEndpoint()));







        HttpServer server1 = new HttpServer(router1);
        server1.start();

    }


}


