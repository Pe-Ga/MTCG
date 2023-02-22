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
import java.util.List;

public class BattlesPostEndpoint implements Route {
    @Override
    public Response process(RequestContext requestContext) throws SQLException {

        final int LOBBY_TIMEOUT = 10;
        final List<User> lobbyUsers = new ArrayList<>();

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
            lobbyUsers.add(usr);
            List<Card> tempDeck = usr.getDeck();
            List<String> battleLog = new ArrayList<>();
            if (lobbyUsers.size() == 2)
            {
                Battle battle = new Battle(lobbyUsers.get(0), lobbyUsers.get(1));
                battleLog = battle.fightBattle();

                if (lobbyUsers.get(0).getDeck().isEmpty() && lobbyUsers.get(1).getDeck().size() == 8)
                {
                    lobbyUsers.get(0).setStatsLoss();
                    lobbyUsers.get(1).setStatsWin();
                    battleLog.add(lobbyUsers.get(1).getUsername() + " won the battle.");
                }
                else if (lobbyUsers.get(1).getDeck().isEmpty() && lobbyUsers.get(0).getDeck().size() == 8)
                {
                    lobbyUsers.get(1).setStatsLoss();
                    lobbyUsers.get(0).setStatsWin();
                    battleLog.add(lobbyUsers.get(0).getUsername() + " won the battle.");
                }
                else
                {
                    battleLog.add("Draw.");
                }
                //TODO RESET DECKS
                String responseBody = String.join("\n", battleLog);
                responseBody += "\"The battle has been carried out successfully.\\n\"";

                response.getHeader().setName("Content-Type");
                response.getHeader().setValue("application/json; charset=utf-8");
                response.setHttpStatus(HttpStatus.OK);
                response.setBody(responseBody);
            }
        }
        return response;
    }
}
