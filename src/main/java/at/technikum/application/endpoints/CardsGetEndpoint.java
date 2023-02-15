package at.technikum.application.endpoints;

import at.technikum.application.router.Route;
import at.technikum.http.Header;
import at.technikum.http.HttpStatus;
import at.technikum.http.RequestContext;
import at.technikum.http.Response;

import java.sql.DriverManager;

public class CardsGetEndpoint implements Route {


    @Override
    public Response process(RequestContext requestContext) {

        var response = new Response();
        response.setHeader(new Header());

        if(true) {
            response.getHeader().setName("Content-Type");
            response.getHeader().setValue("text/plain; charset=utf-8");
            response.setHttpStatus(HttpStatus.OK);
            response.setBody("  {\n" +
                    "    \"Id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n" +
                    "    \"Name\": \"WaterGoblin\",\n" +
                    "    \"Damage\": 55\n" +
                    "  }" +
                    "\nThe user has cards, the response contains these");
        }

        if(false) {
            response.getHeader().setName("Content-Type");
            response.getHeader().setValue("text/plain; charset=utf-8");
            response.setHttpStatus(HttpStatus.NO_CONTENT);
            response.setBody("The request was fine, but the user doesn't have any cards");
        }

        if(false) {
            response.getHeader().setName("Content-Type");
            response.getHeader().setValue("text/plain; charset=utf-8");
            response.setHttpStatus(HttpStatus.NOT_FOUND);
            response.setBody("Access token is missing or invalid");
        }

        return response;
    }
}
