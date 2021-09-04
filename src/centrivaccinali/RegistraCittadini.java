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
import java.util.Locale;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.*;

import static java.lang.Integer.parseInt;

/**
 * La classe Registra Cittadini serve per registrare una nuova vaccinazione nel DB.
 * @author Davide Feldkircher
 */

public class RegistraCittadini extends UnicastRemoteObject {

    /**
     * Indirizzo ip del server
     */
    public static String ip = "";

    /**
     * Stub utilizzato per il collegamento al ServerCV.
     */

    private static ClientCV stub;

    /**
     * Frame di contenimento della finestra.
     */

    JFrame f = new JFrame("Registrazione Cittadino");

    /**
     * Bottone per il ritorno alla schermata principale.
     */

    JButton indietro = new JButton();

    /**
     * Label che indica il titolo della finestra
     */

    JLabel titolo = new JLabel("REGISTRAZIONE CITTADINO");

    /**
     * Label che indica un errore nell'inserimento del Centro Vaccinale
     */
    static JLabel errorCentro = new JLabel("*");
    /**
     * Label che indica un errore nell'inserimento del CF
     */
    static JLabel errorCf = new JLabel("*");
    /**
     * Label che indica un errore nell'inserimento della data
     */
    static JLabel errorData = new JLabel("*");

    /**
     * Label che indica un errore nell'inserimento del tipo di vaccino
     */
    static JLabel errorTipo = new JLabel("*");

    /**
     * Label che indica un errore nell'inserimento del nome
     */
    static JLabel errorNome = new JLabel("*");

    /**
     * Label che indica un errore nell'inserimento del cognome
     */
    static JLabel errorCognome = new JLabel("*");

    /**
     * Label che indica il campo dove l'utente deve inserire il nome del Centro Vaccinale in cui è stato effettuata la vaccinazione
     */
    JLabel centroL = new JLabel("Centro Vacc. :", SwingConstants.CENTER);

    /**
     * TextField dove l'utente deve inserire il nome del Centro Vaccinale in cui è stato effettuata la vaccinazione
     */
    JTextField centroTF = new JTextField("");

    /**
     * Label che indica il campo dove l'utente deve inserire la data del vaccino
     */
    JLabel dataL = new JLabel("Data:", SwingConstants.CENTER);
    /**
     * PasswordField dove l'utente deve inserire la data del vaccino
     */
    JTextField dataTF = new JTextField("");

    /**
     * Label che indica il campo dove l'utente deve inserire il tipo di vaccino
     */

    String[] tipi = {"Pfizer", "Moderna", "Astrazeneca", "J&J"};

    /**
     * JComboBox utilizzata per la selezione del tipo di vaccinazione.
     */

    JComboBox<String> tipoTF = new JComboBox<>(tipi);

    /**
     * PasswordField dove l'utente deve inserire il tipo di vaccino
     */
    JLabel tipoL = new JLabel("Tipo Vaccino:");

    /**
     * Label che indica il campo dove l'utente deve inserire il proprio nome
     */
    JLabel nomeL = new JLabel("Nome:", SwingConstants.CENTER);
    /**
     * TextField dove l'utente deve inserire il proprio nome
     */
    JTextField nomeTF = new JTextField("");

    /**
     * Label che indica il campo dove l'utente deve inserire il proprio cognome
     */
    JLabel cognomeL = new JLabel("Cognome:", SwingConstants.CENTER);
    /**
     * TextField dove l'utente deve inserire il proprio cognome
     */
    JTextField cognomeTF = new JTextField("");

    /**
     * TextField dove l'utente deve inserire il CF
     */
    JTextField cfTF = new JTextField("");
    /**
     * Label che indica il campo dove l'utente deve inserire il CF
     */
    JLabel cfL = new JLabel("Cod. Fiscale:" , SwingConstants.CENTER);
    /**
     * Bottone per inviare i campi ed iscriversi
     */

    JButton b = new JButton("REGISTRA");

    /**
     * Metodo utlizzato per la conversione dei codici RGB.
     */

