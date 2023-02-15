package at.technikum.lessons;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LinkFinderTest {

    private final LinkFinder linkFinder = new LinkFinder();

    @Test
    void findLinksForTechnikumPage() throws IOException, InterruptedException {
        // Given
        HttpClient httpClient = HttpClient.newBuilder().build();

        String htmlContent = httpClient.send(
                HttpRequest.newBuilder()
                        .GET()
                        .uri(URI.create("https://www.technikum-wien.at"))
                        .build(),
                HttpResponse.BodyHandlers.ofString()
        ).body();

        // When
        final List<URI> linksInHtml = linkFinder.findLinksInHtml(htmlContent);

        // Then
        // 14 'a href="https:// links'
        assertEquals(14, linksInHtml.size());

    }

}