/* Monster Trading Card Game
 *
 * Author:      Peter Gamsjäger
 *
 */


package at.technikum;

import at.technikum.application.endpoints.*;
import at.technikum.application.router.*;
import at.technikum.application.util.Pair;
import at.technikum.http.HttpServer;

import java.sql.*;

public class Main
{
    public static void main(String[] args) throws SQLException
    {

        // initialize route pairs for endpoints
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


