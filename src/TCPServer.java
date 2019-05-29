//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

class TCPServer {
    TCPServer() {
    }

    public static void main(String[] argv) throws Exception {
        ServerSocket welcomeSocket = new ServerSocket(6790);

        while(true) {
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            String clientSentence = inFromClient.readLine();
            InetAddress IPAddress = connectionSocket.getInetAddress();
            int port = connectionSocket.getPort();
            System.out.println(IPAddress.getHostAddress() + ":" + port + " => " + clientSentence);
            String echo = clientSentence + '\n';
            outToClient.writeBytes(echo);
            connectionSocket.close();
        }
    }
}
