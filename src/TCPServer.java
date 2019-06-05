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
import java.util.*;

class TCPServer {
    private static BufferedReader inFromClient;
    private static DataOutputStream outToClient;
    private static Socket connectionSocket;
    private static ServerSocket welcomeSocket;
    private static String clientSentence;
    private static Map<String, Channel> channels;
    private static List<String> keysChannels;

    private static boolean statusServer;

    TCPServer() {
    }

    public static void main(String[] argv) throws Exception {
        String clientSentence;
        String stringToSend;
        ServerSocket welcomeSocket = new ServerSocket(6789);

        channels = new HashMap<>();

        Channel ch = new Channel ("lol", "kk");
        Channel ch2 = new Channel ("brizadero", "kk");
        channels.put("KK", ch);
        channels.put("briz", ch2);

        System.out.println("ponto1");


        while (true) {
            try {
                Socket connectionSocket = welcomeSocket.accept();
                System.out.println("ponto2");
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                System.out.println("ponto3");
                clientSentence = inFromClient.readLine();
                System.out.println("ponto4");
                stringToSend = processaMsg(clientSentence) + '\n';
                outToClient.writeBytes(stringToSend);
                // connectionSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static String processaMsg(String clientSentence) {
        if (clientSentence.startsWith("/nick")) {
//                        for (Channel c : channels) {
//                            User usr = (User) c.getParticipantes();
//                            //TESTAR
//                            //VERIFICA SE EXISTE ALGUM NICK IGUAL A PARTIR DA PALAVRA ESCRITA DO INDEX 6
//                            if (usr.getNick().equalsIgnoreCase(clientSentence.substring(6))) {
//                                outToClient.writeBytes("10");
//                            }
//                        }
            return "20";

        } else if (clientSentence.startsWith("/create")) {
            String ms = clientSentence.substring(7);
            Channel channel = new Channel(ms, "connectionSocket.getInetAddress()");
            channels.put(ms, channel);
            keysChannels.add(ms);
//                        channels.get(ms).addParticipante();
//                       channel.addParticipante();


        } else if (clientSentence.startsWith("/remove")) {
            String ms = clientSentence.substring(9);
            channels.remove(ms);
            keysChannels.remove(ms);
//                        if (ms)
//                        channels.remove();

        } else if (clientSentence.startsWith("/list")) {
            String list = "";
            Set<String> keys = channels.keySet();
                for (String key: keys ) {
                    if (key != null){
                        list += channels.get(key).getNome() + "\n";
                    }
                }
            return list;

        } else if (clientSentence.startsWith("/join")) {
            String ch = clientSentence.substring(7);
                if (channels.containsKey(ch)) {
                    Channel channel = channels.get(ch);
                    channel.addParticipante("lol");
                    return "20";
                }
                else {
                    return "10";
                }

        } else if (clientSentence.startsWith("/part")) {


        } else if (clientSentence.startsWith("/names")) {

        } else if (clientSentence.startsWith("/kick")) {

        } else if (clientSentence.startsWith(("msg"))) {

        } else if (clientSentence.startsWith("/quit")) {


        }
        return "ok";
    }

}
