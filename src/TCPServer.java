//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import Model.Channel;
import Model.User;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

class TCPServer {
    private static BufferedReader inFromClient;
    private static DataOutputStream outToClient;
    private static ArrayList<Channel> channels;
    private static boolean statusServer;

    TCPServer() {
    }

    public static void main(String[] argv) throws Exception {
        ServerSocket welcomeSocket = new ServerSocket(6790);
        Socket connectionSocket = welcomeSocket.accept();
        inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        outToClient = new DataOutputStream(connectionSocket.getOutputStream());


        while(statusServer) {
            t1ReadFromClient.run();

            String clientSentence = inFromClient.readLine();
            InetAddress IPAddress = connectionSocket.getInetAddress();
            int port = connectionSocket.getPort();
            System.out.println(IPAddress.getHostAddress() + ":" + port + " => " + clientSentence);
            String echo = clientSentence + '\n';
            outToClient.writeBytes(echo);
            connectionSocket.close();
        }

        connectionSocket.close();
    }

    private static Runnable t1ReadFromClient = new Runnable() {
        public void run() {

            do {

                try {
                    String clientSentence = inFromClient.readLine();

                    if (clientSentence.startsWith("/nick")) {
                        for (Channel c : channels) {
                            User usr = (User) c.getParticipantes();
                            //TESTAR
                            //VERIFICA SE EXISTE ALGUM NICK IGUAL A PARTIR DA PALAVRA ESCRITA DO INDEX 6
                            if (usr.getNick().equalsIgnoreCase(clientSentence.substring(6))) {
                                outToClient.writeBytes("10");
                            }
                        }
                        outToClient.writeBytes("20");

                    } else if (clientSentence.startsWith("/create")) {
                        Channel channel= new Channel(clientSentence.substring(7), "connectionSocket.getInetAddress()");
                       channels.add(channel);
//                       channel.addParticipante();


                    } else if (clientSentence.startsWith("/remove")) {
//                        channels.remove();

                    } else if (clientSentence.startsWith("/list")) {
                        String list = "";
                        for (Channel c: channels ) {
                            list += c.getNome() + "\n";
                        }
                        outToClient.writeBytes(list);

                    } else if (clientSentence.startsWith("/join")) {
                        String ch = clientSentence.substring(6);
                        //TROCAR OS CANAIS PRA HASHMAP PRA PODER ACESSAR PELA CHAVE
//                        channels.get()

                    } else if (clientSentence.startsWith("/part")) {

                    } else if (clientSentence.startsWith("/names")) {

                    } else if (clientSentence.startsWith("/kick")) {

                    } else if (clientSentence.startsWith(("msg"))) {

                    } else if (clientSentence.startsWith("/quit")) {


                    }
                    String echo = clientSentence + '\n';
                    outToClient.writeBytes(echo);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }while (statusServer);
        }
    };
}
