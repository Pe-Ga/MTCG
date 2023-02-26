package at.technikum.application.endpoints;

import at.technikum.application.config.DataSource;
import at.technikum.application.config.DbConnector;
import at.technikum.application.model.card.Card;
import at.technikum.application.repository.CardRepository;
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
import java.util.List;

public class CardsGetEndpoint implements Route {


    @Override
    public Response process(RequestContext requestContext) throws SQLException, JsonProcessingException {

        var response = new Response();
        response.setHeader(new Header());

        DbConnector dataSource = DataSource.getInstance();
        UserRepository postgresUserRepository =  new UserRepository(dataSource);
        CardRepository cardRepository = new CardRepository(dataSource);

        var usr = postgresUserRepository.findUserByToken(requestContext.extractToken());

        if(usr == null || !usr.tokenIsInvalid(requestContext.extractToken())) {
            response.getHeader().setName("Content-Type");
            response.getHeader().setValue("text/plain; charset=utf-8");
            response.setHttpStatus(HttpStatus.NOT_FOUND);
            response.setBody("Access token is missing or invalid");

        }
        else
        {
            List<Card> userCollection = usr.getCollection();
            if(usr.getCollection().isEmpty())
            {
                response.getHeader().setName("Content-Type");
                response.getHeader().setValue("text/plain; charset=utf-8");
                response.setHttpStatus(HttpStatus.NO_CONTENT);
                response.setBody("The request was fine, but the user doesn't have any cards");
            }
            else
            {
                ObjectMapper objectMapper = new ObjectMapper();
                ObjectNode userNode = objectMapper.createObjectNode();
                ArrayNode cardsNode = objectMapper.createArrayNode();

                for (Card card : userCollection) {
                    ObjectNode cardNode = objectMapper.createObjectNode();
                    cardNode.put("Id", card.getId());
                    cardNode.put("Name", card.onlyNameToString());
                    cardNode.put("Damage", card.getBaseDamage());
                    cardsNode.add(cardNode);
                }
                String userJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(cardsNode);

                response.getHeader().setName("Content-Type");
                response.getHeader().setValue("text/plain; charset=utf-8");
                response.setHttpStatus(HttpStatus.OK);
                response.setBody(userJson + "\n\nThe user has cards, the response contains these");
            }
        }
        return response;
    }
}
