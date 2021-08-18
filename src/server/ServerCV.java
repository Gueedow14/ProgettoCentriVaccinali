package server;

import centrivaccinali.CentroVaccinale;
import cittadini.Cittadino;
import cittadini.Prenotazione;
import cittadini.Vaccinazione;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Scanner;

public class ServerCV extends UnicastRemoteObject implements ClientCV {

    private static final long serialVersionUID = 1L;

    protected ServerCV() throws RemoteException {
        super();
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.print("Inserire Database a cui connettersi: ");   //dbCV
        String db = s.nextLine();
        System.out.print("\nInserire User: ");  //postgres
        String user = s.nextLine();
        System.out.print("\nInserire Password: ");  //password123
        String pwd = s.nextLine();


    }


    @Override
    public void visualizzaCentroVaccinale(CentroVaccinale cv) {

    }

    @Override
    public List<CentroVaccinale> cercaCentroVaccinale(String nomeCV) {
        return null;
    }

    @Override
    public boolean registraCittadino(Cittadino c) {
        return false;
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
