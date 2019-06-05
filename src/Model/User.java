package Model;

public class User {

    private String nick;
    private String channel;
    private String[] listMsgs;

    public User(String nick){
        this.nick = nick;
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
