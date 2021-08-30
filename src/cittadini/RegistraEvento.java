package cittadini;

import centrivaccinali.RegistraCentri;
import common.Cittadino;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * La classe Recensione contiene il codice per l'aggiunta di una recensione nel file EatAdvisor.dati per la creazione della finestra dove l'utente rilascia una recensione sul ristorante selezionato
 * @author Davide Feldkircher, Guido Bernasconi
 */
public class RegistraEvento
{

    /**
     * Severita lasciata dall'utente nella recensione
     */
    static int severita = 0;

    JButton indietro = new JButton();

    /**
     * Metodo che setta l'immagine con la stella vuota
     * @param p  Panel dove sarà messa la Label l
     * @param l  Panel dove sarà messa l'ImageIcon con la stella vuota
     * @param v  severita lasciata dall'utente (se non ha lasciato nulla è 0)
     */
    public static void SetStellaVuota(JPanel p, JLabel l, int v)
    {
		/*
		if(v == 0)
		{
			ImageIcon img = new ImageIcon(Cittadini.class.getResource("/stellaVuota.jpeg"));
		    Image img1 = img.getImage();
		    Image stellaVuota = img1.getScaledInstance(110, 70, Image.SCALE_SMOOTH);

			l.setIcon(new ImageIcon(stellaVuota));
		    l.setBounds(0, 0, 70, 70);
		    p.setBackground(Color.decode("#FFFFFF"));
		    p.add(l);
		}
		else if(v < 5)
		{
			ImageIcon img = new ImageIcon(Cittadini.class.getResource("/stellaVuota.jpeg"));
		    Image img1 = img.getImage();
		    Image stellaVuota = img1.getScaledInstance(109, 70, Image.SCALE_SMOOTH);

			l.setIcon(new ImageIcon(stellaVuota));
		    l.setBounds(0, 0, 70, 70);
		    p.setBackground(Color.decode("#FFFFFF"));
		    p.add(l);
		}
		else if(v == 6)
		{
			ImageIcon img = new ImageIcon(Cittadini.class.getResource("/stellaVuota.jpeg"));
		    Image img1 = img.getImage();
		    Image stellaVuota = img1.getScaledInstance(110, 70, Image.SCALE_SMOOTH);

			l.setIcon(new ImageIcon(stellaVuota));
		    l.setBounds(0, 0, 70, 70);
		    p.setBackground(Color.decode("#FFFFFF"));
		    p.setBounds(190, 390, 70, 70);
		    p.add(l);
		}
		else if(v ==7)
		{
			ImageIcon img = new ImageIcon(Cittadini.class.getResource("/stellaVuota.jpeg"));
		    Image img1 = img.getImage();
		    Image stellaVuota = img1.getScaledInstance(109, 70, Image.SCALE_SMOOTH);

			l.setIcon(new ImageIcon(stellaVuota));
		    l.setBounds(0, 0, 70, 70);
		    p.setBackground(Color.decode("#FFFFFF"));
		    p.add(l);
		}
		*/
    }

    /**
     * Metodo che setta l'immagine con la stella piena
     * @param p  Panel dove sarà messa la Label l
     * @param l  Panel dove sarà messa l'ImageIcon con la stella piena
     * @param v  severita lasciata dall'utente (se non ha lasciato nulla è 0)
     */
    public static void SetStellaPiena(JPanel p, JLabel l, int v)
    {
		/*
		if(v == 0)
		{
			ImageIcon img = new ImageIcon(Cittadini.class.getResource("/stellaPiena.jpeg"));
		    Image img1 = img.getImage();
		    Image stellaPiena = img1.getScaledInstance(100, 60, Image.SCALE_SMOOTH);

			l.setIcon(new ImageIcon(stellaPiena));
		    l.setBounds(5, 0, 70, 70);
		    p.setBackground(Color.decode("#FFFFFF"));
		    p.add(l);
		}
		else if(v <= 5)
		{
			ImageIcon img = new ImageIcon(Cittadini.class.getResource("/stellaPiena.jpeg"));
		    Image img1 = img.getImage();
		    Image stellaPiena = img1.getScaledInstance( 99, 60, Image.SCALE_SMOOTH);

			l.setIcon(new ImageIcon(stellaPiena));
		    l.setBounds(5, 0, 70, 70);
		    p.setBackground(Color.decode("#FFFFFF"));
		    p.add(l);
		}
		else if(v == 6)
		{
			ImageIcon img = new ImageIcon(Cittadini.class.getResource("/stellaPiena.jpeg"));
		    Image img1 = img.getImage();
		    Image stellaPiena = img1.getScaledInstance(100, 60, Image.SCALE_SMOOTH);

			l.setIcon(new ImageIcon(stellaPiena));
		    l.setBounds(5, 0, 70, 70);
		    p.setBackground(Color.decode("#FFFFFF"));
		    p.setBounds(190, 398, 70, 70);
		    p.add(l);
		}
		*/
    }

