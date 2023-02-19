package at.technikum.application.endpoints;

import at.technikum.application.config.DataSource;
import at.technikum.application.config.DbConnector;
import at.technikum.application.model.card.Card;
import at.technikum.application.repository.PostgresUserRepository;
import at.technikum.application.router.Route;
import at.technikum.http.Header;
import at.technikum.http.HttpStatus;
import at.technikum.http.RequestContext;
import at.technikum.http.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.sql.SQLException;
import java.util.List;

public class UsersGetEndpoint implements Route{

    public Response process(RequestContext requestContext) throws SQLException, JsonProcessingException {

        var response = new Response();
        response.setHeader(new Header());

        DbConnector dataSource = DataSource.getInstance();
        PostgresUserRepository postgresUserRepository =  new PostgresUserRepository(dataSource);
        var usr = postgresUserRepository.findUser(requestContext.getPathExtensions().get(1).substring(1));

        System.out.println(requestContext.extractToken());
        System.out.println(usr.getUserToken());
        System.out.println(usr.getUserTokenExpiration());

        // TODO check expiration date
        if(!usr.userNameExists()) {
            response.getHeader().setName("Content-Type");
            response.getHeader().setValue("text/plain; charset=utf-8");
            response.setHttpStatus(HttpStatus.NOT_FOUND);
            response.setBody("User not found");
        }

        // compares token provided in header and token persited in db
        if(usr.userNameExists() && usr.getUserToken().equals(requestContext.extractToken()))
        {
            // TODO implement jackson
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
