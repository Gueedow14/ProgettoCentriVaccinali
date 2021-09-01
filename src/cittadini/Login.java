package cittadini;

import centrivaccinali.CentriVaccinali;
import centrivaccinali.RegistraCentri;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.*;


/**
 * La classe Login contiene il codice per la creazione della schermata di login
 * @author Giulio Baricci
 */

public class Login {

    /**
     * Frame della schermata di login
     */
    JFrame f = new JFrame("Login");

    /**
     * TextField usato per il focus
     */
    JTextField focus = new JTextField();

    /**
     * Campo Username dove l'utente inserisce il suo user per effettuare l'accesso
     */
    JTextField tx = new JTextField(" USERNAME ");

    /**
     * Campo Password dove l'utente inserisce la sua pwd per effettuare l'accesso
     */
    JPasswordField tx1 = new JPasswordField("Password");

    /**
     * Label dove verrà inserita l'ImageIcon del simbolo dell'occhio
     */
    JLabel imgOcchio = new JLabel();

    /**
     * Bottone con all'interno la label con l'occhio, una volta premuto cambia l'echo char del campo Password
     */
    JButton occhio = new JButton();

    /**
     * Bottone per l'accesso di un utente nella schermata del suo account (se i campi inseriti sono presenti nel file Account.dati)
     */
    JButton b = new JButton("Accedi");

    /**
     * Label contenente il logo
     */
    JLabel logo = new JLabel();

    /**
     * Variabile che controlla la TextField relativa allo username
     */
    JLabel check = new JLabel("0");
    /**
     * Variabile che controlla la TextField relativa alla password
     */
    JLabel check1 = new JLabel("0");

    /**
     * Variabile booleana per il controllo del bottone 'occhio'
     */
    int chkVisibilityPwd = 0;

    /**
     * Bottone per ritornare alla schermata precedente
     */
    JButton indietro = new JButton();

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
     * Il metodo CheckPWD controlla se il campo Password è vuoto, perciò se l'utente non ha inserito valori ritorna
     * @param pwd è il JPasswordField (campo Password) da controllare
     * @return	ritorna true se il campo Password è vuoto, ritorna false se l'utente ha inserito almeno un carattere nel JPasswordField
     */
    public static boolean CheckPWD(JPasswordField pwd)	//check se il campo pwd è vuoto
    {
        return new String(pwd.getPassword()).equals("");
    }

