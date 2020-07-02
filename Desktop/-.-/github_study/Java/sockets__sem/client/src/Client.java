import java.io.*;
import java.net.Socket;

public class Client {
    private static Socket clientSocket;
    private static BufferedReader reader;
    private static BufferedReader in;
    private static BufferedWriter out;

    public static void main(String[] args) {
        try {
            try {
                clientSocket = new Socket("localhost", 8000); // этой строкой мы запрашиваем
                while (true) {
                    reader = new BufferedReader(new InputStreamReader(System.in));
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                    System.out.println("Input message:");
                    String word = reader.readLine();
                    out.write(word + "\n");
                    out.flush();
                    if (word.equals("exit")) break;
                    String serverWord = in.readLine();
                    System.out.println("Is typing..");
                    System.out.println("Not me:" + serverWord);
                }
            } finally {
                System.out.println("Client is left");
                clientSocket.close();
                in.close();
                out.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }

    }
}

