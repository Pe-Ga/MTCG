package at.technikum.application.endpoints;

import at.technikum.application.config.DataSource;
import at.technikum.application.config.DbConnector;
import at.technikum.application.model.card.Card;
import at.technikum.application.repository.CardRepository;
import at.technikum.application.repository.ICardRepository;
import at.technikum.application.repository.IUserRepository;
import at.technikum.application.repository.UserRepository;
import at.technikum.application.router.Route;
import at.technikum.http.Header;
import at.technikum.http.HttpStatus;
import at.technikum.http.RequestContext;
import at.technikum.http.Response;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.sql.SQLException;
import java.util.List;

public class PackagesPostEndpoint implements Route {
    @Override
    public Response process(RequestContext requestContext) throws SQLException {

        try
        {
            var response = new Response();
            response.setHeader(new Header());

            DbConnector dataSource = DataSource.getInstance();
            IUserRepository postgresUserRepository =  new UserRepository(dataSource);
            var usr = postgresUserRepository.findUserByToken(requestContext.extractToken());

            if (usr != null )
            {
                System.out.println("PING1");
                if (!usr.isAdmin())
                {
                    response.getHeader().setName("Content-Type");
                    response.getHeader().setValue("text/plain; charset=utf-8");
                    response.setHttpStatus(HttpStatus.FORBIDDEN);
                    response.setBody("Provided user is not \"admin\"");
                }
                else if (usr.tokenIsInvalid(requestContext.extractToken()))
                {
                    System.out.println("PING2");
                    response.getHeader().setName("Content-Type");
                    response.getHeader().setValue("text/plain; charset=utf-8");
                    response.setHttpStatus(HttpStatus.UNAUTHORIZED);
                    response.setBody("Access token is missing or invalid");
                }
                else
                {
                    System.out.println("PING3");
                    ICardRepository cardRepository = new CardRepository(dataSource);
                    List<Card> cardList = cardRepository.getCards();

                    for(Card card : cardList)
                    {
                        System.out.println(card);
                    }
                }
            }

                response.getHeader().setName("Content-Type");
                response.getHeader().setValue("text/plain; charset=utf-8");
                response.setHttpStatus(HttpStatus.CREATED);
                response.setBody("Package and cards successfully created");

                response.getHeader().setName("Content-Type");
                response.getHeader().setValue("text/plain; charset=utf-8");
                response.setHttpStatus(HttpStatus.CONFLICT);
                response.setBody("At least one card in the packages already exists");

            return response;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
