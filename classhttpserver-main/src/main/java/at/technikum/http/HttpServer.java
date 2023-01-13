package at.technikum.http;

import at.technikum.application.router.Route;
import at.technikum.application.router.RouteIdentifier;
import at.technikum.application.router.Router;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class HttpServer {

    private final Router router;

    public HttpServer(Router router) {
        this.router = router;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(3000)) {
            while (true) {
                try (final Socket socket = serverSocket.accept()) {
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader
                                    (socket.getInputStream()));

                    final RequestContext requestContext = parseInput(br);
                    System.out.println("Thread: " + Thread.currentThread().getName());
                    requestContext.print();
                    final RouteIdentifier routeIdentifier = new RouteIdentifier(
                            requestContext.getPath(),
                            requestContext.getHttpVerb()
                    );
                    final Route route = router.findRoute(routeIdentifier);
                    Response response;
                    try {
                        response = route.process(requestContext);
                    } catch (BadRequestException badRequestException) {
                        response = new Response();
                        response.setBody(badRequestException.getMessage());
                        response.setHttpStatus(HttpStatus.BAD_REQUEST);
                    } catch (IllegalStateException e) {
                        response = new Response();
                        response.setBody(e.getMessage());
                        response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                    }

                    BufferedWriter w = new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream()));
                    // 200 OK
                    w.write("HTTP/1.1 ");
                    w.write(response.getHttpStatus().getStatusCode() + " ");
                    w.write(response.getHttpStatus().getStatusMessage());
                    w.newLine();
                    // write headers
                    w.newLine();
                    // write body
                    w.flush();
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public RequestContext parseInput(BufferedReader bufferedReader) throws IOException {
        RequestContext requestContext = new RequestContext();

        String versionString = bufferedReader.readLine();
        final String[] splitVersionString = versionString.split(" ");
        requestContext.setHttpVerb(splitVersionString[0]);
        requestContext.setPath(splitVersionString[1]);

        List<Header> headerList = new ArrayList<>();
        HeaderParser headerParser = new HeaderParser();
        String input;
        do {
            input = bufferedReader.readLine();
            if (input.equals("")) {
                break;
            }
            headerList.add(headerParser.parseHeader(input));
        } while (true);
        requestContext.setHeaders(headerList);

        int contentLength = requestContext.getContentLength();
        char[] buffer = new char[contentLength];
        bufferedReader.read(buffer, 0, contentLength);
        requestContext.setBody(new String(buffer));
        return requestContext;
    }
}
