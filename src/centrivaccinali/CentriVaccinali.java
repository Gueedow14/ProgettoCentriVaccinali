package centrivaccinali;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class CentriVaccinali {

    public static Color hex2Rgb(String colorStr) //conversione esadecimale in rgb per sfondo frame
    {
        return new Color(Integer.valueOf( colorStr.substring( 1, 3 ), 16 ), Integer.valueOf( colorStr.substring( 3, 5 ), 16 ), Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }

    JFrame f = new JFrame("Centri Vaccinali");
    JLabel titolo = new JLabel("CENTRI VACCINALI");
    JButton registraCentro = new JButton ("Registra Centro Vaccinale");
    JButton registraCittadino = new JButton ("Registra Cittadino");

    public CentriVaccinali() {

        f.getContentPane().setBackground(hex2Rgb("#7FFFD4"));
        f.setBounds(350,150,800,500);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setResizable(false);

        registraCentro.setBackground(Color.decode("#F0F8FF"));
        registraCentro.setForeground(Color.decode("#000000"));
        registraCentro.setBorder(BorderFactory.createMatteBorder(5, 0, 5, 0, hex2Rgb("#1E90FF")));
        registraCentro.setBounds(300,175,200,75);
        registraCentro.setFont(new Font("Arial", Font.ITALIC, 15));
        registraCentro.setFocusable(false);

        registraCentro.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                f.setVisible(false);
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                f.dispose();
                try {
                    RegistraCentri a = new RegistraCentri();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        registraCittadino.setBackground(Color.decode("#F0F8FF"));
        registraCittadino.setForeground(Color.decode("#000000"));
        registraCittadino.setBorder(BorderFactory.createMatteBorder(5, 0, 5, 0, hex2Rgb("#1E90FF")));
        registraCittadino.setBounds(300,275,200,75);
        registraCittadino.setFont(new Font("Arial", Font.ITALIC, 15));
        registraCittadino.setFocusable(false);

        registraCittadino.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                f.setVisible(false);
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                f.dispose();
                try {
                    RegistraCittadini b = new RegistraCittadini();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        f.add(registraCentro);
        f.add(registraCittadino);

    }


    public static void main (String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {

            @Override
            public void run()
            {
                new CentriVaccinali();
            }
        });
    }


}
