/*
Giulio Baricci 740285 Varese
Guido Bernasconi 740539 Varese
Davide Feldkircher 740956 Varese
 */

package centrivaccinali;

import common.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.*;


/**
 * La classe RegistraCentri serve per registrare un nuovo centro vaccinale nel DB.
 * @author Davide Feldkircher
 */

public class RegistraCentri extends UnicastRemoteObject {

    /**
     * Indirizzo ip della macchina Server
     */

    public static String ip = "";

    /**
     * Oggetto stub utilizzato per interfacciarsi al ServerCV
     */

    private static ClientCV stub;

    /**
     * Metodo utlizzato per la conversione dei codici RGB
     */

    public static Color hex2Rgb(String colorStr)
    {
        return new Color(Integer.valueOf( colorStr.substring( 1, 3 ), 16 ), Integer.valueOf( colorStr.substring( 3, 5 ), 16 ), Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }

    /**
     * Frame della finestra RegistraCentri
     */
    JFrame f = new JFrame("Registrazione Centro Vaccinale");

    /**
     * Bottone di ritorno alla schermata CentriVaccinali
     */

    JButton indietro = new JButton();

    /**
     * Label che indica il titolo della finestra
     */

    JLabel titolo = new JLabel("REGISTRAZIONE CENTRO VACCINALE");

    /**
     * Label che indica un errore nell'inserimento del nome della via
     */
    static JLabel errorNomeVia = new JLabel("*");
    /**
     * Label che indica un errore nell'inserimento del numero civico
     */
    static JLabel errorNumeroVia = new JLabel("*");
    /**
     * Label che indica un errore nell'inserimento del Comune
     */
    static JLabel errorComune = new JLabel("*");

    /**
     * Label che indica un errore nell'inserimento della Provincia
     */
    static JLabel errorProvincia = new JLabel("*");

    /**
     * Label che indica un errore nell'inserimento del nome
     */
    static JLabel errorNome = new JLabel("*");

    /**
     * Label che indica un errore nell'inserimento del CAP
     */
    static JLabel errorCAP = new JLabel("*");

    /**
     * Label che indica il campo dove l'utente deve inserire il nome del Centro Vaccinale
     */
    JLabel nomeCentroL = new JLabel("Nome Centro:", SwingConstants.CENTER);

    /**
     * TextField dove l'utente deve inserire il nome del Centro Vaccinale
     */
    JTextField nomeCentroTF = new JTextField("");

    /**
     * Label che indica il campo dove l'utente deve inserire la data del vaccino
     */
    JLabel nomeViaL = new JLabel("Via:", SwingConstants.CENTER);
    /**
     * PasswordField dove l'utente deve inserire la data del vaccino
     */
    JTextField nomeViaTF = new JTextField("");

    /**
     * Array che contiene i vari tipi di via selezionabili
     */

    String[] vie = {"Via", "Viale", "Piazza"};

    /**
     * JComboBox per la selezione del tipo di via
     */

    JComboBox<String> vieTF = new JComboBox<>(vie);

    /**
     * Array contenente i diversi tipi di centri selezionabili
     */

    String[] tipi = {"Ospedaliero", "Aziendale", "Hub"};

    /**
     * JComboBox per la selezione dei tipi di centro
     */

    JComboBox<String> tipoTF = new JComboBox<>(tipi);

    /**
     * JLabels che indica il campo dove l'utente deve inserire il tipo di centro vaccinale e il tipo di via
     */

    JLabel tipoL = new JLabel("Tipo Centro:");

    JLabel vieL = new JLabel("Qualificatore:");

    /**
     * Label che indica il campo dove l'utente deve inserire il numero civico
     */
    JLabel numeroCivicoL = new JLabel("Num. Civico:", SwingConstants.CENTER);
    /**
     * TextField dove l'utente deve inserire il numero civico
     */
    JTextField numeroCivicoTF = new JTextField("");

    /**
     * Label che indica il campo dove l'utente deve inserire il comune
     */
    JLabel comuneL = new JLabel("Comune:", SwingConstants.CENTER);
    /**
     * TextField dove l'utente deve inserire il comune
     */
    JTextField comuneTF = new JTextField("");

    /**
     * TextField dove l'utente deve inserire la provincia
     */
    JTextField provinciaTF = new JTextField("");
    /**
     * Label che indica il campo dove l'utente deve inserire la provincia
     */
    JLabel provinciaL = new JLabel("Sigla Provincia:" , SwingConstants.CENTER);

    /**
     * TextField dove l'utente deve inserire il CAP
     */
    JTextField CAPTF = new JTextField("");
    /**
     * Label che indica il campo dove l'utente deve inserire il CAP
     */
    JLabel CAPL = new JLabel("CAP:" , SwingConstants.CENTER);

    /**
     * Bottone per inviare i campi ed iscriversi
     */

    JButton b = new JButton("REGISTRA CENTRO");

    /**
     * Esecuzione principale della classe RegistraCentri
     * @param ind Inidirizzo ip della macchina Server
     * @throws IOException perchè il costruttore lavora con delle immagini che posso essere caricate in modo errato.
     * @throws NotBoundException perchè il costruttore contiene codice che si connette all'RMI register.
     */
    public RegistraCentri(String ind) throws IOException, NotBoundException {
        ip = ind;
        Registry registro = LocateRegistry.getRegistry(ip, 1099);
        stub = (common.ClientCV) registro.lookup("SERVERCV");

        int sizeL = 17;
        int sizeTF = 17;

        errorNomeVia.setBounds(95,205,25,25);
        errorNomeVia.setForeground(Color.RED);
        errorNomeVia.setBackground(hex2Rgb("#FF0000"));
        errorNomeVia.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        errorNomeVia.setFont(new Font("Comic Sans",Font.BOLD,25));
        errorNomeVia.setVisible(false);

        errorComune.setBounds(75,305,25,25);
        errorComune.setForeground(Color.RED);
        errorComune.setBackground(hex2Rgb("#FF0000"));
        errorComune.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        errorComune.setFont(new Font("Comic Sans",Font.BOLD,25));
        errorComune.setVisible(false);

        errorNome.setBounds(60,105,25,25);
        errorNome.setForeground(Color.RED);
        errorNome.setBackground(hex2Rgb("#FF0000"));
        errorNome.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        errorNome.setFont(new Font("Comic Sans",Font.BOLD,25));
        errorNome.setVisible(false);

        errorCAP.setBounds(90,405,25,25);
        errorCAP.setForeground(Color.RED);
        errorCAP.setBackground(hex2Rgb("#FF0000"));
        errorCAP.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        errorCAP.setFont(new Font("Comic Sans",Font.BOLD,25));
        errorCAP.setVisible(false);

        errorNumeroVia.setBounds(60,255,25,25);
        errorNumeroVia.setForeground(Color.RED);
        errorNumeroVia.setBackground(hex2Rgb("#FF0000"));
        errorNumeroVia.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        errorNumeroVia.setFont(new Font("Comic Sans",Font.BOLD,25));
        errorNumeroVia.setVisible(false);

        errorProvincia.setBounds(45,355,25,25);
        errorProvincia.setForeground(Color.RED);
        errorProvincia.setBackground(hex2Rgb("#FF0000"));
        errorProvincia.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        errorProvincia.setFont(new Font("Comic Sans",Font.BOLD,25));
        errorProvincia.setVisible(false);

        titolo.setBounds(60 ,30,500,50);
        titolo.setForeground(hex2Rgb("#0000CD"));
        titolo.setBackground(hex2Rgb("#F0F8FF"));
        titolo.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        titolo.setFont(new Font("Comic Sans",Font.BOLD,25));

        nomeCentroL.setBounds(70,100,110,25);
        nomeCentroL.setForeground(hex2Rgb("#1E90FF"));
        nomeCentroL.setBackground(hex2Rgb("#F0F8FF"));
        nomeCentroL.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        nomeCentroL.setFont(new Font("Comic Sans",Font.ITALIC,sizeL));

        nomeCentroTF.setBounds(200,100,300,25);
        nomeCentroTF.setForeground(hex2Rgb("#1E90FF"));
        nomeCentroTF.setBackground(hex2Rgb("#F0F8FF"));
        nomeCentroTF.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, hex2Rgb("#1E90FF")));
        nomeCentroTF.setFont(new Font("Comic Sans",Font.ITALIC,sizeTF));
        nomeCentroTF.setHorizontalAlignment(JTextField.CENTER);
        nomeCentroTF.setCaretColor(hex2Rgb("#1E90FF"));
        nomeCentroTF.setFocusTraversalKeysEnabled(false);