    public static Color hex2Rgb(String colorStr)
    {
        return new Color(Integer.valueOf( colorStr.substring( 1, 3 ), 16 ), Integer.valueOf( colorStr.substring( 3, 5 ), 16 ), Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }

    /**
     * Costruttore che crea l'interfaccia e ne gestisce lo stile, la dimensione, la posizione e i suoi listeners.
     * @param ind Indirizzo ip della macchina Server
     * @throws IOException perchè il costruttore lavora con delle immagini che posso essere caricate in modo errato.
     * @throws NotBoundException perchè il costruttore contiene codice che si connette all'RMI register.
     */


    public RegistraCittadini(String ind) throws IOException, NotBoundException {
        ip = ind;
        Registry registro = LocateRegistry.getRegistry(ip, 1099);
        stub = (common.ClientCV) registro.lookup("SERVERCV");

        int sizeL = 17;
        int sizeTF = 17;

        errorCentro.setBounds(55,105,25,25);
        errorCentro.setForeground(Color.RED);
        errorCentro.setBackground(hex2Rgb("#FF0000"));
        errorCentro.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        errorCentro.setFont(new Font("Comic Sans",Font.BOLD,25));
        errorCentro.setVisible(false);

        errorData.setBounds(90,305,25,25);
        errorData.setForeground(Color.RED);
        errorData.setBackground(hex2Rgb("#FF0000"));
        errorData.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        errorData.setFont(new Font("Comic Sans",Font.BOLD,25));
        errorData.setVisible(false);

        errorNome.setBounds(85,155,25,25);
        errorNome.setForeground(Color.RED);
        errorNome.setBackground(hex2Rgb("#FF0000"));
        errorNome.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        errorNome.setFont(new Font("Comic Sans",Font.BOLD,25));
        errorNome.setVisible(false);

        errorCognome.setBounds(70,205,25,25);
        errorCognome.setForeground(Color.RED);
        errorCognome.setBackground(hex2Rgb("#FF0000"));
        errorCognome.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        errorCognome.setFont(new Font("Comic Sans",Font.BOLD,25));
        errorCognome.setVisible(false);

        errorCf.setBounds(60,255,25,25);
        errorCf.setForeground(Color.RED);
        errorCf.setBackground(hex2Rgb("#FF0000"));
        errorCf.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        errorCf.setFont(new Font("Comic Sans",Font.BOLD,25));
        errorCf.setVisible(false);

        titolo.setBounds(122 ,30,500,50);
        titolo.setForeground(hex2Rgb("#0000CD"));
        titolo.setBackground(hex2Rgb("#F0F8FF"));
        titolo.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        titolo.setFont(new Font("Comic Sans",Font.BOLD,25));

        centroL.setBounds(70,100,110,25);
        centroL.setForeground(hex2Rgb("#1E90FF"));
        centroL.setBackground(hex2Rgb("#F0F8FF"));
        centroL.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        centroL.setFont(new Font("Comic Sans",Font.ITALIC,sizeL));

        centroTF.setBounds(200,100,300,25);
        centroTF.setForeground(hex2Rgb("#1E90FF"));
        centroTF.setBackground(hex2Rgb("#F0F8FF"));
        centroTF.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, hex2Rgb("#1E90FF")));
        centroTF.setFont(new Font("Comic Sans",Font.ITALIC,sizeTF));
        centroTF.setHorizontalAlignment(JTextField.CENTER);
        centroTF.setCaretColor(hex2Rgb("#1E90FF"));
        centroTF.setFocusTraversalKeysEnabled(false);

