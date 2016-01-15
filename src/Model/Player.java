package Model;

import java.io.Serializable;

/**
 * Created by Emanuele on 21/10/2015.
 */
public class Player implements Serializable {

    private String cognome;
    private int id;
    private String squadra;
    private int costo;
    private char ruolo;
    //Posizione nella squadra. Tipo dif1, dif2. Compresa la panchina: Pdif1 Pdif2
    private String pos;


    public Player(String cognome, int costo, int id, char ruolo, String squadra) {
        this.cognome = cognome;
        this.costo = costo;
        this.id = id;
        this.ruolo = ruolo;
        this.squadra = squadra;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getRuolo() {
        return ruolo;
    }

    public void setRuolo(char ruolo) {
        this.ruolo = ruolo;
    }

    public String getSquadra() {
        return squadra;
    }

    public void setSquadra(String squadra) {
        this.squadra = squadra;
    }


    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public Player(String cognome, int costo, int id, String pos, char ruolo, String squadra, int posPanchina) {
        this.cognome = cognome;
        this.costo = costo;
        this.id = id;
        this.pos = pos;
        this.ruolo = ruolo;
        this.squadra = squadra;

    }


    @Override
    public boolean equals(Object obj) {
        boolean retVal = false;
        System.out.println("CHIAMATACONTAINS");
        if (obj instanceof Player){
            Player player = (Player) obj;
            retVal = player.getCognome().equals(this.getCognome());
            System.out.println(this.getCognome()+" "+ ((Player) obj).getCognome());
        }

        return retVal;
    }


    @Override
    public String toString() {
        return "Player{" +
                "cognome='" + cognome + '\'' +
                ", id=" + id +
                ", squadra='" + squadra + '\'' +
                ", costo=" + costo +
                ", ruolo=" + ruolo +
                ", pos='" + pos + '\'' +
                '}';
    }
}
