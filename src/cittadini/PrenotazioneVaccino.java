package cittadini;

import centrivaccinali.RegistraCentri;
import common.Cittadino;
import common.ClientCV;
import common.Prenotazione;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

import static java.lang.Integer.parseInt;


/**
 * La classe PrenotazioneVaccino contiene il codice per la creazione della schermata per la prenotazione di un vaccino
 * @author Giulio Baricci
 */

public class PrenotazioneVaccino {

    /**
     * Indirizzo ip della macchina Server
     */
    public static String ip = "";

    /**
     * Oggetto che fa riferimento al server disponibile sul rmiregistry
     */
    private static ClientCV stub;

    /**
     * Frame della schermata di prenotazione di un vaccino
     */
    JFrame f = new JFrame("Prenotazione Vaccino");

    /**
     * Bottone per ritornare alla schermata precedente
     */
    JButton indietro = new JButton();

    /**
     * Label per il titolo
     */
    JLabel titolo = new JLabel("Prenotazione Vaccino");

    /**
     * Label per visualizzare un eventuale errore nella data
     */
    static JLabel errorData = new JLabel("*");

    /**
     * Label per visualizzare un eventuale errore nell'orario
     */
    static JLabel errorOrario = new JLabel("*");

    /**
     * Label che indica il campo dove deve essere inserita la data
     */
    JLabel dataL = new JLabel("Data:", SwingConstants.CENTER);

    /**
     * TextField dove deve essere inserita la data
     */
    JTextField dataTF = new JTextField("");

    /**
     * Label che indica il campo dove deve essere inserito l'orario
     */
    JLabel orarioL = new JLabel("Orario:", SwingConstants.CENTER);

    /**
     * TextField dove deve essere inserito l'orario
     */
    JTextField orarioTF = new JTextField("");

