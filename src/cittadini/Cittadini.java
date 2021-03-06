/*
Giulio Baricci 740285 Varese
Guido Bernasconi 740539 Varese
Davide Feldkircher 740956 Varese
 */

package cittadini;

import common.Cittadino;
import common.ClientCV;
import common.Prenotazione;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;
import javax.swing.*;

/**
 * La classe Cittadini contiene il codice per la creazione della schermata iniziale
 * @author Giulio Baricci
 */

public class Cittadini {

    /**
     * Indirizzo ip della macchina Server.MF
     */
    public static String ip = "";
    /**
     * Oggetto che fa riferimento al server disponibile sul rmiregistry
     */
    private static ClientCV stub;

    /**
     * Frame iniziale dell'applicazione
     */
    JFrame f = new JFrame("Cittadini");

    /**
     * TextField usato per il focus
     */
    JTextField focus = new JTextField();

    /**
     * Bottone per l'accesso alla lista dei centri vaccinali presenti nel database
     */
    JButton BTLista = new JButton("Lista centri vaccinali");


    String twoLines = "Registrati presso\nun centro";
    /**
     * Bottone per registrarisi presso un centro vaccinale
     */
    JButton BTRegistrazione = new JButton("<html>" + twoLines.replaceAll("\\n", "<br>") + "</html>");


    String twoLines2 = "Registra un\n evento avverso";
    /**
     * Bottone per accedere alla schermata di creazione (registrazione) di un nuovo evento avverso
     */
    JButton BTEventoAvverso = new JButton("<html>" + twoLines2.replaceAll("\\n", "<br>") + "</html>");

    /**
     * Bottone per effettuare l'accesso al proprio account
     */
    JButton BTLogin = new JButton("Accedi");

    /**
     * Bottone per prenotare un vaccino
     */
    JButton BTPrenota = new JButton("Prenota vaccino");

    String twoLines3 = "Visualizza\nprenotazioni";
    /**
     * Bottone per visualizzare le prenotazioni effettuate
     */
    JButton BTListaPrenotazioni = new JButton("<html>" + twoLines3.replaceAll("\\n", "<br>") + "</html>");

    /**
     * Label contenente il logo
     */
    JLabel logo = new JLabel();

