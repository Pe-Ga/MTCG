package at.technikum.application.controller;

import at.technikum.application.model.Credentials;
import at.technikum.application.model.User;
import at.technikum.application.repository.UserRepository;
import at.technikum.http.HttpStatus;
import at.technikum.http.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RestUserControllerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RestUserController restUserController;

    @Test
    void testLogin() {
        //Arrange
        Credentials credentials = new Credentials("kienboec", "daniel");
        restUserController.register(credentials);
        // Act
        Response response = restUserController.login(credentials);

        // Assert
        assertEquals(HttpStatus.OK, response.getHttpStatus());
        assertEquals("kienboec-xxyyyzzzyyxx", response.getBody());
    }

    @Test
    void testRegisterNewUser() {
        // Arrange
        Credentials credentials = new Credentials("kienboec", "daniel");

        // Act
        Response response = restUserController.register(credentials);
        // Assert
        assertEquals(HttpStatus.CREATED, response.getHttpStatus());
        assertNull(response.getBody());
    }

    @Test
    void testRegisterUserTwiceFails() {
        // Arrange
        Credentials credentials = new Credentials("kienboec", "daniel");

        // Act
        restUserController.register(credentials);
        when(userRepository.findUserByUsername("kienboec"))
                .thenReturn(new User("kienboec", "password"));
        Response response = restUserController.register(credentials);
        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getHttpStatus());
        assertEquals("User with username kienboec already exists", response.getBody());
    }

}
