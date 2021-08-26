package cittadini;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

public class Registrazione {

    /**
     * Frame della schermata di registrazione di un nuovo utente
     */
    static JFrame f = new JFrame("Registrazione Nuovo Account");

    /**
     * Label che indica un errore nell'inserimento dell'username
     */
    //static JLabel errorUser = new JLabel("*");

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
     * Label che indica un errore nell'inserimento del comune
     */
    static JLabel errorCodiceFiscale = new JLabel("*");

    /**
     * Label che indica un errore nell'inserimento della sigla della provincia
     */
    //static JLabel errorSigla = new JLabel("*");

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
     * Bottone per tornare alla schermata precedente
     */
    JButton back = new JButton();

    /**
     * Label che indica il campo dove l'utente deve inserire l'username
     */
    //JLabel userL = new JLabel("Username:", SwingConstants.CENTER);

    /**
     * TextField dove l'utente deve inserire l'username
     */
    //JTextField userTF = new JTextField("");

    /**
     * Label che indica il campo dove l'utente deve inserire la password
     */
    JLabel pwdL = new JLabel("Password:", SwingConstants.CENTER);
    /**
     * PasswordField dove l'utente deve inserire l'username
     */
    JPasswordField pwdTF = new JPasswordField("");

    /**
     * Label che indica il campo dove l'utente deve inserire la conferma della password
     */
    JLabel confermaPwdL = new JLabel("Conferma Pwd:", SwingConstants.CENTER);
    /**
     * PasswordField dove l'utente deve inserire la conferma della password
     */
    JPasswordField confermaPwdTF = new JPasswordField("");

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
     * Label che indica il campo dove l'utente deve inserire il comune di residenza
     */
    JLabel codiceFiscaleL = new JLabel("Codice fiscale: ", SwingConstants.CENTER);
    /**
     * TextField dove l'utente deve inserire il comune di residenza
     */
    JTextField codiceFiscaleTF = new JTextField("");

    /**
     * Label che indica il campo dove l'utente deve inserire la mail
     */
    JLabel mailL = new JLabel("E-Mail: ", SwingConstants.CENTER);
    /**
     * TextField dove l'utente deve inserire la mail
     */
    JTextField mailTF = new JTextField("");

    /**
     * Bottone per inviare i campi ed iscriversi
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
     * Array di booleani, ogni casella dell'array è associata ad un tipo di errore di inserimento che l'utente potrebbe commettere, quando l'utente commette uno degli errori previsti la casella corrispondente assume valore true, se l'utente corregge oppure non commette tale errore allora la casella avrà valore false
     */
    boolean[] errorCounter = {false, false, false, false, false, false};

    /**
     * Il metodo hex2rgb traduce un codice esadecimale nel corrispondente valore rgb
     * @param colorStr	stringa che traduce il codice esadecimale in RGB
     * @return	ritorna il valore rgb
     */
    public static Color hex2Rgb(String colorStr) //conversione esadecimale in rgb per sfondo frame
    {
        return new Color(Integer.valueOf( colorStr.substring( 1, 3 ), 16 ), Integer.valueOf( colorStr.substring( 3, 5 ), 16 ), Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }

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

    public static boolean CheckCodFisc(String codFisc)
    {
        if(codFisc.length() == 16)
        {
            for(char c : codFisc.toCharArray())
                if(!((c >= 65 && c <= 90) || (c >= 97 && c <= 122) || (c >= 48 && c <= 57)))
                    return false;
            return true;
        }

        return false;
    }

    //Da sistemare
    public static boolean CheckEmail(String email)
    {
        if(email.length() >= 3)
        {
            for(char c : email.toCharArray())
                if(!((c >= 65 && c <= 90) || (c >= 97 && c <= 122)))
                    return false;
            return true;
        }

        return false;
    }

    public static boolean CheckPwd(String pwd)
    {
        if(pwd.length() >= 8)
        {
            return true;
        }

        return false;
    }

    public static boolean CheckConfPwd(String confPwd, String pwd)
    {
        if(confPwd.equals(pwd))
        {
            return true;
        }

        return false;
    }

    public Registrazione() throws IOException
    {
        b.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e)
            {
                int sentinel = 0;

                //Registrazione cittadino
                /*if(!CheckNome(nomeTF.getText()))
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

                if(!CheckEmail(mailTF.getText()))
                {
                    errorMail.setVisible(true);
                    errorCounter[3] = true;
                    sentinel++;
                }
                else
                {
                    errorMail.setVisible(false);
                    errorCounter[3] = false;
                }

                if(!CheckPwd(pwdTF.getText()))
                {
                    errorPwd.setVisible(true);
                    errorCounter[4] = true;
                    sentinel++;
                }
                else
                {
                    errorPwd.setVisible(false);
                    errorCounter[4] = false;
                }

                if(!CheckConfPwd(confermaPwdTF.getText(), pwdTF.getText()))
                {
                    errorConfermaPwd.setVisible(true);
                    errorCounter[5] = true;
                    sentinel++;
                }
                else
                {
                    errorConfermaPwd.setVisible(false);
                    errorCounter[5] = false;
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }
                */
                if(sentinel == 0)
                {
                    //Cittadino c = new Cittadino(userid, pwdTF.getPassword(), nomeTF.getText(), cognomeTF.getText(), "CF", null, mailTF.getText(), null);


                    try {
                        new Homepage(true);
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                    f.setVisible(false);
                    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    f.dispose();
                }


            }
        });


