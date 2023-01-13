package at.technikum.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HeaderParserTest {

    private HeaderParser headerParser;

    @BeforeEach
    void init() {
        headerParser = new HeaderParser();
    }

    @Test
    void testParseSimpleHeader() {
        // Arrange
        String rawHeader = "Accept: text/html";

        // Act
        Header header = headerParser.parseHeader(rawHeader);

        // Assert
        assertEquals("Accept", header.getName());
        assertEquals("text/html", header.getValue());
    }

    @Test
    void testHeaderWithColonInValue() {
        // Arrange
        String rawHeader = "Type: test:html";

        // Act
        Header header = headerParser.parseHeader(rawHeader);

        // Assert
        assertEquals("Type", header.getName());
        assertEquals("test:html", header.getValue());
    }

    @Test
    void testParseHeadersToMap() {
        // Arrange
        List<String> rawHeaders = List.of(
                "Accept: application/json",
                "Content-Type: text/plain"
        );
        // Act
        Map<String, String> result =
                headerParser.parseHeadersToMap(rawHeaders);
        // Assert
        assertEquals(2, result.size());
        assertEquals("application/json", result.get("Accept"));
        assertEquals("text/plain", result.get("Content-Type"));
    }

    @Test
    void testNullInput() {
        // Act
        Map<String, String> result =
                headerParser.parseHeadersToMap(null);
        Header resultHeader = headerParser.parseHeader(null);
        // Assert
        assertNotNull(result);
        assertNull(resultHeader);
    }

    @Test
    void testEmptyList() {
        // Act
        Map<String, String> result =
                headerParser.parseHeadersToMap(List.of());
        // Assert
        assertNotNull(result);
    }
}