    public static Color hex2Rgb(String colorStr) //conversione esadecimale in rgb per sfondo frame
    {
        return new Color(Integer.valueOf( colorStr.substring( 1, 3 ), 16 ), Integer.valueOf( colorStr.substring( 3, 5 ), 16 ), Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }

    private static final String DEFAULT_EVENTO = "Seleziona il tipo di evento";
    /**
     * Metodo con il codice per la creazione della finestra della recensione
     * @throws IOException  Il costruttore, agendo sul file EatAdvisor.dati, può creare un'IOException
     */
    public RegistraEvento(boolean checkLogin, Cittadino account) throws IOException
    {
        System.out.println(checkLogin);

        JFrame f = new JFrame("Finestra Recensione");
        f.getContentPane().setBackground(Color.decode("#FFFFFF"));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        f.setLocation(dim.width/4-f.getSize().width/2, dim.height/4-f.getSize().height/2);
        f.setSize(800,600);
        f.setLayout(null);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setResizable(false);
        f.setVisible(true);

        JComboBox<String> tipoEvento = new JComboBox<String>();
        tipoEvento.setModel(new DefaultComboBoxModel<String>() {
            private static final long serialVersionUID = 1L;
            boolean selectionAllowed = true;

            @Override
            public void setSelectedItem(Object anObject)
            {
                if (!DEFAULT_EVENTO.equals(anObject)) {
                    super.setSelectedItem(anObject);
                } else if (selectionAllowed) {
                    // Allow this just once
                    selectionAllowed = false;
                    super.setSelectedItem(anObject);
                }
            }
        });

        tipoEvento.addItem(DEFAULT_EVENTO);
        tipoEvento.addItem("Febbre");
        tipoEvento.addItem("Mal di testa");
        tipoEvento.addItem("Male al braccio");
        tipoEvento.addItem("Giramento di testa");
        tipoEvento.addItem("Fastidio all'addome");
        tipoEvento.addItem("Male alle gambe");

        tipoEvento.setBounds(175,70,450,25);
        tipoEvento.setForeground(hex2Rgb("#1E90FF"));
        tipoEvento.setBackground(hex2Rgb("#FFFFFF"));
        tipoEvento.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        tipoEvento.setFont(new Font("Comic Sans",Font.ITALIC,15));

        JTextArea testo = new JTextArea("Inserisci note aggiuntive qui...");
        testo.setBounds(150,210,500,200);
        testo.setBackground(Color.decode("#FFFFFF"));
        testo.setForeground(Color.decode("#1E90FF"));
        testo.setFont(new Font("Arial", Font.ITALIC, 20));
        testo.setVisible(true);
        testo.setLineWrap(true);
        testo.setWrapStyleWord(true);

        KeyListener testoKeyListener = new KeyListener()
        {
            public void keyPressed(KeyEvent keyEvent) {}

            public void keyReleased(KeyEvent keyEvent) {}

            public void keyTyped(KeyEvent keyEvent)
            {
                check(keyEvent);
            }

            private void check(KeyEvent keyEvent)
            {
                if(keyEvent.getKeyChar() == '|')
                {
                    keyEvent.consume();
                    JOptionPane.showMessageDialog(f, "E' stato inserito il carattere proibito '|'", "Errore di Inserimento", JOptionPane.ERROR_MESSAGE);
                    testo.transferFocus();
                }

                else if(keyEvent.getKeyChar() == KeyEvent.VK_ENTER)
                {
                    keyEvent.consume();
                    JOptionPane.showMessageDialog(f, "E' stato inserito il carattere proibito \"invio\"", "Errore di Inserimento", JOptionPane.ERROR_MESSAGE);
                    testo.transferFocus();
                }

            }
        };

        testo.addKeyListener(testoKeyListener);



        JLabel counter = new JLabel("Caratteri rimanenti: 256");
        counter.setFont(new Font("Arial", Font.ITALIC, 20));
        counter.setForeground(Color.decode(("#808080")));
        counter.setBounds(0,0,300,50);


        JPanel p1 = new JPanel();
        JLabel s1 = new JLabel();

        JPanel p2 = new JPanel();
        JLabel s2 = new JLabel();

        JPanel p3 = new JPanel();
        JLabel s3 = new JLabel();

        JPanel p4 = new JPanel();
        JLabel s4 = new JLabel();

        JPanel p5 = new JPanel();
        JLabel s5 = new JLabel();

        SetStellaVuota(p1,s1,severita);
        SetStellaVuota(p2,s2,severita);
        SetStellaVuota(p3,s3,severita);
        SetStellaVuota(p4,s4,severita);
        SetStellaVuota(p5,s5,severita);

        p1.setBounds(190, 390, 70, 70);
        p2.setBounds(280, 390, 70, 70);
        p3.setBounds(370, 390, 70, 70);
        p4.setBounds(460, 390, 70, 70);
        p5.setBounds(550, 390, 70, 70);



        testo.addKeyListener(new KeyAdapter()
        {
            public void keyTyped(KeyEvent evt)
            {
                int i = 256 - testo.getText().length();
                counter.setText("Caratteri rimanenti: " + i);

                if(testo.getText().length()==0)
                    counter.setText("Caratteri rimanenti: 256");

            }
        });

        testo.addKeyListener(new KeyAdapter()
        {
            public void keyTyped(KeyEvent evt)
            {
                if(testo.getText().length()>=256 &&! (evt.getKeyChar()==KeyEvent.VK_DELETE||evt.getKeyChar()==KeyEvent.VK_BACK_SPACE))
                    evt.consume();
            }
        });
    	/*
    	JLabel tmpImage = new JLabel();
    	JPanel panelRec = new JPanel();
    	panelRec.setBounds(200,-20,400,200);
        panelRec.setBackground(Color.decode("#FFFFFF"));
        panelRec.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.decode("#1E90FF")));
        tmpImage.setBounds(50,0,400,200);

        ImageIcon imgButton = new ImageIcon(Cittadini.class.getResource("/recensione.jpeg"));
        Image imgButton1 = imgButton.getImage();
        Image imgButton2 = imgButton1.getScaledInstance(500, 220, Image.SCALE_SMOOTH);
        tmpImage.setIcon(new ImageIcon(imgButton2));
        panelRec.add(tmpImage);
        */

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(testo, BorderLayout.NORTH);
        panel.add(counter, BorderLayout.SOUTH);
        panel.setBounds(175, 180, 450, 200);
        panel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.decode("#1E90FF")));
        panel.setBackground(Color.decode("#FFFFFF"));
        panel.setForeground(Color.decode("#1E90FF"));


