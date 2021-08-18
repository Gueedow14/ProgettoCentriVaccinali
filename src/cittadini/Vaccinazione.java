package cittadini;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Vaccinazione {

    private String cf;
    private Date data;
    private String nomeCV;

    public String getCf() {
        return cf;
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

    public void setIdVaccinazione(String idVaccinazione) {
        this.idVaccinazione = idVaccinazione;
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

    public String getIdVaccinazione() {
        return idVaccinazione;
    }

    private String tipo;
    private String idVaccinazione;

    public boolean primaVaccinazione () {

        return true;
    }

    public static void registraVaccinato(Vaccinazione nuova){

    }

    public Vaccinazione(String NOMECV, String NOME, String COGNOME, String CF, String DATA, String TIPO){
        cf = CF.toUpperCase();
        nomeCV = NOMECV;
        tipo = TIPO;

        SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
        try{
            Date date = DateFor.parse("08/07/2019");
            System.out.println("Date : "+date);
            data = date;
        }catch (ParseException e) {e.printStackTrace();}



        if(primaVaccinazione())
             idVaccinazione = NOME + COGNOME + CF + "1";
        else
            idVaccinazione = NOME + COGNOME + CF + "2";
    }


}
