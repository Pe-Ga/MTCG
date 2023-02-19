package at.technikum.application.endpoints;

import at.technikum.application.config.DataSource;
import at.technikum.application.config.DbConnector;
import at.technikum.application.model.Credentials;
import at.technikum.application.model.User;
import at.technikum.application.repository.PostgresUserRepository;
import at.technikum.application.repository.UserRepository;
import at.technikum.application.router.Route;
import at.technikum.http.HttpStatus;
import at.technikum.http.RequestContext;
import at.technikum.http.Response;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;

import static at.technikum.application.util.AccessTokenGenerator.generateAccessToken;

public class SessionsPostEndpoint implements Route {
    @Override
    public Response process(RequestContext requestContext) throws SQLException, JsonProcessingException {
        var response = new Response();

        boolean login_successful;
        User user = null;

        DbConnector dataSource = DataSource.getInstance();
        UserRepository postgresUserRepository = new PostgresUserRepository(dataSource);
        // TODO: Usenrmae und Passwort aus JSON entpcken

        try
        {
            ObjectMapper mapper = new ObjectMapper();
            var request = mapper.readValue(requestContext.getBody(), Credentials.class);
            // Step 1: Query the user from Database
            user = postgresUserRepository.findUser(request.getUsername());
            System.out.println(">>> " + user.getUserTokenExpiration());

            // Step 2: Check whether user was found in Database
            if (user == null) // User does not exist
            {
                login_successful = false;
            }
            else // User does exist
            {
                // Step 3: Check if credentials provided are valid
                if(!user.getPassword().equals(request.getPassword())) // wrong password provided
                {
                    login_successful = false;
                }
                else // user signed on succefully
                {
                    login_successful = true;
                }
            }

        }
        catch (JsonParseException e)
        {
            login_successful = false;
        }

        // Step 4: create appropriate responses, if reuqired, create fresh access token
        if(!login_successful)
        {
            response.getHeader().setName("Content-Type");
            response.getHeader().setValue("text/plain; charset=utf-8");
            response.setHttpStatus(HttpStatus.UNAUTHORIZED);
            response.setBody("Unauthorized");
        }
        else
        {
            // Step 4.1: create new access token
            String userToken = generateAccessToken();
            Instant expiration = Instant.now().plus(Duration.ofMinutes(15));

            // Step 4.2: refresh access token in user object
            user.setUserToken(userToken);
            user.setUserTokenExpiration(expiration);

            System.out.println(">>> " + user.getUserTokenExpiration());

            // Step 4.3: let the UserRepository persist the refreshed user Object in the database
            // TODO postgresUserRepository.updateUser(user);
            postgresUserRepository.updateUser(user);

            // Step 4.4: send back a httpp 200 response with the token in its body
            response.getHeader().setName("Content-Type");
            response.getHeader().setValue("text/plain; charset=utf-8");
            response.setHttpStatus(HttpStatus.OK);
            response.setBody(user.getUsername() + "-" + user.getUserToken());
        }
        return response;
    }
}
