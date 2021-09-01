package cittadini;

import centrivaccinali.RegistraCentri;
import common.Cittadino;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static java.lang.Integer.parseInt;

public class PrenotazioneVaccino {

    JFrame f = new JFrame("Prenotazione Vaccino");

    JButton indietro = new JButton();

    JLabel titolo = new JLabel("Prenotazione Vaccino");

    static JLabel errorData = new JLabel("*");
    static JLabel errorOrario = new JLabel("*");

    JLabel dataL = new JLabel("Data:", SwingConstants.CENTER);
    JTextField dataTF = new JTextField("");

    JLabel orarioL = new JLabel("Orario:", SwingConstants.CENTER);
    JTextField orarioTF = new JTextField("");

    JButton b = new JButton("Prenota");

    public static Color hex2Rgb(String colorStr)
    {
        return new Color(Integer.valueOf( colorStr.substring( 1, 3 ), 16 ), Integer.valueOf( colorStr.substring( 3, 5 ), 16 ), Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }

    public PrenotazioneVaccino(boolean checkLogin, Cittadino account) throws IOException {

        int sizeL = 17;
        int sizeTF = 17;
        System.out.println(checkLogin);


        errorData.setBounds(90,155,25,25);
        errorData.setForeground(Color.RED);
        errorData.setBackground(hex2Rgb("#FF0000"));
        errorData.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        errorData.setFont(new Font("Comic Sans",Font.BOLD,25));
        errorData.setVisible(false);

        errorOrario.setBounds(85,205,25,25);
        errorOrario.setForeground(Color.RED);
        errorOrario.setBackground(hex2Rgb("#FF0000"));
        errorOrario.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        errorOrario.setFont(new Font("Comic Sans",Font.BOLD,25));
        errorOrario.setVisible(false);



        titolo.setBounds(122 ,30,500,50);
        titolo.setForeground(hex2Rgb("#1E90FF"));
        titolo.setBackground(hex2Rgb("#FFFFFF"));
        titolo.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        titolo.setFont(new Font("Comic Sans",Font.BOLD,25));


        dataL.setBounds(70,150,110,25);
        dataL.setForeground(hex2Rgb("#1E90FF"));
        dataL.setBackground(hex2Rgb("#FFFFFF"));
        dataL.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        dataL.setFont(new Font("Comic Sans",Font.ITALIC,sizeL));

        dataTF.setBounds(200,150,300,25);
        dataTF.setForeground(hex2Rgb("#1E90FF"));
        dataTF.setBackground(hex2Rgb("#FFFFFF"));
        dataTF.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, hex2Rgb("#1E90FF")));
        dataTF.setFont(new Font("Comic Sans",Font.ITALIC,sizeTF));
        dataTF.setHorizontalAlignment(JTextField.CENTER);
        dataTF.setCaretColor(hex2Rgb("#1E90FF"));
        dataTF.setFocusTraversalKeysEnabled(false);
        String sample = java.time.LocalDate.now().toString();
        String today = sample.substring(8,10) + "/" + sample.substring(5,7) + "/" + sample.substring(0,4);
        dataTF.setText(today);




        orarioL.setBounds(70,200,110,25);
        orarioL.setForeground(hex2Rgb("#1E90FF"));
        orarioL.setBackground(hex2Rgb("#FFFFFF"));
        orarioL.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        orarioL.setFont(new Font("Comic Sans",Font.ITALIC,sizeL));


        orarioTF.setBounds(200,200,300,25);
        orarioTF.setForeground(hex2Rgb("#1E90FF"));
        orarioTF.setBackground(hex2Rgb("#FFFFFF"));
        orarioTF.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, hex2Rgb("#1E90FF")));
        orarioTF.setFont(new Font("Comic Sans",Font.ITALIC,sizeTF));
        orarioTF.setHorizontalAlignment(JTextField.CENTER);
        orarioTF.setCaretColor(hex2Rgb("#1E90FF"));
        orarioTF.setFocusTraversalKeysEnabled(false);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime now = LocalDateTime.now();
        orarioTF.setText(dtf.format(now));



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

        b.setBounds(200,325,200,40);
        b.setBackground(hex2Rgb("#FFFFFF"));
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
                    //Prenota vaccino

                    try {
                        new Cittadini(checkLogin, account);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    f.setVisible(false);
                    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    f.dispose();
                }
            }
        });

        f.add(titolo);
        f.add(dataL);
        f.add(dataTF);
        f.add(errorData);
        f.add(orarioL);
        f.add(orarioTF);
        f.add(errorOrario);
        f.add(b);
        f.add(indietro);

        f.getContentPane().setBackground(hex2Rgb("#FFFFFF"));
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setResizable(false);  //lock size finestra
        f.setBounds(660, 100, 600, 450);
        ImageIcon img = new ImageIcon(Objects.requireNonNull(PrenotazioneVaccino.class.getResource("/logo.jpg")));
        Image img1 = img.getImage();
        Image img2 = img1.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        f.setIconImage(img2);
    }


    public static boolean checkData(String data)
    {
        if(!data.equals("") && data.length() == 10){

            String giorno = data.substring(0,2);
            String mese = data.substring(3,5);
            String anno = data.substring(6,10);

            if(parseInt(giorno) > 0  && parseInt(giorno) < 32 &&  parseInt(mese) > 0 &&  parseInt(mese) < 13  && parseInt(anno) > 2020){
                errorData.setVisible(false);
                return true;}

            else {
                errorData.setVisible(true);
                return false;
            }
        }
        errorData.setVisible(true);
        return false;
    }

    public static boolean checkOrario(String orario)
    {
        if(!orario.equals("") && orario.length() == 5){

            String ore = orario.substring(0,2);
            String minuti = orario.substring(3,5);

            if(parseInt(ore) >= 0  && parseInt(ore) < 24 &&  parseInt(minuti) >= 0 &&  parseInt(minuti) < 60){
                errorOrario.setVisible(false);
                return true;}

            else {
                errorOrario.setVisible(true);
                return false;
            }
        }
        errorOrario.setVisible(true);
        return false;
    }

    private boolean controlloCampi () {
        return checkOrario(orarioTF.getText()) & checkData(dataTF.getText());
    }

    public static void main (String[]args) throws IOException {
        new PrenotazioneVaccino(false,null);
    }
}
