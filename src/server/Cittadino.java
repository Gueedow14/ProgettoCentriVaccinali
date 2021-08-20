package server;

public class Cittadino {
    private String userid;
    private String pwd;
    private String nome;
    private String cognome;
    private String cf;
    private short idvaccinazione;
    private String mail;
    private String cv;

    public Cittadino(String uid, String pwd, String nome, String cognome, String cf, short idv, String mail, String cv) {
        userid = uid;
        this.pwd = pwd;
        this.nome = nome;
        this.cognome = cognome;
        this.cf = cf;
        idvaccinazione = idv;
        this.mail = mail;
        this.cv = cv;
    }

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


    public short getIdvaccinazione() {
        return idvaccinazione;
    }

    public String getMail() {
        return mail;
    }

    public String getCv() {
        return cv;
    }

    public String getCognome() {
        return cognome;
    }

    public String getNome() {
        return nome;
    }

    public String getPwd() {
        return pwd;
    }

    public String getUserid() {
        return userid;
    }

    public String getCf() {
        return cf;
    }
}
