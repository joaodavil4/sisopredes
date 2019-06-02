//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import Model.User;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

class TCPClient {
    private static BufferedReader inFromServer;
    private static Boolean statusClient;
    private static  User usr;



    TCPClient() {
    }

    public static void main(String[] argv) throws Exception {
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        Socket clientSocket = new Socket("127.0.0.1", 6790);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        //CONFIGURAÇÃO INICIAL USUÁRIO
        System.out.println("Digite o seu nick");
        String name = inFromUser.readLine();
        System.out.println("Escolha o canal desejado");
        String channel = inFromUser.readLine();

        usr = new User(name, channel);
        String msg = "";

        t1ReadFromServer.run();

        do {
            System.out.print("Digite uma mensagem: ");
            msg = inFromUser.readLine();

            if (!msg.startsWith("/")) {
                outToServer.writeBytes("<" + usr.getNick() + "> " + msg + '\n');
//            String echo = inFromServer.readLine();
//            System.out.println("FROM SERVER: " + echo);


            }
            else if (msg.startsWith("/nick")) {
                outToServer.writeBytes("/nick" + msg);

                //AJEITAR CONFORME A THREAD
                if (inFromServer.readLine() == "20") {
                    usr.setNick("");
                    System.out.println("Nickname alterado!");
                }
                else {
                    System.out.println("Nickname já existente.");
                }

            } else if (msg.startsWith("/create")) {

            } else if (msg.startsWith("/remove")) {

            } else if (msg.startsWith("/list")) {

            } else if (msg.startsWith("/join")) {

            } else if (msg.startsWith("/part")) {

            } else if (msg.startsWith("/names")) {

            } else if (msg.startsWith("/kick")) {

            } else if (msg.startsWith(("msg"))) {

            } else if (msg.startsWith("/quit")) {

                clientSocket.close();
            }
        }
        while (!msg.startsWith("/quit"));



    }

    //THREAD PRA LER AS MSGS DO SERVIDOR
    private static Runnable t1ReadFromServer = new Runnable() {
        public void run() {
            do {
                try {
                    if(inFromServer.readLine() == "20"){
                        usr.setNick("");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }while (!statusClient);


        }
    };
}
