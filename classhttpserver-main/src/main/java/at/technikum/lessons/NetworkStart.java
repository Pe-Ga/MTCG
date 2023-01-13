package at.technikum.lessons;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkStart {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(3000)) {
            final Socket socket = serverSocket.accept();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader
                            (socket.getInputStream()));
            String input = "";
            do {
                input = br.readLine();
                System.out.println(input);
            } while (!input.equals(""));

            Writer w = new OutputStreamWriter(socket.getOutputStream());
            w.write("HTTP/1.1 200 OK");
            w.write("");
            w.flush();
        } catch (IOException e) {
            System.err.println(e);
        }

    }
}
