package cittadini;

import centrivaccinali.CentriVaccinali;
import centrivaccinali.RegistraCentri;
import common.Cittadino;
import common.ClientCV;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;


/**
 * La classe Registrazione contiene il codice per la creazione della schermata relativa alla regisrtazione del cittadino
 * @author Giulio Baricci
 */

public class Registrazione {

    /**
     * Indirizzo ip della macchina Server
     */
    public static String ip = "";

    /**
     * Oggetto che fa riferimento al server disponibile sul rmiregistry
     */
    public static ClientCV stub;


    /**
     * Frame della schermata di registrazione di un nuovo cittadino
     */
    static JFrame f = new JFrame("Registrazione Nuovo Account");

    /**
     * Label che indica un errore nell'inserimento della password
     */
    static JLabel errorPwd = new JLabel("*");

    /**
     * Label che indica un errore nell'inserimento della conferma della password
     */
    static JLabel errorConfermaPwd = new JLabel("*");

    /**
     * Label che indica un errore nell'inserimento del nome
     */
    static JLabel errorNome = new JLabel("*");

    /**
     * Label che indica un errore nell'inserimento del cognome
     */
    static JLabel errorCognome = new JLabel("*");

    /**
     * Label che indica un errore nell'inserimento del codice fiscale
     */
    static JLabel errorCodiceFiscale = new JLabel("*");

    /**
     * Label che indica un errore nell'inserimento dello userId
     */
    static JLabel errorUserId = new JLabel("*");

    /**
     * Label che indica un errore nell'inserimento della mail
     */
    static JLabel errorMail = new JLabel("*");

    /**
     * Label dove viene inserita l'ImageIcon della finestra di registrazione
     */
    JLabel imgReg = new JLabel();
    /**
     * Panel dove viene inserita l'immagine della finestra di registrazione
     */
    JPanel panel = new JPanel();

    /**
     * Label dove viene inserita l'ImageIcon della freccia per tornare alla finestra precedente
     */
    JLabel frecciaBack = new JLabel();

    /**
     * Label che indica il campo dove il cittadino deve inserire la password
     */
    JLabel pwdL = new JLabel("Password:", SwingConstants.CENTER);
    /**
     * PasswordField dove il cittadino deve inserire l'username
     */
    JPasswordField pwdTF = new JPasswordField("");

    /**
     * Label che indica il campo dove il cittadino deve inserire la conferma della password
     */
    JLabel confermaPwdL = new JLabel("Conferma Pwd:", SwingConstants.CENTER);
    /**
     * PasswordField dove il cittadino deve inserire la conferma della password
     */
    JPasswordField confermaPwdTF = new JPasswordField("");

    /**
     * Label che indica il campo dove il cittadino deve inserire il proprio nome
     */
    JLabel nomeL = new JLabel("Nome:", SwingConstants.CENTER);
    /**
     * TextField dove il cittadino deve inserire il proprio nome
     */
    JTextField nomeTF = new JTextField("");

    /**
     * Label che indica il campo dove il cittadino deve inserire il proprio cognome
     */
    JLabel cognomeL = new JLabel("Cognome:", SwingConstants.CENTER);
    /**
     * TextField dove il cittadino deve inserire il proprio cognome
     */
    JTextField cognomeTF = new JTextField("");

    /**
     * Label che indica il campo dove il cittadino deve inserire il proprio codice fiscale
     */
    JLabel codiceFiscaleL = new JLabel("Codice fiscale: ", SwingConstants.CENTER);
    /**
     * TextField dove il cittadino deve inserire il proprio codice fiscale
     */
    JTextField codiceFiscaleTF = new JTextField("");

    /**
     * Label che indica il campo dove il cittadino deve inserire la mail
     */
    JLabel mailL = new JLabel("E-Mail: ", SwingConstants.CENTER);
    /**
     * TextField dove il cittadino deve inserire la mail
     */
    JTextField mailTF = new JTextField("");


    /**
     * Label che indica il campo dove il cittadino deve inserire lo userId
     */
    JLabel useridL = new JLabel("Username: ", SwingConstants.CENTER);
    /**
     * TextField dove il cittadino deve inserire lo userId
     */
    JTextField useridTF = new JTextField("");


