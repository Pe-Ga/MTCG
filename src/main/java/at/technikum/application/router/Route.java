package at.technikum.application.router;

import at.technikum.http.RequestContext;
import at.technikum.http.Response;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.sql.SQLException;

public interface Route {
    Response process(RequestContext requestContext) throws SQLException, JsonProcessingException;
}