        panel.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                String str = new String(testo.getText());
                if(str.equals("Inserisci il commento qui..."))
                    testo.setText("");
                testo.requestFocus();
                testo.setCaretColor(Color.decode("#1E90FF"));
            }
        });


        JButton invia = new JButton("INVIA");
        invia.setBackground(Color.decode("#FFFFFF"));
        invia.setForeground(Color.decode("#1E90FF"));
        invia.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.decode("#1E90FF")));
        invia.setBounds(355, 480, 100, 40);
        invia.setFont(new Font("Arial", Font.ITALIC, 20));

    	/*
    	Image freccia = null;
		try
		{
			freccia = ImageIO.read(Cittadini.class.getResource("/frecciaBottone.jpeg"));
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
        freccia = freccia.getScaledInstance(105, 105, Image.SCALE_SMOOTH);
        */




        f.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                if(testo.isFocusOwner())
                    if(testo.getText().equals(""))
                        testo.setText("Inserisci il commento qui...");

                f.requestFocus();
            }
        });


        testo.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                if(testo.getText().equals("Inserisci il commento qui..."))
                    testo.setText("");
                testo.setCaretColor(Color.decode("#1E90FF"));
            }
        });


        p1.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(severita == 1)
                {
                    SetStellaVuota(p1,s1,severita);
                    severita = 0;
                    p1.setBounds(190, 390, 70, 70);
                }
                else if(severita == 0)
                {
                    severita = 1;
                    SetStellaPiena(p1,s1,severita);
                    p1.setBounds(190, 398, 70, 70);
                }
                else if(severita == 2)
                {
                    SetStellaVuota(p1,s1,severita);
                    p1.setBounds(190, 390, 70, 70);

                    SetStellaVuota(p2,s2,severita);
                    p2.setBounds(280, 390, 70, 70);

                    severita = 1;

                    SetStellaPiena(p1,s1,severita);
                    p1.setBounds(190, 398, 70, 70);
                }
                else if(severita == 3)
                {
                    SetStellaVuota(p1,s1,severita);
                    p1.setBounds(190, 390, 70, 70);

                    SetStellaVuota(p2,s2,severita);
                    p2.setBounds(280, 390, 70, 70);

                    SetStellaVuota(p3,s3,severita);
                    p3.setBounds(370, 390, 70, 70);

                    severita = 1;

                    SetStellaPiena(p1,s1,severita);
                    p1.setBounds(190, 398, 70, 70);
                }
                else if(severita == 4)
                {
                    SetStellaVuota(p1,s1,severita);
                    p1.setBounds(190, 390, 70, 70);

                    SetStellaVuota(p2,s2,severita);
                    p2.setBounds(280, 390, 70, 70);

                    SetStellaVuota(p3,s3,severita);
                    p3.setBounds(370, 390, 70, 70);

                    SetStellaVuota(p4,s4,severita);
                    p4.setBounds(460, 390, 70, 70);

                    severita = 1;

                    SetStellaPiena(p1,s1,severita);
                    p1.setBounds(190, 398, 70, 70);
                }
                else if(severita == 5)
                {
                    severita = 7;
                    SetStellaVuota(p1,s1,severita);
                    p1.setBounds(190, 390, 70, 70);

                    SetStellaVuota(p2,s2,severita);
                    p2.setBounds(280, 390, 70, 70);

                    SetStellaVuota(p3,s3,severita);
                    p3.setBounds(370, 390, 70, 70);

                    SetStellaVuota(p4,s4,severita);
                    p4.setBounds(460, 390, 70, 70);

                    SetStellaVuota(p5,s5,severita);
                    p5.setBounds(550, 390, 70, 70);

                    severita = 1;

                    SetStellaPiena(p1,s1,severita);
                    p1.setBounds(190, 398, 70, 70);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                if(severita == 0)
                {
                    severita = 6;
                    SetStellaPiena(p1,s1,severita);
                    severita = 0;
                }
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                if(severita == 0)
                {
                    severita = 6;
                    SetStellaVuota(p1,s1,severita);
                    severita = 0;
                }
            }
        });



        p2.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(severita == 2)
                {
                    SetStellaVuota(p1,s1,severita);
                    p1.setBounds(190, 390, 70, 70);

                    SetStellaVuota(p2,s2,severita);
                    p2.setBounds(280, 390, 70, 70);

                    severita = 0;
                }
                else if(severita == 0)
                {
                    severita = 2;

                    SetStellaPiena(p1,s1,severita);
                    p1.setBounds(190, 398, 70, 70);

                    SetStellaPiena(p2,s2,severita);
                    p2.setBounds(280, 398, 70, 70);
                }
                else if(severita == 1)
                {
                    SetStellaVuota(p1,s1,severita);
                    p1.setBounds(190, 390, 70, 70);

                    severita = 2;

                    SetStellaPiena(p1,s1,severita);
                    p1.setBounds(190, 398, 70, 70);

                    SetStellaPiena(p2,s2,severita);
                    p2.setBounds(280, 398, 70, 70);
                }
                else if(severita == 3)
                {
                    SetStellaVuota(p1,s1,severita);
                    p1.setBounds(190, 390, 70, 70);

                    SetStellaVuota(p2,s2,severita);
                    p2.setBounds(280, 390, 70, 70);

                    SetStellaVuota(p3,s3,severita);
                    p3.setBounds(370, 390, 70, 70);

                    severita = 2;

                    SetStellaPiena(p1,s1,severita);
                    p1.setBounds(190, 398, 70, 70);

                    SetStellaPiena(p2,s2,severita);
                    p2.setBounds(280, 398, 70, 70);
                }
                else if(severita == 4)
                {
                    SetStellaVuota(p1,s1,severita);
                    p1.setBounds(190, 390, 70, 70);

                    SetStellaVuota(p2,s2,severita);
                    p2.setBounds(280, 390, 70, 70);

                    SetStellaVuota(p3,s3,severita);
                    p3.setBounds(370, 390, 70, 70);

                    SetStellaVuota(p4,s4,severita);
                    p4.setBounds(460, 390, 70, 70);

                    severita = 2;

                    SetStellaPiena(p1,s1,severita);
                    p1.setBounds(190, 398, 70, 70);

                    SetStellaPiena(p2,s2,severita);
                    p2.setBounds(280, 398, 70, 70);
                }
                else if(severita == 5)
                {
                    severita = 7;

                    SetStellaVuota(p1,s1,severita);
                    p1.setBounds(190, 390, 70, 70);

                    SetStellaVuota(p2,s2,severita);
                    p2.setBounds(280, 390, 70, 70);

                    SetStellaVuota(p3,s3,severita);
                    p3.setBounds(370, 390, 70, 70);

                    SetStellaVuota(p4,s4,severita);
                    p4.setBounds(460, 390, 70, 70);

                    SetStellaVuota(p5,s5,severita);
                    p5.setBounds(550, 390, 70, 70);

                    severita = 2;

                    SetStellaPiena(p1,s1,severita);
                    p1.setBounds(190, 398, 70, 70);

                    SetStellaPiena(p2,s2,severita);
                    p2.setBounds(280, 398, 70, 70);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                if(severita == 0)
                {
                    SetStellaPiena(p1,s1,severita);
                    p1.setBounds(190, 398, 70, 70);

                    SetStellaPiena(p2,s2,severita);
                    p2.setBounds(280, 398, 70, 70);
                }
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                if(severita == 0)
                {
                    SetStellaVuota(p1,s1,severita);
                    p1.setBounds(190, 390, 70, 70);

                    SetStellaVuota(p2,s2,severita);
                    p2.setBounds(280, 390, 70, 70);

                    severita = 0;
                }
            }
        });



        p3.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(severita == 3)
                {
                    SetStellaVuota(p1,s1,severita);
                    p1.setBounds(190, 390, 70, 70);

                    SetStellaVuota(p2,s2,severita);
                    p2.setBounds(280, 390, 70, 70);

                    SetStellaVuota(p3,s3,severita);
                    p3.setBounds(370, 390, 70, 70);

                    severita = 0;
                }
                else if(severita == 0)
                {
                    severita = 3;

                    SetStellaPiena(p1,s1,severita);
                    p1.setBounds(190, 398, 70, 70);

                    SetStellaPiena(p2,s2,severita);
                    p2.setBounds(280, 398, 70, 70);

                    SetStellaPiena(p3,s3,severita);
                    p3.setBounds(370, 398, 70, 70);
                }
                else if(severita == 1)
                {
                    SetStellaVuota(p1,s1,severita);
                    p1.setBounds(190, 390, 70, 70);

                    severita = 3;

                    SetStellaPiena(p1,s1,severita);
                    p1.setBounds(190, 398, 70, 70);

                    SetStellaPiena(p2,s2,severita);
                    p2.setBounds(280, 398, 70, 70);

                    SetStellaPiena(p3,s3,severita);
                    p3.setBounds(370, 398, 70, 70);
                }
                else if(severita == 2)
                {
                    SetStellaVuota(p1,s1,severita);
                    p1.setBounds(190, 390, 70, 70);

                    SetStellaVuota(p2,s2,severita);
                    p2.setBounds(280, 390, 70, 70);

                    severita = 3;

                    SetStellaPiena(p1,s1,severita);
                    p1.setBounds(190, 398, 70, 70);

                    SetStellaPiena(p2,s2,severita);
                    p2.setBounds(280, 398, 70, 70);

                    SetStellaPiena(p3,s3,severita);
                    p3.setBounds(370, 398, 70, 70);
                }
                else if(severita == 4)
                {
                    SetStellaVuota(p1,s1,severita);
                    p1.setBounds(190, 390, 70, 70);

                    SetStellaVuota(p2,s2,severita);
                    p2.setBounds(280, 390, 70, 70);

                    SetStellaVuota(p3,s3,severita);
                    p3.setBounds(370, 390, 70, 70);

                    SetStellaVuota(p4,s4,severita);
                    p4.setBounds(460, 390, 70, 70);

                    severita = 3;

                    SetStellaPiena(p1,s1,severita);
                    p1.setBounds(190, 398, 70, 70);

                    SetStellaPiena(p2,s2,severita);
                    p2.setBounds(280, 398, 70, 70);

                    SetStellaPiena(p3,s3,severita);
                    p3.setBounds(370, 398, 70, 70);
                }
                else if(severita == 5)
                {
                    severita = 7;

                    SetStellaVuota(p1,s1,severita);
                    p1.setBounds(190, 390, 70, 70);

                    SetStellaVuota(p2,s2,severita);
                    p2.setBounds(280, 390, 70, 70);

                    SetStellaVuota(p3,s3,severita);
                    p3.setBounds(370, 390, 70, 70);

                    SetStellaVuota(p4,s4,severita);
                    p4.setBounds(460, 390, 70, 70);

                    SetStellaVuota(p5,s5,severita);
                    p5.setBounds(550, 390, 70, 70);

                    severita = 3;

                    SetStellaPiena(p1,s1,severita);
                    p1.setBounds(190, 398, 70, 70);

                    SetStellaPiena(p2,s2,severita);
                    p2.setBounds(280, 398, 70, 70);

                    SetStellaPiena(p3,s3,severita);
                    p3.setBounds(370, 398, 70, 70);
                }
            }


            @Override
            public void mouseEntered(MouseEvent e)
            {
                if(severita == 0)
                {
                    SetStellaPiena(p1,s1,severita);
                    p1.setBounds(190, 398, 70, 70);

                    SetStellaPiena(p2,s2,severita);
                    p2.setBounds(280, 398, 70, 70);

                    SetStellaPiena(p3,s3,severita);
                    p3.setBounds(370, 398, 70, 70);
                }
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                if(severita == 0)
                {
                    SetStellaVuota(p1,s1,severita);
                    p1.setBounds(190, 390, 70, 70);

                    SetStellaVuota(p2,s2,severita);
                    p2.setBounds(280, 390, 70, 70);

                    SetStellaVuota(p3,s3,severita);
                    p3.setBounds(370, 390, 70, 70);

                    severita = 0;
                }
            }
        });



        p4.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(severita == 4)
                {
                    SetStellaVuota(p1,s1,severita);
                    p1.setBounds(190, 390, 70, 70);

                    SetStellaVuota(p2,s2,severita);
                    p2.setBounds(280, 390, 70, 70);

                    SetStellaVuota(p3,s3,severita);
                    p3.setBounds(370, 390, 70, 70);

                    SetStellaVuota(p4,s4,severita);
                    p4.setBounds(460, 390, 70, 70);

                    severita = 0;
                }
                else if(severita == 0)
                {
                    severita = 4;

                    SetStellaPiena(p1,s1,severita);
                    p1.setBounds(190, 398, 70, 70);

                    SetStellaPiena(p2,s2,severita);
                    p2.setBounds(280, 398, 70, 70);

                    SetStellaPiena(p3,s3,severita);
                    p3.setBounds(370, 398, 70, 70);

                    SetStellaPiena(p4,s4,severita);
                    p4.setBounds(460, 398, 70, 70);
                }
                else if(severita == 1)
                {
                    SetStellaVuota(p1,s1,severita);
                    p1.setBounds(190, 390, 70, 70);

                    severita = 4;

                    SetStellaPiena(p1,s1,severita);
                    p1.setBounds(190, 398, 70, 70);

                    SetStellaPiena(p2,s2,severita);
                    p2.setBounds(280, 398, 70, 70);

                    SetStellaPiena(p3,s3,severita);
                    p3.setBounds(370, 398, 70, 70);

                    SetStellaPiena(p4,s4,severita);
                    p4.setBounds(460, 398, 70, 70);
                }
                else if(severita == 2)
                {
                    SetStellaVuota(p1,s1,severita);
                    p1.setBounds(190, 390, 70, 70);

                    SetStellaVuota(p2,s2,severita);
                    p2.setBounds(280, 390, 70, 70);

                    severita = 4;

                    SetStellaPiena(p1,s1,severita);
                    p1.setBounds(190, 398, 70, 70);

                    SetStellaPiena(p2,s2,severita);
                    p2.setBounds(280, 398, 70, 70);

                    SetStellaPiena(p3,s3,severita);
                    p3.setBounds(370, 398, 70, 70);

                    SetStellaPiena(p4,s4,severita);
                    p4.setBounds(460, 398, 70, 70);
                }
                else if(severita == 3)
                {
                    SetStellaVuota(p1,s1,severita);
                    p1.setBounds(190, 390, 70, 70);

                    SetStellaVuota(p2,s2,severita);
                    p2.setBounds(280, 390, 70, 70);

                    SetStellaVuota(p3,s3,severita);
                    p3.setBounds(370, 390, 70, 70);

                    severita = 4;

                    SetStellaPiena(p1,s1,severita);
                    p1.setBounds(190, 398, 70, 70);

                    SetStellaPiena(p2,s2,severita);
                    p2.setBounds(280, 398, 70, 70);

                    SetStellaPiena(p3,s3,severita);
                    p3.setBounds(370, 398, 70, 70);

                    SetStellaPiena(p4,s4,severita);
                    p4.setBounds(460, 398, 70, 70);
                }
                else if(severita == 5)
                {
                    severita = 7;

                    SetStellaVuota(p1,s1,severita);
                    p1.setBounds(190, 390, 70, 70);

                    SetStellaVuota(p2,s2,severita);
                    p2.setBounds(280, 390, 70, 70);

                    SetStellaVuota(p3,s3,severita);
                    p3.setBounds(370, 390, 70, 70);

                    SetStellaVuota(p4,s4,severita);
                    p4.setBounds(460, 390, 70, 70);

                    SetStellaVuota(p5,s5,severita);
                    p5.setBounds(550, 390, 70, 70);

                    severita = 4;

                    SetStellaPiena(p1,s1,severita);
                    p1.setBounds(190, 398, 70, 70);

                    SetStellaPiena(p2,s2,severita);
                    p2.setBounds(280, 398, 70, 70);

                    SetStellaPiena(p3,s3,severita);
                    p3.setBounds(370, 398, 70, 70);

                    SetStellaPiena(p4,s4,severita);
                    p4.setBounds(460, 398, 70, 70);
                }

            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                if(severita == 0)
                {
                    SetStellaPiena(p1,s1,severita);
                    p1.setBounds(190, 398, 70, 70);

                    SetStellaPiena(p2,s2,severita);
                    p2.setBounds(280, 398, 70, 70);

                    SetStellaPiena(p3,s3,severita);
                    p3.setBounds(370, 398, 70, 70);

                    SetStellaPiena(p4,s4,severita);
                    p4.setBounds(460, 398, 70, 70);
                }
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                if(severita == 0)
                {
                    SetStellaVuota(p1,s1,severita);
                    p1.setBounds(190, 390, 70, 70);

                    SetStellaVuota(p2,s2,severita);
                    p2.setBounds(280, 390, 70, 70);

                    SetStellaVuota(p3,s3,severita);
                    p3.setBounds(370, 390, 70, 70);

                    SetStellaVuota(p4,s4,severita);
                    p4.setBounds(460, 390, 70, 70);

                    severita = 0;
                }
            }
        });



        p5.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(severita == 5)
                {
                    severita = 7;
                    SetStellaVuota(p1,s1,severita);
                    p1.setBounds(190, 390, 70, 70);

                    SetStellaVuota(p2,s2,severita);
                    p2.setBounds(280, 390, 70, 70);

                    SetStellaVuota(p3,s3,severita);
                    p3.setBounds(370, 390, 70, 70);

                    SetStellaVuota(p4,s4,severita);
                    p4.setBounds(460, 390, 70, 70);

                    SetStellaVuota(p5,s5,severita);
                    p5.setBounds(550, 390, 70, 70);

                    severita = 0;
                }
                else if(severita == 0)
                {
                    severita = 5;

                    SetStellaPiena(p1,s1,severita);
                    p1.setBounds(190, 398, 70, 70);

                    SetStellaPiena(p2,s2,severita);
                    p2.setBounds(280, 398, 70, 70);

                    SetStellaPiena(p3,s3,severita);
                    p3.setBounds(370, 398, 70, 70);

                    SetStellaPiena(p4,s4,severita);
                    p4.setBounds(460, 398, 70, 70);

                    SetStellaPiena(p5,s5,severita);
                    p5.setBounds(550, 398, 70, 70);
                }
                else if(severita == 1)
                {
                    SetStellaVuota(p1,s1,severita);
                    p1.setBounds(190, 390, 70, 70);

                    severita = 5;

                    SetStellaPiena(p1,s1,severita);
                    p1.setBounds(190, 398, 70, 70);

                    SetStellaPiena(p2,s2,severita);
                    p2.setBounds(280, 398, 70, 70);

                    SetStellaPiena(p3,s3,severita);
                    p3.setBounds(370, 398, 70, 70);

                    SetStellaPiena(p4,s4,severita);
                    p4.setBounds(460, 398, 70, 70);

                    SetStellaPiena(p5,s5,severita);
                    p5.setBounds(550, 398, 70, 70);
                }
                else if(severita == 2)
                {
                    SetStellaVuota(p1,s1,severita);
                    p1.setBounds(190, 390, 70, 70);

                    SetStellaVuota(p2,s2,severita);
                    p2.setBounds(280, 390, 70, 70);

                    severita = 5;

                    SetStellaPiena(p1,s1,severita);
                    p1.setBounds(190, 398, 70, 70);

                    SetStellaPiena(p2,s2,severita);
                    p2.setBounds(280, 398, 70, 70);

                    SetStellaPiena(p3,s3,severita);
                    p3.setBounds(370, 398, 70, 70);

                    SetStellaPiena(p4,s4,severita);
                    p4.setBounds(460, 398, 70, 70);

                    SetStellaPiena(p5,s5,severita);
                    p5.setBounds(550, 398, 70, 70);
                }
                else if(severita == 3)
                {
                    SetStellaVuota(p1,s1,severita);
                    p1.setBounds(190, 390, 70, 70);

                    SetStellaVuota(p2,s2,severita);
                    p2.setBounds(280, 390, 70, 70);

                    SetStellaVuota(p3,s3,severita);
                    p3.setBounds(370, 390, 70, 70);

                    severita = 5;

                    SetStellaPiena(p1,s1,severita);
                    p1.setBounds(190, 398, 70, 70);

                    SetStellaPiena(p2,s2,severita);
                    p2.setBounds(280, 398, 70, 70);

                    SetStellaPiena(p3,s3,severita);
                    p3.setBounds(370, 398, 70, 70);

                    SetStellaPiena(p4,s4,severita);
                    p4.setBounds(460, 398, 70, 70);

                    SetStellaPiena(p5,s5,severita);
                    p5.setBounds(550, 398, 70, 70);
                }
                else if(severita == 4)
                {
                    SetStellaVuota(p1,s1,severita);
                    p1.setBounds(190, 390, 70, 70);

                    SetStellaVuota(p2,s2,severita);
                    p2.setBounds(280, 390, 70, 70);

                    SetStellaVuota(p3,s3,severita);
                    p3.setBounds(370, 390, 70, 70);

                    SetStellaVuota(p4,s4,severita);
                    p4.setBounds(460, 390, 70, 70);

                    severita = 5;

                    SetStellaPiena(p1,s1,severita);
                    p1.setBounds(190, 398, 70, 70);

                    SetStellaPiena(p2,s2,severita);
                    p2.setBounds(280, 398, 70, 70);

                    SetStellaPiena(p3,s3,severita);
                    p3.setBounds(370, 398, 70, 70);

                    SetStellaPiena(p4,s4,severita);
                    p4.setBounds(460, 398, 70, 70);

                    SetStellaPiena(p5,s5,severita);
                    p5.setBounds(550, 398, 70, 70);

                }

            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                if(severita == 0)
                {
                    SetStellaPiena(p1,s1,severita);
                    p1.setBounds(190, 398, 70, 70);

                    SetStellaPiena(p2,s2,severita);
                    p2.setBounds(280, 398, 70, 70);

                    SetStellaPiena(p3,s3,severita);
                    p3.setBounds(370, 398, 70, 70);

                    SetStellaPiena(p4,s4,severita);
                    p4.setBounds(460, 398, 70, 70);

                    SetStellaPiena(p5,s5,severita);
                    p5.setBounds(550, 398, 70, 70);
                }
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                if(severita == 0)
                {
                    SetStellaVuota(p1,s1,severita);
                    p1.setBounds(190, 390, 70, 70);

                    SetStellaVuota(p2,s2,severita);
                    p2.setBounds(280, 390, 70, 70);

                    SetStellaVuota(p3,s3,severita);
                    p3.setBounds(370, 390, 70, 70);

                    SetStellaVuota(p4,s4,severita);
                    p4.setBounds(460, 390, 70, 70);

                    SetStellaVuota(p5,s5,severita);
                    p5.setBounds(550, 390, 70, 70);

                    severita = 0;
                }
            }
        });


        invia.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {

                if(severita>0 && severita<6 && !tipoEvento.getSelectedItem().toString().equals(DEFAULT_EVENTO))
                {
                    //aggiungi recensione
                    f.setVisible(false);
                    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    f.dispose();
                }
                else if(!tipoEvento.getSelectedItem().toString().equals(DEFAULT_EVENTO))
                    JOptionPane.showMessageDialog(f, "Selezionare una severita", "Errore Severita", JOptionPane.ERROR_MESSAGE);
                else if(severita>0 && severita<6)
                    JOptionPane.showMessageDialog(f, "Selezionare un tipo di evento", "Errore Evento", JOptionPane.ERROR_MESSAGE);
                else
                    JOptionPane.showMessageDialog(f, "Selezionare una severita e Selezionare un tipo di evento", "Errore Evento", JOptionPane.ERROR_MESSAGE);
            }
        });

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
                    new Cittadini(checkLogin, account);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                f.setVisible(false);
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                f.dispose();
            }
        });


        f.add(p1);
        f.add(p2);
        f.add(p3);
        f.add(p4);
        f.add(p5);
        f.add(indietro);
        f.add(invia);
        f.add(panel);
        f.add(tipoEvento);
    }


    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {

            @Override
            public void run()
            {
                try
                {
                    new RegistraEvento(true,null);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
}
