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
        Socket clientSocket;
        String sentenceFromServer;
        DataOutputStream outToServer;

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));



        int porta = 6789;
        String servidor = "localhost";

        System.out.println("Digite o seu nick");
        String nick = inFromUser.readLine();


        System.out.println("Conectando ao servidor " + servidor + ":" + porta);
        clientSocket = new Socket(servidor, porta);
        outToServer = new DataOutputStream(clientSocket.getOutputStream());
        inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        sentence = "/list";
        System.out.println("Escolha um canal acima ^");
        outToServer.writeBytes(sentence + '\n');
        System.out.println("Recebido do servidor: " + processaMsgServer(sentence, inFromServer.readLine()));
        String channel = inFromUser.readLine();
        outToServer.writeBytes("/join " + sentence + '\n');
        System.out.println("Recebido do servidor: " + processaMsgServer(sentence, inFromServer.readLine()));

        User user = new User(nick, channel);
        outToServer.writeBytes("/start/ " + user.getNick() + "/" + user.getChannel() + "/" + servidor + "/" + porta +'\n');



        while (true) {
            clientSocket = new Socket(servidor, porta);
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("Digite string a ser enviada para o servidor");
            sentence = inFromUser.readLine();
            outToServer.writeBytes(sentence + '\n');
            sentenceFromServer = inFromServer.readLine();
            System.out.println("Recebido do servidor: " + processaMsgServer(sentence, sentenceFromServer));
        }
//        clientSocket.close();
    }

    public static String processaMsgServer(String msgUser, String msgServer){


        if (!msgUser.startsWith("/")) {
//            outToServer.writeBytes("<" + usr.getNick() + "> " + msg + '\n');
//            String echo = inFromServer.readLine();
//            System.out.println("FROM SERVER: " + echo);
        }
        //COMANDO INICIAL PARA SETAR O USUARIO NO SERVER
        else if (msgUser.startsWith("/start")){
            if (msgServer == "20") {
                return "Conectado ao canal!";
            }
            else {
                return "Nickname já cadastrado.";
            }

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
            return msgServer;


        } else if (msgUser.startsWith("/join")) {
            if (msgServer == "20"){
                return "Entrou no canal";
            }
            else {
                return "Não foi possível entrar no canal.";
            }

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