        centroTF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }
            @Override
            public void focusLost(FocusEvent e) {
                String centro = centroTF.getText();
                if (centro.length()!=0) {
                    centroTF.setText(centro.substring(0, 1).toUpperCase(Locale.ROOT) + centro.substring(1));
                    for (int i = 0; i < centroTF.getText().length() - 1; i++) {
                        centro = centroTF.getText();
                        if (centro.charAt(i) == ' ') {
                            centroTF.setText(centro.substring(0, i + 1) + Character.toUpperCase(centro.charAt(i + 1)) + centro.substring(i + 2));
                        }
                    }
                }
                checkCentro(centroTF.getText());
            }
        });

        KeyListener centroTFkey = new KeyListener() {

            public void keyPressed(KeyEvent keyEvent) {}

            public void keyReleased(KeyEvent keyEvent) {}

            public void keyTyped(KeyEvent keyEvent)
            {
                check(keyEvent);
            }

            private void check(KeyEvent keyEvent)
            {
                if(keyEvent.getKeyChar() == KeyEvent.VK_TAB)
                    nomeTF.requestFocus();
            }
        };
        centroTF.addKeyListener(centroTFkey);

        dataL.setBounds(70,300,110,25);
        dataL.setForeground(hex2Rgb("#1E90FF"));
        dataL.setBackground(hex2Rgb("#F0F8FF"));
        dataL.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        dataL.setFont(new Font("Comic Sans",Font.ITALIC,sizeL));

        dataTF.setBounds(200,300,300,25);
        dataTF.setForeground(hex2Rgb("#1E90FF"));
        dataTF.setBackground(hex2Rgb("#F0F8FF"));
        dataTF.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, hex2Rgb("#1E90FF")));
        dataTF.setFont(new Font("Comic Sans",Font.ITALIC,sizeTF));
        dataTF.setHorizontalAlignment(JTextField.CENTER);
        dataTF.setCaretColor(hex2Rgb("#1E90FF"));
        dataTF.setFocusTraversalKeysEnabled(false);
        String sample = java.time.LocalDate.now().toString();
        String today = sample.substring(8,10) + "/" + sample.substring(5,7) + "/" + sample.substring(0,4);
        dataTF.setText(today);

        KeyListener dataKey = new KeyListener()
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
                    centroTF.requestFocus();
            }
        };
        dataTF.addKeyListener(dataKey);


        tipoL.setBounds(70,350,110,25);
        tipoL.setForeground(hex2Rgb("#1E90FF"));
        tipoL.setBackground(hex2Rgb("#F0F8FF"));
        tipoL.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        tipoL.setFont(new Font("Comic Sans",Font.ITALIC,sizeL));

        tipoTF.setBounds(200,350,300,25);
        tipoTF.setForeground(hex2Rgb("#1E90FF"));
        tipoTF.setBackground(hex2Rgb("#F0F8FF"));
        tipoTF.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, hex2Rgb("#1E90FF")));
        tipoTF.setFont(new Font("Comic Sans",Font.ITALIC,sizeTF));
        tipoTF.setFocusTraversalKeysEnabled(false);
        tipoTF.setFocusable(false);

        nomeL.setBounds(70,150,110,25);
        nomeL.setForeground(hex2Rgb("#1E90FF"));
        nomeL.setBackground(hex2Rgb("#F0F8FF"));
        nomeL.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        nomeL.setFont(new Font("Comic Sans",Font.ITALIC,sizeL));


        nomeTF.setBounds(200,150,300,25);
        nomeTF.setForeground(hex2Rgb("#1E90FF"));
        nomeTF.setBackground(hex2Rgb("#F0F8FF"));
        nomeTF.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, hex2Rgb("#1E90FF")));
        nomeTF.setFont(new Font("Comic Sans",Font.ITALIC,sizeTF));
        nomeTF.setHorizontalAlignment(JTextField.CENTER);
        nomeTF.setCaretColor(hex2Rgb("#1E90FF"));
        nomeTF.setFocusTraversalKeysEnabled(false);

        nomeTF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }
            @Override
            public void focusLost(FocusEvent e) {
                String nome = nomeTF.getText();
                if (nome.length()!=0) {
                    nomeTF.setText(nome.substring(0, 1).toUpperCase(Locale.ROOT) + nome.substring(1));
                    for (int i = 0; i < nomeTF.getText().length() - 1; i++) {
                        nome = nomeTF.getText();
                        if (nome.charAt(i) == ' ') {
                            nomeTF.setText(nome.substring(0, i + 1) + Character.toUpperCase(nome.charAt(i + 1)) + nome.substring(i + 2));
                        }
                    }
                }
                checkNome(nomeTF.getText());
            }
        });

        KeyListener nomeKey = new KeyListener()
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
                    cognomeTF.requestFocus();
            }
        };
        nomeTF.addKeyListener(nomeKey);

        cognomeL.setBounds(70,200,110,25);
        cognomeL.setForeground(hex2Rgb("#1E90FF"));
        cognomeL.setBackground(hex2Rgb("#F0F8FF"));
        cognomeL.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        cognomeL.setFont(new Font("Comic Sans",Font.ITALIC,sizeL));

        cognomeTF.setBounds(200,200,300,25);
        cognomeTF.setForeground(hex2Rgb("#1E90FF"));
        cognomeTF.setBackground(hex2Rgb("#F0F8FF"));
        cognomeTF.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, hex2Rgb("#1E90FF")));
        cognomeTF.setFont(new Font("Comic Sans",Font.ITALIC,sizeTF));
        cognomeTF.setHorizontalAlignment(JTextField.CENTER);
        cognomeTF.setCaretColor(hex2Rgb("#1E90FF"));
        cognomeTF.setFocusTraversalKeysEnabled(false);

        cognomeTF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }
            @Override
            public void focusLost(FocusEvent e) {
                String cognome = cognomeTF.getText();
                if (cognome.length()!=0) {
                    cognomeTF.setText(cognome.substring(0, 1).toUpperCase(Locale.ROOT) + cognome.substring(1));
                    for (int i = 0; i < cognomeTF.getText().length() - 1; i++) {
                        cognome = cognomeTF.getText();
                        if (cognome.charAt(i) == ' ') {
                            cognomeTF.setText(cognome.substring(0, i + 1) + Character.toUpperCase(cognome.charAt(i + 1)) + cognome.substring(i + 2));
                        }
                    }
                }
                checkCognome(cognomeTF.getText());
            }
        });

        KeyListener cognomeKey = new KeyListener()
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
                    cfTF.requestFocus();
            }
        };
        cognomeTF.addKeyListener(cognomeKey);

        cfL.setBounds(70,250,110,25);
        cfL.setForeground(hex2Rgb("#1E90FF"));
        cfL.setBackground(hex2Rgb("#F0F8FF"));
        cfL.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        cfL.setFont(new Font("Comic Sans",Font.ITALIC,sizeL));

        cfTF.setBounds(200,250,300,25);
        cfTF.setForeground(hex2Rgb("#1E90FF"));
        cfTF.setBackground(hex2Rgb("#F0F8FF"));
        cfTF.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, hex2Rgb("#1E90FF")));
        cfTF.setFont(new Font("Comic Sans",Font.ITALIC,sizeTF));
        cfTF.setHorizontalAlignment(JTextField.CENTER);
        cfTF.setCaretColor(hex2Rgb("#1E90FF"));
        cfTF.setFocusTraversalKeysEnabled(false);

        cfTF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }
            @Override
            public void focusLost(FocusEvent e) {
                cfTF.setText(cfTF.getText().toUpperCase());
                checkCF(cfTF.getText());
            }
        });

        KeyListener cfKey = new KeyListener()
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
                    dataTF.requestFocus();
            }
        };
        cfTF.addKeyListener(cfKey);

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

        b.setBounds(200,425,200,40);
        b.setBackground(hex2Rgb("#F0F8FF"));
        b.setForeground(hex2Rgb("#1E90FF"));
        b.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, hex2Rgb("#1E90FF")));
        b.setFont(new Font("Comic Sans",Font.BOLD,15));
        b.setFocusTraversalKeysEnabled(false);
        b.setFocusable(false);

        b.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {

                if(controlloCampi()) {
                    Vaccinazione v = new Vaccinazione(centroTF.getText(), nomeTF.getText(), cognomeTF.getText(), cfTF.getText(), dataTF.getText(), Objects.requireNonNull(tipoTF.getSelectedItem()).toString());
                    try {
                        boolean chk = stub.registraVaccinato(v);
                        if(chk) {
                            JOptionPane.showMessageDialog(f, "Esito vaccinazione negativo", "Errore Vaccinazione", JOptionPane.ERROR_MESSAGE);
                            new CentriVaccinali(ip);
                            f.setVisible(false);
                            f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                            f.dispose();
                        }
                    } catch (SQLException | RemoteException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        });

        f.add(titolo);
        f.add(centroL);
        f.add(centroTF);
        f.add(dataL);
        f.add(dataTF);
        f.add(tipoL);
        f.add(tipoTF);
        f.add(nomeL);
        f.add(nomeTF);
        f.add(cognomeL);
        f.add(cognomeTF);
        f.add(cfL);
        f.add(cfTF);
        f.add(errorCentro);
        f.add(errorData);
        f.add(errorTipo);
        f.add(errorNome);
        f.add(errorCognome);
        f.add(errorCf);
        f.add(b);
        f.add(indietro);

        f.getContentPane().setBackground(hex2Rgb("#FFFFFF"));
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setResizable(false);  //lock size finestra
        f.setBounds(660, 100, 600, 550);
        ImageIcon img = new ImageIcon(Objects.requireNonNull(CentriVaccinali.class.getResource("/logo.jpg")));
        Image img1 = img.getImage();
        Image img2 = img1.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        f.setIconImage(img2);
    }

    /**
     * Metodo che controlla la compilazione del campo Nome Centro Vaccinale: non deve essere vuoto.
     * @param centro che contiene il testo inserito nel campo relativo.
     * @return boolean che restituisce l'esito del check.
     */

    public static boolean checkCentro(String centro){
        if(!centro.equals("")){
            errorCentro.setVisible(false);
            return true;
        }
        errorCentro.setVisible(true);
        return false;
    }

    /**
     * Metodo che controlla la compilazione del campo Data Vaccinazione: non deve essere vuoto e deve essere nel formato gg/mm/aaaa, viene autocopilato con la data odierna.
     * @param data che contiene il testo inserito nel campo relativo.
     * @return boolean che restituisce l'esito del check.
     */

    public static boolean checkData(String data)
    {
        if(data.length() == 10){

            String giorno = data.substring(0,2);
            String mese = data.substring(3,5);
            String anno = data.substring(6,10);

            if(parseInt(giorno) > 0  && parseInt(giorno) < 32 &&  parseInt(mese) > 0 &&  parseInt(mese) < 13  && parseInt(anno) > 2020){
                errorData.setVisible(false);
                return true;}

            else {
                errorData.setVisible(true);
                return false;
            }
        }
        errorData.setVisible(true);
        return false;
    }

    /**
     * Metodo che controlla la compilazione del campo Nome Vaccinato: non deve essere vuoto.
     * @param nome che contiene il testo inserito nel campo relativo.
     * @return boolean che restituisce l'esito del check.
     */

    public static boolean checkNome(String nome)
    {
        if(nome.equals("")) {
            errorNome.setVisible(true);
            return false;
        }

        for (char c : nome.toCharArray())
            if (!(Character.isLetter(c)) && !(c == ' ') && !(c == '\'')) {
                errorNome.setVisible(true);
                return false;
            }
            errorNome.setVisible(false);
            return true;
    }

    /**
     * Metodo che controlla la compilazione del campo Cognome Vaccinato: non deve essere vuoto.
     * @param cognome nome che contiene il testo inserito nel campo relativo.
     * @return boolean che restituisce l'esito del check.
     */

    public static boolean checkCognome(String cognome)
    {
        if(cognome.equals("")) {
            errorCognome.setVisible(true);
            return false;
        }

        for(char c : cognome.toCharArray())
            if(!(Character.isLetter(c)) && !(c == ' ') && !(c == '\'')){
                errorCognome.setVisible(true);
                return false;}
            errorCognome.setVisible(false);
        return true;
    }

    /**
     * Metodo che controlla la compilazione del campo CF : non deve essere vuoto e deve essere formattato correttamente.
     * @param CF nome che contiene il testo inserito nel campo relativo.
     * @return boolean che restituisce l'esito del check.
     */

    public static boolean checkCF(String CF) {
        if(CF.length() == 16) {
            String nome = CF.substring(0, 3);
            String cognome = CF.substring(3, 6);
            String anno = CF.substring(6, 8);
            String mese = CF.substring(8, 9);
            String giorno = CF.substring(9, 11);

            if (nome.matches("[a-zA-Z]+") && cognome.matches("[a-zA-Z]+") && mese.matches("[a-zA-Z]+") && anno.matches("[0-9]+") && giorno.matches("[0-9]+")) {
                errorCf.setVisible(false);
                return true;
            }
            else {
                errorCf.setVisible(true);
                return false;
            }
        }
        errorCf.setVisible(true);
        return true;
    }

    /**
     * Metodo che esegue tutti i check e permette la registrazione a DB della vaccinazione.
     * @return boolean che restituisce l'esito del check.
     */

    private boolean controlloCampi () {
            return checkNome(nomeTF.getText()) & checkCognome(cognomeTF.getText()) & checkCF(cfTF.getText()) & checkData(dataTF.getText()) & checkCentro(centroTF.getText());
        }


        public static void main (String[]args) throws Exception {
            new RegistraCittadini("localhost");
        }
    }
