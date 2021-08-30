package server;

import common.*;

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
                "INSERT INTO centrovaccinale " +
                        "VALUES ('" + cv.getNome() + "','"  + cv.getIndirizzo() + "','"  + cv.getTipologia() + "')"
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
                "INSERT INTO cittadino " +
                        "VALUES ('" + c.getUserid() + "','" + c.getPwd() + "','"  + c.getNome() + "','"  + c.getCognome() + "','"  + c.getCf() + "',"  + c.getIdvaccinazione() + ",'"  + c.getMail() + "','"  + c.getCv() + "')"
        );
    }

    private void queryRicercaCV(Statement s, ArrayList<CentroVaccinale> l) throws SQLException {
        ResultSet rst = s.executeQuery("SELECT * FROM centrovaccinale");
        while(rst.next()) {
            CentroVaccinale cv = new CentroVaccinale(rst.getString("nome"), rst.getString("tipologia"), rst.getString("indirizzo"));
            l.add(cv);
        }
    }

    private void queryPrenotazioneVaccino(Statement s, Prenotazione p) throws SQLException {
        s.executeUpdate(
                "INSERT INTO prenotazione (idprenotazione, userid, nomecv, dataprenotazione) " +
                        "VALUES (" + p.getIdPrenotazione() + "," + p.getUserid()  + "," + p.getNomeCV() + "," + p.getData() + ")"
        );
    }

    private void queryRegistrazioneVaccinato(Statement s, Vaccinazione v) throws SQLException {
        s.executeUpdate(
                "INSERT INTO " + v.getNomeCV() + "_VACCINATI (nomevaccinato, cognomevaccinato, idvaccinazione, data, tipo) " +
                        "VALUES (" + v.getNome() + "," + v.getCognome()  + "," + v.getIdVaccinazione() + "," + v.getData() + "," + v.getTipo() + ")"
        );
    }

    private void exec() {

    }


    @Override
    public void visualizzaCentroVaccinale(CentroVaccinale cv) {
        //PRENDERE LISTA EVENTI AVVERSI DI UN CV E INFO CV
    }

    @Override
    public List<CentroVaccinale> cercaCentroVaccinale(String nomeCV) throws SQLException {
        ArrayList<CentroVaccinale> lista = new ArrayList<>();

        if (conn.isValid(1000)) {
            Statement s = conn.createStatement();
            queryRicercaCV(s,lista);
        } else {
            connessioneDB();
            Statement s = conn.createStatement();
            queryRicercaCV(s,lista);
        }

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
        if (conn.isValid(1000)) {
            Statement s = conn.createStatement();
            queryRegistrazioneVaccinato(s,v);
        } else {
            connessioneDB();
            Statement s = conn.createStatement();
            queryRegistrazioneVaccinato(s,v);
        }
    }

    @Override
    public void prenotaVaccino(Prenotazione p)  throws SQLException {
        if (conn.isValid(1000)) {
            Statement s = conn.createStatement();
            queryPrenotazioneVaccino(s,p);
        } else {
            connessioneDB();
            Statement s = conn.createStatement();
            queryPrenotazioneVaccino(s,p);
        }
    }

    @Override
    public boolean loginCittadino(String userid, String password)  throws SQLException {
        if(!conn.isValid(100))
            connessioneDB();
        Statement s = conn.createStatement();
        ResultSet rst = s.executeQuery("SELECT * FROM cittadino");
        while(rst.next()) {
            String uid = rst.getString("userid");
            String pwd = rst.getString("pwd");
            if(uid.equals(userid) && pwd.equals(password))
                return true;
        }
        return false;
    }

    @Override
    public int contaCittadini() throws SQLException, RemoteException {
        if(!conn.isValid(100))
            connessioneDB();
        Statement s = conn.createStatement();
        ResultSet rst = s.executeQuery("SELECT COUNT(*) FROM cittadino");
        rst.next();
        return rst.getInt(1);
    }

    @Override
    public int getVaccinati() throws SQLException, RemoteException {
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
