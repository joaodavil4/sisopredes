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
    private static InetAddress IPAdress;
    private static Map<String, Channel> channels;
    //PASSA IP + PORTA RETORNA O NICK
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
        keysChannels = new ArrayList<>();

        Channel ch = new Channel ("lol", "kk");
        Channel ch2 = new Channel ("brizadero", "kk");
        channels.put("lol", ch);
        channels.put("brizadero", ch2);

        System.out.println("ponto1");


        while (true) {
            try {
                connectionSocket = welcomeSocket.accept();
                System.out.println("ponto2");
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                System.out.println("ponto3");
                clientSentence = inFromClient.readLine();
                System.out.println("ponto4");
                IPAdress = connectionSocket.getInetAddress();
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
            for (String key : keys) {
                if (key != null) {
                    if (channels.get(key).getNome().equalsIgnoreCase(clientSentence.substring(6))) {
                        return "10";
                    }

                }
            }
            ips.put(IPAdress.toString() + connectionSocket.getPort(), clientSentence.substring(6));
            return "20";

        } else if (clientSentence.startsWith("/start")) {
            String[] ms = clientSentence.split("/", 5);

            //*****start/nickame/canal/servidor/porta
            //PRECISA VERIFICAR SE JA TEM UM USUARIO COM AQUELE NOME
            channels.get(ms[2]).addParticipante(ms[1]);
            //CRIA USUARIO NA LISTA
            usuarios.put(ms[1], new User(IPAdress.toString(), ms[2]));
            //ADICIONA NA LISTA DE IPS O IP + PORTA RELACIONADO AO NICK
            //SO O IP POR ENQNT
            ips.put(IPAdress.toString() + connectionSocket.getPort(), ms[1]);
            return "20";

        } else if (clientSentence.startsWith("/create")) {
            try {
                String ms = clientSentence.substring(7);
                Channel channel = new Channel(ms, "");
                channels.put(ms, channel);
                keysChannels.add(ms);
                channels.get(ms).addParticipante(ips.get(IPAdress.toString() + connectionSocket.getPort()));
                return "20";
            } catch (Exception e) {
                return "10";
            }

        } else if (clientSentence.startsWith("/remove")) {
            //ADMIN PERMISSION
            String ms = clientSentence.substring(9);
            try {
                if (channels.get(ms).getAdmin() != IPAdress.toString() + connectionSocket.getPort()) {
                    return "11";
                } else {
                    channels.remove(ms);
                    keysChannels.remove(ms);
                    Set<String> keys = channels.keySet();
                    for (String key : keys) {
                        //FALTA MANDAR MSG PRA TODOS OS QUE ESTAO NO CANAL

                    }
                    return "20";
                }
            } catch (Exception e) {
                return "10";
            }

        } else if (clientSentence.startsWith("/list")) {
            String list = "";
            Set<String> keys = channels.keySet();
            for (String key : keys) {
                if (key != null) {
                    list += channels.get(key).getNome() + " , ";
                }
            }
            return list;

        } else if (clientSentence.startsWith("/join")) {

            String ch = clientSentence.substring(6);
            if (channels.containsKey(ch)) {
                try {
                    channels.get(ch).addParticipante(ips.get(IPAdress.toString() + connectionSocket.getPort()));
                    return "20";
                } catch (Exception e) {
                    return "10";
                }
            } else {
                return "10";
            }


        } else if (clientSentence.startsWith("/part")) {
            String ch = clientSentence.substring(6);

            try {
                channels.get(ch).removeParticipante(ips.get(IPAdress.toString() + connectionSocket.getPort()));
                return "20";
            } catch (Exception e) {
                return "10";
            }

        } else if (clientSentence.startsWith("/names")) {
            String ch = clientSentence.substring(7);
            return channels.get(ch).getParticipantes().toString();

        } else if (clientSentence.startsWith("/kick")) {
            try {
                //kick/channel/nickname
                String[] ms = clientSentence.split("/", 3);
                if (channels.get(ms[1]).getAdmin() != IPAdress.toString() + connectionSocket.getPort()) {
                    return "11";
                } else {
                    Channel channel = channels.get(ms[1]);
                    if (channel.getParticipantes().contains(ms[2])) {
                        channels.get(ms[1]).removeParticipante(ips.get(IPAdress.toString() + connectionSocket.getPort()));
                        return "20";
                    }
                    return "10";
                }
            } catch (Exception e) {
                return "10";

            }


        } else if (clientSentence.startsWith(("/msg"))) {
            //kick/channel/nickname/msg
            String[] ms = clientSentence.split("/", 3);
            if (channels.get(ms[1]).getParticipantes().contains(ms[2])) {
                //MANDAR MSG PRO USUÁRIO
            }

        } else if (clientSentence.startsWith("/quit")) {
            Set<String> keys = channels.keySet();
            for (String key : keys) {
                if (key != null) {
                    if (channels.get(key).getNome() == ips.get(IPAdress.toString() + connectionSocket.getPort())) {
                        channels.get(key).removeParticipante(ips.get(IPAdress.toString() + connectionSocket.getPort()));
                    }
                }
            }

        }
        return "";
    }
}
