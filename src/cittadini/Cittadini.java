package cittadini;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

public class Cittadini {

    /**
     * Frame inizale dell'applicazione
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

    /**
     * Bottone per registrarisi presso un centro vaccinale
     */
    JButton BTRegistrazione = new JButton("Registrati presso un centro");

    /**
     * Bottone per accedere alla schermata di creazione (registrazione) di un nuovo evento avverso
     */
    JButton BTEventoAvverso = new JButton("Registra un evento avverso");

    /**
     * Bottone per effettuare l'accesso al proprio account
     */
    JButton BTLogin = new JButton("Accedi");

    /**
     * Panel dove viene inserito il logo dell'applicazione
     */
    JPanel panelLista = new JPanel();
    JPanel panelRegistrazione = new JPanel();
    JPanel panelEventiAvversi = new JPanel();
    JLabel tmpImage = new JLabel();
    JLabel tmpImage2 = new JLabel();
    JLabel tmpImage3 = new JLabel();

    boolean checkLogin = false;

    /**
     * Il metodo hex2rgb traduce un codice esadecimale nel corrispondente valore rgb
     * @param colorStr	stringa che traduce il codice esadecimale in RGB
     * @return	ritorna il valore rgb
     */
    public static Color hex2Rgb(String colorStr) //conversione esadecimale in rgb per sfondo frame
    {
        return new Color(Integer.valueOf( colorStr.substring( 1, 3 ), 16 ), Integer.valueOf( colorStr.substring( 3, 5 ), 16 ), Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }


    public Cittadini(boolean login) throws IOException
    {
        checkLogin = login;
        //CLICK BOTTONE E PANEL 1
        panelLista.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseReleased(MouseEvent e) {
                try {
                    new Homepage(false);
                }
                catch(IOException er)
                {
                    er.printStackTrace();
                }

                //chiusura finestra login
                f.setVisible(false);
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                f.dispose();
            }
        });

        BTLista.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                try {
                    new Homepage(false);
                }
                catch(IOException er)
                {
                    er.printStackTrace();
                }

