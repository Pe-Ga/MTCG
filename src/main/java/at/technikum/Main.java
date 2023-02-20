package at.technikum;

import at.technikum.application.endpoints.*;
import at.technikum.application.router.*;
import at.technikum.application.util.Pair;
import at.technikum.application.model.card.ElementType;
import at.technikum.game.Game;
import at.technikum.http.HttpServer;
import at.technikum.application.model.card.Card;
import at.technikum.application.model.card.MonsterType;
import at.technikum.player.Player;

import java.sql.*;

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

    public static void main(String[] args) throws SQLException {


        var router = new Router();

        RouteIdentifier routeIdentifier;

        routeIdentifier = new RouteIdentifier("/users","GET");
        router.registerRoute(new Pair<>(routeIdentifier, new UsersGetEndpoint()));

        routeIdentifier = new RouteIdentifier("/users","POST");
        router.registerRoute(new Pair<>(routeIdentifier, new UsersPostEndpoint()));

        routeIdentifier = new RouteIdentifier("/users", "PUT");
        router.registerRoute(new Pair<>(routeIdentifier, new UsersPutEndpoint()));

        routeIdentifier = new RouteIdentifier("/sessions", "POST");
        router.registerRoute(new Pair<>(routeIdentifier, new SessionsPostEndpoint()));

        routeIdentifier = new RouteIdentifier("/packages", "POST");
        router.registerRoute(new Pair<>(routeIdentifier, new PackagesPostEndpoint()));

        routeIdentifier = new RouteIdentifier("/transactions", "POST");
        router.registerRoute(new Pair<>(routeIdentifier, new TransactionsPostEndpoint()));

        routeIdentifier = new RouteIdentifier("/cards", "GET");
        router.registerRoute(new Pair<>(routeIdentifier, new CardsGetEndpoint()));

        routeIdentifier = new RouteIdentifier("/deck", "GET");
        router.registerRoute(new Pair<>(routeIdentifier, new DeckGetEndpoint()));

        routeIdentifier = new RouteIdentifier("/deck", "PUT");
        router.registerRoute(new Pair<>(routeIdentifier, new DeckPutEndpoint()));

        routeIdentifier = new RouteIdentifier("/stats", "GET");
        router.registerRoute(new Pair<>(routeIdentifier, new StatsGetEndpoint()));

        routeIdentifier = new RouteIdentifier("/scoreboard", "GET");
        router.registerRoute(new Pair<>(routeIdentifier, new ScoreboardGetEndpoints()));

        routeIdentifier = new RouteIdentifier("/battles", "POST");
        router.registerRoute(new Pair<>(routeIdentifier, new BattlesPostEndpoint()));

        routeIdentifier = new RouteIdentifier("/tradings", "GET");
        router.registerRoute(new Pair<>(routeIdentifier, new TradingsGetEndpoint()));

        routeIdentifier = new RouteIdentifier("/tradings", "POST");
        router.registerRoute(new Pair<>(routeIdentifier, new TradingsPostEndpoint()));

        routeIdentifier = new RouteIdentifier("/tradings", "DELETE");
        router.registerRoute(new Pair<>(routeIdentifier, new TradingsDeleteEndpoint()));
        
        var httpServer = new HttpServer(router);
        httpServer.start();

    }


}


