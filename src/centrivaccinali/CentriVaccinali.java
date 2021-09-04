package centrivaccinali;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.Objects;

/**
 * La classe Centri Vaccinali mostra un'interfaccia nel quale decidere se procedere alla registrazione di una nuova vaccinazione o di un nuovo centro vaccinale.
 * @author Davide Feldkircher
 */

public class CentriVaccinali {

    /**
     * Indirizzo ip della macchina server
     */
    public static String ip = "";


    /** Metodo utlizzato per la conversione dei codici RGB*/

    public static Color hex2Rgb(String colorStr) //conversione esadecimale in rgb per sfondo frame
    {
        return new Color(Integer.valueOf( colorStr.substring( 1, 3 ), 16 ), Integer.valueOf( colorStr.substring( 3, 5 ), 16 ), Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }

    /**
     * Frame per la rappresentazione della schermata CentriVaccinali.
     */

    JFrame f = new JFrame("Centri Vaccinali");

    /**
     * Label contenente il titolo della finestra.
     */

    JLabel titolo = new JLabel("CENTRI VACCINALI");

    /**
     * Bottone per il reindirizzamento alla finestra RegistraCentri.
     */

    JButton registraCentro = new JButton ("Registra Centro Vaccinale");

    /**
     * Bottone per il reindirizzamento alla finestra RegistraCittadini.
     */

    JButton registraCittadino = new JButton ("Registra Cittadino");

    /**
     * Label contenente il logo dell'applicazione.
     */

    JLabel logo = new JLabel();

    /**
     * Costruttore che crea l'interfaccia gestendone stile, dimensione, posizione e listeners.
     */

    public CentriVaccinali() {

        titolo.setBounds(250 ,30,500,50);
        titolo.setForeground(hex2Rgb("#0000CD"));
        titolo.setBackground(hex2Rgb("#F0F8FF"));
        titolo.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        titolo.setFont(new Font("Comic Sans",Font.BOLD,30));

        ImageIcon img = new ImageIcon(Objects.requireNonNull(CentriVaccinali.class.getResource("/logo.jpg")));
        Image img1 = img.getImage();
        Image img2 = img1.getScaledInstance(150, 150, Image.SCALE_SMOOTH);

        logo.setIcon(new ImageIcon(img2));
        logo.setBounds(320, 115, 150, 150);

        /*ImageIcon sf = new ImageIcon(Objects.requireNonNull(CentriVaccinali.class.getResource("/sfondo.jpg")));
        Image sf1 = sf.getImage();
        Image sf2 = sf1.getScaledInstance(800, 500, Image.SCALE_SMOOTH);

        sfondo.setIcon(new ImageIcon(sf2));
        sfondo.setBounds(0,-350,1200,1200);
        sfondo.setOpaque(true);*/

        f.getContentPane().setBackground(hex2Rgb("#FFFFFF"));
        f.setBounds(350,150,800,500);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setResizable(false);
        f.setIconImage(img2);

        registraCentro.setBackground(Color.decode("#F0F8FF"));
        registraCentro.setForeground(Color.decode("#000000"));
        registraCentro.setBorder(BorderFactory.createMatteBorder(5, 0, 5, 0, hex2Rgb("#1E90FF")));
        registraCentro.setBounds(425,300,200,75);
        registraCentro.setFont(new Font("Arial", Font.ITALIC, 15));
        registraCentro.setFocusable(false);

        registraCentro.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                f.setVisible(false);
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                f.dispose();
                try {
                    RegistraCentri a = new RegistraCentri(ip);
                } catch (IOException | NotBoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        registraCittadino.setBackground(Color.decode("#F0F8FF"));
        registraCittadino.setForeground(Color.decode("#000000"));
        registraCittadino.setBorder(BorderFactory.createMatteBorder(5, 0, 5, 0, hex2Rgb("#1E90FF")));
        registraCittadino.setBounds(175,300,200,75);
        registraCittadino.setFont(new Font("Arial", Font.ITALIC, 15));
        registraCittadino.setFocusable(false);

        registraCittadino.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                f.setVisible(false);
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                f.dispose();
                try {
                    RegistraCittadini b = new RegistraCittadini(ip);
                } catch (IOException | NotBoundException ex) {
                    ex.printStackTrace();
                }
            }
        });


        f.add(registraCentro);
        f.add(registraCittadino);
        f.add(logo);
        f.add(titolo);
    }

    public static void main (String[] args) {
        if (args.length == 1) {
            ip = args[0];
            SwingUtilities.invokeLater(CentriVaccinali::new);
        } else {
            System.out.print("ERRORE!! Numero di argomenti non valido");
        }
    }


}
