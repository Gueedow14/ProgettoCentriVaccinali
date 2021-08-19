package server;

import cittadini.*;

import java.sql.SQLException;
import java.util.*;

public interface ClientCV {
    void visualizzaCentroVaccinale(CentroVaccinale cv);
    List<CentroVaccinale> cercaCentroVaccinale(String nomeCV);
    void registraCittadino(Cittadino c) throws SQLException;
    boolean registraCentroVaccinale(CentroVaccinale cv);
    boolean registraVaccinato(Vaccinazione v);
    boolean prenotaVaccino(Prenotazione p);
    boolean loginCittadino(String userid, String pwd);
}
