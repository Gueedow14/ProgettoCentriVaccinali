/*
Giulio Baricci 740285 Varese
Guido Bernasconi 740539 Varese
Davide Feldkircher 740956 Varese
 */

package common;

import java.io.Serial;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * La classe Vaccinazione serve per gestire i dati relativi alle vaccinazioni effettuate
 * @author Guido Bernasconi
 */
public class Vaccinazione implements Serializable {

    /**
     * Codice fiscale del cittadino vaccinato
     */
    private String cf;

    /**
     * Data vaccinazione
     */
    private String data;

    /**
     * Nome del centro vaccinale in cui è stata effettuata la vaccinazione
     */
    private String nomeCV;

    /**
     * Nome del cittadino vaccinato
     */
    private String nome;

    /**
     * Cognome del cittadino vaccinato
     */
    private String cognome;

    /**
     * Tipologia di vaccino inoculato (Pfizer, J&J, Moderna o Astrazeneca)
     */
    private String tipo;

    /**
     * Id univoco della vaccinazione, di default settato a 0
     */
    private short idVaccinazione = 0;

    /**
     * Metodo getter per l'attributo nome
     * @return Ritorna il valore dell'attributo nome in questa istanza
     */
    public String getNome() {
        return nome;
    }

    /**
     * Metodo getter per l'attributo cognome
     * @return Ritorna il valore dell'attributo cognome in questa istanza
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Metodo getter per l'attributo data
     * @return Ritorna il valore dell'attributo data in questa istanza
     */
    public String getData() {
        return data;
    }

    /**
     * Metodo getter per l'attributo nomeCV
     * @return Ritorna il valore dell'attributo nomeCV in questa istanza
     */
    public String getNomeCV() {
        return nomeCV;
    }

    /**
     * Metodo getter per l'attributo tipo
     * @return Ritorna il valore dell'attributo tipo in questa istanza
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Metodo getter per l'attributo cf
     * @return Ritorna il valore dell'attributo cf in questa istanza
     */
    public String getCf() {
        return cf;
    }

    /**
     * Costruttore usato quando bisogna registrare una vaccinazione
     * @param nomeCV Nome del centro vaccinale dove la dose è stata inoculata
     * @param nome Nome del cittadino vaccinato
     * @param cognome Cognome del cittadino vaccinato
     * @param cf Codice fiscale del cittadino vaccinato
     * @param data Data della vaccinazione
     * @param tipo Tipo del vaccino inoculato (Pfizer, Astrazeneca, Moderna, J&J)
     */
    public Vaccinazione(String nomeCV, String nome, String cognome, String cf, String data, String tipo){
        this.nomeCV = nomeCV;
        this.nome = nome;
        this.cognome = cognome;
        this.cf = cf;
        this.data = data;
        this.tipo = tipo;
    }

    /**
     * Costruttore di default per la classe Vaccinazione
     */
    public Vaccinazione(){
        idVaccinazione = 0;
        this.nomeCV = "";
        this.nome = "";
        this.cognome = "";
        this.cf = "";
        this.data = "";
        this.tipo = "";
    }

    /**
     * Costruttore usato quando bisogna prendere le vaccinazioni dal database
     * @param idv Id della vaccinazione
     * @param nomeCV Nome del centro vaccinale dove la dose è stata inoculata
     * @param nome Nome del cittadino vaccinato
     * @param cognome Cognome del cittadino vaccinato
     * @param cf Codice fiscale del cittadino vaccinato
     * @param data Data della vaccinazione
     * @param tipo Tipo del vaccino inoculato (Pfizer, Astrazeneca, Moderna, J&J)
     */
    public Vaccinazione(short idv, String nomeCV, String nome, String cognome, String cf, String data, String tipo){
        idVaccinazione = idv;
        this.nomeCV = nomeCV;
        this.nome = nome;
        this.cognome = cognome;
        this.cf = cf;
        this.data = data;
        this.tipo = tipo;
    }


}