                //chiusura finestra login
                f.setVisible(false);
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                f.dispose();
            }
        });

        //CLICK BOTTONE E PANEL 2
        panelRegistrazione.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(!checkLogin) {
                    try {
                        new Registrazione();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                    //chiusura finestra login
                    f.setVisible(false);
                    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    f.dispose();
                }
            }
        });

        BTRegistrazione.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new Registrazione();
                }
                catch(IOException er)
                {
                    er.printStackTrace();
                }

                //chiusura finestra login
                f.setVisible(false);
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                f.dispose();
            }
        });

        //CLICK BOTTONE E PANEL 3
        panelEventiAvversi.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(checkLogin) {
                    //chiusura finestra login
                    f.setVisible(false);
                    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    f.dispose();
                }
            }
        });

        BTEventoAvverso.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                new Login();
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


        BTLogin.addFocusListener(new FocusListener()
        {
            @Override
            public void focusLost(FocusEvent e)
            {
                BTLogin.setBackground(hex2Rgb("#FFFFFF"));
                BTLogin.setForeground(hex2Rgb("#1E90FF"));
            }

            @Override
            public void focusGained(FocusEvent e)
            {
                BTLogin.setBackground(hex2Rgb("#1E90FF"));
                BTLogin.setForeground(hex2Rgb("#FFFFFF"));
            }
        });

        //finestra login
        f.getContentPane().setBackground(hex2Rgb("#FFFFFF"));
        //Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        //f.setLocation(dim.width/4-f.getSize().width/2, dim.height/4-f.getSize().height/2);
        f.setBounds(350,100,1100,700);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setResizable(false);  //lock size finestra


        focus.setBounds(0,0,1,1);
        focus.setBackground(Color.decode("#FFFFFF"));
        focus.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, hex2Rgb("#FFFFFF")));
        focus.requestFocus();
        focus.setEditable(false);


        //bottone lista centri
        BTLista.setBounds(100,450,300,60);
        BTLista.setBackground(hex2Rgb("#FFFFFF"));
        BTLista.setForeground(hex2Rgb("#1E90FF"));
        BTLista.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, hex2Rgb("#1E90FF")));
        BTLista.setFont(new Font("Comic Sans",Font.ITALIC,20));


        //bottone registrazione
        BTRegistrazione.setBounds(400,450,300,60);
        BTRegistrazione.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, hex2Rgb("#1E90FF")));
        BTRegistrazione.setFont(new Font("Comic Sans",Font.ITALIC,20));
        if(!checkLogin) {
            BTRegistrazione.setBackground(hex2Rgb("#FFFFFF"));
            BTRegistrazione.setForeground(hex2Rgb("#1E90FF"));
            BTRegistrazione.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, hex2Rgb("#1E90FF")));
            BTRegistrazione.setEnabled(true);
        }
        else
        {
            BTRegistrazione.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, hex2Rgb("#999999")));
            BTRegistrazione.setEnabled(false);
        }

        //bottone registra evento avverso
        BTEventoAvverso.setBounds(700,450,300,60);
        BTEventoAvverso.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, hex2Rgb("#1E90FF")));
        BTEventoAvverso.setFont(new Font("Comic Sans",Font.ITALIC,20));
        if(checkLogin) {
            BTEventoAvverso.setBackground(hex2Rgb("#FFFFFF"));
            BTEventoAvverso.setForeground(hex2Rgb("#1E90FF"));
            BTEventoAvverso.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, hex2Rgb("#1E90FF")));
            BTEventoAvverso.setEnabled(true);
        }
        else
        {
            BTEventoAvverso.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, hex2Rgb("#999999")));
            BTEventoAvverso.setEnabled(false);
        }


        ImageIcon img = new ImageIcon(Cittadini.class.getResource("/immagine1.jpeg"));
        Image img1 = img.getImage();
        Image img2 = img1.getScaledInstance(300, 300, Image.SCALE_SMOOTH);


        tmpImage.setIcon(new ImageIcon(img2));
        tmpImage.setBounds(0, 0, 300, 300);

        panelLista.setBounds(100, 150, 300, 300);
        panelLista.setBackground(hex2Rgb("#FFFFFF"));
        panelLista.add(tmpImage);


        img = new ImageIcon(Cittadini.class.getResource("/immagine2.jpeg"));
        img1 = img.getImage();
        img2 = img1.getScaledInstance(300, 300, Image.SCALE_SMOOTH);


        tmpImage2.setIcon(new ImageIcon(img2));
        tmpImage2.setBounds(0, 0, 300, 300);


        panelRegistrazione.setBounds(400, 150, 300, 300);
        panelRegistrazione.setBackground(hex2Rgb("#FFFFFF"));
        panelRegistrazione.add(tmpImage2);
        panelRegistrazione.setEnabled(!checkLogin);


        img = new ImageIcon(Cittadini.class.getResource("/immagine3.jpeg"));
        img1 = img.getImage();
        img2 = img1.getScaledInstance(300, 300, Image.SCALE_SMOOTH);


        tmpImage3.setIcon(new ImageIcon(img2));
        tmpImage3.setBounds(0, 0, 300, 300);


        panelEventiAvversi.setBounds(700, 150, 300, 300);
        panelEventiAvversi.setBackground(hex2Rgb("#FFFFFF"));
        panelEventiAvversi.add(tmpImage3);
        panelEventiAvversi.setEnabled(checkLogin);


        BTLogin.setBounds(800,0,300,60);
        BTLogin.setBackground(hex2Rgb("#FFFFFF"));
        BTLogin.setForeground(hex2Rgb("#1E90FF"));
        BTLogin.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, hex2Rgb("#1E90FF")));
        BTLogin.setFont(new Font("Comic Sans",Font.ITALIC,20));


        f.add(BTLista);
        f.add(BTRegistrazione);
        f.add(BTEventoAvverso);
        f.add(panelLista);
        f.add(panelRegistrazione);
        f.add(panelEventiAvversi);
        f.add(BTLogin);
        f.add(focus);
    }





    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                try {
                    new Cittadini(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
