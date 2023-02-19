package at.technikum.application.endpoints;

import at.technikum.application.config.DataSource;
import at.technikum.application.config.DbConnector;
import at.technikum.application.model.Credentials;
import at.technikum.application.model.User;
import at.technikum.application.repository.PostgresUserRepository;
import at.technikum.application.repository.UserRepository;
import at.technikum.application.router.Route;
import at.technikum.http.Header;
import at.technikum.http.HttpStatus;
import at.technikum.http.RequestContext;
import at.technikum.http.Response;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static at.technikum.application.util.AccessTokenGenerator.generateAccessToken;

public class UsersPostEndpoint implements Route {

    public Response process(RequestContext requestContext) {

        boolean dbRequest = false;
        boolean userExists;
        var response = new Response();


        ObjectMapper mapper = new ObjectMapper();
        DbConnector dataSource = DataSource.getInstance();
        UserRepository postgresUserRepository = new PostgresUserRepository(dataSource);

        try
        {

            var request = mapper.readValue(requestContext.getBody(), Credentials.class);
            boolean user_already_exists = postgresUserRepository.findUser(request.getUsername()) != null;

            if(!user_already_exists) {
                User user = new User();
                user.setUsername(request.getUsername());
                user.setPassword(request.getPassword());
                String userToken = generateAccessToken();
                Instant expiration = Instant.now().plus(Duration.ofMinutes(15));
                user.setUserToken(userToken);
                user.setUserTokenExpiration(expiration);

                postgresUserRepository.registerUser(user);

                response.getHeader().setName("Content-Type");
                response.getHeader().setValue("text/plain; charset=utf-8");
                response.setHttpStatus(HttpStatus.CREATED);
                response.setBody("User successfully created");
            }
            else
            {
                response.getHeader().setName("Content-Type");
                response.getHeader().setValue("text/plain; charset=utf-8");
                response.setHttpStatus(HttpStatus.CONFLICT);
                response.setBody("User with same username already registered");
            }
        }
        catch (JsonProcessingException | SQLException e)
        {
            //response.getHeader().setName("Content-Type");
            //response.getHeader().setValue("text/plain; charset=utf-8");
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            //response.setBody("Bad request");
        }

        return response;
    }
}
