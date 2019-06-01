//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import Model.Channel;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

class TCPServer {
    private static BufferedReader inFromClient;
    private static DataOutputStream outToClient;
    private List<Channel> channels;

    TCPServer() {
    }

    public static void main(String[] argv) throws Exception {
        ServerSocket welcomeSocket = new ServerSocket(6790);

        while(true) {
            Socket connectionSocket = welcomeSocket.accept();
            inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            String clientSentence = inFromClient.readLine();
            InetAddress IPAddress = connectionSocket.getInetAddress();
            int port = connectionSocket.getPort();
            System.out.println(IPAddress.getHostAddress() + ":" + port + " => " + clientSentence);
            String echo = clientSentence + '\n';
            outToClient.writeBytes(echo);
            connectionSocket.close();
        }
    }

    private static Runnable t1ReadFromClient = new Runnable() {
        public void run() {

                try {
                    String clientSentence = inFromClient.readLine();
                    String echo = clientSentence + '\n';
                    outToClient.writeBytes(echo);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }


        }
    };
}
