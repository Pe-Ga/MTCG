package at.technikum.application.controller;

import at.technikum.application.exception.ClientException;
import at.technikum.application.model.Message;
import at.technikum.application.model.PlainMessage;
import at.technikum.application.repository.MessageRepository;
import at.technikum.http.HttpStatus;
import at.technikum.http.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestMessageControllerTest {

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private RestMessageController restMessageController;

    @Test
    void testGetMessages() {
        // Arrange
        final Message message1 = new Message(12, "Hallo");
        final Message message2 = new Message(66, "Hi!");
        when(messageRepository.getAllMessages())
                .thenReturn(List.of(message1, message2));

        // Act
        Response response = restMessageController.getMessages();
        // Assert
        assertNotNull(response);
        assertTrue(response.getBody().contains("66"));
        assertTrue(response.getBody().contains("Hallo"));
        assertTrue(response.getBody().contains("12"));
        assertTrue(response.getBody().contains("Hi"));
        assertEquals(HttpStatus.OK, response.getHttpStatus());
    }

    @Test
    void testAddMessages() {
        // Arrange
        final PlainMessage plainMessage = new PlainMessage();
        when(messageRepository.addMessage(plainMessage))
                .thenReturn(50);
        // Act
        Response response = restMessageController.addMessages(plainMessage);
        // Assert
        assertEquals("50", response.getBody());
        assertEquals(HttpStatus.CREATED, response.getHttpStatus());
    }

    @Test
    void testGetMessage() {
        // Arrange
        final int givenId = 12;
        final Message message1 = new Message(givenId, "Hallo");
        when(messageRepository.getMessage(givenId))
                .thenReturn(message1);

        // Act
        Response response = restMessageController.getMessage(givenId);
        // Assert
        assertNotNull(response);
        assertTrue(response.getBody().contains(""+givenId));
        assertTrue(response.getBody().contains("Hallo"));
        assertEquals(HttpStatus.OK, response.getHttpStatus());
    }

    @Test
    void testEditMessage() {
        // Arrange
        final PlainMessage message = new PlainMessage();
        message.setContent("Hello");
        Message expectedMessage = new Message(12, "Hello");

        // Act
        Response response = restMessageController.editMessage(12, message);
        // Assert
        verify(messageRepository).editMessage(expectedMessage);
        assertEquals(HttpStatus.NO_CONTENT, response.getHttpStatus());
    }

    @Test
    void testDeleteMessage() {
        // Arrange
        // Act
        Response response = restMessageController.deleteMessage(12);
        // Assert
        verify(messageRepository).deleteMessage(12);
        assertEquals(HttpStatus.NO_CONTENT, response.getHttpStatus());
    }

    @Test
    void testShouldRespondWith500ForExceptionOnDelete() {
        // Arrange
        Exception exception = new IllegalStateException("Error when deleting Message");
        doThrow(exception)
                .when(messageRepository)
                .deleteMessage(12);
        // Act
        Response response = restMessageController.deleteMessage(12);
        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getHttpStatus());
        assertEquals(exception.getMessage(), response.getBody());
    }

    @Test
    void testShouldRespondWith400ForClientExceptionOnDelete() {
        // Arrange
        Exception exception = new ClientException("ClientError when deleting Message");
        doThrow(exception)
                .when(messageRepository)
                .deleteMessage(12);
        // Act
        Response response = restMessageController.deleteMessage(12);
        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getHttpStatus());
        assertEquals(exception.getMessage(), response.getBody());

    }

}