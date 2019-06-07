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
    private static String lastChannelRemoved;
    private static String lastMsgActive;
    private static String channelActive;
    private static Map<String, Channel> channels;
    //PASSA IP + PORTA RETORNA O NICK
    private static Map<String , String> ips;
    private static Map<String, User> usuarios;
    private static List<String> keysChannels;

    private static boolean statusServer;
    private static String stringToSend;

    TCPServer() {
    }

    public static void main(String[] argv) throws Exception {
        String clientSentence;
        String stringToSend;
        ServerSocket welcomeSocket = new ServerSocket(6789);

        ips = new HashMap<>();

        channels = new HashMap<>();
        keysChannels = new ArrayList<>();
        usuarios = new HashMap<>();

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

    static class ReadFrom implements Runnable {

        Thread t;

        public ReadFrom(){
            t = new Thread(ReadFrom.this);
            t.run();
            t.start();
        }

        public void run() {
            try{
                    for (Socket socket: channels.get(lastChannelRemoved).getConexoes()  ) {
                        outToClient = new DataOutputStream(socket.getOutputStream());
                        outToClient.writeBytes("O canal não está mais disponível" + '\n');
                    }
                } catch (IOException ex) {
                ex.printStackTrace();
            }


        }


    }

    static class BroadCast implements Runnable{

        Thread t;

        public BroadCast(){
            t = new Thread(BroadCast.this);
            t.run();
            t.start();
        }

        public void run() {
            try{
                for (Socket socket: channels.get(channelActive).getConexoes()  ) {
                    outToClient = new DataOutputStream(socket.getOutputStream());
                    outToClient.writeBytes(lastMsgActive + '\n');
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }


        }


    }

    public static String getUniqueClient(){
        return IPAdress.toString();
    }

    public static String processaMsg(String clientSentence) {
        if (!clientSentence.startsWith("/")){
            lastMsgActive = clientSentence;


            try {
                BroadCast broadCast = new BroadCast();
                broadCast.t.join();
                outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                outToClient.writeBytes(lastMsgActive + '\n');

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        else if (clientSentence.startsWith("/nick")) {
            Set<String> keys = channels.keySet();
            for (String key : keys) {
                if (key != null) {
                    if (channels.get(key).getNome().equalsIgnoreCase(clientSentence.substring(6))) {
                        return "10";
                    }

                }
            }
            if (ips.containsKey(getUniqueClient())){
                ips.replace(getUniqueClient(), clientSentence.substring(6));
            }
            else {
                ips.put(getUniqueClient(), clientSentence.substring(6));
            }
            return "20";

        } else if (clientSentence.startsWith("/start")) {
            String[] ms = clientSentence.split("/", 5);

            //*****start/nickame/canal/servidor/porta
            //PRECISA VERIFICAR SE JA TEM UM USUARIO COM AQUELE NOME
   //         channels.get(ms[3]).addParticipante(ms[2]);
            //CRIA USUARIO NA LISTA
            usuarios.put(ms[2], new User(ms[2], ms[3]));
            return "20";

        } else if (clientSentence.startsWith("/create")) {
            try {
                String ms = clientSentence.substring(8);
                Channel channel = new Channel(ms, getUniqueClient());
                channels.put(ms, channel);
                keysChannels.add(ms);
                channels.get(ms).addParticipante(ips.get(getUniqueClient()));
                channels.get(ms).addConexoes(connectionSocket);

                return ms;
            } catch (Exception e) {
                return "10";
            }

        } else if (clientSentence.startsWith("/remove")) {
            //ADMIN PERMISSION
            String ms = clientSentence.substring(8);
            try {
                if (!channels.get(ms).getAdmin().equals(getUniqueClient())) {
                    return "11";
                } else {

                    lastChannelRemoved = ms;

                    ReadFrom readFrom = new ReadFrom();
                    readFrom.t.join();


                    channels.remove(ms);
                    keysChannels.remove(ms);

                    outToClient = new DataOutputStream(connectionSocket.getOutputStream());


                    //INICIA THREAD PRA MANDAR PRA TODOS

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
                    channelActive = ch;
                    channels.get(ch).addParticipante(ips.get(getUniqueClient()));
                    channels.get(ch).addConexoes(connectionSocket);
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
                channels.get(ch).removeParticipante(ips.get(getUniqueClient()));
                return "20";
            } catch (Exception e) {
                return "10";
            }

        } else if (clientSentence.startsWith("/names")) {
            try {
                String ch = clientSentence.substring(6);
                return channels.get(ch).getParticipantes().toString();
            }
            catch (Exception e){
                return "10";
            }

        } else if (clientSentence.startsWith("/kick")) {
            //ADMIN PERMISSION
            try {
                //kick/channel/nickname
                String[] ms = clientSentence.split("/", 4);
                if (!channels.get(ms[2]).getAdmin().equals(getUniqueClient())) {
                    return "11";
                } else {
                    Channel channel = channels.get(ms[2]);
                    if (channel.getParticipantes().contains(ms[3])) {
                        channels.get(ms[2]).removeParticipante(ips.get(getUniqueClient()));
                        return "20";
                    }
                    return "10";
                }
            } catch (Exception e) {
                return "10";

            }


        } else if (clientSentence.startsWith(("/msg"))) {
            //kick/channel/nickname/msg
            try {
                String[] ms = clientSentence.split("/", 3);
                if (channels.get(ms[1]).getParticipantes().contains(ms[2])) {
                    //MANDAR MSG PRO USUÁRIO
                }
            }
            catch (Exception e){
                return "10";
            }

        } else if (clientSentence.startsWith("/quit")) {
            try {
                Set<String> keys = channels.keySet();
                for (String key : keys) {
                    if (key != null) {
                        if (channels.get(key).getNome() == ips.get(getUniqueClient())) {
                            channels.get(key).removeParticipante(ips.get(getUniqueClient()));
                        }
                    }
                }
            }
            catch (Exception e){
                return "10";
            }

        }
        return "10";
    }
}
/*
class ThreadReadFrom extends Thread {
    @Override
    public void run() {
        try {
            for (Socket socket : channels.get(channel).getConexoes()) {
                outToClient = new DataOutputStream(socket.getOutputStream());
                outToClient.writeBytes("O canal não está mais disponível" + '\n');
            }
        } catch (
                IOException ex) {
            ex.printStackTrace();
        }

    }

}
*/


