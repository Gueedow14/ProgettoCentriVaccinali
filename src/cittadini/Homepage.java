package cittadini;

import centrivaccinali.RegistraCentri;
import common.CentroVaccinale;
import common.Cittadino;
import common.ClientCV;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.JTableHeader;


/**
 * La classe Homepage contiene il codice per la creazione della schermata relativa alla visualizzazione dei centri vaccinali
 * presenti nel database
 * @author Giulio Baricci
 */

public class Homepage extends UnicastRemoteObject {

    /**
     * Oggetto che fa riferimento al server disponibile sul rmiregistry
     */
    private static ClientCV stub;

    /**
     * Frame della schermata di visualizzazione della lista di centri vaccinali
     */
    static JFrame f = new JFrame("Finestra Account");

    /**
     * Lista contenente i centri vaccinali
     */
    List<CentroVaccinale> lista = new ArrayList<CentroVaccinale>();

    /**
     * Bottone per tornare alla schermata precedente
     */
    JButton indietro = new JButton();

    public static boolean check;
    public static boolean checkReg;
    public static Cittadino c;

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
     * Il metodo PopolaTabella serve, come suggerisce il nome, a popolare la tabella della classe AccessoAccount
     * @param l lista dei centri da visualizzare
     * @return ritorna una matrice di Object che continene i valori da mostrare nella tabella
     */
    public static Object[][] PopolaTabella(List<CentroVaccinale> l)
    {
        String[][] matrix = new String[l.size()][3];

        int i = 0;
        if(l.size() != 0)
            for(CentroVaccinale c1 : l)
            {
                matrix[i][0] = c1.getNome().replaceAll("_"," ");
                matrix[i][1] = c1.getTipologia();
                String indirizzo = c1.getIndirizzo().replaceAll("§"," ");
                matrix[i][2] = indirizzo;
                i++;
            }

        return matrix;
    }