    /**
     * Bottone per inviare i campi ed passare alla selezione del centro vaccinale
     */
    JButton b = new JButton("SELEZIONA CV");

    /**
     * Label dove verrà inserita l'ImageIcon del simbolo dell'occhio
     */
    JLabel imgOcchio = new JLabel();

    /**
     * Bottone con all'interno la label con l'occhio, una volta premuto cambia l'echo char del campo Password
     */
    JButton occhio = new JButton();

    /**
     * Label dove verrà inserita l'ImageIcon del simbolo dell'occhio
     */
    JLabel imgOcchio1 = new JLabel();

    /**
     * Bottone con all'interno la label con l'occhio, una volta premuto cambia l'echo char del campo Password
     */
    JButton occhio1 = new JButton();

    /**
     * Bottone per tornare alla schermata precedente
     */
    JButton indietro = new JButton();

    /**
     * Label contenente il logo
     */
    JLabel logo = new JLabel();

    /**
     * Variabile integer per il controllo dell'echo char del JPasswordField 'password'
     */
    int chkVisibilityPwdA = 1;


    /**
     * Variabile integer per il controllo dell'echo char del JPasswordField 'conferma password'
     */
    int chkVisibilityPwdB = 1;


    /**
     * Varibile integer per il controllo dell'username nel database degli account
     */
    static int chkOcc = 1;	//presuppone ci sia gia' l'username nel db


    /**
     * Array di booleani, ogni casella dell'array è associata ad un tipo di errore di inserimento che il cittadino potrebbe commettere, quando il cittadino commette uno degli errori previsti la casella corrispondente assume valore true, se il cittadino corregge oppure non commette tale errore allora la casella avrà valore false
     */
    boolean[] errorCounter = {false, false, false, false, false, false, false};

