//import java.io.*;
//import java.net.Socket;
//
//public class ServerThread extends Thread {
//    private Socket socket;
//    private BufferedReader in;
//    private BufferedWriter out;
//
//    public ServerThread(Socket socket) throws IOException {
//        this.socket = socket;
//        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//        start();
//    }
//    @Override
//    public void run() {
//        String word;
//        try {
//
//            while (true) {
//                word = in.readLine();
//                if(word.equals("stop")) {
//                    break;                }
//                for (ServerThread vr : Server.serverList) {
//                    vr.send(word); // отослать принятое сообщение с
//                    // привязанного клиента всем остальным включая его
//                }
//            }
//
//        } catch (IOException e) {
//        }
//    }
//
//    private void send(String msg) {
//        try {
//            out.write(msg + "\n");
//            out.flush();
//        } catch (IOException ignored) {}
//    }
//}