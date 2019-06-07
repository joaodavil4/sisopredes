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
    private static String channel;
    private static DataOutputStream outToServer;
    private static User usr;



    TCPClient() {
    }

    public static void main(String[] argv) throws Exception {
        String sentence;
        Socket clientSocket;
        String sentenceFromServer;

        usr = new User();

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));


        String msgServidor = "";

        int porta = 6789;
        String servidor = "localhost";
        String nick = "";

        System.out.println("Conectando ao servidor " + servidor + ":" + porta);

        do {
            conectaServer(servidor, porta);
            System.out.println("Digite o seu nick");
            nick = inFromUser.readLine();
            sentence = "/nick " + nick;
            outToServer.writeBytes(sentence + '\n');
            msgServidor = inFromServer.readLine();
            System.out.println(processaMsgServer(sentence, msgServidor));
        }while (msgServidor == "20");


        conectaServer(servidor, porta);
        sentence = "/list";
        outToServer.writeBytes(sentence + '\n');
        System.out.println(processaMsgServer(sentence, inFromServer.readLine()));
        System.out.println("Escolha um canal acima ^");
        conectaServer(servidor, porta);
        channel = inFromUser.readLine();
        sentence = "/join " + channel;
        outToServer.writeBytes(sentence + '\n');
        System.out.println(processaMsgServer(sentence, inFromServer.readLine()));

        conectaServer(servidor, porta);
        outToServer.writeBytes("/start/ " + usr.getNick() + "/" + usr.getChannel() + "/" + servidor + "/" + porta +'\n');



        while (true) {
            clientSocket = new Socket(servidor, porta);
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("Digite a mensagem...");
            sentence = inFromUser.readLine();
                if(!sentence.startsWith("/names")){
                    outToServer.writeBytes(sentence + '\n');
                }
                else {
                    outToServer.writeBytes(sentence + usr.getChannel() + '\n');
                }
            sentenceFromServer = inFromServer.readLine();
            System.out.println("Recebido do servidor: " + processaMsgServer(sentence, sentenceFromServer));
        }
//        clientSocket.close();
    }

    public static void conectaServer(String server, int port){

        try {
            clientSocket = new Socket(server, port);
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String processaMsgServer(String msgUser, String msgServer){


        if (!msgUser.startsWith("/")) {
//            outToServer.writeBytes("<" + usr.getNick() + "> " + msg + '\n');
//            String echo = inFromServer.readLine();
//            System.out.println("FROM SERVER: " + echo);
        }
        //COMANDO INICIAL PARA SETAR O USUARIO NO SERVER
        else if (msgUser.startsWith("/start")){
            if (msgServer.equals("20")){
                usr.setChannel(channel);
                return "Conectado ao canal!";
            }
            else {
                return "Nickname já cadastrado.";
            }

        }
        else if (msgUser.startsWith("/nick")) {
            //AJEITAR CONFORME A THREAD
            if (msgServer.equals("10")) {
                return "Nickname já existente";
            }
            else {
//                usr.setNick(msgServer);
                return "Nickname alterado!";
            }

        } else if (msgUser.startsWith("/create")) {
            if (msgServer.equals("20")){
                return "Canal criado";
            }
            else {
                return "Não foi possível criar o canal.";
            }

        } else if (msgUser.startsWith("/remove")) {
            if (msgServer.equals("20")){
                return "Canal removido!";
            }
            else if (msgServer.equals("11")){
                return "Você não possui permissão para remover um canal.";
            }
            else if (msgServer.equals("10")){
                return "Não foi possível remover o canal.";
            }

        } else if (msgUser.startsWith("/list")) {
            return msgServer;


        } else if (msgUser.startsWith("/join")) {
            if (msgServer.equals("20")){
                usr.setChannel(channel);
                return "Entrou no canal";
            }
            else {
                return "Não foi possível entrar no canal.";
            }

        } else if (msgUser.startsWith("/part")) {
            if (msgServer.equals("20")){
                return "Saiu do canal";
            }
            else {
                return "Não foi possível sair do canal.";
            }
        } else if (msgUser.startsWith("/names")) {
            return msgServer;

        } else if (msgUser.startsWith("/kick")) {
            if (msgServer.equals("20")){
                return "O usuário foi kickado";
            }
            else {
                return "Não foi possível retirar o usuário.";
            }

        } else if (msgUser.startsWith(("/msg"))) {
            if (msgServer.equals("20")){
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

