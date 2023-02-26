package at.technikum.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HeaderParserTest {

    private HeaderParser headerParser;

    @BeforeEach
    void init()
    {
        headerParser = new HeaderParser();
    }

    @Test
    void testParseSimpleHeader()
    {
        String rawHeader = "Accept: text/html";

        Header header = headerParser.parseHeader(rawHeader);

        assertEquals("Accept", header.getName());
        assertEquals("text/html", header.getValue());
    }

    @Test
    void testHeaderWithColonInValue()
    {
        String rawHeader = "Type: test:html";

        Header header = headerParser.parseHeader(rawHeader);


        assertEquals("Type", header.getName());
        assertEquals("test:html", header.getValue());
    }

    @Test
    void testParseHeadersToMap()
    {
        List<String> rawHeaders = List.of(
                "Accept: application/json",
                "Content-Type: text/plain"
        );

        Map<String, String> result =
                headerParser.parseHeadersToMap(rawHeaders);

        assertEquals(2, result.size());
        assertEquals("application/json", result.get("Accept"));
        assertEquals("text/plain", result.get("Content-Type"));
    }

    @Test
    void testNullInput()
    {
        Map<String, String> result =
                headerParser.parseHeadersToMap(null);
        Header resultHeader = headerParser.parseHeader(null);

        assertNotNull(result);
        assertNull(resultHeader);
    }

    @Test
    void testEmptyList()
    {
        Map<String, String> result =
                headerParser.parseHeadersToMap(List.of());

        assertNotNull(result);
    }
}