package at.technikum.http;

import at.technikum.application.router.Route;
import at.technikum.application.router.RouteIdentifier;
import at.technikum.application.router.Router;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.Strings;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class HttpServer {

    private final Router router;

    public HttpServer(Router router) {
        this.router = router;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(10001)) {
            while (true) {
                try (final Socket socket = serverSocket.accept()) {
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader
                                    (socket.getInputStream()));

                    final RequestContext requestContext = parseInput(br);
                    System.out.println("Thread: " + Thread.currentThread().getName());
                    requestContext.print();
                    final RouteIdentifier routeIdentifier = new RouteIdentifier(
                            requestContext.getPathExtensions().get(0),
                            requestContext.getHttpVerb()
                    );
                    final Route route = router.findRoute(routeIdentifier);
                    Response response;
                    if( route == null)
                    {
                        response = new Response();
                        response.setBody("Not found: Path " + requestContext.getPath().split("\\?")[0]);
                        response.setHttpStatus(HttpStatus.NOT_FOUND);
                    }
                    else
                    {
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
                    }
                    Header header = new Header();
                    header.setName("Content-Type");
                    header.setValue("application/json");

                    BufferedWriter w = new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream()));
                    // 200 OK
                    w.write("HTTP/1.1 ");
                    w.write(response.getHttpStatus().getStatusCode() + " ");
                    w.write(response.getHttpStatus().getStatusMessage());
                    w.write("\r\n");
                    w.write(header.getName() + ": " + header.getValue() + "; charset=utf-8\r\n");
                    w.write("\r\n");
                    w.write(response.getBody());
                    w.flush();
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public RequestContext parseInput(BufferedReader bufferedReader) throws IOException {
        RequestContext requestContext = new RequestContext();

        // read first line of request.
        // it looks somehow like this:
        // GET /tutorials/other/top-20-mysql-best-practices/ HTTP/1.1
        String firstLineOfHeader = bufferedReader.readLine();

        // split the first line along its spaces " "
        // this should give us an array of strings:
        // [0] "GET"
        // [1] "/tutorials/other/top-20-mysql-best-practices/"
        // [2] HTTP/1.1
        String arr[] = firstLineOfHeader.split(" ");

        // assign to Strings with speaking names:
        String verb = arr[0];
        String path = arr[1];

        // path looks something like:
        // /tutorials/other/top-20-mysql-best-practices/
        // but it could also look like:
        // - /
        // - /tutorials?paramter=value
        // - /helloWorld/abc?parameter1=value1&paramter2=vlue2
        // Our aim is to seperate everything before a '?' as pure path ("path extensions")
        // and, if present, everything after a "?" as URL Parameters.

        String[] pathArr = path.split("\\?");
        String pathExtensions = pathArr[0];
        String urlParameters = path.contains("?") ? pathArr[1] : null;

        // we'll split the path extensions along
        // their slashes "/" into a list of Strings

        List<String> pathList = new ArrayList<String>(List.of(pathExtensions.split("(?=/)")));

        // The url parameters undergo a further parsing:
        // Parse the URL Parameters into a Map of parameters->values:

        Map<String, String> urlParams = new HashMap<String, String>();
        if ( urlParameters!=null) {
            String[] keyValuePairs = urlParameters.split("&");
            for (String elements : keyValuePairs) {
                String[] array = elements.split("=");
                urlParams.put(array[0], elements.contains("=") ? array[1] : null);
            }
        }



        // now introduce the RequestContext to the calculated values
        requestContext.setHttpVerb(verb);
        requestContext.setPath(path);
        requestContext.setPathExtensions(pathList);
        requestContext.setUrlParameters(urlParams);

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
