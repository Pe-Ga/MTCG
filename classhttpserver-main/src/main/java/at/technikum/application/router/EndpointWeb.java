package at.technikum.application.router;

import at.technikum.http.HttpStatus;
import at.technikum.http.RequestContext;
import at.technikum.http.Response;

public class EndpointWeb implements Route{
    @Override
    public Response process(RequestContext requestContext) {
        Response resp = new Response();

        resp.setHttpStatus(HttpStatus.OK);
        resp.setBody("<html><head><title>Hello Title!</title></head><body>Hello World!</body></html>");

        return resp;
    }
}
