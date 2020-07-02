package battleshipv2.sockets;

public class Server {
    public String name;
    public int port;
    public int connections;

    public Server(String name, String port) {
        this.name = name;
        this.port = parsePort(port);
    }

    public static int parsePort(String port) {
        int p = 0;
        try {
            int tmp = Integer.parseInt(port);
            if (tmp < 1024 || tmp > 35535) throw new NumberFormatException();
            p = tmp;
        } catch (NumberFormatException ex) {
            throw ex;
        }
        return p;
    }

}
