package Model;

import java.util.List;

public class Channel {
    private List<User> participantes;
    private String nome;

    public List<User> getParticipantes(){
        return participantes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
