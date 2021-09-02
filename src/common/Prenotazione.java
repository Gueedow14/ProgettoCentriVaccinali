package common;

import java.io.Serializable;

public class Prenotazione implements Serializable {

    private int idprenotazione = 0;
    private String userid;
    private String nomecv;
    private String data;

    public Prenotazione(String userid, String nomecv, String data) {
        this.userid = userid;
        this.nomecv = nomecv;
        this.data = data;
    }

    public Prenotazione(int idp, String userid, String nomecv, String data) {
        idprenotazione = idp;
        this.userid = userid;
        this.nomecv = nomecv;
        this.data = data;
    }

    public Prenotazione() {
        idprenotazione = 0;
        userid = "";
        nomecv = "";
        data = "";
    }

    public int getIdPrenotazione() {
        return idprenotazione;
    }

    public String getUserid() {
        return userid;
    }

    public String getNomeCV() {
        return nomecv;
    }

    public String getData() {
        return data;
    }
}
