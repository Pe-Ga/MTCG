package at.technikum.application.endpoints;

import at.technikum.application.config.DataSource;
import at.technikum.application.config.DbConnector;
import at.technikum.application.model.RequestBody;
import at.technikum.application.repository.UserRepository;
import at.technikum.application.router.Route;
import at.technikum.http.Header;
import at.technikum.http.HttpStatus;
import at.technikum.http.RequestContext;
import at.technikum.http.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.SQLException;

public class UsersPutEndpoint implements Route {
    @Override
    public Response process(RequestContext requestContext) throws SQLException, JsonProcessingException {

        var response = new Response();
        response.setHeader(new Header());
        response.getHeader().setName("Content-Type");
        response.getHeader().setValue("text/plain; charset=utf-8");

        DbConnector dataSource = DataSource.getInstance();
        UserRepository postgresUserRepository =  new UserRepository(dataSource);
        var usr = postgresUserRepository.findUser(requestContext.getPathExtensions().get(1).substring(1));

        ObjectMapper mapper = new ObjectMapper();
        var requestBody = mapper.readValue(requestContext.getBody(), RequestBody.class);

        /*System.out.println("AUS DEM REQUEST");
        System.out.println(requestBody.getName());
        System.out.println(requestBody.getBio());
        System.out.println(requestBody.getImage());*/

       /* System.out.println("HIER SIND DIE DATEN DIE IN DIE DB KOMMEN");
        System.out.println(usr.getRealName());
        System.out.println(usr.getBio());
        System.out.println(usr.getImage());*/

        if(usr != null)
        {           // TODO check expiration date
            if(usr.getUserToken().equals(requestContext.extractToken()))
            {
                usr.setRealName(requestBody.getName());
                usr.setBio(requestBody.getBio());
                usr.setImage(requestBody.getImage());
                postgresUserRepository.updateUser(usr);

                response.setHttpStatus(HttpStatus.OK);
                response.setBody("User sucessfully updated");
            }
            else
            {
                response.setHttpStatus(HttpStatus.UNAUTHORIZED);
                response.setBody("Access token is missing or invalid");
            }
        }
        else
        {
            response.setHttpStatus(HttpStatus.NOT_FOUND);
            response.setBody("User not found");
        }

        return response;
    }
}
