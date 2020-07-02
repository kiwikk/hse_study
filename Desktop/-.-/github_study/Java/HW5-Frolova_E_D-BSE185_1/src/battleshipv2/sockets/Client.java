package battleshipv2.sockets;

public class Client {
    public String name;
    public String host;
    public int port;

    public Client(String name, String host, String port) {
        this.name = name;
        this.host = host;
        this.port = Server.parsePort(port);
    }
}
