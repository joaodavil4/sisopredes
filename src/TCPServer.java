//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import Model.Channel;
import Model.User;
import org.graalvm.compiler.lir.LIRInstruction;

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
    private static Map<String , String> ips;
    private static Map<String, User> usuarios;
    private static List<String> keysChannels;

    private static boolean statusServer;

    TCPServer() {
    }

    public static void main(String[] argv) throws Exception {
        String clientSentence;
        String stringToSend;
        ServerSocket welcomeSocket = new ServerSocket(6789);

        ips = new HashMap<>();

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
            Set<String> keys = channels.keySet();
            for (String key: keys ) {
                if (key != null){
                    if (channels.get(key).getNome().equalsIgnoreCase(clientSentence.substring(6))){
                        return "10";
                    }
                }
            }
            return "20";

        }
        else if (clientSentence.startsWith("/start")){
            String[] ms = clientSentence.split("/", 5);

            //*****start/nickame/canal/servidor/porta
            //PRECISA VERIFICAR SE JA TEM UM USUARIO COM AQUELE NOME
            channels.get(ms[2]).addParticipante(ms[1]);
            //CRIA USUARIO NA LISTA
            usuarios.put(ms[1], new User(ms[1],ms[2]));
            //ADICIONA NA LISTA DE IPS O IP + PORTA RELACIONADO AO NICK
            ips.put(ms[3] + ms[4], ms[1]);
            return "20";

        }
        else if (clientSentence.startsWith("/create")) {
            String ms = clientSentence.substring(7);
            Channel channel = new Channel(ms,  );
            channels.put(ms, channel);
            keysChannels.add(ms);
//                        channels.get(ms).addParticipante();
//                       channel.addParticipante();


        } else if (clientSentence.startsWith("/remove")) {
            String ms = clientSentence.substring(9);
            try {
                channels.remove(ms);
                keysChannels.remove(ms);
                return "20";
            }
            catch (Exception e){
                return "10";
            }

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
            String ch = clientSentence.substring(7);
            channels.get(ch);
             try{
                 return "20";
             }
             catch (Exception e){
                 return "10";
             }

        } else if (clientSentence.startsWith("/names")) {
            String ch = clientSentence.substring(8);
            Channel channel = channels.get(ch);
            return channel.getParticipantes().toString();

        } else if (clientSentence.startsWith("/kick")) {
            //kick/channel/nickname
            String[] ms = clientSentence.split("/", 3);
            try {
                Channel channel = channels.get(ms[1]);
                if (channel.getParticipantes().contains(ms[2])){
                    channel.getParticipantes().remove(ms[2]);
                    return "20";
                }
            }
            catch (Exception e){
                return "10";
            }


        } else if (clientSentence.startsWith(("msg"))) {
            //kick/nickname/msg
            String[] ms = clientSentence.split("/", 3);

        } else if (clientSentence.startsWith("/quit")) {


        }
        return "ok";
    }

}
