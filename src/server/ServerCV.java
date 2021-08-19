package server;

import cittadini.Cittadino;
import cittadini.Prenotazione;
import cittadini.Vaccinazione;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.*;

public class ServerCV extends UnicastRemoteObject implements ClientCV {

    private static final long serialVersionUID = 1L;
    private static Connection conn;


    protected ServerCV() throws RemoteException {
        super();
    }

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Inserire Database a cui connettersi: ");   //dbCV
        String db = scanner.nextLine();
        System.out.print("\nInserire User: ");  //postgres
        String user = scanner.nextLine();
        System.out.print("\nInserire Password: ");  //password123
        String pwd = scanner.nextLine();

        String url = "jdbc:postgresql://localhost/" + db;
        Properties props = new Properties();
        props.setProperty("user",user);
        props.setProperty("password",pwd);
        conn = DriverManager.getConnection(url, props);

        /*Statement s = conn.createStatement();
        s.executeUpdate(
                "CREATE TABLE Film (" +
                        " titolo VARCHAR(30)," +
                        " regista VARCHAR(20)," +
                        " anno DECIMAL(4) NOT NULL," +
                        " genere CHAR(15) NOT NULL," +
                        " valutaz NUMERIC(3, 2)," +
                        " PRIMARY KEY (titolo, regista)" +
                        ")"
        );*/

    }


    @Override
    public void visualizzaCentroVaccinale(CentroVaccinale cv) {

    }

    @Override
    public List<CentroVaccinale> cercaCentroVaccinale(String nomeCV) {
        return null;
    }

    @Override
    public void registraCittadino(Cittadino c) throws SQLException {

    }

    @Override
    public boolean registraCentroVaccinale(CentroVaccinale cv) {
        return false;
    }

    @Override
    public boolean registraVaccinato(Vaccinazione v) {
        return false;
    }

    @Override
    public boolean prenotaVaccino(Prenotazione p) {
        return false;
    }

    @Override
    public boolean loginCittadino(String userid, String pwd) {
        return false;
    }
}
