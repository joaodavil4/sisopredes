package Model;

import java.util.List;

public class Channel {
    private List<String> participantes;
    private String nome;
    private String admin;

    public Channel(String nome, String admin){
        this.nome = nome;
        this.admin = admin;
    }

    public List<String> getParticipantes(){
        return participantes;
    }

    public void addParticipante(String user){
        participantes.add(user);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
