package at.technikum.lessons;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WebcrawlerStart {
    public static void main(String[] args) throws IOException, InterruptedException {
        long startTime = System.currentTimeMillis();
        HttpClient httpClient = HttpClient.newBuilder().build();

        String htmlContent = httpClient.send(
                HttpRequest.newBuilder()
                        .GET()
                        .uri(URI.create("https://www.technikum-wien.at"))
                        .build(),
                HttpResponse.BodyHandlers.ofString()
        ).body();
        System.out.println(htmlContent.contains("<a href="));
        final int indexOf = htmlContent.indexOf("<a href=\"https://");
        System.out.println(htmlContent.substring(indexOf, indexOf + 400));

        long endTime = System.currentTimeMillis();
        System.out.println("Lasted: " + (endTime - startTime) + " millis");
    }
}
