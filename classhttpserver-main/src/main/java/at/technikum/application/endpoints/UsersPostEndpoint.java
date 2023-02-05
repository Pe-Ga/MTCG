package at.technikum.application.endpoints;

import at.technikum.application.router.Route;
import at.technikum.http.HttpStatus;
import at.technikum.http.RequestContext;
import at.technikum.http.Response;

public class UsersPostEndpoint implements Route {

    public Response process(RequestContext requestContext){

        Response response = new Response();
        response.setHttpStatus(HttpStatus.OK);
        response.setBody("TEST POST");

        return response;
    }

}
