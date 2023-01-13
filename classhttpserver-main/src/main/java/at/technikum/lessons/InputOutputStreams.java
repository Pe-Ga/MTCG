package at.technikum.lessons;

import java.io.*;

public class InputOutputStreams {
    public static void main(String[] args) {

        File file = new File("./mytext.txt");
        try (Writer w = new OutputStreamWriter(new FileOutputStream(file))) {
            w.write("Test aus java");
        } catch (IOException e) {
            System.err.println(e);
        }

        try (BufferedReader r = new BufferedReader(
                     new InputStreamReader(new FileInputStream(file)))) {
            System.out.println(r.readLine());
        } catch( IOException e) {
            System.err.println(e);
        }


    }
}
