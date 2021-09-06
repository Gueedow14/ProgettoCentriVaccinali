/*
Giulio Baricci 740285 Varese
Guido Bernasconi 740539 Varese
Davide Feldkircher 740956 Varese
 */

package common;

import java.io.Serializable;

/**
 * La classe Vaccinazione serve per gestire i dati relativi alle prenotazioni effettuate
 * @author Guido Bernasconi
 */
public class Prenotazione implements Serializable {

    /**
     * Id univoco della prenotazione effettuata
     */
    private int idprenotazione = 0;

    /**
     * Id dell'utente che prenota la vaccinazione
     */
    private String userid;

    /**
     * Nome del centro vaccinale dove il cittadino si è registrato
     */
    private String nomecv;

    /**
     * Data della vaccinazione prenotata
     */
    private String data;


    /**
     * Costruttore usato per la creazione di una prenotazione
     * @param userid Id dell'utente che prenota la vaccinazione
     * @param nomecv Nome del centro vaccinale dove il cittadino si è registrato
     * @param data Data della vaccinazione prenotata
     */
    public Prenotazione(String userid, String nomecv, String data) {
        this.userid = userid;
        this.nomecv = nomecv;
        this.data = data;
    }

    /**
     * Costruttore usato quando bisogna prendere dati delle prenotazioni dal database
     * @param idp Id della prenotazione
     * @param userid Id dell'utente che prenota la vaccinazione
     * @param nomecv Nome del centro vaccinale dove il cittadino si è registrato
     * @param data Data della vaccinazione prenotata
     */
    public Prenotazione(int idp, String userid, String nomecv, String data) {
        idprenotazione = idp;
        this.userid = userid;
        this.nomecv = nomecv;
        this.data = data;
    }

    /**
     * Costruttore di default della classe Prenotazione
     */
    public Prenotazione() {
        idprenotazione = 0;
        userid = "";
        nomecv = "";
        data = "";
    }

    /**
     * Metodo getter per l'attributo userid
     * @return Ritorna il valore dell'attributo userid in questa istanza
     */
    public String getUserid() {
        return userid;
    }

    /**
     * Metodo getter per l'attributo nomecv
     * @return Ritorna il valore dell'attributo nomecv in questa istanza
     */
    public String getNomeCV() {
        return nomecv;
    }

    /**
     * Metodo getter per l'attributo data
     * @return Ritorna il valore dell'attributo data in questa istanza
     */
    public String getData() {
        return data;
    }
}
