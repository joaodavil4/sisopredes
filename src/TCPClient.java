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


        String msgServidor = "";

        int porta = 6789;
        String servidor = "localhost";
        String nick = "";

        System.out.println("Conectando ao servidor " + servidor + ":" + porta);

        do {
            clientSocket = new Socket(servidor, porta);
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("Digite o seu nick");
            nick = inFromUser.readLine();
            sentence = "/nick " + nick;
            outToServer.writeBytes(sentence + '\n');
            msgServidor = inFromServer.readLine();
            System.out.println(processaMsgServer(sentence, msgServidor));
        }while (msgServidor == "20");


        sentence = "/list";
        outToServer.writeBytes(sentence + '\n');
        System.out.println(processaMsgServer(sentence, inFromServer.readLine()));
        System.out.println("Escolha um canal acima ^");
        String channel = inFromUser.readLine();
        outToServer.writeBytes("/join " + channel + '\n');
       // System.out.println("Recebido do servidor: " + processaMsgServer(sentence, inFromServer.readLine()));

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
            if (msgServer.equals("20")) {
            //    usr.setNick("");
                return "Nickname alterado!";
            }
            else {
                return "Nickname já existente";
            }

        } else if (msgUser.startsWith("/create")) {
            if (msgServer.equals("20")){
                return "Canal criado";
            }
            else {
                return "Não foi possível criar o canal.";
            }

        } else if (msgUser.startsWith("/remove")) {
            if (msgServer == "20"){
                return "Canal removido!";
            }
            else if (msgServer == "11"){
                return "Você não possui permissão para remover um canal.";
            }
            else if (msgServer == "10"){
                return "Não foi possível remover o canal.";
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
            if (msgServer == "20"){
                return "Saiu do canal";
            }
            else {
                return "Não foi possível sair do canal.";
            }
        } else if (msgUser.startsWith("/names")) {
            return msgServer;

        } else if (msgUser.startsWith("/kick")) {
            if (msgServer == "20"){
                return "O usuário foi kickado";
            }
            else {
                return "Não foi possível retirar o usuário.";
            }

        } else if (msgUser.startsWith(("/msg"))) {
            if (msgServer == "20"){
                return "Mensagem enviada";
            }
            else {
                return "Não foi possível enviar a mensagem.";
            }
        } else if (msgUser.startsWith("/quit")) {
    //        clientSocket.close();
        }

        return "k";
    }


}

