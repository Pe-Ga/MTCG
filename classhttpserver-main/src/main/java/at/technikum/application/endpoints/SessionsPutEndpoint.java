package at.technikum.application.endpoints;

import at.technikum.application.router.Route;
import at.technikum.http.Header;
import at.technikum.http.HttpStatus;
import at.technikum.http.RequestContext;
import at.technikum.http.Response;

public class SessionsPutEndpoint implements Route {
    @Override
    public Response process(RequestContext requestContext) {
        //404 no further tests
        var response = new Response();
        response.setHeader(new Header());

        String usr = "simon-mtcgToken";

        if(true) {
            response.getHeader().setName("Content-Type");
            response.getHeader().setValue("text/plain; charset=utf-8");
            response.setHttpStatus(HttpStatus.CREATED);
            response.setBody("User successfully created");
        }

        if(false) {
            response.getHeader().setName("Content-Type");
            response.getHeader().setValue("application/json; charset=utf-8");
            response.setHttpStatus(HttpStatus.UNAUTHORIZED);
            response.setBody("Invalid username/password provided\n" + usr);
        }

        return response;
    }
}
