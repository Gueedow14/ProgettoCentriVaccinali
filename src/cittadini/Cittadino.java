package cittadini;

public class Cittadino {
    private String userid;
    private String pwd;
    private String nome;
    private String cognome;
    private short idvaccinazione;
    private String mail;
    private String cv;

    public Cittadino(String uid, String pwd, String nome, String cognome, short idv, String mail, String cv) {
        userid = uid;
        this.pwd = pwd;
        this.nome = nome;
        this.cognome = cognome;
        idvaccinazione = idv;
        this.mail = mail;
        this.cv = cv;
    }

    public Cittadino() {
        userid = "";
        pwd = "";
        nome = "";
        cognome = "";
        idvaccinazione = 0;
        mail = "";
        cv = "";
    }
}
