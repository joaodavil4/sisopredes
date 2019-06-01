//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

class TCPClient {

    private String nickname;
    private String channel;


    TCPClient() {
    }

    public static void main(String[] argv) throws Exception {
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        Socket clientSocket = new Socket("127.0.0.1", 6790);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        System.out.print("Digite uma mensagem: ");
        String sentence = inFromUser.readLine();
        outToServer.writeBytes(sentence + '\n');
        String echo = inFromServer.readLine();
        System.out.println("FROM SERVER: " + echo);
        clientSocket.close();
    }
}
