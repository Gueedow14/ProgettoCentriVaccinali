/*
Giulio Baricci 740285 Varese
Guido Bernasconi 740539 Varese
Davide Feldkircher 740956 Varese
 */

package common;

import java.io.Serial;
import java.io.Serializable;

/**
 * La classe Evento Avverso serve per gestire i dati relativi alle segnalazioni di eventi avversi presentatisi dopo l'inoculazione del vaccino
 * @author Guido Bernasconi
 */
public class EventoAvverso implements Serializable {

    /**
     * Id dell'evento avverso, di default è impostato a 0
     */
    private int id = 0;

    /**
     * Evento presentatosi dopo la vaccinazione
     */
    private String evento;

    /**
     * Grado di intensità dell'evento presentatosi (da 1 a 5)
     */
    private int severita;

    /**
     * Note aggiuntive, opzionali, alla segnalazione dell'evento
     */
    private String note;

    /**
     * Centro Vaccinale dove è stata effettuata l'inoculazione
     */
    private String cv;

    /**
     * Cittadino che segnala l'evento avverso
     */
    private String cittadino;

    /**
     * Metodo getter per l'attributo id
     * @return Ritorna l'id dell'evento registrato
     */
    public int getId() {
        return id;
    }

    /**
     * Metodo getter per l'attributo note
     * @return Ritorna le note aggiuntive di questa segnalazione, se ci sono
     */
    public String getTesto() {
        return note;
    }

    /**
     * Metodo getter per l'attributo cv
     * @return Ritorna il nome del centro vaccinale dove è stata inoculata la dose di vaccino
     */
    public String getCv() {
        return cv;
    }

    /**
     * Metodo getter per l'attributo severità
     * @return Ritorna il grado di intensità dell'evento
     */
    public int getSeverita() {
        return severita;
    }

    /**
     * Metodo getter per l'attributo evento
     * @return Ritorna il tipo di evento verificatosi
     */
    public String getEvento() {
        return evento;
    }

    /**
     * Metodo getter per l'attributo cittadino
     * @return Ritorna il cittadino che ha segnalato l'evento avverso
     */
    public String getCittadino() {
        return cittadino;
    }

    /**
     * Costruttore usato qunado bisogna registrare una segnalazione
     * @param evento Tipo di evento presentatosi
     * @param severita Grado di severità dell'evento
     * @param note Note aggiuntive, dato opzionale
     * @param cv Nome del centro vaccinale dove è stata effettuata la vaccinazione
     * @param c Cittadino che segnala l'evento
     */
    public EventoAvverso(String evento, int severita, String note, String cv, String c) {
        this.evento = evento;
        this.severita = severita;
        this.note = note;
        this.cv = cv;
        cittadino = c;
    }

    /**
     * Costruttore usato quando bisogna prendere le segnalazioni dal database
     * @param id Id della segnalazione dell'evento
     * @param evento Tipo di evento presentatosi
     * @param severita Grado di severità dell'evento
     * @param note Note aggiuntive, dato opzionale
     * @param cv Nome del centro vaccinale dove è stata effettuata la vaccinazione
     * @param c Cittadino che segnala l'evento
     */
    public EventoAvverso(int id, String evento, int severita, String note, String cv, String c) {
        this.id = id;
        this.evento = evento;
        this.severita = severita;
        this.note = note;
        this.cv = cv;
        cittadino = c;
    }

    /**
     * Costruttore di default per la classe Evento Avverso
     */
    public EventoAvverso() {
        id = 0;
        this.evento = "";
        this.severita = 0;
        this.note = "";
        this.cv = "";
        this.cittadino = "";
    }
}
