package cittadini;

import centrivaccinali.RegistraCentri;
import common.Cittadino;
import common.ClientCV;
import common.Prenotazione;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;


/**
 * La classe ListaPrenotazioni contiene il codice per la creazione della schermata relativa alla lista delle
 * prenotazioni effettuate da un cittadino
 * @author Giulio Baricci
 */

public class ListaPrenotazioni extends UnicastRemoteObject {

    /**
     * Indirizzo ip della macchina Server
     */
    public static String ip = "";

    /**
     * Oggetto che fa riferimento al server disponibile sul rmiregistry
     */
    private static ClientCV stub;

    /**
     * Lista che contiene tutte le prenotazioni effettuate dal cittadino
     */
    java.util.List<Prenotazione> lista = new ArrayList<>();

    /**
     * Frame della schermata delle prenotazioni
     */
    JFrame f = new JFrame("Prenotazioni");

    /**
     * Bottone per ritornare alla schermata precedente
     */
    JButton indietro = new JButton();

    /**
     * Titolo visualizzato nella schrmata
     */
    JLabel titolo = new JLabel("Prenotazioni effettuate");

    /**
     * Label che indica le informazioni relative alla prima prenotazione
     */
    JLabel pren1 = new JLabel("Prenotazione 1:");

    /**
     * Label che indica la data relativa alla prima prenotazione
     */
    JLabel data1L = new JLabel("Data:", SwingConstants.CENTER);

    /**
     * Label che indica l'orario relativo alla prima prenotazione
     */
    JLabel orario1L = new JLabel("Orario:", SwingConstants.CENTER);

    /**
     * Label che indica le informazioni relative alla seconda prenotazione
     */
    JLabel pren2 = new JLabel("Prenotazione 2:");

    /**
     * Label che indica la data relativa alla seconda prenotazione
     */
    JLabel data2L = new JLabel("Data:", SwingConstants.CENTER);

    /**
     * Label che indica l'orario relativo alla prima prenotazione
     */
    JLabel orario2L = new JLabel("Orario:", SwingConstants.CENTER);

