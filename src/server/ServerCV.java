package server;

import common.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.*;

/**
 * Classe server che implementa i metodi definiti nell'interfaccia ClientCV
 * @author Guido Bernasconi
 */
public class ServerCV extends UnicastRemoteObject implements ClientCV {

    /**
     * Versione della serializzazione utilizzata
     */
    private static final long serialVersionUID = 1L;

    /**
     * Connessione al databse effettuata
     */
    private static Connection conn;

    /**
     * Nome del databse a cui accedere
     */
    private static String db;

    /**
     * Nome utente per l'accesso al database
     */
    private static String user;

    /**
     * Password per l'accesso al database
     */
    private static String pwd;

    /**
     * Costruttore della classe server
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server perciò può lanciare un'eccezione di questo tipo
     */
    protected ServerCV() throws RemoteException {
        super();
    }

    /**
     * Punto d'inizio dell'esecuzione del server
     * @param args Argomenti passati quando il server viene lanciato
     * @throws Exception Questo metodo lancia un'eccezione generica
     */
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        ServerCV obj = new ServerCV();

        System.out.print("Inserire Database a cui connettersi: ");   //dbCV
        db = scanner.nextLine();
        System.out.print("\nInserire User: ");  //postgres
        user = scanner.nextLine();
        System.out.print("\nInserire Password: ");  //password123
        pwd = scanner.nextLine();

        connessioneDB();

