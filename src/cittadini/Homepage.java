package cittadini;

import centrivaccinali.CentroVaccinale;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.JTableHeader;


public class Homepage {

    static JFrame f = new JFrame("Finestra Account");
    JLabel nomeL = new JLabel("Nome Cognome", SwingConstants.CENTER);
    List<centrivaccinali.CentroVaccinale> lista = new ArrayList<centrivaccinali.CentroVaccinale>();;

    public static Color hex2Rgb(String colorStr) //conversione esadecimale in rgb per sfondo frame
    {
        return new Color(Integer.valueOf( colorStr.substring( 1, 3 ), 16 ), Integer.valueOf( colorStr.substring( 3, 5 ), 16 ), Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }


    public static Object[][] PopolaTabella(List<centrivaccinali.CentroVaccinale> l) throws IOException
    {
        String[][] matrix = new String[5][3];

        int i = 0;
        for(centrivaccinali.CentroVaccinale c1 : l)
        {
            matrix[i][0] = c1.nome;
            matrix[i][1] = c1.tipologia;
            matrix[i][2] = c1.indirizzo;
            i++;
        }

        return matrix;
    }


    public Homepage(boolean check) throws IOException
    {
        //JLabel l1 = new JLabel("Accesso effettuato correttamente!");


        JTextField tmpFocus = new JTextField();


        JButton exit = new JButton("Esci");
        JButton ricerca = new JButton();
        JButton info = new JButton("Visualizza Informazioni Centro");


        for(int i = 0; i < 5; i++)
        {
            lista.add(new CentroVaccinale(i,check));
        }

        String[] colonneTab = {"Nome Centro Vaccinale", "Tipologia", "Indirizzo"};
        Object[][] data = PopolaTabella(lista);


        JTable tab = new JTable(data,colonneTab);
        JTableHeader header = tab.getTableHeader();
        header.setEnabled(false);

        JPanel panel = new JPanel();

        JScrollPane scrollPane;
        tab.getColumnModel().getColumn(0).setMinWidth(350);
        tab.getColumnModel().getColumn(0).setMaxWidth(350);
        tab.getColumnModel().getColumn(1).setMinWidth(100);
        tab.getColumnModel().getColumn(1).setMaxWidth(100);
        tab.getColumnModel().getColumn(2).setMinWidth(400);
        tab.getColumnModel().getColumn(2).setMaxWidth(400);
        tab.setRowHeight(30);
        tab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tab.setForeground(hex2Rgb("#1E90FF"));

        //l1.setFont(new Font("Comic Sans",Font.ITALIC,20));
        //l1.setBounds(15,5,500,30);
        //l1.setForeground(hex2Rgb("#1E90FF"));


        tmpFocus.setBounds(440,2,10,10);
        tmpFocus.setBackground(hex2Rgb("#FFFFFF"));
        tmpFocus.setCaretColor(hex2Rgb("#FFFFFF"));
        tmpFocus.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, hex2Rgb("#FFFFFF")));

        panel.setBackground(hex2Rgb("#FFFFFF"));
        panel.setBounds(35,80,819,300);
        panel.setLayout(new BorderLayout());
        panel.add(header, BorderLayout.NORTH);

        scrollPane = new JScrollPane(tab);
        scrollPane.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, hex2Rgb("#1E90FF")));
        scrollPane.getVerticalScrollBar().setBackground(hex2Rgb("#FFFFFF"));
        scrollPane.getVerticalScrollBar().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, hex2Rgb("#FFFFFF")));

        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI()
        {
            @Override
            protected void configureScrollBarColors()
            {
                this.thumbColor = hex2Rgb("#1E90FF");
            }
        });

        info.setBounds(100,450,250,50);
        info.setBackground(hex2Rgb("#FFFFFF"));
        info.setForeground(hex2Rgb("#1E90FF"));

        info.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, hex2Rgb("#FFFFFF")));
        info.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, hex2Rgb("#1E90FF")));
        info.setFont(new Font("Comic Sans",Font.ITALIC + Font.BOLD,16));
        panel.add(scrollPane,BorderLayout.CENTER);

        tab.setBounds(100,100,700,300);
        tab.setForeground(hex2Rgb("#1E90FF"));
        tab.setBackground(hex2Rgb("#FFFFFF"));
        tab.setGridColor(hex2Rgb("#1E90FF"));;
        tab.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, hex2Rgb("#1E90FF")), BorderFactory.createMatteBorder(0, 1, 1, 0, hex2Rgb("#FFFFFF"))));
        tab.setDragEnabled(false);

        f.getContentPane().setBackground(hex2Rgb("#FFFFFF"));
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setResizable(false);  //lock size finestra
        f.setBounds(410, 240, 900, 600);

        nomeL.setBounds(50, 50, 150, 20);
        nomeL.setForeground(hex2Rgb("#1E90FF"));

        f.add(ricerca);
        //f.add(l1);
        f.add(tmpFocus);
        f.add(info);
        f.add(exit);
        f.add(panel);
    }

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        new Homepage(false);
    }

}