        nomeCentroTF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }
            @Override
            public void focusLost(FocusEvent e) {
                String nome = nomeCentroTF.getText();
                if (nome.length()!=0) {
                    nomeCentroTF.setText(nome.substring(0, 1).toUpperCase(Locale.ROOT) + nome.substring(1));
                    for (int i = 0; i < nomeCentroTF.getText().length() - 1; i++) {
                        nome = nomeCentroTF.getText();
                        if (nome.charAt(i) == ' ') {
                            nomeCentroTF.setText(nome.substring(0, i + 1) + Character.toUpperCase(nome.charAt(i + 1)) + nome.substring(i + 2));
                        }
                    }
                }
                checkNomeCentro(nomeCentroTF.getText());
            }
        });

        KeyListener centroKey = new KeyListener()
        {
            public void keyPressed(KeyEvent keyEvent) {}

            public void keyReleased(KeyEvent keyEvent) {}

            public void keyTyped(KeyEvent keyEvent)
            {
                check(keyEvent);
            }

            private void check(KeyEvent keyEvent)
            {
                if(keyEvent.getKeyChar() == KeyEvent.VK_TAB)
                    nomeViaTF.requestFocus();
            }
        };
        nomeCentroTF.addKeyListener(centroKey);

        nomeViaL.setBounds(70,200,110,25);
        nomeViaL.setForeground(hex2Rgb("#1E90FF"));
        nomeViaL.setBackground(hex2Rgb("#F0F8FF"));
        nomeViaL.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        nomeViaL.setFont(new Font("Comic Sans",Font.ITALIC,sizeL));

        nomeViaTF.setBounds(200,200,300,25);
        nomeViaTF.setForeground(hex2Rgb("#1E90FF"));
        nomeViaTF.setBackground(hex2Rgb("#F0F8FF"));
        nomeViaTF.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, hex2Rgb("#1E90FF")));
        nomeViaTF.setFont(new Font("Comic Sans",Font.ITALIC,sizeTF));
        nomeViaTF.setHorizontalAlignment(JTextField.CENTER);
        nomeViaTF.setCaretColor(hex2Rgb("#1E90FF"));
        nomeViaTF.setFocusTraversalKeysEnabled(false);

        nomeViaTF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }
            @Override
            public void focusLost(FocusEvent e) {
                String via = nomeViaTF.getText();
                if (via.length()!=0) {
                    nomeViaTF.setText(via.substring(0, 1).toUpperCase(Locale.ROOT) + via.substring(1));
                    for (int i = 0; i < nomeViaTF.getText().length() - 1; i++) {
                        via = nomeViaTF.getText();
                        if (via.charAt(i) == ' ') {
                            nomeViaTF.setText(via.substring(0, i + 1) + Character.toUpperCase(via.charAt(i + 1)) + via.substring(i + 2));
                        }
                    }
                }
                checkVia(nomeViaTF.getText());
            }
        });

        KeyListener viaKey = new KeyListener()
        {
            public void keyPressed(KeyEvent keyEvent) {}

            public void keyReleased(KeyEvent keyEvent) {}

            public void keyTyped(KeyEvent keyEvent)
            {
                check(keyEvent);
            }

            private void check(KeyEvent keyEvent)
            {
                if(keyEvent.getKeyChar() == KeyEvent.VK_TAB)
                    numeroCivicoTF.requestFocus();
            }
        };
        nomeViaTF.addKeyListener(viaKey);

        tipoL.setBounds(70,450,110,25);
        tipoL.setForeground(hex2Rgb("#1E90FF"));
        tipoL.setBackground(hex2Rgb("#F0F8FF"));
        tipoL.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        tipoL.setFont(new Font("Comic Sans",Font.ITALIC,sizeL));

        tipoTF.setBounds(200,450,300,25);
        tipoTF.setForeground(hex2Rgb("#1E90FF"));
        tipoTF.setBackground(hex2Rgb("#F0F8FF"));
        tipoTF.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, hex2Rgb("#1E90FF")));
        tipoTF.setFont(new Font("Comic Sans",Font.ITALIC,sizeTF));
        tipoTF.setFocusTraversalKeysEnabled(false);
        tipoTF.setFocusable(false);

        vieL.setBounds(70,150,110,25);
        vieL.setForeground(hex2Rgb("#1E90FF"));
        vieL.setBackground(hex2Rgb("#F0F8FF"));
        vieL.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        vieL.setFont(new Font("Comic Sans",Font.ITALIC,sizeL));

        vieTF.setBounds(200,150,300,25);
        vieTF.setForeground(hex2Rgb("#1E90FF"));
        vieTF.setBackground(hex2Rgb("#F0F8FF"));
        vieTF.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, hex2Rgb("#1E90FF")));
        vieTF.setFont(new Font("Comic Sans",Font.ITALIC,sizeTF));
        vieTF.setFocusTraversalKeysEnabled(false);
        vieTF.setFocusable(false);

        numeroCivicoL.setBounds(70,250,110,25);
        numeroCivicoL.setForeground(hex2Rgb("#1E90FF"));
        numeroCivicoL.setBackground(hex2Rgb("#F0F8FF"));
        numeroCivicoL.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        numeroCivicoL.setFont(new Font("Comic Sans",Font.ITALIC,sizeL));

        numeroCivicoTF.setBounds(200,250,300,25);
        numeroCivicoTF.setForeground(hex2Rgb("#1E90FF"));
        numeroCivicoTF.setBackground(hex2Rgb("#F0F8FF"));
        numeroCivicoTF.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, hex2Rgb("#1E90FF")));
        numeroCivicoTF.setFont(new Font("Comic Sans",Font.ITALIC,sizeTF));
        numeroCivicoTF.setHorizontalAlignment(JTextField.CENTER);
        numeroCivicoTF.setCaretColor(hex2Rgb("#1E90FF"));
        numeroCivicoTF.setFocusTraversalKeysEnabled(false);

        numeroCivicoTF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }
            @Override
            public void focusLost(FocusEvent e) {
                checkCiv(numeroCivicoTF.getText());
            }
        });

        KeyListener numeroKey = new KeyListener()
        {
            public void keyPressed(KeyEvent keyEvent) {}

            public void keyReleased(KeyEvent keyEvent) {}

            public void keyTyped(KeyEvent keyEvent)
            {
                check(keyEvent);
            }

            private void check(KeyEvent keyEvent)
            {
                if(keyEvent.getKeyChar() == KeyEvent.VK_TAB)
                    comuneTF.requestFocus();
            }
        };
        numeroCivicoTF.addKeyListener(numeroKey);

        comuneL.setBounds(70,300,110,25);
        comuneL.setForeground(hex2Rgb("#1E90FF"));
        comuneL.setBackground(hex2Rgb("#F0F8FF"));
        comuneL.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        comuneL.setFont(new Font("Comic Sans",Font.ITALIC,sizeL));

        comuneTF.setBounds(200,300,300,25);
        comuneTF.setForeground(hex2Rgb("#1E90FF"));
        comuneTF.setBackground(hex2Rgb("#F0F8FF"));
        comuneTF.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, hex2Rgb("#1E90FF")));
        comuneTF.setFont(new Font("Comic Sans",Font.ITALIC,sizeTF));
        comuneTF.setHorizontalAlignment(JTextField.CENTER);
        comuneTF.setCaretColor(hex2Rgb("#1E90FF"));
        comuneTF.setFocusTraversalKeysEnabled(false);

        comuneTF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }
            @Override
            public void focusLost(FocusEvent e) {
                String comune = comuneTF.getText();
                if (comune.length()!=0) {
                    comuneTF.setText(comune.substring(0, 1).toUpperCase(Locale.ROOT) + comune.substring(1));
                    for (int i = 0; i < comuneTF.getText().length() - 1; i++) {
                        comune = comuneTF.getText();
                        if (comune.charAt(i) == ' ') {
                            comuneTF.setText(comune.substring(0, i + 1) + Character.toUpperCase(comune.charAt(i + 1)) + comune.substring(i + 2));
                        }
                    }
                }
                checkComune(comuneTF.getText());
            }
        });

        KeyListener comuneKey = new KeyListener()
        {
            public void keyPressed(KeyEvent keyEvent) {}

            public void keyReleased(KeyEvent keyEvent) {}

            public void keyTyped(KeyEvent keyEvent)
            {
                check(keyEvent);
            }

            private void check(KeyEvent keyEvent)
            {
                if(keyEvent.getKeyChar() == KeyEvent.VK_TAB)
                    provinciaTF.requestFocus();
            }
        };
        comuneTF.addKeyListener(comuneKey);

        CAPL.setBounds(70,400,110,25);
        CAPL.setForeground(hex2Rgb("#1E90FF"));
        CAPL.setBackground(hex2Rgb("#F0F8FF"));
        CAPL.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        CAPL.setFont(new Font("Comic Sans",Font.ITALIC,sizeL));


        CAPTF.setBounds(200,400,300,25);
        CAPTF.setForeground(hex2Rgb("#1E90FF"));
        CAPTF.setBackground(hex2Rgb("#F0F8FF"));
        CAPTF.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, hex2Rgb("#1E90FF")));
        CAPTF.setFont(new Font("Comic Sans",Font.ITALIC,sizeTF));
        CAPTF.setHorizontalAlignment(JTextField.CENTER);
        CAPTF.setCaretColor(hex2Rgb("#1E90FF"));
        CAPTF.setFocusTraversalKeysEnabled(false);

        CAPTF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }
            @Override
            public void focusLost(FocusEvent e) {
                checkCAP(CAPTF.getText());
            }
        });

        KeyListener capKey = new KeyListener()
        {
            public void keyPressed(KeyEvent keyEvent) {}

            public void keyReleased(KeyEvent keyEvent) {}

            public void keyTyped(KeyEvent keyEvent)
            {
                check(keyEvent);
            }

            private void check(KeyEvent keyEvent)
            {
                if(keyEvent.getKeyChar() == KeyEvent.VK_TAB)
                    nomeCentroTF.requestFocus();
            }
        };
        CAPTF.addKeyListener(capKey);

        provinciaL.setBounds(60,350,120,25);
        provinciaL.setForeground(hex2Rgb("#1E90FF"));
        provinciaL.setBackground(hex2Rgb("#F0F8FF"));
        provinciaL.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        provinciaL.setFont(new Font("Comic Sans",Font.ITALIC,sizeL));

        provinciaTF.setBounds(200,350,300,25);
        provinciaTF.setForeground(hex2Rgb("#1E90FF"));
        provinciaTF.setBackground(hex2Rgb("#F0F8FF"));
        provinciaTF.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, hex2Rgb("#1E90FF")));
        provinciaTF.setFont(new Font("Comic Sans",Font.ITALIC,sizeTF));
        provinciaTF.setHorizontalAlignment(JTextField.CENTER);
        provinciaTF.setCaretColor(hex2Rgb("#1E90FF"));
        provinciaTF.setFocusTraversalKeysEnabled(false);

        provinciaTF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }
            @Override
            public void focusLost(FocusEvent e) {
                String provincia = provinciaTF.getText();
                provinciaTF.setText(provincia.toUpperCase());
                checkProvincia(provinciaTF.getText());
            }
        });

        KeyListener provKey = new KeyListener()
        {
            public void keyPressed(KeyEvent keyEvent) {}

            public void keyReleased(KeyEvent keyEvent) {}

            public void keyTyped(KeyEvent keyEvent)
            {
                check(keyEvent);
            }

            private void check(KeyEvent keyEvent)
            {
                if(keyEvent.getKeyChar() == KeyEvent.VK_TAB)
                    CAPTF.requestFocus();
            }
        };
        provinciaTF.addKeyListener(provKey);

        Image imageBack = ImageIO.read(Objects.requireNonNull(RegistraCentri.class.getResource("/indietro.jpeg")));
        imageBack = imageBack.getScaledInstance( 35, 35,  java.awt.Image.SCALE_SMOOTH ) ;
        indietro.setIcon(new ImageIcon(imageBack));
        indietro.setBounds(15,15,35,35);
        indietro.setForeground(hex2Rgb("#1E90FF"));
        indietro.setBackground(hex2Rgb("#F0F8FF"));
        indietro.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        indietro.setFont(new Font("Comic Sans",Font.ITALIC,sizeL));
        indietro.setFocusable(false);

        indietro.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                new CentriVaccinali(ip);
                f.setVisible(false);
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                f.dispose();
            }
        });

        b.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    if (checkCampiCentri()) {

                        try {
                            String nomeCentro = nomeCentroTF.getText().replaceAll(" ", "_");
                            String indirizzo = vieTF.getSelectedItem() + "§" + nomeViaTF.getText() + "§" + numeroCivicoTF.getText() + "§" + comuneTF.getText() + "§" + provinciaTF.getText() + "§" + CAPTF.getText();
                            CentroVaccinale cv = new CentroVaccinale(nomeCentro, Objects.requireNonNull(tipoTF.getSelectedItem()).toString(), indirizzo);
                            stub.registraCentroVaccinale(cv);
                            new CentriVaccinali(ip);
                            f.setVisible(false);
                            f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                            f.dispose();

                        } catch (SQLException | RemoteException ex) {
                            ex.printStackTrace();
                        }
                    }
                } catch (SQLException | RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });

        b.setBounds(200,525,200,40);
        b.setBackground(hex2Rgb("#F0F8FF"));
        b.setForeground(hex2Rgb("#1E90FF"));
        b.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, hex2Rgb("#1E90FF")));
        b.setFont(new Font("Comic Sans",Font.BOLD,15));
        b.setFocusTraversalKeysEnabled(false);
        b.setFocusable(false);

        f.add(nomeCentroL);
        f.add(nomeCentroTF);
        f.add(nomeViaL);
        f.add(nomeViaTF);
        f.add(comuneL);
        f.add(comuneTF);
        f.add(numeroCivicoL);
        f.add(numeroCivicoTF);
        f.add(provinciaL);
        f.add(provinciaTF);
        f.add(tipoL);
        f.add(tipoTF);
        f.add(vieL);
        f.add(vieTF);
        f.add(CAPL);
        f.add(CAPTF);
        f.add(errorCAP);
        f.add(errorComune);
        f.add(errorNome);
        f.add(errorNomeVia);
        f.add(errorNumeroVia);
        f.add(errorProvincia);
        f.add(indietro);
        f.add(b);
        f.add(titolo);

        f.getContentPane().setBackground(hex2Rgb("#FFFFFF"));
        f.setBounds(350,150,600,650);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setResizable(false);
        ImageIcon img = new ImageIcon(Objects.requireNonNull(CentriVaccinali.class.getResource("/logo.jpg")));
        Image img1 = img.getImage();
        Image img2 = img1.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        f.setIconImage(img2);
    }

    /**
     * Metodo per il controllo dell'inserimento del campo nome: non deve essere vuoto e non deve essere già presente nel DB.
     * @param nome che contiene il testo inserito nel TextField del nome.
     * @return booleano che indica l'esito del check.
     */

    public boolean checkNomeCentro(String nome){
        if(nome.equals("")){
            errorNome.setVisible(true);
            return false;
        }
        errorNome.setVisible(false);
        return true;
    }

    /**
     * Metodo per il controllo dell'inserimento del campo Nome Via: non deve essere vuoto.
     * @param via che contiene il testo inserito nel TextField della via.
     * @return booleano che indica l'esito del check.
     */

    public boolean checkVia(String via){
        if(via.equals("")){
            errorNomeVia.setVisible(true);
            return false;
        }
        errorNomeVia.setVisible(false);
        return true;
    }

    /**
     * Metodo per il controllo dell'inserimento del campo Numero Civico: non deve essere vuoto e deve essere composto da solo numeri.
     * @param civ che contiene il testo inserito nel TextField della via.
     * @return booleano che indica l'esito del check.
     */

    public boolean checkCiv(String civ){
        if(!civ.matches("[0-9]+")) {
            errorNumeroVia.setVisible(true);
            return false;
        }
        errorNumeroVia.setVisible(false);
        return true;
    }

    /**
     * Metodo per il controllo dell'inserimento del campo Comune: non deve essere vuoto.
     * @param comune che contiene il testo inserito nel TextField del comune.
     * @return booleano che indica l'esito del check.
     */

    public boolean checkComune(String comune){
        if(comune.equals("")){
            errorComune.setVisible(true);
            return false;
        }
        errorComune.setVisible(false);
        return true;
    }

    /**
     * Metodo per il controllo dell'inserimento del campo Provincia: non deve essere vuoto e deve essere composto da solo due lettere.
     * @param provincia che contiene il testo inserito nel TextField della sigla della provincia.
     * @return booleano che indica l'esito del check.
     */

    public boolean checkProvincia(String provincia){
        if(provincia.length() != 2){
            errorProvincia.setVisible(true);
            return false;
        }
        errorProvincia.setVisible(false);
        return true;
    }

    /**
     * Metodo per il controllo dell'inserimento del campo CAP: non deve essere vuoto e deve essere composto da solo cinque numeri.
     * @param CAP che contiene il testo inserito nel TextField del CAP.
     * @return booleano che indica l'esito del check.
     */

    public boolean checkCAP(String CAP){
        if(CAP.length() != 5){
            errorCAP.setVisible(true);
            return false;}
        errorCAP.setVisible(false);
        return true;
    }

    /**
     *
     * Metodo
     * @param nome che contiene il testo inserito nel TextField del nome del centro vaccinale.
     * @return booleano che indica l'esito del check.
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè chiama un altro metodo al cui interno c'è una query.
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server perciò può lanciare un'eccezione di questo tipo.
     */


    public boolean checkUnique(String nome) throws SQLException, RemoteException {
        nome = nome.replaceAll(" ", "_");
        List<CentroVaccinale> lista = stub.cercaCentroVaccinale(nome);
        for(CentroVaccinale c: lista)
            if(c.getNome().equals(nome)){
                JOptionPane.showMessageDialog(f, "Centro gia' presente nel DB", "Errore registrazione", JOptionPane.ERROR_MESSAGE);
                return false;}
        return true;
    }

    /**
     * Metodo che chiama tutti i check dei campi della schermata prima di eseguire l'inserimento nel DB.
     * @return booleano che indica l'esito del check.
     * @throws SQLException Questo metodo può lanciare questa eccezione perchè chiama un altro metodo al cui interno c'è una query.
     * @throws RemoteException Questo metodo è coinvolto in una comunicazione Client Server perciò può lanciare un'eccezione di questo tipo.
     */


    public boolean checkCampiCentri() throws SQLException, RemoteException {

        return checkCAP(CAPTF.getText()) & checkCiv(numeroCivicoTF.getText()) & checkComune(comuneTF.getText()) & checkProvincia(provinciaTF.getText()) & checkNomeCentro(nomeCentroTF.getText()) & checkVia(nomeViaTF.getText()) & checkUnique(nomeCentroTF.getText());
    }

}