    /**
     * Il costruttore contiene il codice per la creazione e la visualizzazione della schermata contenente la lista di tutti
     * i centi vaccinali presenti nel database
     * @param checkLogin controlla se è stato effettuato un accesso
     * @param account fa riferimento al cttadino che ha effettuato l'accesso
     * @param checkR controlla se la schermata viene creata durante una registrazione
     * @throws IOException il costruttore contiene del codice che legge delle immagini quindi può genererare IOException
     * @throws NotBoundException il costruttore contiene del codice che si conntte al rmiregistry quindi può genererare NotBoundException
     * @throws SQLException il costruttore contiene del codice che riceve dati dal database quindi può genererare SQLException
     */
    public Homepage(boolean checkLogin, Cittadino account, boolean checkR) throws IOException, NotBoundException, SQLException {

        Registry registro = LocateRegistry.getRegistry("localhost", 1099);
        stub = (common.ClientCV) registro.lookup("SERVERCV");

        c = account;
        check = checkLogin;
        checkReg = checkR;
        JTextField tmpFocus = new JTextField();


        JButton exit = new JButton("Esci");
        JButton ricerca = new JButton("Ricerca centro");
        JButton info = new JButton("Visualizza Informazioni Centro");

        lista = stub.centriRegistrati();

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

        info.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e)
            {
                if(!tab.getSelectionModel().isSelectionEmpty() && !(tab.getValueAt(tab.getSelectedRow(), 0) == null))
                {
                    try {
                        new InfoCentro(new CentroVaccinale((String)tab.getValueAt(tab.getSelectedRow(), 0), (String)tab.getValueAt(tab.getSelectedRow(), 1), (String)tab.getValueAt(tab.getSelectedRow(), 2)), check, c, checkReg);
                    } catch (IOException | NotBoundException | SQLException ex) {
                        ex.printStackTrace();
                    }
                    f.setVisible(false);
                    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    f.dispose();
                }
                else
                    JOptionPane.showMessageDialog(f, "Selezionare un centro vaccinale!", "Errore selezione centro vaccinale", JOptionPane.ERROR_MESSAGE);
            }
        });

        ricerca.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                try {
                    new CercaCentro(check, c, checkReg);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                f.setVisible(false);
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                f.dispose();
            }
        });

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

        info.setBounds(550,450,250,50);
        info.setFont(new Font("Arial", Font.ITALIC, 15));
        info.setHorizontalAlignment(SwingConstants.CENTER);
        info.setBackground(Color.decode("#F0F8FF"));
        info.setForeground(Color.decode("#000000"));
        info.setBorder(BorderFactory.createMatteBorder(5, 0, 5, 0, hex2Rgb("#1E90FF")));

        ricerca.setBounds(100,450,250,50);
        ricerca.setFont(new Font("Arial", Font.ITALIC, 15));
        ricerca.setHorizontalAlignment(SwingConstants.CENTER);
        ricerca.setBackground(Color.decode("#F0F8FF"));
        ricerca.setForeground(Color.decode("#000000"));
        ricerca.setBorder(BorderFactory.createMatteBorder(5, 0, 5, 0, hex2Rgb("#1E90FF")));

        panel.add(scrollPane,BorderLayout.CENTER);

        tab.setBounds(100,100,700,300);
        tab.setForeground(hex2Rgb("#1E90FF"));
        tab.setBackground(hex2Rgb("#FFFFFF"));
        tab.setGridColor(hex2Rgb("#1E90FF"));;
        tab.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, hex2Rgb("#1E90FF")), BorderFactory.createMatteBorder(0, 1, 1, 0, hex2Rgb("#FFFFFF"))));
        tab.setDragEnabled(false);

        Image imageBack = ImageIO.read(Objects.requireNonNull(Homepage.class.getResource("/indietro.jpeg")));
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
                if(!checkReg) {
                    try {
                        new Cittadini(checkLogin, account);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                else
                {
                    try {
                        new Registrazione();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                f.setVisible(false);
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                f.dispose();
            }
        });

        f.getContentPane().setBackground(hex2Rgb("#FFFFFF"));
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setResizable(false);  //lock size finestra
        f.setBounds(410, 240, 900, 600);
        ImageIcon img = new ImageIcon(Objects.requireNonNull(Homepage.class.getResource("/logo.jpg")));
        Image img1 = img.getImage();
        Image img2 = img1.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        f.setIconImage(img2);

        f.add(ricerca);
        f.add(indietro);
        f.add(tmpFocus);
        f.add(info);
        f.add(exit);
        f.add(panel);
    }

    /**
     * Il costruttore contiene il codice per la creazione e la visualizzazione della schermata contenente la lista dei
     * centi vaccinali presenti nel database filtrati per nome dal valore contenuto in nomeC
     * @param checkLogin controlla se è stato effettuato un accesso
     * @param account fa riferimento al cttadino che ha effettuato l'accesso
     * @param checkR controlla se la schermata viene creata durante una registrazione
     * @param nomeC contiene il nome del centro da ricercare
     * @throws IOException il costruttore contiene del codice che legge delle immagini quindi può genererare IOException
     * @throws NotBoundException il costruttore contiene del codice che si conntte al rmiregistry quindi può genererare NotBoundException
     * @throws SQLException il costruttore contiene del codice che riceve dati dal database quindi può genererare SQLException
     */
    public Homepage(boolean checkLogin, Cittadino account, boolean checkR, String nomeC) throws IOException, NotBoundException, SQLException {

        Registry registro = LocateRegistry.getRegistry("localhost", 1099);
        stub = (common.ClientCV) registro.lookup("SERVERCV");


        check = checkLogin;
        checkReg = checkR;
        JTextField tmpFocus = new JTextField();


        JButton exit = new JButton("Esci");
        JButton ricerca = new JButton("Ricerca centro");
        JButton info = new JButton("Visualizza Informazioni Centro");

        lista = stub.cercaCentroVaccinale(nomeC);

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

        info.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e)
            {
                if(!tab.getSelectionModel().isSelectionEmpty())
                {
                    try {
                        new InfoCentro(new CentroVaccinale((String)tab.getValueAt(tab.getSelectedRow(), 0), (String)tab.getValueAt(tab.getSelectedRow(), 1), (String)tab.getValueAt(tab.getSelectedRow(), 2)), check, account, checkReg);
                    } catch (IOException | NotBoundException | SQLException ex) {
                        ex.printStackTrace();
                    }
                    f.setVisible(false);
                    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    f.dispose();
                }
                else
                    JOptionPane.showMessageDialog(f, "Selezionare un centro vaccinale!", "Errore selezione centro vaccinale", JOptionPane.ERROR_MESSAGE);
            }
        });

        ricerca.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                try {
                    new CercaCentro(check, account, checkReg);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                f.setVisible(false);
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                f.dispose();
            }
        });

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

        info.setBounds(550,450,250,50);
        info.setFont(new Font("Arial", Font.ITALIC, 15));
        info.setHorizontalAlignment(SwingConstants.CENTER);
        info.setBackground(Color.decode("#F0F8FF"));
        info.setForeground(Color.decode("#000000"));
        info.setBorder(BorderFactory.createMatteBorder(5, 0, 5, 0, hex2Rgb("#1E90FF")));;

        ricerca.setBounds(100,450,250,50);
        ricerca.setFont(new Font("Arial", Font.ITALIC, 15));
        ricerca.setHorizontalAlignment(SwingConstants.CENTER);
        ricerca.setBackground(Color.decode("#F0F8FF"));
        ricerca.setForeground(Color.decode("#000000"));
        ricerca.setBorder(BorderFactory.createMatteBorder(5, 0, 5, 0, hex2Rgb("#1E90FF")));

        panel.add(scrollPane,BorderLayout.CENTER);

        tab.setBounds(100,100,700,300);
        tab.setForeground(hex2Rgb("#1E90FF"));
        tab.setBackground(hex2Rgb("#FFFFFF"));
        tab.setGridColor(hex2Rgb("#1E90FF"));;
        tab.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, hex2Rgb("#1E90FF")), BorderFactory.createMatteBorder(0, 1, 1, 0, hex2Rgb("#FFFFFF"))));
        tab.setDragEnabled(false);

        Image imageBack = ImageIO.read(Objects.requireNonNull(Homepage.class.getResource("/indietro.jpeg")));
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
                if(!checkReg) {
                    try {
                        new Cittadini(checkLogin, account);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                else
                {
                    try {
                        new Registrazione();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                f.setVisible(false);
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                f.dispose();
            }
        });

        f.getContentPane().setBackground(hex2Rgb("#FFFFFF"));
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setResizable(false);  //lock size finestra
        f.setBounds(410, 240, 900, 600);
        ImageIcon img = new ImageIcon(Objects.requireNonNull(Homepage.class.getResource("/logo.jpg")));
        Image img1 = img.getImage();
        Image img2 = img1.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        f.setIconImage(img2);

        f.add(ricerca);
        f.add(indietro);
        f.add(tmpFocus);
        f.add(info);
        f.add(exit);
        f.add(panel);
    }

    /**
     * Il costruttore contiene il codice per la creazione e la visualizzazione della schermata contenente la lista dei
     * centi vaccinali presenti nel database filtrati per comune dal valore contenuto in comuneC e per tipo dal valore
     * contenuto in tipoC
     * @param checkLogin controlla se è stato effettuato un accesso
     * @param account fa riferimento al cttadino che ha effettuato l'accesso
     * @param checkR controlla se la schermata viene creata durante una registrazione
     * @param comuneC contiene il comune su cui fare la ricerca
     * @param tipoC contiene la tipologia del centro su cui fare la ricerca
     * @throws IOException il costruttore contiene del codice che legge delle immagini quindi può genererare IOException
     * @throws NotBoundException il costruttore contiene del codice che si conntte al rmiregistry quindi può genererare NotBoundException
     * @throws SQLException il costruttore contiene del codice che riceve dati dal database quindi può genererare SQLException
     */
    public Homepage(boolean checkLogin, Cittadino account, boolean checkR, String comuneC, String tipoC) throws IOException, NotBoundException, SQLException {
        Registry registro = LocateRegistry.getRegistry("localhost", 1099);
        stub = (common.ClientCV) registro.lookup("SERVERCV");

        check = checkLogin;
        checkReg = checkR;
        JTextField tmpFocus = new JTextField();


        JButton exit = new JButton("Esci");
        JButton ricerca = new JButton("Ricerca centro");
        JButton info = new JButton("Visualizza Informazioni Centro");

        lista = stub.cercaCentroVaccinale(comuneC, tipoC);

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

        info.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e)
            {
                if(!tab.getSelectionModel().isSelectionEmpty())
                {
                    try {
                        new InfoCentro(new CentroVaccinale((String)tab.getValueAt(tab.getSelectedRow(), 0), (String)tab.getValueAt(tab.getSelectedRow(), 1), (String)tab.getValueAt(tab.getSelectedRow(), 2)), check, account, checkReg);
                    } catch (IOException | NotBoundException | SQLException ex) {
                        ex.printStackTrace();
                    }
                    f.setVisible(false);
                    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    f.dispose();
                }
                else
                    JOptionPane.showMessageDialog(f, "Selezionare un centro vaccinale!", "Errore selezione centro vaccinale", JOptionPane.ERROR_MESSAGE);
            }
        });

        ricerca.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                try {
                    new CercaCentro(check, account, checkReg);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                f.setVisible(false);
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                f.dispose();
            }
        });

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

        info.setBounds(550,450,250,50);
        info.setFont(new Font("Arial", Font.ITALIC, 15));
        info.setHorizontalAlignment(SwingConstants.CENTER);
        info.setBackground(Color.decode("#F0F8FF"));
        info.setForeground(Color.decode("#000000"));
        info.setBorder(BorderFactory.createMatteBorder(5, 0, 5, 0, hex2Rgb("#1E90FF")));

        ricerca.setBounds(100,450,250,50);
        ricerca.setFont(new Font("Arial", Font.ITALIC, 15));
        ricerca.setHorizontalAlignment(SwingConstants.CENTER);
        ricerca.setBackground(Color.decode("#F0F8FF"));
        ricerca.setForeground(Color.decode("#000000"));
        ricerca.setBorder(BorderFactory.createMatteBorder(5, 0, 5, 0, hex2Rgb("#1E90FF")));

        panel.add(scrollPane,BorderLayout.CENTER);

        tab.setBounds(100,100,700,300);
        tab.setForeground(hex2Rgb("#1E90FF"));
        tab.setBackground(hex2Rgb("#FFFFFF"));
        tab.setGridColor(hex2Rgb("#1E90FF"));;
        tab.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, hex2Rgb("#1E90FF")), BorderFactory.createMatteBorder(0, 1, 1, 0, hex2Rgb("#FFFFFF"))));
        tab.setDragEnabled(false);

        Image imageBack = ImageIO.read(Objects.requireNonNull(Homepage.class.getResource("/indietro.jpeg")));
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
                if(!checkReg) {
                    try {
                        new Cittadini(checkLogin, account);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                else
                {
                    try {
                        new Registrazione();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                f.setVisible(false);
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                f.dispose();
            }
        });

        f.getContentPane().setBackground(hex2Rgb("#FFFFFF"));
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setResizable(false);  //lock size finestra
        f.setBounds(410, 240, 900, 600);
        ImageIcon img = new ImageIcon(Objects.requireNonNull(Homepage.class.getResource("/logo.jpg")));
        Image img1 = img.getImage();
        Image img2 = img1.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        f.setIconImage(img2);

        f.add(ricerca);
        f.add(indietro);
        f.add(tmpFocus);
        f.add(info);
        f.add(exit);
        f.add(panel);
    }

    public static void main(String[] args) throws IOException, NotBoundException, SQLException {
        // TODO Auto-generated method stub
        new Homepage(false, null, false);
    }

}
