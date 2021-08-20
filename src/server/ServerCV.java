package server;

import cittadini.Prenotazione;
import cittadini.Vaccinazione;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.*;

public class ServerCV extends UnicastRemoteObject implements ClientCV {

    private static final long serialVersionUID = 1L;
    private static Connection conn;
    private static String db,user,pwd;


    protected ServerCV() throws RemoteException {
        super();
    }

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
        obj.exec();



    }

    private static void connessioneDB() throws SQLException {
        String url = "jdbc:postgresql://localhost/" + db;
        Properties props = new Properties();
        props.setProperty("user",user);
        props.setProperty("password",pwd);
        conn = DriverManager.getConnection(url, props);
    }

    private void queryRegistrazioneCV(Statement s, CentroVaccinale cv) throws SQLException {
        s.executeUpdate(
                "INSERT INTO centrovaccinale (nome, indirizzo, tipologia) " +
                        "VALUES " + cv.getNome() + cv.getIndirizzo() + cv.getTipologia()
        );

        String nome_tab = cv.getNome() + "_VACCINATI";
        s.executeUpdate(
                "CREATE TABLE " + nome_tab + " (" +
                        " nomevaccinato VARCHAR(20) NOT NULL," +
                        " cognomevaccinato VARCHAR(25) NOT NULL," +
                        " idvaccinazione SMALLINT PRIMARY KEY," +
                        " data VARCHAR(10) NOT NULL," +
                        " tipo VARCHAR(20) NOT  NULL" +
                        " CHECK (tipo IN('Pfizer','Moderna','Astrazeneca','J&J'))" +
                        ")"
        );
    }

    private void queryRegistrazioneCittadino(Statement s, Cittadino c) throws SQLException {
        s.executeUpdate(
                "INSERT INTO cittadino (userid, pwd, nome, cognome, cf, idvaccinazione, mail, cv) " +
                        "VALUES " + c.getUserid() + c.getPwd() + c.getNome() + c.getCognome() + c.getCf() + c.getIdvaccinazione() + c.getMail() + c.getCv()
        );
    }


    private void exec() {

    }


    @Override
    public void visualizzaCentroVaccinale(CentroVaccinale cv) {

    }

    @Override
    public List<CentroVaccinale> cercaCentroVaccinale(String nomeCV) {
        List<CentroVaccinale> lista = new ArrayList<>();



        return lista;
    }

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

    @Override
    public void registraVaccinato(Vaccinazione v)  throws SQLException {

    }

    @Override
    public void prenotaVaccino(Prenotazione p)  throws SQLException {

    }

    @Override
    public void loginCittadino(String userid, String pwd)  throws SQLException {

    }
}
