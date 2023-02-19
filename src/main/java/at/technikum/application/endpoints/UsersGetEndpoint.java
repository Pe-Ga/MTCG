package at.technikum.application.endpoints;

import at.technikum.application.config.DataSource;
import at.technikum.application.config.DbConnector;
import at.technikum.application.repository.PostgresUserRepository;
import at.technikum.application.router.Route;
import at.technikum.http.Header;
import at.technikum.http.HttpStatus;
import at.technikum.http.RequestContext;
import at.technikum.http.Response;

import java.sql.SQLException;

public class UsersGetEndpoint implements Route{

    public Response process(RequestContext requestContext) throws SQLException {

        var response = new Response();
        response.setHeader(new Header());

        DbConnector dataSource = DataSource.getInstance();
        PostgresUserRepository postgresUserRepository =  new PostgresUserRepository(dataSource);

        var usr = postgresUserRepository.findUser(requestContext.getPathExtensions().get(1).substring(1));
        //var usr = postgresUserRepository.findUserByToken(requestContext.getPathExtensions().get(1).substring(1));

        System.out.println(usr.getUserToken());
        System.out.println(usr.getUserTokenExpiration());

        if(usr.userNameExists())
        {
            response.setHttpStatus(HttpStatus.OK);
            response.setBody("{\n" +
                    "  \"Name\": " + usr.getUsername() + ",\n" +
                    "  \"Bio\": " + usr.getBio() + ",\n" +
                    "  \"Image\": " + usr.getImage() + "\"\n" +
                    "}");
        }

        if(false) {
            response.setHttpStatus(HttpStatus.UNAUTHORIZED);
            response.setBody("Access token is missing or invalid");
        }

        if(!usr.userNameExists()) {
            response.getHeader().setName("Content-Type");
            response.getHeader().setValue("text/plain; charset=utf-8");
            response.setHttpStatus(HttpStatus.NOT_FOUND);
            response.setBody("User not found");
        }

        return response;
    }
}
