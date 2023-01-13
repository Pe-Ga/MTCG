package at.technikum.application.repository;

import at.technikum.application.config.TestDataSource;
import at.technikum.application.model.Message;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PostgresMessageRepositoryTest {

    private PostgresMessageRepository postgresMessageRepository
            = new PostgresMessageRepository(TestDataSource.getInstance());

    @AfterEach
    void cleanUpMessageTable() throws SQLException {
        try (Connection c = TestDataSource.getInstance().getConnection()) {
            c.prepareStatement("""
                        delete from messages
                    """).execute();
        }
    }

    @Test
    void getAllMessages() throws SQLException {
        // Arrange
        setupTestdata();

        // Act
        final List<Message> allMessages = postgresMessageRepository.getAllMessages();

        // Assert
        assertEquals(1, allMessages.size());
        var firstMessage = allMessages.get(0);
        assertEquals(12345, firstMessage.getId());
        assertEquals("Eine Nachricht", firstMessage.getMessage());
    }

    private void setupTestdata() throws SQLException {
        try (Connection c = TestDataSource.getInstance().getConnection()) {
            final var insert = c.prepareStatement("""
                INSERT INTO messages VALUES (?, ?)
            """);
            insert.setInt(1, 12345);
            insert.setString(2, "Eine Nachricht");
            insert.execute();
        }
    }
}