package Model;

import java.util.ArrayList;
import java.util.List;

public class Channel {
    private List<String> participantes;
    private String nome;



    private String admin; //IP + PORTA

    public Channel(String nome, String admin){
        participantes = new ArrayList<>();
        this.nome = nome;
        this.admin = admin;
    }

    public List<String> getParticipantes(){
        return participantes;
    }

    public void addParticipante(String user){
        participantes.add(user);
    }

    public void removeParticipante(String user){
        participantes.remove(user);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }
}