    /**
     * Bottone per effettuare la prenotazione del vaccino
     */
    JButton b = new JButton("Prenota");

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
     * Il costruttore della classe PrenotazioneVaccino contiene il codice per la creazione e la visualizzazione
     * della schermata relativa alla prenotazione del vaccino
     * @param checkLogin controlla se è stato effettuato l'accesso
     * @param account fa riferimento al cittadino che ha effettuato l'accesso
     * @throws IOException il costruttore contiene del codice che legge delle immagini quindi può genererare IOException
     * @throws NotBoundException il costruttore contiene del codice che si conntte al rmiregistry quindi può genererare NotBoundException
     */
    public PrenotazioneVaccino(boolean checkLogin, Cittadino account, String ind) throws IOException, NotBoundException, SQLException {

        ip = ind;
        int sizeL = 17;
        int sizeTF = 17;

        Registry registro = LocateRegistry.getRegistry(ip, 1099);
        stub = (common.ClientCV) registro.lookup("SERVERCV");

        errorData.setBounds(90,155,25,25);
        errorData.setForeground(Color.RED);
        errorData.setBackground(hex2Rgb("#FF0000"));
        errorData.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        errorData.setFont(new Font("Comic Sans",Font.BOLD,25));
        errorData.setVisible(false);

        errorOrario.setBounds(85,205,25,25);
        errorOrario.setForeground(Color.RED);
        errorOrario.setBackground(hex2Rgb("#FF0000"));
        errorOrario.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        errorOrario.setFont(new Font("Comic Sans",Font.BOLD,25));
        errorOrario.setVisible(false);



        titolo.setBounds(122 ,30,500,50);
        titolo.setForeground(hex2Rgb("#1E90FF"));
        titolo.setBackground(hex2Rgb("#FFFFFF"));
        titolo.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        titolo.setFont(new Font("Comic Sans",Font.BOLD,25));


        dataL.setBounds(70,150,110,25);
        dataL.setForeground(hex2Rgb("#1E90FF"));
        dataL.setBackground(hex2Rgb("#FFFFFF"));
        dataL.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        dataL.setFont(new Font("Comic Sans",Font.ITALIC,sizeL));

        dataTF.setBounds(200,150,300,25);
        dataTF.setForeground(hex2Rgb("#1E90FF"));
        dataTF.setBackground(hex2Rgb("#FFFFFF"));
        dataTF.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, hex2Rgb("#1E90FF")));
        dataTF.setFont(new Font("Comic Sans",Font.ITALIC,sizeTF));
        dataTF.setHorizontalAlignment(JTextField.CENTER);
        dataTF.setCaretColor(hex2Rgb("#1E90FF"));
        dataTF.setFocusTraversalKeysEnabled(false);
        String sample = java.time.LocalDate.now().toString();
        String today = sample.substring(8,10) + "/" + sample.substring(5,7) + "/" + sample.substring(0,4);
        dataTF.setText(today);




        orarioL.setBounds(70,200,110,25);
        orarioL.setForeground(hex2Rgb("#1E90FF"));
        orarioL.setBackground(hex2Rgb("#FFFFFF"));
        orarioL.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        orarioL.setFont(new Font("Comic Sans",Font.ITALIC,sizeL));


        orarioTF.setBounds(200,200,300,25);
        orarioTF.setForeground(hex2Rgb("#1E90FF"));
        orarioTF.setBackground(hex2Rgb("#FFFFFF"));
        orarioTF.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, hex2Rgb("#1E90FF")));
        orarioTF.setFont(new Font("Comic Sans",Font.ITALIC,sizeTF));
        orarioTF.setHorizontalAlignment(JTextField.CENTER);
        orarioTF.setCaretColor(hex2Rgb("#1E90FF"));
        orarioTF.setFocusTraversalKeysEnabled(false);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime now = LocalDateTime.now();
        orarioTF.setText(dtf.format(now));



        Image imageBack = ImageIO.read(Objects.requireNonNull(PrenotazioneVaccino.class.getResource("/indietro.jpeg")));
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

        b.setBounds(200,325,200,40);
        b.setFont(new Font("Arial", Font.ITALIC, 15));
        b.setBackground(Color.decode("#F0F8FF"));
        b.setForeground(Color.decode("#000000"));
        b.setBorder(BorderFactory.createMatteBorder(5, 0, 5, 0, hex2Rgb("#1E90FF")));
        b.setFocusTraversalKeysEnabled(false);
        b.setFocusable(false);
        java.util.List<Prenotazione> pren = stub.getPrenotazioni(account);
        if(pren.size() == 2)
            b.setEnabled(false);

        b.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(controlloCampi()) {
                        try {
                            Prenotazione p = new Prenotazione(stub.contaPrenotazioni(), account.getUserid(), account.getCv(), dataTF.getText()+" "+orarioTF.getText());
                            stub.prenotaVaccino(p);
                            new Cittadini(checkLogin, account, ip);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        f.setVisible(false);
                        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                        f.dispose();
                    } else
                        JOptionPane.showMessageDialog(f, "- La data non puo' essere precedente o uguale al giorno odierno e deve\n corrisponedere al formato dd/mm/yyyy\n- L'orario deve corrispondere al formato hh:mm", "Errore registrazione", JOptionPane.ERROR_MESSAGE);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
        });

        f.add(titolo);
        f.add(dataL);
        f.add(dataTF);
        f.add(errorData);
        f.add(orarioL);
        f.add(orarioTF);
        f.add(errorOrario);
        f.add(b);
        f.add(indietro);

        f.getContentPane().setBackground(hex2Rgb("#FFFFFF"));
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setResizable(false);  //lock size finestra
        f.setBounds(660, 100, 600, 450);
        ImageIcon img = new ImageIcon(Objects.requireNonNull(PrenotazioneVaccino.class.getResource("/logo.jpg")));
        Image img1 = img.getImage();
        Image img2 = img1.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        f.setIconImage(img2);
    }

    /**
     * Metodo che controlla la correttezza della data inserita
     * @param data corrisponde alla data inserita
     * @return ritorna l'esito del controllo
     */
    public static boolean checkData(String data) throws ParseException {
        if(!data.equals("") && data.length() == 10){

            String giorno = data.substring(0,2);
            String mese = data.substring(3,5);
            String anno = data.substring(6,10);

            if(data.substring(2,3).equals("/") && data.substring(5,6).equals("/")) {
                if (parseInt(giorno) > 0 && parseInt(giorno) < 32 &&
                        parseInt(mese) > 0 && parseInt(mese) < 13 &&
                        parseInt(anno) >= LocalDateTime.now().getYear()) {

                    Date current = new Date();
                    Date selezionata = null;
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    selezionata = sdf.parse(data);
                    if(current.before(selezionata)) {
                        errorData.setVisible(false);
                        return true;
                    }
                } else {

                    errorData.setVisible(true);
                    return false;
                }
            }
        }
        errorData.setVisible(true);
        return false;
    }

    /**
     * Metodo che controlla la correttezza dell'orario inserito
     * @param orario corrisponde all'orario inserito
     * @return ritorna l'esito del controllo
     */
    public static boolean checkOrario(String orario)
    {
        if(!orario.equals("") && orario.length() == 5){

            String ore = orario.substring(0,2);
            String minuti = orario.substring(3,5);

            if(parseInt(ore) >= 0  && parseInt(ore) < 24 &&  parseInt(minuti) >= 0 &&  parseInt(minuti) < 60){
                errorOrario.setVisible(false);
                return true;
            }
            else {
                errorOrario.setVisible(true);
                return false;
            }
        }
        errorOrario.setVisible(true);
        return false;
    }

    /**
     * Metodo che richiama i controlli sulle informazioni inserite
     * @return ritorna l'esito dei controlli
     * @throws ParseException possibile errore di conversione tra data e stringa
     */
    private boolean controlloCampi () throws ParseException {
        return checkOrario(orarioTF.getText()) & checkData(dataTF.getText());
    }


}
