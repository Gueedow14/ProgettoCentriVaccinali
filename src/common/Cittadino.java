package common;

import java.io.Serializable;

/**
 * La classe Cittadino serve per gestire i dati dei cittadini da registrare o da prelevare dal database
 * @author Guido Bernasconi
 */

public class Cittadino implements Serializable {

    /**
     * L'attributo userid indica l'id univoco che identifica un cittadino registratosi nel database
     */
    private String userid;

    /**
     * L'attributo pwd indica la password del profilo del cittadino registrato nel databse
     */
    private String pwd;

    /**
     * L'attributo nome indica il nome del cittadino
     */
    private String nome;

    /**
     * L'attributo cognome indica il cognome del cittadino
     */
    private String cognome;

    /**
     * L'attributo cf indica il codice fiscale del cittadino
     */
    private String cf;

    /**
     * L'attributo idvaccinazione indica l'id della vaccinazione, di default è 0
     */
    private short idvaccinazione = 0;

    /**
     * L'attributo mail indica l'indirizzo di posta elettronica del cittadino
     */
    private String mail;

    /**
     * L'attributo cv indica il nome del centro vaccinale a cui si è registrato il cittadino
     */
    private String cv;

    /**
     * Costruttore della classe Cittadino per quando si deve registrare un nuovo cittadino
     * @param uid Id univoco del cittadino
     * @param pwd Password del cittadino
     * @param nome Nome del cittadino
     * @param cognome Cognome del cittadino
     * @param cf Codice fiscale del cittadino
     * @param mail indirizzo di posta elettronica del cittadino
     * @param cv Nome del centro vaccinale a cui si è registrato il cittadino
     */
    public Cittadino(String uid, String pwd, String nome, String cognome, String cf, String mail, String cv) {
        userid = uid;
        this.pwd = pwd;
        this.nome = nome;
        this.cognome = cognome;
        this.cf = cf;
        idvaccinazione = 0;
        this.mail = mail;
        this.cv = cv;
    }


    /**
     * Costruttore della classe Cittadino per quando si deve prendere un cittadino dal database
     * @param uid Id univoco del cittadino
     * @param pwd Password del cittadino
     * @param nome Nome del cittadino
     * @param idvaccinazione Id della vaccinazione effettuata dal cittadino
     * @param cognome Cognome del cittadino
     * @param cf Codice fiscale del cittadino
     * @param mail indirizzo di posta elettronica del cittadino
     * @param cv Nome del centro vaccinale a cui si è registrato il cittadino
     */
    public Cittadino(String uid, String pwd, String nome, String cognome, short idvaccinazione, String cf, String mail, String cv) {
        userid = uid;
        this.pwd = pwd;
        this.nome = nome;
        this.cognome = cognome;
        this.cf = cf;
        this.idvaccinazione = idvaccinazione;
        this.mail = mail;
        this.cv = cv;
    }

    /**
     * Costruttore di default della classe Cittadino
     */
    public Cittadino() {
        userid = "";
        pwd = "";
        nome = "";
        cognome = "";
        cf = "";
        idvaccinazione = 0;
        mail = "";
        cv = "";
    }


    /**
     * Metodo getter per l'attributo idvaccinazione
     * @return Ritorna l'id della vaccinazione del cittadino, se viene ritornato 0 allora il cittadino non ha ricevuto nessuna dose di vaccino
     */
    public short getIdvaccinazione() {
        return idvaccinazione;
    }

    /**
     * Metodo getter per l'attributo mail
     * @return Ritorna l'indirizzo di posta elettronica del cittadino
     */
    public String getMail() {
        return mail;
    }

    /**
     * Metodo getter per l'attributo cv
     * @return Ritorna il nome del centro vaccinale a cui si è iscritto il cittadino
     */
    public String getCv() {
        return cv;
    }

    /**
     * Metodo getter per l'attributo cognome
     * @return Ritorna il cognome del Cittadino
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Metodo getter per l'attributo nome
     * @return Ritorna il nome del Cittadino
     */
    public String getNome() {
        return nome;
    }

    /**
     * Metodo getter per l'attributo pwd
     * @return Ritorna la password del Cittadino
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * Metodo getter per l'attributo userid
     * @return Ritorna l'id del Cittadino
     */
    public String getUserid() {
        return userid;
    }

    /**
     * Metodo getter per l'attributo cf
     * @return Ritorna il codice fiscale del Cittadino
     */
    public String getCf() {
        return cf;
    }
}
