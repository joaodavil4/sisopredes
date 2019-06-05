//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import Model.User;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

class TCPClient {
    private static BufferedReader inFromServer;
    private static Boolean statusClient;
    private static Socket clientSocket;
    private static User usr;



    TCPClient() {
    }

    public static void main(String[] argv) throws Exception {
        String sentence;
        String modifiedSentence;

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        int porta = 6789;
        String servidor = "localhost";

        System.out.println("Conectando ao servidor " + servidor + ":" + porta);

        while (true) {
            Socket clientSocket = new Socket(servidor, porta);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("Digite string a ser enviada para o servidor");
            sentence = inFromUser.readLine();
            outToServer.writeBytes(sentence + '\n');
            modifiedSentence = inFromServer.readLine();
            System.out.println("Recebido do servidor: " + processaMsgServer(sentence, modifiedSentence));
        }
//        clientSocket.close();
    }

    public static String processaMsgServer(String msgUser, String msgServer){


        if (!msgUser.startsWith("/")) {
//            outToServer.writeBytes("<" + usr.getNick() + "> " + msg + '\n');
//            String echo = inFromServer.readLine();
//            System.out.println("FROM SERVER: " + echo);
        }
        else if (msgUser.startsWith("/nick")) {
            //AJEITAR CONFORME A THREAD
            if (msgServer == "20") {
                usr.setNick("");
                return "Nickname alterado!";
            }
            else {
                return "Nickname já existente";
            }

        } else if (msgUser.startsWith("/create")) {
            if (msgServer == "20"){
                return "Canal criado";
            }
            else {
                return "Não foi possível criar o canal.";
            }

        } else if (msgUser.startsWith("/remove")) {
            if (msgServer == "20"){
                return "Can";
            }
            else {
                return "Não foi possível criar o canal.";
            }

        } else if (msgUser.startsWith("/list")) {


        } else if (msgUser.startsWith("/join")) {

        } else if (msgUser.startsWith("/part")) {

        } else if (msgUser.startsWith("/names")) {

        } else if (msgUser.startsWith("/kick")) {

        } else if (msgUser.startsWith(("msg"))) {

        } else if (msgUser.startsWith("/quit")) {

//                clientSocket.close();
        }


        return "k";
    }


}

