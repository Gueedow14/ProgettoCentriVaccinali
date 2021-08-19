package centrivaccinali;

import cittadini.Vaccinazione;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class RegistraCittadini {

    JFrame f = new JFrame("Registrazione Cittadino");

    JButton indietro = new JButton("Indietro");

    /**
     * Label che indica un errore nell'inserimento del Centro Vaccinale
     */
    static JLabel errorCentro = new JLabel("*");
    /**
     * Label che indica un errore nell'inserimento del CF
     */
    static JLabel errorCF = new JLabel("*");
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

    String[] tipi = {"Pfizer", "Moderna", "AstraZeneca", "J&J"};

    JComboBox tipoTF = new JComboBox(tipi);

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

    JButton b = new JButton("ISCRIVITI");

    /**
     * Varibile integer per il controllo dell'username nel database degli account
     */
    static int chkOcc = 1;	//presuppone ci sia giï¿½ l'username nel db

    /**
     * Array di booleani, ogni casella dell'array Ã¨ associata ad un tipo di errore di inserimento che l'utente potrebbe commettere, quando l'utente commette uno degli errori previsti la casella corrispondente assume valore true, se l'utente corregge oppure non commette tale errore allora la casella avrÃ  valore false
     */
    boolean[] errorCounter = {false, false, false, false, false, false, false, false, false, false, false};

    public static Color hex2Rgb(String colorStr)
    {
        return new Color(Integer.valueOf( colorStr.substring( 1, 3 ), 16 ), Integer.valueOf( colorStr.substring( 3, 5 ), 16 ), Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }

    public RegistraCittadini() {

        int sizeL = 17;
        int sizeTF = 17;

        errorCentro.setBounds(70,100,25,25);
        errorCentro.setForeground(Color.RED);
        errorCentro.setBackground(hex2Rgb("#F0F8FF"));
        errorCentro.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        errorCentro.setFont(new Font("Comic Sans",Font.BOLD,25));
        errorCentro.setVisible(false);


        errorData.setBounds(70,150,25,25);
        errorData.setForeground(Color.RED);
        errorData.setBackground(hex2Rgb("#F0F8FF"));
        errorData.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        errorData.setFont(new Font("Comic Sans",Font.BOLD,25));
        errorData.setVisible(false);


        errorTipo.setBounds(60,200,25,25);
        errorTipo.setForeground(Color.RED);
        errorTipo.setBackground(hex2Rgb("#F0F8FF"));
        errorTipo.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        errorTipo.setFont(new Font("Comic Sans",Font.BOLD,25));
        errorTipo.setVisible(false);


        errorNome.setBounds(90,250,25,25);
        errorNome.setForeground(Color.RED);
        errorNome.setBackground(hex2Rgb("#F0F8FF"));
        errorNome.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        errorNome.setFont(new Font("Comic Sans",Font.BOLD,25));
        errorNome.setVisible(false);


        errorCognome.setBounds(80,300,25,25);
        errorCognome.setForeground(Color.RED);
        errorCognome.setBackground(hex2Rgb("#F0F8FF"));
        errorCognome.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        errorCognome.setFont(new Font("Comic Sans",Font.BOLD,25));
        errorCognome.setVisible(false);

        errorCF.setBounds(90,350,25,25);
        errorCF.setForeground(Color.RED);
        errorCF.setBackground(hex2Rgb("#F0F8FF"));
        errorCF.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        errorCF.setFont(new Font("Comic Sans",Font.BOLD,25));
        errorCF.setVisible(false);

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
        nomeTF.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, hex2Rgb("#1E90FF")));
        nomeTF.setFont(new Font("Comic Sans",Font.ITALIC,sizeTF));
        nomeTF.setHorizontalAlignment(JTextField.CENTER);
        nomeTF.setCaretColor(hex2Rgb("#1E90FF"));
        nomeTF.setFocusTraversalKeysEnabled(false);


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


        cfL.setBounds(70,250,110,25);
        cfL.setForeground(hex2Rgb("#1E90FF"));
        cfL.setBackground(hex2Rgb("#F0F8FF"));
        cfL.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        cfL.setFont(new Font("Comic Sans",Font.ITALIC,sizeL));

        cfTF.setBounds(200,250,300,25);
        cfTF.setForeground(hex2Rgb("#1E90FF"));
        cfTF.setBackground(hex2Rgb("#F0F8FF"));
        cfTF.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, hex2Rgb("#1E90FF")));
        cfTF.setFont(new Font("Comic Sans",Font.ITALIC,sizeTF));
        cfTF.setHorizontalAlignment(JTextField.CENTER);
        cfTF.setCaretColor(hex2Rgb("#1E90FF"));
        cfTF.setFocusTraversalKeysEnabled(false);

        indietro.setBounds(30,30,80,25);
        indietro.setForeground(hex2Rgb("#1E90FF"));
        indietro.setBackground(hex2Rgb("#F0F8FF"));
        indietro.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, hex2Rgb("#1E90FF")));
        indietro.setFont(new Font("Comic Sans",Font.ITALIC,sizeL));
        indietro.setFocusable(false);

        indietro.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                new CentriVaccinali();
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

                    Vaccinazione nuovo;

                    Vaccinazione.registraVaccinato( nuovo = new Vaccinazione(centroTF.getText(), nomeTF.getText(), cognomeTF.getText(), cfTF.getText(), dataTF.getText(), tipoTF.getSelectedItem().toString()));
                    System.out.println(nuovo.getCf() +" "+ nuovo.getIdVaccinazione() +" "+ nuovo.getNomeCV() +" "+ nuovo.getTipo() +" "+ nuovo.getData());
                    f.setVisible(false);
                    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    f.dispose();
                }
            }
        });

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
        f.add(b);
        f.add(indietro);

        f.getContentPane().setBackground(hex2Rgb("#FFFFFF"));
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setResizable(false);  //lock size finestra
        f.setBounds(660, 100, 600, 850);

    }

    public static boolean checkGen(String nome)
    {
        for(char c : nome.toCharArray())
            if(!(Character.isLetter(c)) && !(c == ' ') && !(c == '\''))
                return false;

        return true;
    }

    public static boolean checkCF(String CF) {
        if(CF.length() != 15) {
            String nome = CF.substring(0, 3);
            String cognome = CF.substring(3, 6);
            String anno = CF.substring(6, 8);
            String mese = CF.substring(8, 9);
            String giorno = CF.substring(9, 11);

            if (nome.matches("[a-zA-Z]+") && cognome.matches("[a-zA-Z]+") && mese.matches("[a-zA-Z]+") && anno.matches("[0-9]+") && giorno.matches("[0-9]+"))
                return true;
            else
                return false;
        }
        return false;
    }

        private boolean controlloCampi () {

            if (checkGen(nomeTF.getText()))
                if (checkGen(cognomeTF.getText()))
                    if (checkCF(cfTF.getText())) {
                        return true;
                    }

            return false;
        }

        public static void main (String[]args)
        {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    new RegistraCittadini();
                }
            });
        }
    }