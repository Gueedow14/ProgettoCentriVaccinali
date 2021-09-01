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

public class ListaPrenotazioni {

    JFrame f = new JFrame("Prenotazioni");

    JButton indietro = new JButton();

    JLabel titolo = new JLabel("Prenotazioni effettuate");

    static JLabel errorData = new JLabel("*");
    static JLabel errorOrario = new JLabel("*");

    JLabel pren1 = new JLabel("Prenotazione 1:");
    JLabel data1L = new JLabel("Data:", SwingConstants.CENTER);

    JLabel orario1L = new JLabel("Orario:", SwingConstants.CENTER);

    JLabel pren2 = new JLabel("Prenotazione 2:");
    JLabel data2L = new JLabel("Data:", SwingConstants.CENTER);

    JLabel orario2L = new JLabel("Orario:", SwingConstants.CENTER);


    public static Color hex2Rgb(String colorStr)
    {
        return new Color(Integer.valueOf( colorStr.substring( 1, 3 ), 16 ), Integer.valueOf( colorStr.substring( 3, 5 ), 16 ), Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }

    public ListaPrenotazioni(boolean checkLogin, Cittadino account) throws IOException {

        int sizeL = 17;
        int sizeTF = 17;
        System.out.println(checkLogin);


        titolo.setBounds(122 ,30,500,50);
        titolo.setForeground(hex2Rgb("#1E90FF"));
        titolo.setBackground(hex2Rgb("#FFFFFF"));
        titolo.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        titolo.setFont(new Font("Comic Sans",Font.BOLD,25));

        pren1.setBounds(70,140,150,25);
        pren1.setForeground(hex2Rgb("#1E90FF"));
        pren1.setBackground(hex2Rgb("#FFFFFF"));
        pren1.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        pren1.setFont(new Font("Comic Sans",Font.ITALIC,sizeL));

        data1L.setBounds(130,140,200,25);
        data1L.setForeground(hex2Rgb("#1E90FF"));
        data1L.setBackground(hex2Rgb("#FFFFFF"));
        data1L.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        data1L.setFont(new Font("Comic Sans",Font.ITALIC,sizeL));

        orario1L.setBounds(132,170,200,25);
        orario1L.setForeground(hex2Rgb("#1E90FF"));
        orario1L.setBackground(hex2Rgb("#FFFFFF"));
        orario1L.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        orario1L.setFont(new Font("Comic Sans",Font.ITALIC,sizeL));

        pren2.setBounds(70,240,150,25);
        pren2.setForeground(hex2Rgb("#1E90FF"));
        pren2.setBackground(hex2Rgb("#FFFFFF"));
        pren2.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        pren2.setFont(new Font("Comic Sans",Font.ITALIC,sizeL));

        data2L.setBounds(130,240,200,25);
        data2L.setForeground(hex2Rgb("#1E90FF"));
        data2L.setBackground(hex2Rgb("#FFFFFF"));
        data2L.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        data2L.setFont(new Font("Comic Sans",Font.ITALIC,sizeL));

        orario2L.setBounds(132,270,200,25);
        orario2L.setForeground(hex2Rgb("#1E90FF"));
        orario2L.setBackground(hex2Rgb("#FFFFFF"));
        orario2L.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, hex2Rgb("#1E90FF")));
        orario2L.setFont(new Font("Comic Sans",Font.ITALIC,sizeL));

        /*
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime now = LocalDateTime.now();
        orario1TF.setText(dtf.format(now));
        */


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



        f.add(titolo);
        f.add(pren1);
        f.add(data1L);
        f.add(orario1L);
        f.add(pren2);
        f.add(data2L);
        f.add(orario2L);
        f.add(indietro);

        f.getContentPane().setBackground(hex2Rgb("#FFFFFF"));
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setResizable(false);  //lock size finestra
        f.setBounds(660, 100, 550, 400);
        ImageIcon img = new ImageIcon(Objects.requireNonNull(PrenotazioneVaccino.class.getResource("/logo.jpg")));
        Image img1 = img.getImage();
        Image img2 = img1.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        f.setIconImage(img2);
    }


    public static void main (String[]args) throws IOException {
        new ListaPrenotazioni(false,null);
    }
}
