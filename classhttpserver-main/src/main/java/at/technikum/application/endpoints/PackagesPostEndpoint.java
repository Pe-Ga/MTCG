package at.technikum.application.endpoints;

import at.technikum.application.router.Route;
import at.technikum.http.Header;
import at.technikum.http.HttpStatus;
import at.technikum.http.RequestContext;
import at.technikum.http.Response;

public class PackagesPostEndpoint implements Route {
    @Override
    public Response process(RequestContext requestContext) {

        var response = new Response();
        response.setHeader(new Header());

        if(true) {
            response.getHeader().setName("Content-Type");
            response.getHeader().setValue("text/plain; charset=utf-8");
            response.setHttpStatus(HttpStatus.CREATED);
            response.setBody("Package and cards successfully created");
        }

        if(true) {
            response.getHeader().setName("Content-Type");
            response.getHeader().setValue("text/plain; charset=utf-8");
            response.setHttpStatus(HttpStatus.UNAUTHORIZED);
            response.setBody("Access token is missing or invalid");
        }

        if(true) {
            response.getHeader().setName("Content-Type");
            response.getHeader().setValue("text/plain; charset=utf-8");
            response.setHttpStatus(HttpStatus.FORBIDDEN);
            response.setBody("Provided user is not \"admin\"");
        }

        if(false) {
            response.getHeader().setName("Content-Type");
            response.getHeader().setValue("text/plain; charset=utf-8");
            response.setHttpStatus(HttpStatus.CONFLICT);
            response.setBody("At least one card in the packages already exists");
        }

        return response;
    }
}
