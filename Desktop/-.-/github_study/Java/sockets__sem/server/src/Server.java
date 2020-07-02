import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {
    private static Socket clientSocket;
    private static ServerSocket server;
    private static BufferedReader reader;
    private static BufferedReader in;
    private static BufferedWriter out;

    public static void main(String[] args) throws IOException {
        //public static LinkedList<ServerThread> serverList=new LinkedList<ServerThread>();
//        ServerSocket server = new ServerSocket(8000);
//        System.out.println("Server is running");
//        Socket socket = server.accept();
//        System.out.println("Client is connected");
//
//        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));    }
        try {
            try {
                server = new ServerSocket(8000);
                System.out.println("Server is running");
                try (Socket clientSocket = server.accept()) {
                    try {
                        while (true) {
                            reader = new BufferedReader(new InputStreamReader(System.in));
                            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                            String word = in.readLine();
                            System.out.println("Not me: " + word);
                            System.out.println("Input message:");
                            String myword = reader.readLine();
                            out.write(myword + "\n");
                            out.flush();
                            if (word.equals("exit")) break;
                            out.write("Me: " + word + "\n");
                            out.flush();
                        }
                    } finally {
                        in.close();
                        out.close();
                    }
                } catch (Exception ex) {
                    clientSocket.close();

                }
            } finally {
                System.out.println("The end of server");
                server.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