        Registry registro = LocateRegistry.createRegistry(1099);
        registro.rebind("SERVERCV", obj);
        System.out.println("Server registrato correttamente");
    }

    /**
     * Metodo per la connessione al database
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè è in atto la connessione al database
     */
    private synchronized static void connessioneDB() throws SQLException {
        String url = "jdbc:postgresql://localhost/" + db;
        Properties props = new Properties();
        props.setProperty("user",user);
        props.setProperty("password",pwd);
        conn = DriverManager.getConnection(url, props);
    }

    /**
     * Metodo che esegue la query di registrazione di un centro vaccinale nel databse
     * @param s Statement per eseguire la query
     * @param cv Centro vaccinale da registrare
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè al suo interno c'è una query
     */
    private synchronized void queryRegistrazioneCV(Statement s, CentroVaccinale cv) throws SQLException {
        s.executeUpdate(
                "INSERT INTO centrovaccinale " +
                        "VALUES ('" + cv.getNome() + "','"  + cv.getIndirizzo() + "','"  + cv.getTipologia() + "')"
        );

        String nome_tab = cv.getNome() + "_VACCINATI";
        s.executeUpdate(
                "CREATE TABLE " + nome_tab + " (" +
                        " nomecv VARCHAR(20) NOT NULL REFERENCES centrovaccinale(nome)," +
                        " cf VARCHAR(20) NOT NULL REFERENCES cittadino(cf)," +
                        " nomevaccinato VARCHAR(20) NOT NULL," +
                        " cognomevaccinato VARCHAR(25) NOT NULL," +
                        " idvaccinazione SMALLINT PRIMARY KEY," +
                        " data VARCHAR(10) NOT NULL," +
                        " tipo VARCHAR(20) NOT  NULL" +
                        " CHECK (tipo IN('Pfizer','Moderna','Astrazeneca','J&J'))" +
                        ")"
        );
    }

    /**
     * Metodo che esegue la query per la registrazione di un cittadino
     * @param s Statement per eseguire la query
     * @param c Cittadino da registrare
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè al suo interno c'è una query
     */
    private synchronized void queryRegistrazioneCittadino(Statement s, Cittadino c) throws SQLException {
        s.executeUpdate(
                "INSERT INTO cittadino " +
                        "VALUES ('" + c.getUserid() + "','" + c.getPwd() + "','"  + c.getNome() + "','"  + c.getCognome() + "','"  + c.getCf() + "','"  + c.getMail() + "','"  + c.getCv() + "',"  + c.getIdvaccinazione() + ")"
        );
    }

    /**
     * Metodo che esegue la query che ottiene tutti i centri vaccinali memorizzati
     * @param s Statement per eseguire la query
     * @return ritorna la lista di tutti i centri vaccinali presenti nel database
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè al suo interno c'è una query
     */
    private synchronized ArrayList<CentroVaccinale> queryRicercaCV(Statement s) throws SQLException {
        ResultSet rst = s.executeQuery("SELECT * FROM centrovaccinale");
        ArrayList<CentroVaccinale> l = new ArrayList<>();
        while(rst.next()) {
            CentroVaccinale cv = new CentroVaccinale(rst.getString("nome"), rst.getString("tipologia"), rst.getString("indirizzo"));
            l.add(cv);
        }
        return l;
    }

    /**
     * Metodo che esegue la query che ottiene tutte le prenotazioni memorizzate nel database
     * @param s Statement per eseguire la query
     * @param l Lista dove inserire le prenotazioni effettuate
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè al suo interno c'è una query
     */
    private synchronized void queryRicercaPrenotazioni(Statement s, ArrayList<Prenotazione> l) throws SQLException {
        ResultSet rst = s.executeQuery("SELECT * FROM prenotazione");
        while(rst.next()) {
            Prenotazione p = new Prenotazione(rst.getInt("idprenotazione"), rst.getString("userid"), rst.getString("cv"), rst.getString("dataprenotazione"));
            l.add(p);
        }
    }

    /**
     * Metodo che esegue la query che inserisce una prenotazione nel database
     * @param s Statement per eseguire la query
     * @param p Prenotazione da registrare
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè al suo interno c'è una query
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server perciò può lanciare un'eccezione di questo tipo
     */
    private synchronized void queryPrenotazioneVaccino(Statement s, Prenotazione p) throws SQLException, RemoteException {
        s.executeUpdate(
                "INSERT INTO prenotazione " +
                        "VALUES (" + (contaPrenotazioni() +1) + ",'" + p.getUserid()  + "','" + p.getNomeCV() + "','" + p.getData() + "')"
        );
    }

    /**
     * Metodo che esegue la query che inserisce una vaccinazione nel database
     * @param s Statement per eseguire la query
     * @param v Vaccinazione da registrare
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè al suo interno c'è una query
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server perciò può lanciare un'eccezione di questo tipo
     */
    private synchronized void queryRegistrazioneVaccinato(Statement s, Vaccinazione v) throws SQLException, RemoteException {
        s.executeUpdate(
                "INSERT INTO " + v.getNomeCV() + "_VACCINATI " +
                        "VALUES ('" + v.getNomeCV() + "','" + v.getCf() + "','" + v.getNome() + "','" + v.getCognome()  + "'," + (contaVaccinati() + 1) + ",'" + v.getData() + "','" + v.getTipo() + "')"
        );
    }

    /**
     * Metodo che esegue la query che ottiene tutte le segnalazioni di eventi avversi memorizzate nel database
     * @param s Statement per eseguire la query
     * @param l Lista in cui verranno inserite le prenotazioni effettuate
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè al suo interno c'è una query
     */
    private synchronized void queryRicercaEventiAvversiCV(Statement s, ArrayList<EventoAvverso> l) throws SQLException {
        ResultSet rst = s.executeQuery("SELECT * FROM presenta");
        while (rst.next()) {
            EventoAvverso p = new EventoAvverso(rst.getInt("idevento"), rst.getString("evento"), rst.getInt("severita"), rst.getString("note"), rst.getString("cv"), rst.getString("userid"));
            l.add(p);
        }
    }

    /**
     * Metodo che esegue la query che inserisce una segnalazine di un evento avverso nel database
     * @param s Statement per eseguire la query
     * @param e Eccezione da registrare
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè al suo interno c'è una query
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server perciò può lanciare un'eccezione di questo tipo
     */
    private synchronized void queryRegistrazioneEventiAvversi(Statement s, EventoAvverso e) throws SQLException, RemoteException {
        s.executeUpdate(
                "INSERT INTO presenta " +
                        "VALUES (" + (contaEventiAvversi() + 1) + ",'" + e.getTesto() + "'," + e.getSeverita() + ",'" + e.getCittadino()  + "','" + e.getEvento() + "','" + e.getCv() + "')"
        );
    }

    /**
     * Metodo di ricerca di un centro vaccinale dato il nome
     * @param nomeCV Nome del centro vaccinale da cercare
     * @return Tutti i centri vaccinali che contengono nomeCV nel proprio nome
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè chiama un altro metodo al cui interno c'è una query
     */
    @Override
    public List<CentroVaccinale> cercaCentroVaccinale(String nomeCV) throws SQLException {
        ArrayList<CentroVaccinale> listaCV = new ArrayList<>(), ris = new ArrayList<>();

        if (conn.isValid(1000)) {
            Statement s = conn.createStatement();
            listaCV = queryRicercaCV(s);
        } else {
            connessioneDB();
            Statement s = conn.createStatement();
            listaCV = queryRicercaCV(s);
        }

        for(CentroVaccinale centroVaccinale : listaCV)
            if(centroVaccinale.getNome().contains(nomeCV))
                ris.add(centroVaccinale);

        return ris;
    }

    /**
     * Metodo di ricerca di un centro vaccinale dato il comune e il tipo del centro
     * @param comune Comune in cui è localizzato il centro vaccinale
     * @param tipo Tipologia del centro vaccinale
     * @return Tutti i centri vaccinali che sono del tipo indicato e sono localizzati nel comune specificato
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè chiama un altro metodo al cui interno c'è una query
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server perciò può lanciare un'eccezione di questo tipo
     */
    @Override
    public List<CentroVaccinale> cercaCentroVaccinale(String comune, String tipo) throws SQLException, RemoteException {
        ArrayList<CentroVaccinale> listaCV = new ArrayList<>(), ris = new ArrayList<>();

        if (conn.isValid(1000)) {
            Statement s = conn.createStatement();
            listaCV = queryRicercaCV(s);
        } else {
            connessioneDB();
            Statement s = conn.createStatement();
            listaCV = queryRicercaCV(s);
        }
        if(listaCV.size() == 0)
            System.out.print("ciao");
        for(CentroVaccinale centroVaccinale : listaCV) {
            String[] tmp = centroVaccinale.getIndirizzo().split("§");
            String com = tmp[3];
            if(com.equals(comune) && centroVaccinale.getTipologia().equals(tipo))
                ris.add(centroVaccinale);
        }

        return ris;
    }

    /**
     * Metodo che ritorna tutti i centri vaccinali memorizzati nel databse
     * @return Tutti i centri vaccinali registrati nel database
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè chiama un altro metodo al cui interno c'è una query
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server perciò può lanciare un'eccezione di questo tipo
     */
    @Override
    public List<CentroVaccinale> centriRegistrati() throws SQLException, RemoteException {
        ArrayList<CentroVaccinale> lista = new ArrayList<>();

        if (conn.isValid(1000)) {
            Statement s = conn.createStatement();
            lista = queryRicercaCV(s);
        } else {
            connessioneDB();
            Statement s = conn.createStatement();
            lista = queryRicercaCV(s);
        }

        return lista;
    }

    /**
     * Metodo di ricerca delle prenotazioni effettuate da un cittadino
     * @param c Cittadino registrato al sistema
     * @return Una lista contenente le prenotazioni effettuate da un cittadino
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè chiama un altro metodo al cui interno c'è una query
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server perciò può lanciare un'eccezione di questo tipo
     */
    @Override
    public List<Prenotazione> getPrenotazioni(Cittadino c) throws SQLException, RemoteException {
        ArrayList<Prenotazione> lista = new ArrayList<>(), ris = new ArrayList<>();

        if (conn.isValid(1000)) {
            Statement s = conn.createStatement();
            queryRicercaPrenotazioni(s,lista);
        } else {
            connessioneDB();
            Statement s = conn.createStatement();
            queryRicercaPrenotazioni(s,lista);
        }

        for (Prenotazione p : lista)
            if (p.getUserid().equals(c.getUserid()))
                ris.add(p);

        return ris;
    }

    /**
     * Metodo per registrare un cittadino nel database
     * @param c Cittadino da registrare nel database
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè chiama un altro metodo al cui interno c'è una query
     */
    @Override
    public void registraCittadino(Cittadino c) throws SQLException {
        if (conn.isValid(1000)) {
            Statement s = conn.createStatement();
            queryRegistrazioneCittadino(s,c);
        } else {
            connessioneDB();
            Statement s = conn.createStatement();
            queryRegistrazioneCittadino(s,c);
        }
    }

    /**
     * Metodo per la registrazione di un evento avverso
     * @param e Evento avverso da registrare
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè chiama un altro metodo al cui interno c'è una query
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server perciò può lanciare un'eccezione di questo tipo
     */
    @Override
    public void registraEventoAvverso(EventoAvverso e) throws SQLException, RemoteException {
        if (conn.isValid(1000)) {
            Statement s = conn.createStatement();
            queryRegistrazioneEventiAvversi(s,e);
        } else {
            connessioneDB();
            Statement s = conn.createStatement();
            queryRegistrazioneEventiAvversi(s,e);
        }
    }

    /**
     * Metodo che ritorna tutti i tipi di eventi avversi registrati
     * @return Lista contenente i tipi di eventi avversi
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè al suo interno c'è una query
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server perciò può lanciare un'eccezione di questo tipo
     */
    @Override
    public List<String> getTipiEventoAvverso() throws SQLException, RemoteException {
        if(!conn.isValid(100))
            connessioneDB();

        List<String> listEA = new ArrayList<>();

        Statement s = conn.createStatement();
        ResultSet rst = s.executeQuery("SELECT evento FROM eventoavverso");

        while(rst.next())
            listEA.add(rst.getString("evento"));


        return listEA;
    }

    /**
     * Metodo per registrare un centro vaccinale nel database
     * @param cv Centro Vaccinale da registrare nel databse
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè chiama un altro metodo al cui interno c'è una query
     */
    @Override
    public void registraCentroVaccinale(CentroVaccinale cv) throws SQLException {
        if (conn.isValid(1000)) {
            Statement s = conn.createStatement();
            queryRegistrazioneCV(s,cv);
        } else {
            connessioneDB();
            Statement s = conn.createStatement();
            queryRegistrazioneCV(s,cv);
        }
    }

    /**
     * Metodo per registrare una vaccinazione nel database
     * @param v Vaccinazione da inserire nel database
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè chiama un altro metodo al cui interno c'è una query
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server perciò può lanciare un'eccezione di questo tipo
     */
    @Override
    public void registraVaccinato(Vaccinazione v)  throws SQLException, RemoteException {
        if (conn.isValid(1000)) {
            Statement s = conn.createStatement();
            queryRegistrazioneVaccinato(s,v);
        } else {
            connessioneDB();
            Statement s = conn.createStatement();
            queryRegistrazioneVaccinato(s,v);
        }
    }

    /**
     * Metodo di ricerca degli eventi avversi registrati presso il centro vaccinale passato come argomento
     * @param cv Centro Vaccinale di cui interessa conoscere gli eventi avversi
     * @return Una lista contenente tutti gli eventi avversi registrati presso un centro
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè chiama un altro metodo al cui interno c'è una query
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server perciò può lanciare un'eccezione di questo tipo
     */
    @Override
    public List<EventoAvverso> getEventiAvversi(CentroVaccinale cv) throws SQLException, RemoteException {
        ArrayList<EventoAvverso> lista = new ArrayList<>();
        ArrayList<EventoAvverso> ris = new ArrayList<>();

        if (conn.isValid(1000)) {
            Statement s = conn.createStatement();
            queryRicercaEventiAvversiCV(s,lista);
        } else {
            connessioneDB();
            Statement s = conn.createStatement();
            queryRicercaEventiAvversiCV(s,lista);
        }

        for (EventoAvverso p : lista)
            if (p.getCv().equals(cv.getNome()))
                ris.add(p);

        return ris;
    }

    /**
     * Metodo per registrare una prenotazione nel database
     * @param p Prenotazione da inserire nel database
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè chiama un altro metodo al cui interno c'è una query
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server perciò può lanciare un'eccezione di questo tipo
     */
    @Override
    public void prenotaVaccino(Prenotazione p)  throws SQLException, RemoteException {
        if (conn.isValid(1000)) {
            Statement s = conn.createStatement();
            queryPrenotazioneVaccino(s,p);
        } else {
            connessioneDB();
            Statement s = conn.createStatement();
            queryPrenotazioneVaccino(s,p);
        }
    }

    /**
     * Metodo per il controllo dell'accesso di un utente (cittadino) al sistema
     * @param userid Id univoco dell'utente che vuole effettuare l'accesso al sistema
     * @param password Password inserita dall'utente che cerca di effettuare l'accesso al sistema
     * @return true se le credednziali di accesso sono corrette, altrimenti false
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè al suo interno c'è una query
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server perciò può lanciare un'eccezione di questo tipo
     */
    @Override
    public synchronized Cittadino loginCittadino(String userid, String password)  throws SQLException, RemoteException {
        if(!conn.isValid(100))
            connessioneDB();
        Statement s = conn.createStatement();
        ResultSet rst = s.executeQuery("SELECT * FROM cittadino");
        while(rst.next()) {
            Cittadino c = new Cittadino(rst.getString("userid"), rst.getString("pwd"), rst.getString("nome"), rst.getString("cognome"), rst.getShort("idvaccinazione"), rst.getString("cf"), rst.getString("mail"), rst.getString("cv"));
            if(c.getUserid().equals(userid) && c.getPwd().equals(password))
                return c;
        }
        return null;
    }

    /**
     * Metodo che conta tutti gli eventi avversi segnalati
     * @return Il numero di eventi avversi segnalati e registrati nel database
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè al suo interno c'è una query
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server perciò può lanciare un'eccezione di questo tipo
     */
    @Override
    public synchronized int contaEventiAvversi() throws SQLException, RemoteException {
        if(!conn.isValid(100))
            connessioneDB();

        Statement s = conn.createStatement();
        ResultSet rst = s.executeQuery("SELECT COUNT(*) FROM presenta");
        rst.next();
        return rst.getInt(1);
    }

    /**
     * Metodo che conta il nuemero di prenotazioni effettuate
     * @return Il numero di prenotazioni effettuate e registrate
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè al suo interno c'è una query
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server perciò può lanciare un'eccezione di questo tipo
     */
    @Override
    public synchronized int contaPrenotazioni() throws SQLException, RemoteException {
        if(!conn.isValid(100))
            connessioneDB();

        Statement s = conn.createStatement();
        ResultSet rst = s.executeQuery("SELECT COUNT(*) FROM prenotazione");
        rst.next();
        return rst.getInt(1);
    }

    /**
     * Metodo per contare il numero di vaccinazioni effettuate in tutti i centri vaccinali registrati nel sistema
     * @return Il numero totale di vaccinazioni effettuate
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè al suo interno c'è una query
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server perciò può lanciare un'eccezione di questo tipo
     */
    @Override
    public synchronized int contaVaccinati() throws SQLException, RemoteException {
        if(!conn.isValid(100))
            connessioneDB();

        List<String> nomicv = new ArrayList<>();

        Statement s = conn.createStatement();
        ResultSet rst = s.executeQuery("SELECT * FROM centrovaccinale");
        while(rst.next()) {
            nomicv.add(rst.getString("nome"));
        }

        int i = 0, count = 0;
        while(i < nomicv.size()) {
            String nome_tab = nomicv.get(i++) + "_vaccinati";
            rst = s.executeQuery("SELECT COUNT(*) FROM " + nome_tab);
            rst.next();
            count += rst.getInt(1);
        }

        return count;
    }
}
