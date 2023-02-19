package at.technikum.application.endpoints;

import at.technikum.application.config.DataSource;
import at.technikum.application.config.DbConnector;
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

public class StatsGetEndpoint implements Route {
    @Override
    public Response process(RequestContext requestContext) throws SQLException, JsonProcessingException {

        var response = new Response();
        response.setHeader(new Header());
        response.getHeader().setName("Content-Type");
        response.getHeader().setValue("text/plain; charset=utf-8");

        DbConnector dataSource = DataSource.getInstance();
        PostgresUserRepository postgresUserRepository =  new PostgresUserRepository(dataSource);
        //var usr = postgresUserRepository.findUser(requestContext.getPathExtensions().get(1).substring(1));
        var usr = postgresUserRepository.findUser("peter");
        boolean has_valid_token = usr.getUserToken().equals(requestContext.extractToken());

        if(has_valid_token)
        {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode userNode = objectMapper.createObjectNode();
            userNode.put("Name", usr.getUsername());
            userNode.put("Elo", usr.getElo());
            userNode.put("Wins", usr.getWins());
            userNode.put("Losses", usr.getLosses());
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