    /**
     * Il metodo hex2rgb traduce un codice esadecimale nel corrispondente valore rgb
     * @param colorStr	stringa che traduce il codice esadecimale in RGB
     * @return	ritorna il valore rgb
     */
    public static Color hex2Rgb(String colorStr) //conversione esadecimale in rgb per sfondo frame
    {
        return new Color(Integer.valueOf( colorStr.substring( 1, 3 ), 16 ), Integer.valueOf( colorStr.substring( 3, 5 ), 16 ), Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }

    /**
     * Metodo per il controllo del nome
     * @param nome valore inserito dal cittadino
     * @return ritorna l'esito del controllo
     */
    public static boolean CheckNome(String nome)
    {
        if(nome.length() >= 3)
        {
            for(char c : nome.toCharArray())
                if(!((c >= 65 && c <= 90) || (c >= 97 && c <= 122)))
                    return false;
            return true;
        }
        else
            return false;
    }

    /**
     * Metodo per il controllo del cognome
     * @param cognome valore inserito dal cittadino
     * @return ritorna l'esito del controllo
     */
    public static boolean CheckCognome(String cognome)
    {
        if(cognome.length() >= 3)
        {
            for(char c : cognome.toCharArray())
                if(!((c >= 65 && c <= 90) || (c >= 97 && c <= 122)))
                    return false;
            return true;
        }
        else
            return false;
    }

    /**
     * Metodo per il contollo del codice fiscale
     * @param CF valore inserito dal cittadino
     * @return ritorna l'esito del controllo
     */
    public static boolean CheckCodFisc(String CF) throws SQLException, RemoteException {
        java.util.List<String> lista = stub.getCF();
        for(String uid : lista)
            if (uid.equals(CF))
                return false;

        if(CF.length() == 16) {
            String nome = CF.substring(0, 3);
            String cognome = CF.substring(3, 6);
            String anno = CF.substring(6, 8);
            String mese = CF.substring(8, 9);
            String giorno = CF.substring(9, 11);

            if (nome.matches("[a-zA-Z]+") && cognome.matches("[a-zA-Z]+") && mese.matches("[a-zA-Z]+") && anno.matches("[0-9]+") && giorno.matches("[0-9]+")) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    /**
     * Metodo per il controllo dello userId
     * @param user valore inserito dal cittadino
     * @return ritorna l'esito del controllo
     */
    private boolean CheckUserId(String user) throws SQLException, RemoteException {
        java.util.List<String> lista = stub.getUsedId();
        for(String uid : lista)
            if (uid.equals(user))
                return false;
        return user.length() >= 3;
    }

    /**
     * Metodo per il controllo della mail
     * @param email valore inserito dal cittadino
     * @return ritorna l'esito del controllo
     */
    public static boolean CheckEmail(String email) throws SQLException, RemoteException {
        java.util.List<String> lista = stub.getEmail();
        for(String uid : lista)
            if (uid.equals(email))
                return false;
        String[] s = email.split("@");
        return s.length == 2;
    }

    /**
     * Metodo per il controllo dell password
     * @param pwd valore inserito dal cittadino
     * @return ritorna l'esito del controllo
     */
    public static boolean CheckPwd(String pwd)
    {
        if(pwd.length() >= 8)
        {
            return true;
        }

        return false;
    }

    /**
     * Metodo per la conferma della password
     * @param confPwd valore inserito dal cittadino
     * @param pwd valore di confronto
     * @return ritorna l'esito del controllo
     */
    public static boolean CheckConfPwd(String confPwd, String pwd)
    {
        if(confPwd.equals(pwd))
        {
            return true;
        }

        return false;
    }

    /**
     * Il costruttore contine il codice per la creazione e la visualizzazione della schermata relativa
     * allaregistrazione del cittadino
     * @throws IOException il costruttore contiene del codice che legge delle immagini quindi può genererare IOException
     */
    public Registrazione(String ind) throws Exception {

        ip = ind;

        Registry registro = LocateRegistry.getRegistry(ip, 1099);
        stub = (common.ClientCV) registro.lookup("SERVERCV");



        b.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e)
            {
                int sentinel = 0;

                //Registrazione cittadino
                if(!CheckNome(nomeTF.getText()))
                {
                    errorNome.setVisible(true);
                    errorCounter[0] = true;
                    sentinel++;
                }
                else
                {
                    errorNome.setVisible(false);
                    errorCounter[0] = false;
                }

                if(!CheckCognome(cognomeTF.getText()))
                {
                    errorCognome.setVisible(true);
                    errorCounter[1] = true;
                    sentinel++;
                }
                else
                {
                    errorCognome.setVisible(false);
                    errorCounter[1] = false;
                }

                try {
                    if(!CheckCodFisc(codiceFiscaleTF.getText()))
                    {
                        errorCodiceFiscale.setVisible(true);
                        errorCounter[2] = true;
                        sentinel++;
                    }
                    else
                    {
                        errorCodiceFiscale.setVisible(false);
                        errorCounter[2] = false;
                    }
                } catch (SQLException | RemoteException ex) {
                    ex.printStackTrace();
                }

                try {
                    if(!CheckUserId(useridTF.getText()))
                    {
                        errorConfermaPwd.setVisible(true);
                        errorCounter[3] = true;
                        sentinel++;
                    }
                    else
                    {
                        errorConfermaPwd.setVisible(false);
                        errorCounter[3] = false;
                    }
                } catch (SQLException | RemoteException ex) {
                    ex.printStackTrace();
                }

                try {
                    if(!CheckEmail(mailTF.getText()))
                    {
                        errorMail.setVisible(true);
                        errorCounter[4] = true;
                        sentinel++;
                    }
                    else
                    {
                        errorMail.setVisible(false);
                        errorCounter[4] = false;
                    }
                } catch (SQLException | RemoteException ex) {
                    ex.printStackTrace();
                }

                if(!CheckPwd(pwdTF.getText()))
                {
                    errorPwd.setVisible(true);
                    errorCounter[5] = true;
                    sentinel++;
                }
                else
                {
                    errorPwd.setVisible(false);
                    errorCounter[5] = false;
                }

                if(!CheckConfPwd(confermaPwdTF.getText(), pwdTF.getText()))
                {
                    errorConfermaPwd.setVisible(true);
                    errorCounter[6] = true;
                    sentinel++;
                }
                else
                {
                    errorConfermaPwd.setVisible(false);
                    errorCounter[6] = false;
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }

                if(sentinel == 0)
                {
                    String pwd = new String(pwdTF.getPassword());
                    Cittadino c = new Cittadino(useridTF.getText(), pwd, nomeTF.getText(), cognomeTF.getText(), codiceFiscaleTF.getText(), mailTF.getText(), null);
                    System.out.println(pwd);

                    try {
                        new Homepage(true, c, true, ip);
                    } catch (IOException | NotBoundException | SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                    f.setVisible(false);
                    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    f.dispose();
                }
                else
                {
                    String msg = "";
                    if(errorCounter[0])
                        msg += "- Il nome deve contenere almeno 3 lettere \n";
                    if(errorCounter[1])
                        msg += "- Il cognome deve contenere almeno 3 lettere \n";
                    if(errorCounter[2])
                        msg += "- Il codice fiscale deve essere di 16 caratteri \n";
                    if(errorCounter[3])
                        msg += "- Lo userId inserito e' gia' esistente \n";
                    if(errorCounter[4])
                        msg += "- L'indirizzo email deve essere corretto \n";
                    if(errorCounter[5])
                        msg += "- La password deve essere di almeno 8 caratteri \n";
                    if(errorCounter[6])
                        msg += "- La conferma deve combaciare con la password \n";
                    JOptionPane.showMessageDialog(f, msg, "Errore registrazione", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        f.getContentPane().setBackground(hex2Rgb("#FFFFFF"));
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setResizable(false);  //lock size finestra
        f.setBounds(660, 50, 600, 770);
        ImageIcon img = new ImageIcon(Objects.requireNonNull(Registrazione.class.getResource("/logo.jpg")));
        Image img1 = img.getImage();
        Image img2 = img1.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        f.setIconImage(img2);

        logo.setIcon(new ImageIcon(img2));
        logo.setBounds(200, 50, 200, 200);

        errorNome.setBounds(60,300,25,25);
        errorNome.setForeground(Color.RED);
        errorNome.setBackground(hex2Rgb("#FFFFFF"));
        errorNome.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        errorNome.setFont(new Font("Comic Sans",Font.BOLD,25));
        errorNome.setVisible(false);


        errorCognome.setBounds(60,350,25,25);
        errorCognome.setForeground(Color.RED);
        errorCognome.setBackground(hex2Rgb("#FFFFFF"));
        errorCognome.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        errorCognome.setFont(new Font("Comic Sans",Font.BOLD,25));
        errorCognome.setVisible(false);


        errorCodiceFiscale.setBounds(60,400,25,25);
        errorCodiceFiscale.setForeground(Color.RED);
        errorCodiceFiscale.setBackground(hex2Rgb("#FFFFFF"));
        errorCodiceFiscale.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        errorCodiceFiscale.setFont(new Font("Comic Sans",Font.BOLD,25));
        errorCodiceFiscale.setVisible(false);

        errorUserId.setBounds(60,450,25,25);
        errorUserId.setForeground(Color.RED);
        errorUserId.setBackground(hex2Rgb("#FFFFFF"));
        errorUserId.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        errorUserId.setFont(new Font("Comic Sans",Font.BOLD,25));
        errorUserId.setVisible(false);

        errorMail.setBounds(60,500,25,25);
        errorMail.setForeground(Color.RED);
        errorMail.setBackground(hex2Rgb("#FFFFFF"));
        errorMail.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        errorMail.setFont(new Font("Comic Sans",Font.BOLD,25));
        errorMail.setVisible(false);

        errorPwd.setBounds(60,550,25,25);
        errorPwd.setForeground(Color.RED);
        errorPwd.setBackground(hex2Rgb("#FFFFFF"));
        errorPwd.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        errorPwd.setFont(new Font("Comic Sans",Font.BOLD,25));
        errorPwd.setVisible(false);


        errorConfermaPwd.setBounds(60,600,25,25);
        errorConfermaPwd.setForeground(Color.RED);
        errorConfermaPwd.setBackground(hex2Rgb("#FFFFFF"));
        errorConfermaPwd.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        errorConfermaPwd.setFont(new Font("Comic Sans",Font.BOLD,25));
        errorConfermaPwd.setVisible(false);


        nomeL.setBounds(80,300,100,25);
        nomeL.setForeground(hex2Rgb("#1E90FF"));
        nomeL.setBackground(hex2Rgb("#FFFFFF"));
        nomeL.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        nomeL.setFont(new Font("Comic Sans",Font.ITALIC,15));


        nomeTF.setBounds(200,300,300,25);
        nomeTF.setForeground(hex2Rgb("#1E90FF"));
        nomeTF.setBackground(hex2Rgb("#F0F8FF"));
        nomeTF.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, hex2Rgb("#1E90FF")));
        nomeTF.setFont(new Font("Comic Sans",Font.ITALIC,20));
        nomeTF.setHorizontalAlignment(JTextField.CENTER);
        nomeTF.setCaretColor(hex2Rgb("#1E90FF"));
        nomeTF.setFocusTraversalKeysEnabled(false);

        nomeTF.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == KeyEvent.VK_TAB)
                    cognomeTF.requestFocus();
            }
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
        });


        cognomeL.setBounds(80,350,100,25);
        cognomeL.setForeground(hex2Rgb("#1E90FF"));
        cognomeL.setBackground(hex2Rgb("#FFFFFF"));
        cognomeL.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        cognomeL.setFont(new Font("Comic Sans",Font.ITALIC,15));


        cognomeTF.setBounds(200,350,300,25);
        cognomeTF.setForeground(hex2Rgb("#1E90FF"));
        cognomeTF.setBackground(hex2Rgb("#F0F8FF"));
        cognomeTF.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, hex2Rgb("#1E90FF")));
        cognomeTF.setFont(new Font("Comic Sans",Font.ITALIC,20));
        cognomeTF.setHorizontalAlignment(JTextField.CENTER);
        cognomeTF.setCaretColor(hex2Rgb("#1E90FF"));
        cognomeTF.setFocusTraversalKeysEnabled(false);

        cognomeTF.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == KeyEvent.VK_TAB)
                    codiceFiscaleTF.requestFocus();
            }
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
        });

        codiceFiscaleL.setBounds(80,400,120,25);
        codiceFiscaleL.setForeground(hex2Rgb("#1E90FF"));
        codiceFiscaleL.setBackground(hex2Rgb("#FFFFFF"));
        codiceFiscaleL.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        codiceFiscaleL.setFont(new Font("Comic Sans",Font.ITALIC,15));


        codiceFiscaleTF.setBounds(200,400,300,25);
        codiceFiscaleTF.setForeground(hex2Rgb("#1E90FF"));
        codiceFiscaleTF.setBackground(hex2Rgb("#F0F8FF"));
        codiceFiscaleTF.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, hex2Rgb("#1E90FF")));
        codiceFiscaleTF.setFont(new Font("Comic Sans",Font.ITALIC,20));
        codiceFiscaleTF.setHorizontalAlignment(JTextField.CENTER);
        codiceFiscaleTF.setCaretColor(hex2Rgb("#1E90FF"));
        codiceFiscaleTF.setFocusTraversalKeysEnabled(false);

        codiceFiscaleTF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }
            @Override
            public void focusLost(FocusEvent e) {
                codiceFiscaleTF.setText(codiceFiscaleTF.getText().toUpperCase());
            }
        });

        codiceFiscaleTF.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == KeyEvent.VK_TAB)
                    useridTF.requestFocus();
            }
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
        });


        useridL.setBounds(80,450,110,25);
        useridL.setForeground(hex2Rgb("#1E90FF"));
        useridL.setBackground(hex2Rgb("#FFFFFF"));
        useridL.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        useridL.setFont(new Font("Comic Sans",Font.ITALIC,15));


        useridTF.setBounds(200,450,300,25);
        useridTF.setForeground(hex2Rgb("#1E90FF"));
        useridTF.setBackground(hex2Rgb("#F0F8FF"));
        useridTF.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, hex2Rgb("#1E90FF")));
        useridTF.setFont(new Font("Comic Sans",Font.ITALIC,20));
        useridTF.setHorizontalAlignment(JTextField.CENTER);
        useridTF.setCaretColor(hex2Rgb("#1E90FF"));
        useridTF.setFocusTraversalKeysEnabled(false);

        useridTF.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == KeyEvent.VK_TAB)
                    mailTF.requestFocus();
            }
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
        });


        mailL.setBounds(80,500,100,25);
        mailL.setForeground(hex2Rgb("#1E90FF"));
        mailL.setBackground(hex2Rgb("#FFFFFF"));
        mailL.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        mailL.setFont(new Font("Comic Sans",Font.ITALIC,15));


        mailTF.setBounds(200,500,300,25);
        mailTF.setForeground(hex2Rgb("#1E90FF"));
        mailTF.setBackground(hex2Rgb("#F0F8FF"));
        mailTF.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, hex2Rgb("#1E90FF")));
        mailTF.setFont(new Font("Comic Sans",Font.ITALIC,20));
        mailTF.setHorizontalAlignment(JTextField.CENTER);
        mailTF.setCaretColor(hex2Rgb("#1E90FF"));
        mailTF.setFocusTraversalKeysEnabled(false);

        mailTF.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == KeyEvent.VK_TAB)
                    pwdTF.requestFocus();
            }
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
        });


        pwdL.setBounds(80,550,100,25);
        pwdL.setForeground(hex2Rgb("#1E90FF"));
        pwdL.setBackground(hex2Rgb("#FFFFFF"));
        pwdL.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        pwdL.setFont(new Font("Comic Sans",Font.ITALIC,15));


        pwdTF.setBounds(200,550,300,25);
        pwdTF.setForeground(hex2Rgb("#1E90FF"));
        pwdTF.setBackground(hex2Rgb("#F0F8FF"));
        pwdTF.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, hex2Rgb("#1E90FF")));
        pwdTF.setFont(new Font("Comic Sans",Font.ITALIC,20));
        pwdTF.setHorizontalAlignment(JTextField.CENTER);
        pwdTF.setCaretColor(hex2Rgb("#1E90FF"));
        pwdTF.setFocusTraversalKeysEnabled(false);

        pwdTF.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == KeyEvent.VK_TAB)
                    confermaPwdTF.requestFocus();
            }
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
        });


        occhio.setBounds(500,540,40,30);
        occhio.setBackground(hex2Rgb("#FFFFFF"));
        occhio.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        imgOcchio.setBounds(50,0,100,40);
        ImageIcon imgButton = new ImageIcon(Cittadini.class.getResource("/occhioAperto.jpeg"));
        Image imgButton1 = imgButton.getImage();
        Image imgButton2 = imgButton1.getScaledInstance(40, 30, Image.SCALE_SMOOTH);
        imgOcchio.setIcon(new ImageIcon(imgButton2));
        occhio.add(imgOcchio);

        occhio.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {

                if(chkVisibilityPwdA == 1)
                {

                    ImageIcon imgButton = new ImageIcon(Cittadini.class.getResource("/occhioChiuso.jpeg"));
                    Image imgButton1 = imgButton.getImage();
                    Image imgButton2 = imgButton1.getScaledInstance(40, 30, Image.SCALE_SMOOTH);
                    imgOcchio.setIcon(new ImageIcon(imgButton2));
                    occhio.add(imgOcchio);
                    pwdTF.setEchoChar((char) 0);

                    chkVisibilityPwdA = 0;
                }
                else if (chkVisibilityPwdA == 0)
                {

                    ImageIcon imgButton = new ImageIcon(Cittadini.class.getResource("/occhioAperto.jpeg"));
                    Image imgButton1 = imgButton.getImage();
                    Image imgButton2 = imgButton1.getScaledInstance(40, 30, Image.SCALE_SMOOTH);
                    imgOcchio.setIcon(new ImageIcon(imgButton2));
                    occhio.add(imgOcchio);
                    pwdTF.setEchoChar('•');

                    chkVisibilityPwdA = 1;
                }
            }
        });


        confermaPwdL.setBounds(75,600,110,25);
        confermaPwdL.setForeground(hex2Rgb("#1E90FF"));
        confermaPwdL.setBackground(hex2Rgb("#FFFFFF"));
        confermaPwdL.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        confermaPwdL.setFont(new Font("Comic Sans",Font.ITALIC,15));


        confermaPwdTF.setBounds(200,600,300,25);
        confermaPwdTF.setForeground(hex2Rgb("#1E90FF"));
        confermaPwdTF.setBackground(hex2Rgb("#F0F8FF"));
        confermaPwdTF.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, hex2Rgb("#1E90FF")));
        confermaPwdTF.setFont(new Font("Comic Sans",Font.ITALIC,20));
        confermaPwdTF.setHorizontalAlignment(JTextField.CENTER);
        confermaPwdTF.setCaretColor(hex2Rgb("#1E90FF"));
        confermaPwdTF.setFocusTraversalKeysEnabled(false);

        confermaPwdTF.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == KeyEvent.VK_TAB)
                    b.requestFocus();
            }
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
        });


        occhio1.setBounds(500,590,40,30);
        occhio1.setBackground(hex2Rgb("#FFFFFF"));
        occhio1.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        imgOcchio1.setBounds(50,0,100,40);
        ImageIcon imgButtonB = new ImageIcon(Cittadini.class.getResource("/occhioAperto.jpeg"));
        Image imgButtonB1 = imgButtonB.getImage();
        Image imgButtonB2 = imgButtonB1.getScaledInstance(40, 30, Image.SCALE_SMOOTH);
        imgOcchio1.setIcon(new ImageIcon(imgButtonB2));
        occhio1.add(imgOcchio1);

        occhio1.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e) {

                if (chkVisibilityPwdB == 1) {

                    ImageIcon imgButton = new ImageIcon(Cittadini.class.getResource("/occhioChiuso.jpeg"));
                    Image imgButton1 = imgButton.getImage();
                    Image imgButton2 = imgButton1.getScaledInstance(40, 30, Image.SCALE_SMOOTH);
                    imgOcchio1.setIcon(new ImageIcon(imgButton2));
                    occhio1.add(imgOcchio1);
                    confermaPwdTF.setEchoChar((char) 0);

                    chkVisibilityPwdB = 0;
                } else if (chkVisibilityPwdB == 0) {

                    ImageIcon imgButton = new ImageIcon(Cittadini.class.getResource("/occhioAperto.jpeg"));
                    Image imgButton1 = imgButton.getImage();
                    Image imgButton2 = imgButton1.getScaledInstance(40, 30, Image.SCALE_SMOOTH);
                    imgOcchio1.setIcon(new ImageIcon(imgButton2));
                    occhio1.add(imgOcchio1);
                    confermaPwdTF.setEchoChar('•');

                    chkVisibilityPwdB = 1;
                }

            }
        });


        b.setBounds(200,660,200,40);
        b.setBackground(hex2Rgb("#FFFFFF"));
        b.setForeground(hex2Rgb("#1E90FF"));
        b.setBorder(BorderFactory.createMatteBorder(1, 0, 2, 0, hex2Rgb("#1E90FF")));
        b.setFont(new Font("Comic Sans",Font.BOLD,15));
        b.setFocusTraversalKeysEnabled(false);


        Image imageBack = ImageIO.read(Objects.requireNonNull(Registrazione.class.getResource("/indietro.jpeg")));
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
                nomeTF.setText("");
                cognomeTF.setText("");
                codiceFiscaleTF.setText("");
                useridTF.setText("");
                mailTF.setText("");
                pwdTF.setText("");
                confermaPwdTF.setText("");
                try {
                    new Cittadini(false, null, ip);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                f.setVisible(false);
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                f.dispose();
            }
        });


        f.add(panel);
        f.add(pwdL);
        f.add(pwdTF);
        f.add(confermaPwdL);
        f.add(confermaPwdTF);
        f.add(nomeL);
        f.add(nomeTF);
        f.add(cognomeL);
        f.add(cognomeTF);
        f.add(mailL);
        f.add(mailTF);
        f.add(codiceFiscaleL);
        f.add(codiceFiscaleTF);
        f.add(b);
        f.add(occhio);
        f.add(occhio1);
        f.add(errorPwd);
        f.add(errorConfermaPwd);
        f.add(errorNome);
        f.add(errorCognome);
        f.add(errorCodiceFiscale);
        f.add(errorMail);
        f.add(indietro);
        f.add(useridL);
        f.add(useridTF);
        f.add(logo);
    }



    public static void main(String[] args)
    {
        try {
            new Registrazione("localhost");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
