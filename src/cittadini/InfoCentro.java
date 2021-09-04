package cittadini;

import centrivaccinali.RegistraCentri;
import common.*;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.*;


/**
 * La classe InfoCentro contiene il codice per la creazione della schermata relativa alla visualizzazione
 * delle informazioni relative a un centro vaccinale
 * @author Giulio Baricci
 */

public class InfoCentro extends UnicastRemoteObject {

    /**
     * Oggetto che fa riferimento al server disponibile sul rmiregistry
     */
    private static ClientCV stub;

    /**
     * Bottone per tornare alla schermata precedente
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
     * Il costruttore contine il codice per la creazione e la visualizzazione della schermata di informazioni del
     * centro vaccinale selezionato
     * @param selezionato contiene le informazioni relative al centro selezionato
     * @param checkLogin controlla se è stato effettuato un accesso
     * @param account fa riferimento al cittadino che ha effettuato l'accesso
     * @param checkR controlla se la schermata viene creata durante una registrazione
     * @throws IOException il costruttore contiene del codice che legge delle immagini quindi può genererare IOException
     * @throws NotBoundException il costruttore contiene del codice che si conntte al rmiregistry quindi può genererare NotBoundException
     * @throws SQLException il costruttore contiene del codice che riceve dati dal database quindi può genererare SQLException
     */
    public InfoCentro(CentroVaccinale selezionato, boolean checkLogin, Cittadino account, boolean checkR) throws IOException, NotBoundException, SQLException {
        super();


        Registry registro = LocateRegistry.getRegistry("localhost", 1099);
        stub = (common.ClientCV) registro.lookup("SERVERCV");

        ArrayList<EventoAvverso> eventi = (ArrayList<EventoAvverso>) stub.getEventiAvversi(selezionato); //Prendere lista eventi avversi registrati per il centro selezionato

        JPanel panel = new JPanel();
        JLabel tmpImage = new JLabel();

        JButton registra = new JButton("Registrati al centro");

        JFrame f = new JFrame("Finestra Informazioni");

		/*
		ImageIcon img = new ImageIcon(Clienti.class.getResource("/eventiUtenti.jpeg"));
        Image img1 = img.getImage();
        Image img2 = img1.getScaledInstance(430, 200, Image.SCALE_SMOOTH);


        tmpImage.setIcon(new ImageIcon(img2));
        tmpImage.setBounds(0, 0, 430, 200);


        panel.setBounds(160, 0, 430, 200);
        panel.setBackground(hex2Rgb("#FFFFFF"));
        panel.add(tmpImage);
		*/

        registra.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {

                try {
                    Cittadino c = new Cittadino(account.getUserid(), account.getPwd(), account.getNome(), account.getCognome(), account.getCf(), account.getMail(), selezionato.getNome().replaceAll(" ","_"));
                    stub.registraCittadino(c);

                    new Cittadini(checkLogin, account);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                f.setVisible(false);
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                f.dispose();
            }
        });

        Dimension d = new Dimension(500,300);

        JLabel noCommenti = new JLabel("Questo centro vaccinale non ha nessun evento avverso registrato.");

