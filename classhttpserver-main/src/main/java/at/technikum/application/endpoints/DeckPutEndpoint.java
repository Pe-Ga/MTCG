package at.technikum.application.endpoints;

import at.technikum.application.router.Route;
import at.technikum.http.HttpStatus;
import at.technikum.http.RequestContext;
import at.technikum.http.Response;

public class DeckPutEndpoint implements Route {
    @Override
    public Response process(RequestContext requestContext) {
        Response r = new Response();
        r.setHttpStatus(HttpStatus.OK);
        r.setBody("This is the DECK endpoint on HTTP PUT.");
        return r;
    }
}
