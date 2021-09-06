/*
Giulio Baricci 740285 Varese
Guido Bernasconi 740539 Varese
Davide Feldkircher 740956 Varese
 */

package common;

import java.io.Serializable;

/**
 * La classe CentroVaccinale serve per gestire i dati dei centri vaccinali da registrare o da prelevare dal database
 * @author Guido Bernasconi
 */

public class CentroVaccinale implements Serializable {

    /**
     * Nome del Centro Vaccinale
     */
    private String nome;

    /**
     * Tipo del Centro Vaccinale (Hub, Aziendale oppure Ospedaliero)
     */
    private String tipologia;

    /**
     * Indirizzo del Centro Vaccinale
     */
    private String indirizzo;

    /**
     * Costruttore per la classe CentroVaccinale
     * @param nome Nome del Centro Vaccinale
     * @param tipologia Tipologia del Centro Vaccinale
     * @param indirizzo Indirizzo del Centro Vaccinale
     */
    public CentroVaccinale(String nome, String tipologia, String indirizzo) {
        this.nome = nome;
        this.tipologia = tipologia;
        this.indirizzo = indirizzo;
    }

    /**
     * Costruttore di default per la classe CentroVaccinale
     */
    public CentroVaccinale() {
        nome = "";
        tipologia = "";
        indirizzo = "";
    }

    /**
     * Funzione getter per l'attributo nome
     * @return Ritorna il nome del Centro Vaccinale
     */
    public String getNome() {
        return nome;
    }

    /**
     * Metodo getter per l'attributo tipologia
     * @return Ritorna il tipo del Centro Vaccinale
     */
    public String getTipologia() {
        return tipologia;
    }

    /**
     * Metodo getter per l'attributo indirizzo
     * @return Ritorna l'indirizzo del Centro Vaccinale
     */
    public String getIndirizzo() {
        return indirizzo;
    }
}