        noCommenti.setBounds(110,290,500,40);
        noCommenti.setForeground(hex2Rgb("#FFFFFF"));
        noCommenti.setBackground(hex2Rgb("#FFFFFF"));
        noCommenti.setFont(new Font("Arial", Font.ITALIC, 20));
        noCommenti.setHorizontalAlignment(JTextField.CENTER);
        noCommenti.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, hex2Rgb("#1E90FF")));
        noCommenti.setVisible(false);

        JPanel eventPanel = new JPanel();
        eventPanel.setLayout(new BorderLayout());
        eventPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, hex2Rgb("#1E90FF")));
        eventPanel.setBackground(hex2Rgb("#FFFFFF"));
        eventPanel.setPreferredSize(d = new Dimension(500,300));

        JScrollPane scroll = new JScrollPane(eventPanel);
        scroll.setHorizontalScrollBarPolicy(31);
        scroll.setBackground(hex2Rgb("#FFFFFF"));
        scroll.setForeground(hex2Rgb("#1E90FF"));
        scroll.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, hex2Rgb("#1E90FF")));
        scroll.setBounds(110, 230, 520, 300);
        scroll.setVerticalScrollBarPolicy(20);
        scroll.getVerticalScrollBar().setBackground(Color.BLACK);
        scroll.getVerticalScrollBar().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, hex2Rgb("#1E90FF")));
        scroll.getVerticalScrollBar().setOpaque(true);
        scroll.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = hex2Rgb("#1E90FF");
            }
        });
        scroll.getVerticalScrollBar().setUI(new BasicScrollBarUI()
        {
            @Override
            protected void configureScrollBarColors()
            {
                this.thumbColor = hex2Rgb("#1E90FF");
            }

            @Override
            protected JButton createDecreaseButton(int orientation)
            {
                JButton decreaseButton = new JButton("/\\")
                {
                    @Override
                    public Dimension getPreferredSize()
                    {
                        return new Dimension(30,20);
                    }
                };
                decreaseButton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, hex2Rgb("#FFFFFF")));
                decreaseButton.setBackground(Color.decode("#FFFFFF"));
                decreaseButton.setForeground(Color.decode("#1E90FF"));
                return decreaseButton;
            }

            @Override
            protected JButton createIncreaseButton(int orientation)
            {
                JButton increaseButton = new JButton("\\/")
                {
                    @Override
                    public Dimension getPreferredSize()
                    {
                        return new Dimension(30, 20);
                    }
                };
                increaseButton.setBackground(Color.decode("#FFFFFF"));
                increaseButton.setForeground(Color.decode("#1E90FF"));
                increaseButton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, hex2Rgb("#FFFFFF")));
                return increaseButton;
            }
        });

        int y = 0;
        int h = 0;

		if(eventi.size()!=0) {
            if(eventi.get(eventi.size()-1).getTesto().length() < 62)
            {
                y = 70;
                h = 70;
            }
            else if(eventi.get(eventi.size()-1).getTesto().length() < 125)
            {
                y = 90;
                h = 90;
            }
            else if(eventi.get(eventi.size()-1).getTesto().length() <195)
            {
                y = 110;
                h = 110;
            }
            else if(eventi.get(eventi.size()-1).getTesto().length() < 300)
            {
                y = 130;
                h = 130;
            }


            for(int i=0; i<eventi.size(); i++)
            {

                JPanel evento = new JPanel();
                evento.setBackground(hex2Rgb("#FFFFFF"));
                evento.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, hex2Rgb("#1E90FF")));


                JLabel eventoAvv = new JLabel("              Evento:  " + eventi.get(i).getEvento() + "       Severita':  " + eventi.get(i).getSeverita() + "                 ");

                eventoAvv.setForeground(hex2Rgb("#1E90FF"));
                eventoAvv.setBackground(hex2Rgb("#FFFFFF"));
                eventoAvv.setFont(new Font("Arial", Font.ITALIC, 20));
                eventoAvv.setBounds(0, 0, 500, 20);

                JTextArea testo = new JTextArea();
                testo.setFocusable(false);

                if(eventi.get(i).getTesto().length() < 62)
                {
                    testo.setText("  " + eventi.get(i).getTesto()+"        ");
                    evento.setBounds(0, y, 520, 70);
                    eventPanel.setPreferredSize(d = new Dimension(500,h));
                    y += 70;
                    h += 70;
                }
                else if(eventi.get(i).getTesto().length() < 125)
                {
                    testo.setText("  " + eventi.get(i).getTesto().substring(0, 62) + " \n  " + eventi.get(i).getTesto().substring(62));
                    evento.setBounds(0, y, 520, 90);
                    eventPanel.setPreferredSize(d = new Dimension(500,h));
                    y += 90;
                    h += 90;
                }
                else if(eventi.get(i).getTesto().length() <195)
                {
                    testo.setText("  " + eventi.get(i).getTesto().substring(0, 62) + " \n  " + eventi.get(i).getTesto().substring(62,125) + " \n  " + eventi.get(i).getTesto().substring(125));
                    evento.setBounds(0, y, 520, 110);
                    eventPanel.setPreferredSize(d = new Dimension(500,h));
                    y += 110;
                    h += 110;
                }
                else if(eventi.get(i).getTesto().length() < 300)
                {
                    testo.setText("  " + eventi.get(i).getTesto().substring(0, 62) + " \n  " + eventi.get(i).getTesto().substring(62,125) + " \n  " + eventi.get(i).getTesto().substring(125, 190) + " \n  " + eventi.get(i).getTesto().substring(190));
                    evento.setBounds(0, y, 520, 130);
                    eventPanel.setPreferredSize(d = new Dimension(500,h));
                    y += 130;
                    h += 130;
                }
                testo.setForeground(hex2Rgb("#1E90FF"));
                testo.setBackground(hex2Rgb("#FFFFFF"));
                testo.setFont(new Font("Arial", Font.ITALIC, 16));

                evento.add(eventoAvv);
                evento.add(testo);

                eventPanel.add(evento);
			}
		}
		else
		{
		    scroll.setBackground(hex2Rgb("#FFFFFF"));
			scroll.setVisible(false);
			noCommenti.setVisible(true);
		}

        JLabel nomeCentro = new JLabel("Nome: " + selezionato.getNome());
        nomeCentro.setBounds(110, 50, 500, 30);
        nomeCentro.setBackground(hex2Rgb("#FFFFFF"));
        nomeCentro.setForeground(hex2Rgb("#1E90FF"));
        nomeCentro.setFont(new Font("Arial", Font.ITALIC, 25));

        JLabel indirizzoCentro = new JLabel("Indirizzo: " + selezionato.getIndirizzo());
        indirizzoCentro.setBounds(110, 100, 500, 30);
        indirizzoCentro.setBackground(hex2Rgb("#FFFFFF"));
        indirizzoCentro.setForeground(hex2Rgb("#1E90FF"));
        indirizzoCentro.setFont(new Font("Arial", Font.ITALIC, 25));

        JLabel tipologiaCentro = new JLabel("Tipologia: " + selezionato.getTipologia());
        tipologiaCentro.setBounds(110, 150, 500, 30);
        tipologiaCentro.setBackground(hex2Rgb("#FFFFFF"));
        tipologiaCentro.setForeground(hex2Rgb("#1E90FF"));
        tipologiaCentro.setFont(new Font("Arial", Font.ITALIC, 25));



        f.getContentPane().setBackground(hex2Rgb("#FFFFFF"));
        if(checkLogin)
            f.setBounds(560,100,750, 730);
        else
            f.setBounds(560,100,750, 630);
        f.setLayout(null);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setResizable(false);  //lock size finestra
        f.setVisible(true);

        registra.setBounds(250,600,250,50);
        registra.setBackground(hex2Rgb("#FFFFFF"));
        registra.setForeground(hex2Rgb("#1E90FF"));
        registra.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, hex2Rgb("#FFFFFF")));
        registra.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, hex2Rgb("#1E90FF")));
        registra.setFont(new Font("Comic Sans",Font.ITALIC + Font.BOLD,16));

        Image imageBack = ImageIO.read(Objects.requireNonNull(InfoCentro.class.getResource("/indietro.jpeg")));
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
                    new Homepage(checkLogin, account, checkR);
                } catch (IOException | NotBoundException | SQLException ex) {
                    ex.printStackTrace();
                }
                f.setVisible(false);
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                f.dispose();
            }
        });

        f.add(noCommenti);
        f.add(scroll);
        f.add(nomeCentro);
        f.add(indirizzoCentro);
        f.add(tipologiaCentro);
        if(checkLogin)
            f.add(registra);
        f.add(panel);
        f.add(indietro);
        ImageIcon img = new ImageIcon(Objects.requireNonNull(InfoCentro.class.getResource("/logo.jpg")));
        Image img1 = img.getImage();
        Image img2 = img1.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        f.setIconImage(img2);
    }

    public static void main(String[] args) throws IOException, NotBoundException, SQLException {

        Registry registro = LocateRegistry.getRegistry("192.168.1.111", 1099);
        stub = (common.ClientCV) registro.lookup("SERVERCV");

        new InfoCentro(new CentroVaccinale("1", "2", "3"),true, null, false);

    }

}
