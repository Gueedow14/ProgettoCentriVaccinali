package common;

import java.io.Serial;
import java.io.Serializable;

public class EventoAvverso implements Serializable {


    private int id = 0;
    private String evento;
    private int severita;
    private String note;
    private String cv;
    private String cittadino;

    public int getId() {
        return id;
    }

    public String getTesto() {
        return note;
    }

    public int getSeverita() {
        return severita;
    }

    public String getEvento() {
        return evento;
    }

    public String getCittadino() {
        return cittadino;
    }

    public EventoAvverso(String evento, int severita, String note, String cv, String c) {
        this.evento = evento;
        this.severita = severita;
        this.note = note;
        this.cv = cv;
        cittadino = c;
    }

    public EventoAvverso(int id, String evento, int severita, String note, String cv, String c) {
        this.id = id;
        this.evento = evento;
        this.severita = severita;
        this.note = note;
        this.cv = cv;
        cittadino = c;
    }

    public EventoAvverso() {
        id = 0;
        this.evento = "";
        this.severita = 0;
        this.note = "";
        this.cv = "";
        this.cittadino = "";
    }

    public String getCv() {
        return cv;
    }

}
