package at.technikum.application.endpoints;

import at.technikum.application.model.User;
import at.technikum.application.router.Route;
import at.technikum.http.Header;
import at.technikum.http.RequestContext;
import at.technikum.http.Response;

import java.util.ArrayList;
import java.util.List;

public class BattlesPostEndpoint implements Route {
    @Override
    public Response process(RequestContext requestContext) {

        final int LOBBY_TIMEOUT = 10;
        final List<User> lobbyUsers = new ArrayList<>();

        var response = new Response();
        response.setHeader(new Header());




        return response;
    }
}
