package at.technikum.application.EndPointsTest;

import at.technikum.application.model.User;
import at.technikum.application.endpoints.UsersGetEndpoint;
import at.technikum.application.repository.IUserRepository;
import at.technikum.http.Header;
import at.technikum.http.HttpStatus;
import at.technikum.http.RequestContext;
import at.technikum.http.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

public class UsersGetEndpointTest {
    private IUserRepository mockUserRepository;
    private UsersGetEndpoint usersGetEndpoint;
    private RequestContext requestContext;

    @BeforeEach
    public void setUp() throws Exception {
        mockUserRepository = Mockito.mock(IUserRepository.class);
        usersGetEndpoint = new UsersGetEndpoint();
        requestContext = new RequestContext();
        requestContext.setPathExtensions(Arrays.asList("/users", "/123"));
        requestContext.getPathExtensions().get(1).substring(1);
    }

    @Test
    public void processWhenUserNotFoundReturns404Response() throws Exception
    {
        Mockito.when(mockUserRepository.findUser(anyString())).thenReturn(null);

        Response response = usersGetEndpoint.process(requestContext);

        assertEquals(HttpStatus.NOT_FOUND, response.getHttpStatus());
        assertEquals("text/plain; charset=utf-8", response.getHeader().getValue());
        assertEquals("User not found", response.getBody());
    }

    @Test
    public void processWhenTokenInvalidReturns401Response() throws Exception
    {
        User mockUser = Mockito.mock(User.class);
        Mockito.when(mockUser.tokenIsInvalid(Mockito.anyString())).thenReturn(true);
        Mockito.when(mockUserRepository.findUser(anyString())).thenReturn(mockUser);

        Response response = usersGetEndpoint.process(requestContext);

        assertEquals(HttpStatus.NOT_FOUND, response.getHttpStatus());
        assertEquals("User not found", response.getBody());
    }

}

