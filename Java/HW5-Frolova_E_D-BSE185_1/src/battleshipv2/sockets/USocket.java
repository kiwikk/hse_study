package battleshipv2.sockets;

import battleshipv2.panes.GamePane;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class USocket {
    public static Socket client;
    public static ServerSocket server;
    public static BufferedReader reader;
    public static BufferedWriter writer;

    public Server uServer;
    public Client uClient;

    static boolean isClosed;

    public void startServer(Server ser) throws IOException {
        this.uServer = ser;
        server = new ServerSocket(uServer.port, 0);
        GamePane.logger.setTextMessage("Server was started");
    }

    public void startClient(Client cl) throws IOException {
        this.uClient = cl;

        client = new Socket(uClient.host, uClient.port);
        reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        GamePane.logger.setTextMessage("Client was started.");

    }

    public String startServerConnection() {
        String name = "";
        try {
            while (client == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Waiting a competitor");
                alert.getDialogPane().lookupButton(ButtonType.OK).setVisible(false);
                alert.show();
                client = server.accept();
                server.close();
                alert.close();
            }
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            name = reader.readLine();
            writer.write(uServer.name + "\n");
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        GamePane.logger.setTextMessage("Server connection was created.");
        return name;
    }

    public String startClientConnection() {
        String name = "";
        try {
            writer.write(uClient.name + "\n");
            writer.flush();
            name = reader.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        GamePane.logger.setTextMessage("Client connection was created.");
        return name;
    }

    public static void writeCoordinates(int x, int y) {
        try {
            writer.write(x + " " + y + " " + "\n");
            System.out.println("coordinates: " + x + " " + y + " " + "\n");
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void writeState(int state) {
        try {
            writer.write(state + "\n");
            System.out.println("state " + state + "\n");
            writer.flush();
        } catch (IOException ex) {
            //ex.printStackTrace();
        }
    }

    public static String readResponse() {
        if (!client.isClosed() && !client.isOutputShutdown()) {
            System.out.println("in readResponse");
            String message = "";
            try {
                Thread.sleep(100);
                message = reader.readLine();
                System.out.println("message: " + message);
                return message;
            } catch (IOException ex) {
                //ex.printStackTrace();
                System.out.println("exp");
            } catch (Exception ignored) {
                return "";
            }
        }
        return "error";
    }

    public static void closeTheGame() {
        isClosed = true;
        try {
            reader.close();
            writer.close();
            client.close();
        } catch (IOException ex) {
            //ex.printStackTrace();
        }
    }

}
