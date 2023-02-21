package at.technikum.application.endpoints;

import at.technikum.application.config.DataSource;
import at.technikum.application.config.DbConnector;
import at.technikum.application.model.User;
import at.technikum.application.model.card.Card;
import at.technikum.application.model.card.ElementType;
import at.technikum.application.model.card.MonsterType;
import at.technikum.application.model.card.Package;
import at.technikum.application.repository.CardRepository;
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

public class TransactionsPostEndpoint implements Route {
    @Override
    public Response process(RequestContext requestContext) throws SQLException, JsonProcessingException {

        var response = new Response();
        response.setHeader(new Header());

        DbConnector dataSource = DataSource.getInstance();
        UserRepository postgresUserRepository =  new UserRepository(dataSource);
        CardRepository cardRepository = new CardRepository(dataSource);
        User usr;
        usr = postgresUserRepository.findUserByToken(requestContext.extractToken());

        boolean has_valid_token;
        boolean has_enough_coins;

        Package cardPackage = new Package();


/*        System.out.println(has_enough_coins);
        System.out.println(has_valid_token);*/

        var card = new Card(MonsterType.Spell, ElementType.Fire, 99);
        card.setId(100);
        System.out.println(">>");
        //System.out.println(usr.getCoins());

        // TODO Create packages class and
        if (false)
        {
            response.getHeader().setName("Content-Type");
            response.getHeader().setValue("text/plain; charset=utf-8");
            response.setHttpStatus(HttpStatus.NOT_FOUND);
            response.setBody("No card package available for buying");
            return response;

        }

        if (usr == null)
        {
            response.getHeader().setName("Content-Type");
            response.getHeader().setValue("text/plain; charset=utf-8");
            response.setHttpStatus(HttpStatus.NOT_FOUND);
            response.setBody("User not found");
            return response;
        }

         has_valid_token = usr.getUserToken().equals(requestContext.extractToken());
         has_enough_coins = usr.getCoins() >= 5;

        if (!has_valid_token)
        {

            response.getHeader().setName("Content-Type");
            response.getHeader().setValue("text/plain; charset=utf-8");
            response.setHttpStatus(HttpStatus.UNAUTHORIZED);
            response.setBody("Access token is missing or invalid");
        }
        else if (!has_enough_coins)
        {
            response.getHeader().setName("Content-Type");
            response.getHeader().setValue("text/plain; charset=utf-8");
            response.setHttpStatus(HttpStatus.UNAUTHORIZED);
            response.setBody("Not enough money for buying a card package");
        }
        else
        {
            // TODO send card to DB
            // update user and substract 5 coins doesnt work YET
            System.out.println("COINS: ");
            System.out.println(usr.getCoins());
            usr.setCoins(usr.getCoins() - 5);
            System.out.println(usr.getCoins());
            // upodate users coins
            postgresUserRepository.updateUser(usr);
            //save new card with userId in DB
            cardRepository.saveCard(card, usr.getUserId());

            // map
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode userNode = objectMapper.createObjectNode();
            userNode.put("Id", card.getId());
            userNode.put("Name", card.onlyNameToString());
            userNode.put("Damage", card.getBaseDamage());
            String userJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(userNode);

            response.getHeader().setName("Content-Type");
            response.getHeader().setValue("application/json; charset=utf-8");
            response.setHttpStatus(HttpStatus.OK);
            response.setBody(userJson);

        }

        return response;
    }
}