    /**
     * Il metodo hex2rgb traduce un codice esadecimale nel corrispondente valore rgb
     * @param colorStr	stringa che traduce il codice esadecimale in RGB
     * @return	ritorna il valore rgb
     */
    public static Color hex2Rgb(String colorStr)
    {
        return new Color(Integer.valueOf( colorStr.substring( 1, 3 ), 16 ), Integer.valueOf( colorStr.substring( 3, 5 ), 16 ), Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }

    /**
     * Il costruttore contine il codice per la creazione e la visualizzazione della schermata relativa
     * alle prenotazioni effettuate dal cittadino
     * @param checkLogin controlla se è stato effettuato un accesso
     * @param account fa riferimento al cittadino che ha effettuato l'accesso
     * @throws IOException il costruttore contiene del codice che legge delle immagini quindi può genererare IOException
     * @throws NotBoundException il costruttore contiene del codice che si conntte al rmiregistry quindi può genererare NotBoundException
     * @throws SQLException il costruttore contiene del codice che riceve dati dal database quindi può genererare SQLException
     */
    public ListaPrenotazioni(boolean checkLogin, Cittadino account, String ind) throws IOException, NotBoundException, SQLException {
        ip = ind;
        Registry registro = LocateRegistry.getRegistry(ip, 1099);
        stub = (common.ClientCV) registro.lookup("SERVERCV");

        lista = stub.getPrenotazioni(account);

        if(lista.size() == 0){
            orario1L.setVisible(false);
            orario2L.setVisible(false);
            data2L.setText("Non prenotata");
            data1L.setText("Non prenotata");
        }
        else if(lista.size() == 1){
            orario1L.setVisible(true);
            Prenotazione p1 = lista.get(0);
            String data[] = p1.getData().split(" ");
            data1L.setText("Data: "+data[0]);
            orario1L.setText("Orario: "+data[1]);
            orario2L.setVisible(false);
            data2L.setText("Non prenotata");
        }
        else if(lista.size() == 2){
            orario1L.setVisible(true);
            orario2L.setVisible(true);
            Prenotazione p1 = lista.get(1);
            Prenotazione p2 = lista.get(0);
            String data1[] = p1.getData().split(" ");
            String data2[] = p2.getData().split(" ");
            data2L.setText("Data: "+data1[0]);
            orario1L.setText("Orario: "+data1[1]);
            data1L.setText("Data: "+data2[0]);
            orario2L.setText("Orario: "+data2[1]);
        }

        int sizeL = 17;
        int sizeTF = 17;

        titolo.setBounds(140 ,30,500,50);
        titolo.setForeground(hex2Rgb("#1E90FF"));
        titolo.setBackground(hex2Rgb("#FFFFFF"));
        titolo.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        titolo.setFont(new Font("Comic Sans",Font.BOLD,25));

        pren1.setBounds(70,140,150,25);
        pren1.setForeground(hex2Rgb("#1E90FF"));
        pren1.setBackground(hex2Rgb("#FFFFFF"));
        pren1.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        pren1.setFont(new Font("Comic Sans",Font.ITALIC,sizeL));

        data1L.setBounds(160,140,200,25);
        data1L.setForeground(hex2Rgb("#1E90FF"));
        data1L.setBackground(hex2Rgb("#FFFFFF"));
        data1L.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        data1L.setFont(new Font("Comic Sans",Font.ITALIC,sizeL));

        orario1L.setBounds(162,170,200,25);
        orario1L.setForeground(hex2Rgb("#1E90FF"));
        orario1L.setBackground(hex2Rgb("#FFFFFF"));
        orario1L.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        orario1L.setFont(new Font("Comic Sans",Font.ITALIC,sizeL));

        pren2.setBounds(70,240,150,25);
        pren2.setForeground(hex2Rgb("#1E90FF"));
        pren2.setBackground(hex2Rgb("#FFFFFF"));
        pren2.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        pren2.setFont(new Font("Comic Sans",Font.ITALIC,sizeL));

        data2L.setBounds(160,240,200,25);
        data2L.setForeground(hex2Rgb("#1E90FF"));
        data2L.setBackground(hex2Rgb("#FFFFFF"));
        data2L.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        data2L.setFont(new Font("Comic Sans",Font.ITALIC,sizeL));

        orario2L.setBounds(162,270,200,25);
        orario2L.setForeground(hex2Rgb("#1E90FF"));
        orario2L.setBackground(hex2Rgb("#FFFFFF"));
        orario2L.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        orario2L.setFont(new Font("Comic Sans",Font.ITALIC,sizeL));

        /*
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime now = LocalDateTime.now();
        orario1TF.setText(dtf.format(now));
        */


        Image imageBack = ImageIO.read(Objects.requireNonNull(ListaPrenotazioni.class.getResource("/indietro.jpeg")));
        imageBack = imageBack.getScaledInstance( 35, 35,  java.awt.Image.SCALE_SMOOTH ) ;
        indietro.setIcon(new ImageIcon(imageBack));
        indietro.setBounds(15,15,35,35);
        indietro.setForeground(hex2Rgb("#1E90FF"));
        indietro.setBackground(hex2Rgb("#F0F8FF"));
        indietro.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        indietro.setFocusable(false);

        indietro.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                try {
                    new Cittadini(checkLogin, account, ip);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                f.setVisible(false);
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                f.dispose();
            }
        });



        f.add(titolo);
        f.add(pren1);
        f.add(data1L);
        f.add(orario1L);
        f.add(pren2);
        f.add(data2L);
        f.add(orario2L);
        f.add(indietro);

        f.getContentPane().setBackground(hex2Rgb("#FFFFFF"));
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setResizable(false);  //lock size finestra
        f.setBounds(660, 100, 550, 400);
        ImageIcon img = new ImageIcon(Objects.requireNonNull(ListaPrenotazioni.class.getResource("/logo.jpg")));
        Image img1 = img.getImage();
        Image img2 = img1.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        f.setIconImage(img2);
    }


    public static void main (String[]args) throws IOException, NotBoundException, SQLException {
        new ListaPrenotazioni(false,null, "localhost");
    }
}
