package at.technikum.application.endpoints;

import at.technikum.application.config.DataSource;
import at.technikum.application.config.DbConnector;
import at.technikum.application.repository.PostgresUserRepository;
import at.technikum.application.repository.UserRepository;
import at.technikum.application.router.Route;
import at.technikum.http.Header;
import at.technikum.http.HttpStatus;
import at.technikum.http.RequestContext;
import at.technikum.http.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.sql.SQLException;

public class UsersGetEndpoint implements Route{

    public Response process(RequestContext requestContext) throws SQLException, JsonProcessingException {

        var response = new Response();
        response.setHeader(new Header());

        DbConnector dataSource = DataSource.getInstance();
        UserRepository postgresUserRepository =  new PostgresUserRepository(dataSource);
        var usr = postgresUserRepository.findUser(requestContext.getPathExtensions().get(1).substring(1));

        if(usr == null) {
            response.getHeader().setName("Content-Type");
            response.getHeader().setValue("text/plain; charset=utf-8");
            response.setHttpStatus(HttpStatus.NOT_FOUND);
            response.setBody("User not found");
            return response;
        }

       // boolean tokenIsExpired = (Instant.now().isAfter(usr.getUserTokenExpiration()));

        // compares token provided in header and token persited in db
      //  if(usr.getUserToken().equals(requestContext.extractToken()) && !tokenIsExpired)
        if (!usr.tokenIsInvalid(requestContext.extractToken()))
        {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode userNode = objectMapper.createObjectNode();
            userNode.put("Name", usr.getUsername());
            userNode.put("Bio", usr.getBio());
            userNode.put("Image", usr.getImage());
            String userJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(userNode);

            response.getHeader().setName("Content-Type");
            response.getHeader().setValue("application/json; charset=utf-8");
            response.setHttpStatus(HttpStatus.OK);
            response.setBody(userJson);
        }
        else
        {
            response.setHttpStatus(HttpStatus.UNAUTHORIZED);
            response.setBody("Access token is missing or invalid");
        }

        return response;
    }
}
