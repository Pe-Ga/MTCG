package at.technikum.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public class RequestContext {

    private static final String CONTENT_LENGTH_HEADER_NAME = "Content-Length";
    private String token;
    private String httpVerb;
    private String path;
    private List<String> pathExtensions;
    private List<Header> headers;
    private Map<String, String > urlParameters;
    private String body;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getHttpVerb() {
        return httpVerb;
    }

    public void setHttpVerb(String httpVerb) {
        this.httpVerb = httpVerb;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getPathExtensions() {
        return pathExtensions;
    }

    public void setPathExtensions(List<String> pathExtensions) {
        this.pathExtensions = pathExtensions;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    public Map<String, String> getUrlParameters() {
        return urlParameters;
    }

    public void setUrlParameters(Map<String, String> urlParameters) {
        this.urlParameters = urlParameters;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public int getContentLength() {
        return headers.stream()
                .filter(header -> CONTENT_LENGTH_HEADER_NAME.equals(header.getName()))
                .findFirst()
                .map(Header::getValue)
                .map(Integer::parseInt)
                .orElse(0);
    }
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public <T> T getBodyAs(Class<T> clazz) {
        try {
            return objectMapper.readValue(body, clazz);
        } catch (JsonProcessingException e) {
            System.err.println(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }
    public void print() {
        System.out.println("HTTP-Verb: " + httpVerb);
        System.out.println("Path " + path);
        System.out.println("Extensions: " + this.pathExtensions);
        System.out.println("URL parameters: " + this.urlParameters);
        System.out.println("Headers: " + headers);
        System.out.println("Body: " + body);
    }

    public String extractToken()
    {
        String[] strings = this.getToken().split(" ",2);
        return strings[1];
    }


}
