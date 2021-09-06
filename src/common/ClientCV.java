/*
Giulio Baricci 740285 Varese
Guido Bernasconi 740539 Varese
Davide Feldkircher 740956 Varese
 */

package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Interfaccia che definisce i metodi del Server.MF
 * @author Guido Bernasconi
 */
public interface ClientCV extends Remote {
    /**
     * Metodo di ricerca di un centro vaccinale dato il nome
     * @param nomeCV Nome del centro vaccinale da cercare
     * @return Tutti i centri vaccinali che contengono nomeCV nel proprio nome
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè chiama un altro metodo al cui interno c'è una query
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server.MF perciò può lanciare un'eccezione di questo tipo
     */
    List<CentroVaccinale> cercaCentroVaccinale(String nomeCV) throws SQLException, RemoteException;
    /**
     * Metodo di ricerca di un centro vaccinale dato il comune e il tipo del centro
     * @param comune Comune in cui è localizzato il centro vaccinale
     * @param tipo Tipologia del centro vaccinale
     * @return Tutti i centri vaccinali che sono del tipo indicato e sono localizzati nel comune specificato
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè chiama un altro metodo al cui interno c'è una query
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server.MF perciò può lanciare un'eccezione di questo tipo
     */
    List<CentroVaccinale> cercaCentroVaccinale(String comune, String tipo) throws SQLException, RemoteException;
    /**
     * Metodo che ritorna tutti i centri vaccinali memorizzati nel databse
     * @return Tutti i centri vaccinali registrati nel database
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè chiama un altro metodo al cui interno c'è una query
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server.MF perciò può lanciare un'eccezione di questo tipo
     */
    List<CentroVaccinale> centriRegistrati() throws SQLException, RemoteException;
    /**
     * Metodo di ricerca delle prenotazioni effettuate da un cittadino
     * @param c Cittadino registrato al sistema
     * @return Una lista contenente le prenotazioni effettuate da un cittadino
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè chiama un altro metodo al cui interno c'è una query
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server.MF perciò può lanciare un'eccezione di questo tipo
     */
    List<Prenotazione> getPrenotazioni(Cittadino c) throws SQLException, RemoteException;
    /**
     * Metodo per registrare un cittadino nel database
     * @param c Cittadino da registrare nel database
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè chiama un altro metodo al cui interno c'è una query
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server.MF perciò può lanciare un'eccezione di questo tipo
     */
    void registraCittadino(Cittadino c) throws SQLException, RemoteException;
    /**
     * Metodo per registrare un centro vaccinale nel database
     * @param cv Centro Vaccinale da registrare nel databse
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè chiama un altro metodo al cui interno c'è una query
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server.MF perciò può lanciare un'eccezione di questo tipo
     */
    void registraCentroVaccinale(CentroVaccinale cv) throws SQLException, RemoteException;
    /**
     * Metodo per registrare una vaccinazione nel database
     * @param v Vaccinazione da inserire nel database
     * @return Ritorna l'esito della registrazione della vaccinazione
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè chiama un altro metodo al cui interno c'è una query
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server.MF perciò può lanciare un'eccezione di questo tipo
     */
    boolean registraVaccinato(Vaccinazione v) throws SQLException, RemoteException;
    /**
     * Metodo per registrare una prenotazione nel database
     * @param p Prenotazione da inserire nel database
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè chiama un altro metodo al cui interno c'è una query
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server.MF perciò può lanciare un'eccezione di questo tipo
     */
    void prenotaVaccino(Prenotazione p) throws SQLException, RemoteException;
    /**
     * Metodo per il controllo dell'accesso di un utente (cittadino) al sistema
     * @param userid Id univoco dell'utente che vuole effettuare l'accesso al sistema
     * @param pwd Password inserita dall'utente che cerca di effettuare l'accesso al sistema
     * @return true se le credednziali di accesso sono corrette, altrimenti false
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè al suo interno c'è una query
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server.MF perciò può lanciare un'eccezione di questo tipo
     */
    Cittadino loginCittadino(String userid, String pwd) throws SQLException, RemoteException;
    /**
     * Metodo per contare il numero di vaccinazioni effettuate in tutti i centri vaccinali registrati nel sistema
     * @return Il numero totale di vaccinazioni effettuate
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè al suo interno c'è una query
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server.MF perciò può lanciare un'eccezione di questo tipo
     */
    int contaVaccinati() throws SQLException, RemoteException;
    /**
     * Metodo di ricerca degli eventi avversi registrati presso il centro vaccinale passato come argomento
     * @param cv Centro Vaccinale di cui interessa conoscere gli eventi avversi
     * @return Una lista contenente tutti gli eventi avversi registrati presso un centro
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè chiama un altro metodo al cui interno c'è una query
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server.MF perciò può lanciare un'eccezione di questo tipo
     */
    List<EventoAvverso> getEventiAvversi(CentroVaccinale cv) throws SQLException, RemoteException;
    /**
     * Metodo che conta tutti gli eventi avversi segnalati
     * @return Il numero di eventi avversi segnalati e registrati nel database
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè al suo interno c'è una query
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server.MF perciò può lanciare un'eccezione di questo tipo
     */
    int contaEventiAvversi() throws SQLException, RemoteException;
    /**
     * Metodo che conta il nuemero di prenotazioni effettuate
     * @return Il numero di prenotazioni effettuate e registrate
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè al suo interno c'è una query
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server.MF perciò può lanciare un'eccezione di questo tipo
     */
    int contaPrenotazioni() throws SQLException, RemoteException;
    /**
     * Metodo per la registrazione di un evento avverso
     * @param e Evento avverso da registrare
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè chiama un altro metodo al cui interno c'è una query
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server.MF perciò può lanciare un'eccezione di questo tipo
     */
    void registraEventoAvverso(EventoAvverso e) throws SQLException, RemoteException;
    /**
     * Metodo che ritorna la lista di tutti i tipi di eventi avversi possibili
     * @return Lista con tutti i tipi di evento avverso
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè chiama un altro metodo al cui interno c'è una query
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server.MF perciò può lanciare un'eccezione di questo tipo
     */
    List<String> getTipiEventoAvverso() throws SQLException, RemoteException;
    /**
     * Metodo che ritorna la lista di tutti gli indirizzi mail
     * @return Lista con tutti gli indirizzi mail
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè chiama un altro metodo al cui interno c'è una query
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server.MF perciò può lanciare un'eccezione di questo tipo
     */
    List<String> getEmail() throws SQLException, RemoteException;
    /**
     * Metodo che ritorna la lista di tutti gli userid
     * @return Lista con tutti gli userid
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè chiama un altro metodo al cui interno c'è una query
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server.MF perciò può lanciare un'eccezione di questo tipo
     */
    List<String> getUsedId() throws SQLException, RemoteException;
    /**
     * Metodo che ritorna la lista di tutti i codici fiscali
     * @return Lista con tutti i codici fiscali
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè chiama un altro metodo al cui interno c'è una query
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server.MF perciò può lanciare un'eccezione di questo tipo
     */
    List<String> getCF() throws SQLException, RemoteException;
    /**
     * Metodo che dato un codice fiscale ritorna il cittadino identificato da quel codice fiscale
     * @param cf Codice fiscale del cittadino da trovare
     * @return Ritorna il cittadino con il CF specificato
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè chiama un altro metodo al cui interno c'è una query
     */
    Cittadino getCittadino(String cf) throws SQLException, RemoteException;

    /**
     * Metodo che, dato un centro vaccinale, ritorna la media della severità degli eventi avversi segnalati in quel cv
     * @param nomeCV Nome del centro vaccinale
     * @return Ritorna la media della severità degli eventi avversi segnalati nel cv specificato
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè chiama un altro metodo al cui interno c'è una query
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server.MF perciò può lanciare un'eccezione di questo tipo
     */
    double getMediaSev(String nomeCV) throws SQLException, RemoteException;
}