    /**
     * Il costruttore della classe Login contiene il codice per la creazione della schermata di accesso
     * @throws IOException il costruttore contiene del codice che legge delle immagini quindi può genererare IOException
     */
    public Login() throws IOException {
        f.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                if(tx.isFocusOwner() && tx.getText().equals(""))
                {
                    tx.setText(" USERNAME ");
                    check.setText("0");
                    tx.setCaretColor(hex2Rgb("#FFFFFF"));
                }
                else if(tx1.isFocusOwner() && CheckPWD(tx1))
                {
                    tx1.setEchoChar((char) 0);
                    tx1.setText("PASSWORD");
                    check1.setText("0");
                    tx1.setCaretColor(hex2Rgb("#FFFFFF"));
                }
                else if(b.isFocusOwner())
                {
                    b.setBackground(hex2Rgb("#FFFFFF"));
                    b.setForeground(hex2Rgb("#1E90FF"));
                }
            }
        });


        tx.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                if(tx.getText().equals(" USERNAME ") && check.getText().equals("0"))
                {
                    tx.setText("");
                    check.setText("1");
                    tx.setCaretColor(hex2Rgb("#1E90FF"));
                }
            }
        });


        tx1.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                String str = new String(tx1.getPassword());
                if(str.equals("PASSWORD") && check1.getText().equals("0"))
                {
                    tx1.setText("");
                    chkVisibilityPwd = 1;
                    check1.setText("1");
                    tx1.setEchoChar('•');
                }
                tx1.setCaretColor(hex2Rgb("#1E90FF"));
            }
        });


        tx.addFocusListener(new FocusListener()
        {
            @Override
            public void focusLost(FocusEvent e)
            {
                if(tx.getText().equals(""))
                {
                    tx.setText(" USERNAME ");
                    check.setText("0");
                    tx.setCaretColor(hex2Rgb("#FFFFFF"));
                }
            }

            @Override
            public void focusGained(FocusEvent e) {}
        });


        tx1.addFocusListener(new FocusListener()
        {
            @Override
            public void focusLost(FocusEvent e)
            {
                if(CheckPWD(tx1))
                {
                    tx1.setText("PASSWORD");
                    chkVisibilityPwd = 0;
                    check1.setText("0");
                    tx1.setEchoChar((char) 0);
                    tx1.setCaretColor(hex2Rgb("#FFFFFF"));
                }
            }

            @Override
            public void focusGained(FocusEvent e) {}
        });



        occhio.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                if(!((new String(tx1.getPassword())).equals("PASSWORD")))
                {
                    if(chkVisibilityPwd == 1)
                    {
	            		/*
		            	ImageIcon imgButton = new ImageIcon(Clienti.class.getResource("/occhioPwdReverse.jpeg"));
		                Image imgButton1 = imgButton.getImage();
		                Image imgButton2 = imgButton1.getScaledInstance(60, 40, Image.SCALE_SMOOTH);
		                imgOcchio.setIcon(new ImageIcon(imgButton2));
		                occhio.add(imgOcchio);
		            	tx1.setEchoChar((char) 0);
		            	*/
                        chkVisibilityPwd = 0;
                    }
                    else if (chkVisibilityPwd == 0)
                    {
	            		/*
	            		ImageIcon imgButton = new ImageIcon(Clienti.class.getResource("/occhioPwd.jpeg"));
		                Image imgButton1 = imgButton.getImage();
		                Image imgButton2 = imgButton1.getScaledInstance(60, 40, Image.SCALE_SMOOTH);
		                imgOcchio.setIcon(new ImageIcon(imgButton2));
		                occhio.add(imgOcchio);
		            	tx1.setEchoChar('•');
		            	*/
                        chkVisibilityPwd = 1;
                    }
                }
            }
        });


        occhio.addFocusListener(new FocusListener()
        {
            @Override
            public void focusLost(FocusEvent e)
            {
                b.setBackground(hex2Rgb("#FFFFFF"));
            }

            @Override
            public void focusGained(FocusEvent e)
            {
                b.setBackground(hex2Rgb("#FFFFFF"));
            }
        });

        b.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                try {
                    new Cittadini(true, null);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                //chiusura finestra login
                f.setVisible(false);
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                f.dispose();
            }
        });


        //finestra login
        f.getContentPane().setBackground(hex2Rgb("#FFFFFF"));
        //Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        //f.setLocation(dim.width/4-f.getSize().width/2, dim.height/4-f.getSize().height/2);
        f.setBounds(350,50,500,700);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setResizable(false);  //lock size finestra
        ImageIcon img = new ImageIcon(Objects.requireNonNull(Registrazione.class.getResource("/logo.jpg")));
        Image img1 = img.getImage();
        Image img2 = img1.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        f.setIconImage(img2);

        logo.setIcon(new ImageIcon(img2));
        logo.setBounds(150, 50, 200, 200);


        focus.setBounds(0,0,1,1);
        focus.setBackground(Color.decode("#FFFFFF"));
        focus.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, hex2Rgb("#FFFFFF")));
        focus.requestFocus();
        focus.setEditable(false);




        //username field

        tx.setText(" USERNAME ");
        tx.setFont(new Font("Comic Sans",Font.ITALIC,20));
        tx.setBounds(50,350,400,50);
        tx.setBackground(hex2Rgb("#FFFFFF"));
        tx.setForeground(hex2Rgb("#1E90FF"));
        tx.setCaretColor(hex2Rgb("#FFFFFF"));
        tx.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, hex2Rgb("#1E90FF")));
        tx.setHorizontalAlignment(JTextField.CENTER);


        //password field

        tx1.setText("PASSWORD");
        tx1.setFont(new Font("Comic Sans",Font.ITALIC,20));
        tx1.setBounds(50,420,400,50);
        tx1.requestFocus(true);

        if(check1.getText().equals("0"))
            tx1.setEchoChar((char)0);



        tx1.setBackground(hex2Rgb("#FFFFFF"));
        tx1.setForeground(hex2Rgb("#1E90FF"));
        tx1.setCaretColor(hex2Rgb("#FFFFFF"));
        tx1.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, hex2Rgb("#1E90FF")));
        tx1.setHorizontalAlignment(JTextField.CENTER);

        /*
        occhio.setBounds(755,425,60,40);
        occhio.setBackground(hex2Rgb("#FFFFFF"));
        occhio.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        imgOcchio.setBounds(50,0,60,40);

        ImageIcon imgButton = new ImageIcon(Clienti.class.getResource("/occhioPwd.jpeg"));
        Image imgButton1 = imgButton.getImage();
        Image imgButton2 = imgButton1.getScaledInstance(60, 40, Image.SCALE_SMOOTH);

        imgOcchio.setIcon(new ImageIcon(imgButton2));
        occhio.add(imgOcchio);
        */


        //bottone accedi
        b.setBounds(150,550,200,60);
        b.setBackground(hex2Rgb("#FFFFFF"));
        b.setForeground(hex2Rgb("#1E90FF"));
        b.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, hex2Rgb("#1E90FF")));
        b.setFont(new Font("Comic Sans",Font.ITALIC,20));


        Image imageBack = ImageIO.read(Objects.requireNonNull(RegistraCentri.class.getResource("/indietro.jpeg")));
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
                    new Cittadini(false, null);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                f.setVisible(false);
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                f.dispose();
            }
        });


        check.setVisible(false);
        check1.setVisible(false);

        /*
        ImageIcon img = new ImageIcon(Clienti.class.getResource("/logo.jpeg"));
        Image img1 = img.getImage();
        Image img2 = img1.getScaledInstance(400, 390, Image.SCALE_SMOOTH);


        tmpImage.setIcon(new ImageIcon(img2));
        tmpImage.setBounds(0, 0, 350, 350);


        panel.setBounds(375, 0, 350, 350);
        panel.setBackground(hex2Rgb("#FFFFFF"));
        panel.add(tmpImage);
		*/

        f.add(check);
        f.add(check1);
        f.add(b);
        f.add(tx);
        f.add(tx1);
        f.add(indietro);
        f.add(occhio);
        f.add(focus);
        f.add(logo);
    }


}
