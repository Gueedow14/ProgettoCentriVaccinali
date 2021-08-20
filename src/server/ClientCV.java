package server;

import cittadini.*;

import java.sql.SQLException;
import java.util.*;

public interface ClientCV {
    void visualizzaCentroVaccinale(CentroVaccinale cv);
    List<CentroVaccinale> cercaCentroVaccinale(String nomeCV);
    void registraCittadino(Cittadino c) throws SQLException;
    void registraCentroVaccinale(CentroVaccinale cv) throws SQLException;
    void registraVaccinato(Vaccinazione v) throws SQLException;
    void prenotaVaccino(Prenotazione p) throws SQLException;
    void loginCittadino(String userid, String pwd) throws SQLException;
}
