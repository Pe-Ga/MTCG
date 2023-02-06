package at.technikum.application.endpoints;

import at.technikum.application.router.Route;
import at.technikum.http.Header;
import at.technikum.http.HttpStatus;
import at.technikum.http.RequestContext;
import at.technikum.http.Response;

public class TransactionsPostEndpoint implements Route {
    @Override
    public Response process(RequestContext requestContext) {

        var response = new Response();
        response.setHeader(new Header());

        if(false) {
            response.getHeader().setName("Content-Type");
            response.getHeader().setValue("text/plain; charset=utf-8");
            response.setHttpStatus(HttpStatus.OK);
            response.setBody("{\n" +
                    "    \"Id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n" +
                    "    \"Name\": \"WaterGoblin\",\n" +
                    "    \"Damage\": 55\n" +
                    "}" + "\n\nA package has been successfully bought");
        }

        if(false) {
            response.getHeader().setName("Content-Type");
            response.getHeader().setValue("text/plain; charset=utf-8");
            response.setHttpStatus(HttpStatus.UNAUTHORIZED);
            response.setBody("Access token is missing or invalid");
        }

        if(false) {
            response.getHeader().setName("Content-Type");
            response.getHeader().setValue("text/plain; charset=utf-8");
            response.setHttpStatus(HttpStatus.FORBIDDEN);
            response.setBody("Not enough money for buying a card package");
        }

        if(true) {
            response.getHeader().setName("Content-Type");
            response.getHeader().setValue("text/plain; charset=utf-8");
            response.setHttpStatus(HttpStatus.NOT_FOUND);
            response.setBody("No card package available for buying");
        }

        return response;
    }
}