        f.getContentPane().setBackground(hex2Rgb("#FFFFFF"));
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setResizable(false);  //lock size finestra
        f.setBounds(660, 50, 600, 770);


        errorNome.setBounds(60,350,25,25);
        errorNome.setForeground(Color.RED);
        errorNome.setBackground(hex2Rgb("#FFFFFF"));
        errorNome.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        errorNome.setFont(new Font("Comic Sans",Font.BOLD,25));
        errorNome.setVisible(false);


        errorCognome.setBounds(60,400,25,25);
        errorCognome.setForeground(Color.RED);
        errorCognome.setBackground(hex2Rgb("#FFFFFF"));
        errorCognome.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        errorCognome.setFont(new Font("Comic Sans",Font.BOLD,25));
        errorCognome.setVisible(false);


        errorCodiceFiscale.setBounds(60,450,25,25);
        errorCodiceFiscale.setForeground(Color.RED);
        errorCodiceFiscale.setBackground(hex2Rgb("#FFFFFF"));
        errorCodiceFiscale.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        errorCodiceFiscale.setFont(new Font("Comic Sans",Font.BOLD,25));
        errorCodiceFiscale.setVisible(false);


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

/*
        Image freccia = ImageIO.read(Cittadini.class.getResource("/frecciaBottone.jpeg"));
        freccia = freccia.getScaledInstance(105, 105, Image.SCALE_SMOOTH);
    	back.setIcon(new ImageIcon(freccia));
        back.setBounds(2,2,45,45);
        back.setForeground(hex2Rgb("#1E90FF"));
        back.setBackground(hex2Rgb("#FFFFFF"));
        back.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, hex2Rgb("#FFFFFF")));


        ImageIcon img = new ImageIcon(Cittadini.class.getResource("/logoRegistrazione.jpeg"));
		Image img1 = img.getImage();
		Image img2 = img1.getScaledInstance(480, 240, Image.SCALE_SMOOTH);
		imgReg.setIcon(new ImageIcon(img2));
		imgReg.setBounds(0, 0, 480, 240);
		panel.setBounds(56, 30, 480, 240);
		panel.setBackground(hex2Rgb("#FFFFFF"));
		panel.add(imgReg);
*/

        nomeL.setBounds(80,350,100,25);
        nomeL.setForeground(hex2Rgb("#1E90FF"));
        nomeL.setBackground(hex2Rgb("#FFFFFF"));
        nomeL.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        nomeL.setFont(new Font("Comic Sans",Font.ITALIC,15));


