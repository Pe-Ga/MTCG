package at.technikum.application.endpoints;

import at.technikum.application.router.Route;
import at.technikum.http.Header;
import at.technikum.http.HttpStatus;
import at.technikum.http.RequestContext;
import at.technikum.http.Response;

import java.util.ArrayList;
import java.util.List;

public class UsersGetEndpoint implements Route{

    public Response process(RequestContext requestContext) {

        var response = new Response();
        response.setHeader(new Header());

        List<String> dbMock = new ArrayList<String>();
        dbMock.add("peter");
        dbMock.add("simon");

        boolean tokenIsMissing = false;

        boolean user_exists_in_db = requestContext.getPathExtensions().size() > 1 && dbMock.contains(
                requestContext.getPathExtensions().get(1).substring(1)
        );

        if(user_exists_in_db) {
            response.setHttpStatus(HttpStatus.OK);
            response.setBody("{\n" +
                    "  \"Name\": \"Hoax\",\n" +
                    "  \"Bio\": \"me playin...\",\n" +
                    "  \"Image\": \":-)\"\n" +
                    "}");
        }

        if(tokenIsMissing) {
            response.setHttpStatus(HttpStatus.UNAUTHORIZED);
            response.setBody("Access token is missing or invalid");
        }

        if(!user_exists_in_db) {
            response.getHeader().setName("Content-Type");
            response.getHeader().setValue("text/plain; charset=utf-8");
            response.setHttpStatus(HttpStatus.NOT_FOUND);
            response.setBody("User not found");
        }

        return response;
    }
}
