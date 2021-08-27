package cittadini;

import common.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.plaf.basic.*;

public class InfoCentro extends UnicastRemoteObject {

    private static ClientCV stub;

    public static Color hex2Rgb(String colorStr) //conversione esadecimale in rgb per sfondo frame
    {
        return new Color(Integer.valueOf( colorStr.substring( 1, 3 ), 16 ), Integer.valueOf( colorStr.substring( 3, 5 ), 16 ), Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }

    public InfoCentro(String valueAt, boolean check) throws RemoteException {
        super();

        CentroVaccinale selezionato = new CentroVaccinale(); //Prendere informazioni sul centro selezionato

        ArrayList<EventoAvverso> eventi = null; //Prendere lista eventi avversi registrati per il centro selezionato

        JPanel panel = new JPanel();
        JLabel tmpImage = new JLabel();

        JButton registra = new JButton("Registrati al centro");

        JFrame info = new JFrame("Finestra Informazioni");

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

                    CentroVaccinale cv = new CentroVaccinale("Centro_2", "Aziendale","via Manzoni, 34, Pescara");
                    stub.registraCentroVaccinale(cv);
                    Cittadino c = new Cittadino("abcd", "1234", "Amilcare", "Rossi", "amlrsi0011", "a.b@gb.it", "Centro_2");
                    stub.registraCittadino(c);
                    //registraCittadino();
                    new Cittadini(true);
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                info.setVisible(false);
                info.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                info.dispose();
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
		/*
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

			JPanel review = new JPanel();
			review.setBackground(hex2Rgb("#FFFFFF"));
			review.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, hex2Rgb("#1E90FF")));


			JLabel eventoAvv = new JLabel("              Evento:  " + eventi.get(i).getEvento() + "       Severita':  " + eventi.get(i).getSeverita() + "                 ");

			eventoAvv.setForeground(hex2Rgb("#1E90FF"));
			eventoAvv.setBackground(hex2Rgb("#FFFFFF"));
			eventoAvv.setFont(new Font("Arial", Font.ITALIC, 20));
			eventoAvv.setBounds(0, 0, 500, 20);

		    JTextArea testo = new JTextArea();
		    testo.setFocusable(false);

		    if(eventi.get(i).getTesto().length() < 62)

		    {
			    testo.setText("  " + eventi.get(i).getTesto());
			    review.setBounds(0, y, 520, 70);
			    eventPanel.setPreferredSize(d = new Dimension(500,h));
			    y += 70;
			    h += 70;
			}

		    else if(eventi.get(i).getTesto().length() < 125)

		    {
			    testo.setText("  " + eventi.get(i).getTesto().substring(0, 62) + " \n  " + eventi.get(i).getTesto().substring(62));
			    review.setBounds(0, y, 520, 90);
			    eventPanel.setPreferredSize(d = new Dimension(500,h));
			    y += 90;
			    h += 90;
		    }

		    else if(eventi.get(i).getTesto().length() <195)

		    {
		    	testo.setText("  " + eventi.get(i).getTesto().substring(0, 62) + " \n  " + eventi.get(i).getTesto().substring(62,125) + " \n  " + eventi.get(i).getTesto().substring(125));
			    review.setBounds(0, y, 520, 110);
			    eventPanel.setPreferredSize(d = new Dimension(500,h));
			    y += 110;
			    h += 110;
		    }

		    else if(eventi.get(i).getTesto().length() < 300)

		    {
		    	testo.setText("  " + eventi.get(i).getTesto().substring(0, 62) + " \n  " + eventi.get(i).getTesto().substring(62,125) + " \n  " + eventi.get(i).getTesto().substring(125, 190) + " \n  " + eventi.get(i).getTesto().substring(190));
			    review.setBounds(0, y, 520, 130);
			    eventPanel.setPreferredSize(d = new Dimension(500,h));
			    y += 130;
			    h += 130;
		    }

		    testo.setForeground(hex2Rgb("#FFFFFF"));
		    testo.setBackground(hex2Rgb("#FFFFFF"));
		    testo.setFont(new Font("Arial", Font.ITALIC, 16));

		    review.add(eventoAvv);
		    review.add(testo);

		    eventPanel.add(review);
			}

		}
		else
		{
			scroll.setVisible(false);
			noCommenti.setVisible(true);
		}
		*/
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



        info.getContentPane().setBackground(hex2Rgb("#FFFFFF"));
        if(!check)
            info.setBounds(560,100,750, 630);
        else
            info.setBounds(560,100,750, 730);
        info.setLayout(null);
        info.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        info.setResizable(false);  //lock size finestra
        info.setVisible(true);

        registra.setBounds(250,600,250,50);
        registra.setBackground(hex2Rgb("#FFFFFF"));
        registra.setForeground(hex2Rgb("#1E90FF"));
        registra.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, hex2Rgb("#FFFFFF")));
        registra.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, hex2Rgb("#1E90FF")));
        registra.setFont(new Font("Comic Sans",Font.ITALIC + Font.BOLD,16));

        info.add(noCommenti);
        info.add(scroll);
        info.add(nomeCentro);
        info.add(indirizzoCentro);
        info.add(tipologiaCentro);
        if(check)
            info.add(registra);
        info.add(panel);

    }

    public static void main(String[] args) throws RemoteException, NotBoundException {

        Registry registro = LocateRegistry.getRegistry("localhost", 1099);
        stub = (common.ClientCV) registro.lookup("SERVERCV");

        new InfoCentro("ciao",true);

    }

}
