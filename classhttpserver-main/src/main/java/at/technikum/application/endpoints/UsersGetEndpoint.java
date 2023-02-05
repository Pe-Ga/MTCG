package at.technikum.application.endpoints;

import at.technikum.application.router.Route;
import at.technikum.http.HttpStatus;
import at.technikum.http.RequestContext;
import at.technikum.http.Response;

import java.util.ArrayList;
import java.util.List;

public class UsersGetEndpoint implements Route{

    public Response process(RequestContext requestContext) {

        List<String> dbMock = new ArrayList<String>();
        dbMock.add("peter");
        dbMock.add("simon");

        Response response = new Response();

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
        else {

            response.setHttpStatus(HttpStatus.NOT_FOUND);
            response.setBody("User not found.");
        }


        return response;
    }
}