        nomeTF.setBounds(200,350,300,25);
        nomeTF.setForeground(hex2Rgb("#1E90FF"));
        nomeTF.setBackground(hex2Rgb("#FFFFFF"));
        nomeTF.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, hex2Rgb("#1E90FF")));
        nomeTF.setFont(new Font("Comic Sans",Font.ITALIC,20));
        nomeTF.setHorizontalAlignment(JTextField.CENTER);
        nomeTF.setCaretColor(hex2Rgb("#1E90FF"));
        nomeTF.setFocusTraversalKeysEnabled(false);


        cognomeL.setBounds(80,400,100,25);
        cognomeL.setForeground(hex2Rgb("#1E90FF"));
        cognomeL.setBackground(hex2Rgb("#FFFFFF"));
        cognomeL.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        cognomeL.setFont(new Font("Comic Sans",Font.ITALIC,15));


        cognomeTF.setBounds(200,400,300,25);
        cognomeTF.setForeground(hex2Rgb("#1E90FF"));
        cognomeTF.setBackground(hex2Rgb("#FFFFFF"));
        cognomeTF.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, hex2Rgb("#1E90FF")));
        cognomeTF.setFont(new Font("Comic Sans",Font.ITALIC,20));
        cognomeTF.setHorizontalAlignment(JTextField.CENTER);
        cognomeTF.setCaretColor(hex2Rgb("#1E90FF"));
        cognomeTF.setFocusTraversalKeysEnabled(false);


        codiceFiscaleL.setBounds(80,450,110,25);
        codiceFiscaleL.setForeground(hex2Rgb("#1E90FF"));
        codiceFiscaleL.setBackground(hex2Rgb("#FFFFFF"));
        codiceFiscaleL.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        codiceFiscaleL.setFont(new Font("Comic Sans",Font.ITALIC,15));


        codiceFiscaleTF.setBounds(200,450,300,25);
        codiceFiscaleTF.setForeground(hex2Rgb("#1E90FF"));
        codiceFiscaleTF.setBackground(hex2Rgb("#FFFFFF"));
        codiceFiscaleTF.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, hex2Rgb("#1E90FF")));
        codiceFiscaleTF.setFont(new Font("Comic Sans",Font.ITALIC,20));
        codiceFiscaleTF.setHorizontalAlignment(JTextField.CENTER);
        codiceFiscaleTF.setCaretColor(hex2Rgb("#1E90FF"));
        codiceFiscaleTF.setFocusTraversalKeysEnabled(false);


        mailL.setBounds(80,500,100,25);
        mailL.setForeground(hex2Rgb("#1E90FF"));
        mailL.setBackground(hex2Rgb("#FFFFFF"));
        mailL.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        mailL.setFont(new Font("Comic Sans",Font.ITALIC,15));


        mailTF.setBounds(200,500,300,25);
        mailTF.setForeground(hex2Rgb("#1E90FF"));
        mailTF.setBackground(hex2Rgb("#FFFFFF"));
        mailTF.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, hex2Rgb("#1E90FF")));
        mailTF.setFont(new Font("Comic Sans",Font.ITALIC,20));
        mailTF.setHorizontalAlignment(JTextField.CENTER);
        mailTF.setCaretColor(hex2Rgb("#1E90FF"));
        mailTF.setFocusTraversalKeysEnabled(false);


        pwdL.setBounds(80,550,100,25);
        pwdL.setForeground(hex2Rgb("#1E90FF"));
        pwdL.setBackground(hex2Rgb("#FFFFFF"));
        pwdL.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        pwdL.setFont(new Font("Comic Sans",Font.ITALIC,15));


        pwdTF.setBounds(200,550,300,25);
        pwdTF.setForeground(hex2Rgb("#1E90FF"));
        pwdTF.setBackground(hex2Rgb("#FFFFFF"));
        pwdTF.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, hex2Rgb("#1E90FF")));
        pwdTF.setFont(new Font("Comic Sans",Font.ITALIC,20));
        pwdTF.setHorizontalAlignment(JTextField.CENTER);
        pwdTF.setCaretColor(hex2Rgb("#1E90FF"));
        pwdTF.setFocusTraversalKeysEnabled(false);

        /*
        occhio.setBounds(500,540,60,40);
        occhio.setBackground(hex2Rgb("#FFFFFF"));
        occhio.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        imgOcchio.setBounds(50,0,100,40);
        ImageIcon imgButton = new ImageIcon(Clienti.class.getResource("/occhioPwd.jpeg"));
        Image imgButton1 = imgButton.getImage();
        Image imgButton2 = imgButton1.getScaledInstance(60, 40, Image.SCALE_SMOOTH);
        imgOcchio.setIcon(new ImageIcon(imgButton2));
        occhio.add(imgOcchio);
        */

        confermaPwdL.setBounds(75,600,110,25);
        confermaPwdL.setForeground(hex2Rgb("#1E90FF"));
        confermaPwdL.setBackground(hex2Rgb("#FFFFFF"));
        confermaPwdL.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        confermaPwdL.setFont(new Font("Comic Sans",Font.ITALIC,15));


        confermaPwdTF.setBounds(200,600,300,25);
        confermaPwdTF.setForeground(hex2Rgb("#1E90FF"));
        confermaPwdTF.setBackground(hex2Rgb("#FFFFFF"));
        confermaPwdTF.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, hex2Rgb("#1E90FF")));
        confermaPwdTF.setFont(new Font("Comic Sans",Font.ITALIC,20));
        confermaPwdTF.setHorizontalAlignment(JTextField.CENTER);
        confermaPwdTF.setCaretColor(hex2Rgb("#1E90FF"));
        confermaPwdTF.setFocusTraversalKeysEnabled(false);

        /*
        occhio1.setBounds(500,590,60,40);
        occhio1.setBackground(hex2Rgb("#FFFFFF"));
        occhio1.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        imgOcchio1.setBounds(50,0,100,40);
        ImageIcon imgButtonB = new ImageIcon(Clienti.class.getResource("/occhioPwd.jpeg"));
        Image imgButtonB1 = imgButtonB.getImage();
        Image imgButtonB2 = imgButtonB1.getScaledInstance(60, 40, Image.SCALE_SMOOTH);
        imgOcchio1.setIcon(new ImageIcon(imgButtonB2));
        occhio1.add(imgOcchio1);
        */



        b.setBounds(200,660,200,40);
        b.setBackground(hex2Rgb("#FFFFFF"));
        b.setForeground(hex2Rgb("#1E90FF"));
        b.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, hex2Rgb("#1E90FF")));
        b.setFont(new Font("Comic Sans",Font.BOLD,15));
        b.setFocusTraversalKeysEnabled(false);


        f.add(panel);
        f.add(back);
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
    }

    public static void main(String[] args)
    {
        try {
            new Registrazione();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
