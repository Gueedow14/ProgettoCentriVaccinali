package server;

import centrivaccinali.*;
import cittadini.*;
import java.util.*;

public interface ClientCV {
    void visualizzaCentroVaccinale(CentroVaccinale cv);
    List<CentroVaccinale> cercaCentroVaccinale(String nomeCV);
    boolean registraCittadino(Cittadino c);
    boolean registraCentroVaccinale(CentroVaccinale cv);
    boolean registraVaccinato(Vaccinazione v);
    boolean prenotaVaccino(Prenotazione p);
    boolean loginCittadino(String userid, String pwd);
}
