package Model;

public class User {

    private String nick;
    private String channel; //chave
    private String[] listMsgs;

    public User(String nick, String channel){
        this.nick = nick;
        this.channel = channel;
    }

    public User (){

    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }




}
