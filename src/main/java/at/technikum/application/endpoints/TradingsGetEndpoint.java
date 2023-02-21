package at.technikum.application.endpoints;

import at.technikum.application.config.DataSource;
import at.technikum.application.config.DbConnector;
import at.technikum.application.model.User;
import at.technikum.application.repository.PostgresUserRepository;
import at.technikum.application.router.Route;
import at.technikum.http.Header;
import at.technikum.http.HttpStatus;
import at.technikum.http.RequestContext;
import at.technikum.http.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.sql.SQLException;

public class TradingsGetEndpoint implements Route {
    @Override
    public Response process(RequestContext requestContext) throws SQLException, JsonProcessingException {

        boolean tradeOffers = false;

        try
        {
            DbConnector dataSource = DataSource.getInstance();
            PostgresUserRepository postgresUserRepository = new PostgresUserRepository(dataSource);
            var response = new Response();
            response.setHeader(new Header());
            var usr = postgresUserRepository.findUserByToken(requestContext.extractToken());

            if(usr == null || !usr.tokenIsInvalid(requestContext.extractToken())) {

                response.getHeader().setName("Content-Type");
                response.getHeader().setValue("text/plain; charset=utf-8");
                response.setHttpStatus(HttpStatus.UNAUTHORIZED);
                response.setBody("Access token is missing or invalid");

            }
            else if (!usr.tokenIsInvalid(requestContext.extractToken()) && tradeOffers == true)
            {
                var usersList = postgresUserRepository.findAllUsers();

                ObjectMapper objectMapper = new ObjectMapper();
                ArrayNode arrayNode = objectMapper.createArrayNode();

                for(User user : usersList)
                {
                    ObjectNode userNode = objectMapper.createObjectNode();
                    userNode.put("Name", user.getUsername());
                    userNode.put("Elo", user.getElo());
                    userNode.put("Wins", user.getWins());
                    userNode.put("Losses", user.getLosses());
                    arrayNode.add(userNode);
                }
                String userJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode);

                response.getHeader().setName("Content-Type");
                response.getHeader().setValue("application/json; charset=utf-8");
                response.setHttpStatus(HttpStatus.OK);
                response.setBody(userJson);
            }
            else
            {
                response.getHeader().setName("Content-Type");
                response.getHeader().setValue("text/plain; charset=utf-8");
                response.setHttpStatus(HttpStatus.UNAUTHORIZED);
                response.setBody("The request was fine, but there are no trading deals available");
            }
            return response;
        }
        catch (SQLException e)
        {
            var response = new Response();
            response.setHeader(new Header());
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setBody("An error occurred while processing the request");
            response.getHeader().setName("Content-Type");
            response.getHeader().setValue("text/plain; charset=utf-8");
            return response;
        }
        catch (JsonProcessingException e)
        {
            var response = new Response();
            response.setHeader(new Header());
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setBody("An error occurred while processing the response data");
            response.getHeader().setName("Content-Type");
            response.getHeader().setValue("text/plain; charset=utf-8");
            return response;
        }
    }
}
