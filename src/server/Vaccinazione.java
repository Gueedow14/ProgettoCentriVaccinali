package server;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Vaccinazione {

    private String cf;
    private Date data;
    private String nomeCV;
    private String nome;
    private String cognome;
    private String tipo;
    private static short idVaccinazione = 0;

    //Metodi Setters

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setNomeCV(String nomeCV) {
        this.nomeCV = nomeCV;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    //Metodi Getters

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public Date getData() {
        return data;
    }

    public String getNomeCV() {
        return nomeCV;
    }

    public String getTipo() {
        return tipo;
    }

    public String getCf() {
        return cf;
    }

    public short getIdVaccinazione() {
        return idVaccinazione;
    }

    public Vaccinazione(String nomeCV, String nome, String cognome, String cf, Date data, String tipo){
        idVaccinazione++;
        this.nomeCV = nomeCV;
        this.nome = nome;
        this.cognome = cognome;
        this.cf = cf.toUpperCase();
        this.data = data;
        this.tipo = tipo;

        SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = DateFor.parse("08/07/2019");
            System.out.println("Date : " + date);
            data = date;
        }catch (ParseException e) { e.printStackTrace(); }
    }


}
