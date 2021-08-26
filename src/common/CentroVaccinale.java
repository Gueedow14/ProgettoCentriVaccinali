package common;

import java.io.Serializable;

//Provvisorio
public class CentroVaccinale implements Serializable {

    private String nome;
    private String tipologia;
    private String indirizzo;

    public CentroVaccinale(String nome, String tipologia, String indirizzo) {
        this.nome = nome;
        this.tipologia = tipologia;
        this.indirizzo = indirizzo;
    }

    public CentroVaccinale() {
        nome = "";
        tipologia = "";
        indirizzo = "";
    }

    public String getNome() {
        return nome;
    }

    public String getTipologia() {
        return tipologia;
    }

    public String getIndirizzo() {
        return indirizzo;
    }
}
