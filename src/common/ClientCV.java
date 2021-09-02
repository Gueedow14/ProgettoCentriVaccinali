package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface ClientCV extends Remote {
    List<CentroVaccinale> cercaCentroVaccinale(String nomeCV) throws SQLException, RemoteException;
    List<CentroVaccinale> cercaCentroVaccinale(String comune, String tipo) throws SQLException, RemoteException;
    List<CentroVaccinale> centriRegistrati() throws SQLException, RemoteException;
    List<Prenotazione> getPrenotazioni(Cittadino c) throws SQLException, RemoteException;
    void registraCittadino(Cittadino c) throws SQLException, RemoteException;
    void registraCentroVaccinale(CentroVaccinale cv) throws SQLException, RemoteException;
    void registraVaccinato(Vaccinazione v) throws SQLException, RemoteException;
    void prenotaVaccino(Prenotazione p) throws SQLException, RemoteException;
    boolean loginCittadino(String userid, String pwd) throws SQLException, RemoteException;
    int contaVaccinati() throws SQLException, RemoteException;
    List<EventoAvverso> getEventiAvversi(CentroVaccinale cv) throws SQLException, RemoteException;
    int contaEventiAvversi() throws SQLException, RemoteException;
    int contaPrenotazioni() throws SQLException, RemoteException;
    void registraEventoAvverso(EventoAvverso e) throws SQLException, RemoteException;
}
