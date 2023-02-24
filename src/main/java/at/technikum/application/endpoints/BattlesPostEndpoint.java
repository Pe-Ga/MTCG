package at.technikum.application.endpoints;

import at.technikum.application.config.DataSource;
import at.technikum.application.config.DbConnector;
import at.technikum.application.model.User;
import at.technikum.application.model.card.Card;
import at.technikum.application.repository.UserRepository;
import at.technikum.application.router.Route;
import at.technikum.http.Header;
import at.technikum.http.HttpStatus;
import at.technikum.http.RequestContext;
import at.technikum.http.Response;
import at.technikum.game.Battle;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class BattlesPostEndpoint implements Route
{
    @Override
    public Response process(RequestContext requestContext) throws SQLException {
        var response = new Response();
        DbConnector dataSource = DataSource.getInstance();
        UserRepository postgresUserRepository =  new UserRepository(dataSource);

        //final int LOBBY_TIMEOUT = 10;
        var usr = postgresUserRepository.findUserByToken(requestContext.extractToken());

        if(usr == null || usr.tokenIsInvalid(requestContext.extractToken())) {

            response.setHttpStatus(HttpStatus.UNAUTHORIZED);
            response.setBody("Access token is missing or invalid");
        }
        else
        {
            // add user to queue in the lobby
            System.out.println("Endpoint will now add user " + usr + " to queue in the Lobby.");
            if (requestContext.getLobby().addParticipant(usr))
            {
                // wait for battle to complete
                while (!requestContext.getLobby().getBattleLog().containsKey(usr));

                // get battle report from Lobby and remove it there
                String battleReport = requestContext.getLobby().getBattleLog().remove(usr);
                //TODO UPDATE USER
                // assemble response for client
                response.getHeader().setName("Content-Type");
                response.getHeader().setValue("text/plain; charset=utf-8");
                response.setHttpStatus(HttpStatus.OK);
                response.setBody(battleReport);
            }
            else
            {
                // assemble response for client
                response.getHeader().setName("Content-Type");
                response.getHeader().setValue("text/plain; charset=utf-8");
                response.setHttpStatus(HttpStatus.CONFLICT);
                response.setBody("For some reason, the lobby did not accept to line you up in its battle queue. Possible reasons: Your deck does not contain EXACTLY 4 cards. Or you are already lined-up in queue and waiting for a battle opponent.");
            }
        }
        return response;
    }
}