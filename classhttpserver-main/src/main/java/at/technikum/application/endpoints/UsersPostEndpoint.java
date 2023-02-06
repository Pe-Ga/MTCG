package at.technikum.application.endpoints;

import at.technikum.application.router.Route;
import at.technikum.http.Header;
import at.technikum.http.HttpStatus;
import at.technikum.http.RequestContext;
import at.technikum.http.Response;

public class UsersPostEndpoint implements Route {

    public Response process(RequestContext requestContext) {

        boolean dbRequest = false;
        var response = new Response();
        response.setHeader(new Header());

        if(dbRequest) {
            response.getHeader().setName("Content-Type");
            response.getHeader().setValue("text/plain; charset=utf-8");
            response.setHttpStatus(HttpStatus.CREATED);
            response.setBody("User successfully created");
        }

        if(!dbRequest) {
            response.getHeader().setName("Content-Type");
            response.getHeader().setValue("text/plain; charset=utf-8");
            response.setHttpStatus(HttpStatus.CONFLICT);
            response.setBody("User with same username already registered");
        }

        return response;
    }
}
