package cittadini;

import centrivaccinali.RegistraCentri;
import common.Cittadino;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.sql.SQLException;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.*;

public class CercaCentro {

    private static final String DEAFULT_RICERCA = "Seleziona il tipo di ricerca";
    private static final String PRIMA_RICERCA = "Per nome";
    private static final String SECONDA_RICERCA = "Per comune e tipologia";

    private static final String DEFAULT_TIPOLOGIA = "Seleziona la tipologia del centro";
    private static final String PRIMA_TIPOLOGIA = "Ospedaliero";
    private static final String SECONDA_TIPOLOGIA = "Aziendale";
    private static final String TERZA_TIPOLOGIA = "Hub";

    JButton indietro = new JButton();

    public static Color hex2Rgb(String colorStr) //conversione esadecimale in rgb per sfondo frame
    {
        return new Color(Integer.valueOf( colorStr.substring( 1, 3 ), 16 ), Integer.valueOf( colorStr.substring( 3, 5 ), 16 ), Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }

    public CercaCentro(boolean checkLogin, Cittadino account, boolean checkR) throws IOException {

        System.out.println("cerca "+checkLogin);

        JFrame f = new JFrame("Cerca centro");

        JButton ricerca = new JButton("Ricerca centro");

        JLabel nomeL = new JLabel("Nome:");
        JTextField nomeTF = new JTextField();

        JLabel comuneL = new JLabel("Comune:");
        JTextField comuneTF = new JTextField();

        JComboBox<String> tipologiaCentro = new JComboBox<String>();

        tipologiaCentro.setModel(new DefaultComboBoxModel<String>() {
            private static final long serialVersionUID = 1L;
            boolean selectionAllowed = true;

            @Override
            public void setSelectedItem(Object anObject)
            {
                if (!DEFAULT_TIPOLOGIA.equals(anObject)) {
                    super.setSelectedItem(anObject);
                } else if (selectionAllowed) {
                    // Allow this just once
                    selectionAllowed = false;
                    super.setSelectedItem(anObject);
                }
            }
        });

        tipologiaCentro.addItem(DEFAULT_TIPOLOGIA);
        tipologiaCentro.addItem(PRIMA_TIPOLOGIA);
        tipologiaCentro.addItem(SECONDA_TIPOLOGIA);
        tipologiaCentro.addItem(TERZA_TIPOLOGIA);

        JButton cerca = new JButton("Applica ricerca");

        JComboBox<String> tipoRicerca = new JComboBox<String>();

        tipoRicerca.setModel(new DefaultComboBoxModel<String>() {
            private static final long serialVersionUID = 1L;
            boolean selectionAllowed = true;

            @Override
            public void setSelectedItem(Object anObject)
            {
                if (!DEAFULT_RICERCA.equals(anObject)) {
                    super.setSelectedItem(anObject);
                } else if (selectionAllowed) {
                    // Allow this just once
                    selectionAllowed = false;
                    super.setSelectedItem(anObject);
                }
            }
        });

        tipoRicerca.addItem(DEAFULT_RICERCA);
        tipoRicerca.addItem(PRIMA_RICERCA);
        tipoRicerca.addItem(SECONDA_RICERCA);

        tipoRicerca.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sel = (String) tipoRicerca.getSelectedItem();

                switch(sel)
                {
                    case "Per nome":
                        nomeL.setVisible(true);
                        nomeTF.setVisible(true);

                        comuneL.setVisible(false);
                        comuneTF.setVisible(false);

                        tipologiaCentro.setVisible(false);

                        break;
                    case "Per comune e tipologia":
                        nomeL.setVisible(false);
                        nomeTF.setVisible(false);

                        comuneL.setVisible(true);
                        comuneTF.setVisible(true);

                        tipologiaCentro.setVisible(true);

                        break;
                    default:
                        break;
                }

            }
        });

        ricerca.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!tipoRicerca.getSelectedItem().toString().equals(DEAFULT_RICERCA)) {
                    try {
                        if (tipoRicerca.getSelectedItem().toString().equals(PRIMA_RICERCA))
                            if(CheckNome(nomeTF.getText())) {
                                new Homepage(true, account, checkR, nomeTF.getText());
                                f.setVisible(false);
                                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                                f.dispose();
                            }
                            else {
                                JOptionPane.showMessageDialog(f, "Inserire un nome da ricercare", "Errore ricerca", JOptionPane.ERROR_MESSAGE);
                            }
                        else if (tipoRicerca.getSelectedItem().toString().equals(SECONDA_RICERCA))
                            if(!tipologiaCentro.getSelectedItem().toString().equals(DEFAULT_TIPOLOGIA))
                                if(CheckComune(comuneTF.getText())) {
                                    new Homepage(true, account, checkR, comuneTF.getText(), tipologiaCentro.getSelectedItem().toString());
                                    f.setVisible(false);
                                    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                                    f.dispose();
                                }
                                    else
                                    JOptionPane.showMessageDialog(f, "Inserire un comune da ricercare", "Errore ricerca", JOptionPane.ERROR_MESSAGE);
                            else
                                JOptionPane.showMessageDialog(f, "Selezionare una tipologia da ricercare", "Errore ricerca", JOptionPane.ERROR_MESSAGE);

                    } catch (IOException | NotBoundException | SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }


                }
                else
                    JOptionPane.showMessageDialog(f, "Selezionare un tipo di ricerca", "Errore selezione ricerca", JOptionPane.ERROR_MESSAGE);
            }
        });


        f.getContentPane().setBackground(hex2Rgb("#FFFFFF"));
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setResizable(false);  //lock size finestra
        f.setBounds(660, 50, 600, 500);
        ImageIcon img = new ImageIcon(Objects.requireNonNull(CercaCentro.class.getResource("/logo.jpg")));
        Image img1 = img.getImage();
        Image img2 = img1.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        f.setIconImage(img2);

        tipoRicerca.setBounds(175,50,250,25);
        tipoRicerca.setForeground(hex2Rgb("#1E90FF"));
        tipoRicerca.setBackground(hex2Rgb("#FFFFFF"));
        tipoRicerca.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        tipoRicerca.setFont(new Font("Comic Sans",Font.ITALIC,15));


        nomeL.setBounds(80,150,100,25);
        nomeL.setForeground(hex2Rgb("#1E90FF"));
        nomeL.setBackground(hex2Rgb("#FFFFFF"));
        nomeL.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        nomeL.setFont(new Font("Comic Sans",Font.ITALIC,15));
        nomeL.setVisible(false);

        nomeTF.setBounds(200,150,300,25);
        nomeTF.setForeground(hex2Rgb("#1E90FF"));
        nomeTF.setBackground(hex2Rgb("#FFFFFF"));
        nomeTF.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, hex2Rgb("#1E90FF")));
        nomeTF.setFont(new Font("Comic Sans",Font.ITALIC,20));
        nomeTF.setHorizontalAlignment(JTextField.CENTER);
        nomeTF.setCaretColor(hex2Rgb("#1E90FF"));
        nomeTF.setFocusTraversalKeysEnabled(false);
        nomeTF.setVisible(false);


        comuneL.setBounds(80,150,100,25);
        comuneL.setForeground(hex2Rgb("#1E90FF"));
        comuneL.setBackground(hex2Rgb("#FFFFFF"));
        comuneL.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        comuneL.setFont(new Font("Comic Sans",Font.ITALIC,15));
        comuneL.setVisible(false);

        comuneTF.setBounds(200,150,300,25);
        comuneTF.setForeground(hex2Rgb("#1E90FF"));
        comuneTF.setBackground(hex2Rgb("#FFFFFF"));
        comuneTF.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, hex2Rgb("#1E90FF")));
        comuneTF.setFont(new Font("Comic Sans",Font.ITALIC,20));
        comuneTF.setHorizontalAlignment(JTextField.CENTER);
        comuneTF.setCaretColor(hex2Rgb("#1E90FF"));
        comuneTF.setFocusTraversalKeysEnabled(false);
        comuneTF.setVisible(false);


        tipologiaCentro.setBounds(175,250,250,25);
        tipologiaCentro.setForeground(hex2Rgb("#1E90FF"));
        tipologiaCentro.setBackground(hex2Rgb("#FFFFFF"));
        tipologiaCentro.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        tipologiaCentro.setFont(new Font("Comic Sans",Font.ITALIC,15));
        tipologiaCentro.setVisible(false);


        ricerca.setBounds(175,350,250,50);
        ricerca.setBackground(hex2Rgb("#FFFFFF"));
        ricerca.setForeground(hex2Rgb("#1E90FF"));
        ricerca.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, hex2Rgb("#FFFFFF")));
        ricerca.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, hex2Rgb("#1E90FF")));
        ricerca.setFont(new Font("Comic Sans",Font.ITALIC + Font.BOLD,16));

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
                    new Homepage(checkLogin, account, checkR);
                } catch (IOException | NotBoundException ex) {
                    ex.printStackTrace();
                }
                f.setVisible(false);
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                f.dispose();
            }
        });

        f.add(tipologiaCentro);
        f.add(nomeL);
        f.add(nomeTF);
        f.add(comuneL);
        f.add(comuneTF);
        f.add(tipoRicerca);
        f.add(ricerca);
        f.add(indietro);
    }

    private boolean CheckNome(String text) {
        if(text.length() > 0)
            return true;
        else
            return false;
    }

    private boolean CheckComune(String text) {
        if(text.length() > 0)
            return true;
        else
            return false;
    }


    public static void main(String[] args) throws IOException {
        new CercaCentro(false, null, false);
    }

}
