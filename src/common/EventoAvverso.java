package common;

import java.io.Serial;
import java.io.Serializable;

public class EventoAvverso implements Serializable {

    private String evento;
    private int severita;
    private String note;

    public String getTesto() {
        return note;
    }

    public int getSeverita() {
        return severita;
    }

    public String getEvento() {
        return evento;
    }

    public EventoAvverso(String evento, int severita, String note) {
        this.evento = evento;
        this.severita = severita;
        this.note = note;
    }

}
