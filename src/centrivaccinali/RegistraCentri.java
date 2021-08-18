package centrivaccinali;

import java.awt.Color;

import javax.swing.JFrame;

public class RegistraCentri {

    public static Color hex2Rgb(String colorStr)
    {
        return new Color(Integer.valueOf( colorStr.substring( 1, 3 ), 16 ), Integer.valueOf( colorStr.substring( 3, 5 ), 16 ), Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }

    public RegistraCentri() {

        JFrame f = new JFrame("Registrazione Nuovi Centri Vaccinali");

        f.getContentPane().setBackground(hex2Rgb("#F0FFFF"));
        f.setBounds(350,150,800,500);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setResizable(false);
    }
}