    private static Cittadino c = null;

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
     * Costruttore della schermata iniziale
     * @param checkLogin controlla se ?? avvenuto un accesso
     * @param account fa riferimento al cittadino che ha effettuato l'accesso
     * @param ind Indirizzo ip della macchina server
     * @throws IOException il costruttore contiene del codice che legge delle immagini quindi pu?? genererare IOException
     * @throws NotBoundException il costruttore contiene del codice che si conntte al rmiregistry quindi pu?? genererare NotBoundException
     */
    public Cittadini(boolean checkLogin, Cittadino account, String ind) throws IOException, NotBoundException, SQLException {
        ip = ind;
        Registry registro = LocateRegistry.getRegistry(ip, 1099);
        stub = (common.ClientCV) registro.lookup("SERVERCV");

        if(account != null)
            c = stub.getCittadino(account.getCf());

        BTLista.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                try {
                    new Homepage(checkLogin, c, false, ip);
                }
                catch(IOException | NotBoundException | SQLException er)
                {
                    er.printStackTrace();
                }

                //chiusura finestra login
                f.setVisible(false);
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                f.dispose();
            }
        });

        BTRegistrazione.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new Registrazione(ip);
                }
                catch(Exception er)
                {
                    er.printStackTrace();
                }

                //chiusura finestra login
                f.setVisible(false);
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                f.dispose();
            }
        });

        BTEventoAvverso.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new RegistraEvento(checkLogin, c, ip);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                //chiusura finestra login
                f.setVisible(false);
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                f.dispose();
            }
        });

        BTPrenota.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new PrenotazioneVaccino(checkLogin, c, ip);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                //chiusura finestra login
                f.setVisible(false);
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                f.dispose();
            }
        });

        BTListaPrenotazioni.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new ListaPrenotazioni(checkLogin, c, ip);
                } catch (IOException | NotBoundException | SQLException ex) {
                    ex.printStackTrace();
                }
                //chiusura finestra login
                f.setVisible(false);
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                f.dispose();
            }
        });

        BTLogin.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(BTLogin.getText().equals("Accedi")) {
                    try {
                        new Login(ip);
                    } catch (IOException | NotBoundException ex) {
                        ex.printStackTrace();
                    }
                }
                else
                {
                    try {
                        new Cittadini(false,null, ip);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                //chiusura finestra login
                f.setVisible(false);
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                f.dispose();
            }
        });


        //FOCUS
        BTLista.addFocusListener(new FocusListener()
        {
            @Override
            public void focusLost(FocusEvent e)
            {
                BTLista.setBackground(hex2Rgb("#FFFFFF"));
                BTLista.setForeground(hex2Rgb("#1E90FF"));
            }

            @Override
            public void focusGained(FocusEvent e)
            {
                BTLista.setBackground(hex2Rgb("#1E90FF"));
                BTLista.setForeground(hex2Rgb("#FFFFFF"));
            }
        });


        BTRegistrazione.addFocusListener(new FocusListener()
        {
            @Override
            public void focusLost(FocusEvent e)
            {
                BTRegistrazione.setBackground(hex2Rgb("#FFFFFF"));
                BTRegistrazione.setForeground(hex2Rgb("#1E90FF"));
            }

            @Override
            public void focusGained(FocusEvent e)
            {
                BTRegistrazione.setBackground(hex2Rgb("#1E90FF"));
                BTRegistrazione.setForeground(hex2Rgb("#FFFFFF"));
            }
        });


        BTEventoAvverso.addFocusListener(new FocusListener()
        {
            @Override
            public void focusLost(FocusEvent e)
            {
                BTEventoAvverso.setBackground(hex2Rgb("#FFFFFF"));
                BTEventoAvverso.setForeground(hex2Rgb("#1E90FF"));
            }

            @Override
            public void focusGained(FocusEvent e)
            {
                BTEventoAvverso.setBackground(hex2Rgb("#1E90FF"));
                BTEventoAvverso.setForeground(hex2Rgb("#FFFFFF"));
            }
        });

        BTListaPrenotazioni.addFocusListener(new FocusListener()
        {
            @Override
            public void focusLost(FocusEvent e)
            {
                BTListaPrenotazioni.setBackground(hex2Rgb("#FFFFFF"));
                BTListaPrenotazioni.setForeground(hex2Rgb("#1E90FF"));
            }

            @Override
            public void focusGained(FocusEvent e)
            {
                BTListaPrenotazioni.setBackground(hex2Rgb("#1E90FF"));
                BTListaPrenotazioni.setForeground(hex2Rgb("#FFFFFF"));
            }
        });

        BTPrenota.addFocusListener(new FocusListener()
        {
            @Override
            public void focusLost(FocusEvent e)
            {
                BTPrenota.setBackground(hex2Rgb("#FFFFFF"));
                BTPrenota.setForeground(hex2Rgb("#1E90FF"));
            }

            @Override
            public void focusGained(FocusEvent e)
            {
                BTPrenota.setBackground(hex2Rgb("#1E90FF"));
                BTPrenota.setForeground(hex2Rgb("#FFFFFF"));
            }
        });

        BTLogin.addFocusListener(new FocusListener()
        {
            @Override
            public void focusLost(FocusEvent e)
            {
                BTLogin.setForeground(hex2Rgb("#1E90FF"));
            }

            @Override
            public void focusGained(FocusEvent e)
            {
                BTLogin.setForeground(hex2Rgb("#FFFFFF"));
            }
        });

        //finestra login
        f.getContentPane().setBackground(hex2Rgb("#FFFFFF"));
        //Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        //f.setLocation(dim.width/4-f.getSize().width/2, dim.height/4-f.getSize().height/2);
        f.setBounds(350,150,800,500);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setResizable(false);  //lock size finestra
        ImageIcon img = new ImageIcon(Objects.requireNonNull(Cittadini.class.getResource("/logo.jpg")));
        Image img1 = img.getImage();
        Image img2 = img1.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        f.setIconImage(img2);

        logo.setIcon(new ImageIcon(img2));
        logo.setBounds(320, 50, 150, 150);

        focus.setBounds(0,0,1,1);
        focus.setBackground(Color.decode("#FFFFFF"));
        focus.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, hex2Rgb("#FFFFFF")));
        focus.requestFocus();
        focus.setEditable(false);


        //bottone lista centri
        BTLista.setBounds(50,350,200,60);
        BTLista.setBackground(Color.decode("#F0F8FF"));
        BTLista.setForeground(Color.decode("#000000"));
        BTLista.setBorder(BorderFactory.createMatteBorder(5, 0, 5, 0, hex2Rgb("#1E90FF")));
        BTLista.setFont(new Font("Arial", Font.ITALIC, 15));

        //bottone registrazione
        BTRegistrazione.setBounds(300,350,200,60);
        BTRegistrazione.setFont(new Font("Arial", Font.ITALIC, 15));
        BTRegistrazione.setHorizontalAlignment(SwingConstants.CENTER);
        BTRegistrazione.setBackground(Color.decode("#F0F8FF"));
        BTRegistrazione.setForeground(Color.decode("#000000"));
        BTRegistrazione.setBorder(BorderFactory.createMatteBorder(5, 0, 5, 0, hex2Rgb("#1E90FF")));
        if(!checkLogin) {
            BTRegistrazione.setEnabled(true);
            BTRegistrazione.setVisible(true);
        }
        else
        {
            BTRegistrazione.setVisible(false);
            BTRegistrazione.setEnabled(false);
        }

        //bottone prenotazione
        BTPrenota.setBounds(300,350,200,60);
        BTPrenota.setFont(new Font("Arial", Font.ITALIC, 15));
        BTPrenota.setHorizontalAlignment(SwingConstants.CENTER);
        BTPrenota.setBackground(Color.decode("#F0F8FF"));
        BTPrenota.setForeground(Color.decode("#000000"));
        BTPrenota.setBorder(BorderFactory.createMatteBorder(5, 0, 5, 0, hex2Rgb("#1E90FF")));

        if(checkLogin) {
            BTPrenota.setEnabled(true);
            BTPrenota.setVisible(true);
        }
        else
        {
            BTPrenota.setVisible(false);
            BTPrenota.setEnabled(false);
        }

        BTListaPrenotazioni.setBounds(300,260,200,60);
        BTListaPrenotazioni.setFont(new Font("Arial", Font.ITALIC, 15));
        BTListaPrenotazioni.setHorizontalAlignment(SwingConstants.CENTER);
        BTListaPrenotazioni.setBackground(Color.decode("#F0F8FF"));
        BTListaPrenotazioni.setForeground(Color.decode("#000000"));
        BTListaPrenotazioni.setBorder(BorderFactory.createMatteBorder(5, 0, 5, 0, hex2Rgb("#1E90FF")));
        if(checkLogin) {
            BTListaPrenotazioni.setEnabled(true);
            BTListaPrenotazioni.setVisible(true);
        }
        else
        {
            BTListaPrenotazioni.setVisible(false);
            BTListaPrenotazioni.setEnabled(false);
        }

        //bottone registra evento avverso
        BTEventoAvverso.setBounds(550,350,200,60);
        BTEventoAvverso.setFont(new Font("Arial", Font.ITALIC, 15));
        BTEventoAvverso.setHorizontalAlignment(SwingConstants.CENTER);
        BTEventoAvverso.setBackground(Color.decode("#F0F8FF"));
        BTEventoAvverso.setForeground(Color.decode("#000000"));
        BTEventoAvverso.setBorder(BorderFactory.createMatteBorder(5, 0, 5, 0, hex2Rgb("#1E90FF")));
        java.util.List<Prenotazione> lista = null;
        if(checkLogin)
             lista = stub.getPrenotazioni(account);



        if(checkLogin && c.getIdvaccinazione() != 0) {
            BTEventoAvverso.setBackground(Color.decode("#F0F8FF"));
            BTEventoAvverso.setForeground(Color.decode("#000000"));
            BTEventoAvverso.setEnabled(true);
        }
        else
        {
            BTEventoAvverso.setEnabled(false);
        }

        BTLogin.setBounds(630,0,150,60);
        BTLogin.setBackground(hex2Rgb("#FFFFFF"));
        BTLogin.setForeground(hex2Rgb("#1E90FF"));
        BTLogin.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        BTLogin.setFont(new Font("Comic Sans",Font.ITALIC,20));
        Font btnFont = BTLogin.getFont();
        Map attributes = btnFont.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        btnFont = btnFont.deriveFont(attributes);
        BTLogin.setFont(btnFont);
        if(checkLogin)
            BTLogin.setText("Disconnettiti");


        f.add(BTLista);
        f.add(BTRegistrazione);
        f.add(BTEventoAvverso);
        f.add(BTPrenota);
        f.add(BTLogin);
        f.add(focus);
        f.add(BTListaPrenotazioni);
        f.add(logo);
    }


    public static void main(String[] args) {
        if (args.length == 1) {
            ip = args[0];
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        new Cittadini(false, null, ip);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else
            System.out.print("ERRORE!! Numero di argomenti non valido");
    }

}
