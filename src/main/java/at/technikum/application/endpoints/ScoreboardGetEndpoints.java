package at.technikum.application.endpoints;

import at.technikum.application.config.DataSource;
import at.technikum.application.config.DbConnector;
import at.technikum.application.model.User;
import at.technikum.application.repository.UserRepository;
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

public class ScoreboardGetEndpoints implements Route {
    @Override
    public Response process(RequestContext requestContext) throws JsonProcessingException, SQLException {
        var response = new Response();
        response.setHeader(new Header());

        DbConnector dataSource = DataSource.getInstance();
        UserRepository postgresUserRepository =  new UserRepository(dataSource);



        var usr = postgresUserRepository.findUserByToken(requestContext.extractToken());

        if(usr == null || !usr.getUserToken().equals(requestContext.extractToken())) {

            response.setHttpStatus(HttpStatus.UNAUTHORIZED);
            response.setBody("Access token is missing or invalid");

        }
        else
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
        return response;
    }
}
