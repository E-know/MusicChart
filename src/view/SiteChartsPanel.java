package view;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class SiteChartsPanel extends JPanel{
    public ChartPanel _pnlChartPanel;
    private JButton _btnRefresh, _btnSite_Melon, _btnSite_Bugs, _btnSite_Genie, _btnSearch;

    public JLabel _lblTime;
    public JComboBox<String> _strCombo;

    public JTextField _txtSearch;

    public String _formatted_Melon = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    public String _formatted_Bugs = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    public String _formatted_Genie = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    //refreshTime


    public SiteChartsPanel(){

        setBackground(new Color(255, 255, 255, 0));
        setBounds(1,0,1278,960);
        setLayout(null);

        setInitBtnRefresh();

        setInitStrCombo();

        setInitBtnSearch();

        setInitTextSearch();

        setInitPnlChartPanel();

        setInitBtnSites();

        setInitLblTime();

    }//constructor

    private void setInitBtnRefresh(){
        _btnRefresh = new JButton(new ImageIcon("Image/Refresh.png"));
        _btnRefresh.setBounds(30,30,40,40);
        _btnRefresh.setForeground(Color.DARK_GRAY);
        _btnRefresh.setBackground(Color.lightGray);
        this.add(_btnRefresh);
    }

    private void setInitBtnSearch(){
        _btnSearch = new JButton("Search");
        _btnSearch.setBounds(1090,30,150,40);
        _btnSearch.setForeground(Color.DARK_GRAY);
        _btnSearch.setBackground(Color.lightGray);
        this.add(_btnSearch);
    }

    private void setInitStrCombo(){
        _strCombo = new JComboBox<String>(new String[]{"Name","Artist"});
        _strCombo.setBounds(100, 30, 150,40);
        _strCombo.setEditable(false);
        this.add(_strCombo);
    }

    private void setInitTextSearch(){
        _txtSearch = new JTextField();
        _txtSearch.setBounds(250,30,840,40);
        _txtSearch.setFont(new Font("SansSerif", Font.PLAIN, 25));
        this.add(_txtSearch);
    }

    private void setInitBtnSites(){
        _btnSite_Melon = new JButton(new ImageIcon("Image/logo_Melon.png"));
        _btnSite_Melon.setBounds(100,100,150,40);
        _btnSite_Melon.setBackground(Color.WHITE);
        this.add(_btnSite_Melon);

        _btnSite_Bugs = new JButton(new ImageIcon("Image/logo_Bugs.png"));
        _btnSite_Bugs.setBounds(250,100,150,40);
        _btnSite_Bugs.setBackground(Color.WHITE);
        this.add(_btnSite_Bugs);

        _btnSite_Genie = new JButton(new ImageIcon("Image/logo_Genie.png"));
        _btnSite_Genie.setBounds(400,100,150,40);
        _btnSite_Genie.setBackground(Color.WHITE);
        this.add(_btnSite_Genie);
    }

    private void setInitPnlChartPanel(){
        _pnlChartPanel = new ChartPanel();
        _pnlChartPanel.setBounds(100,140,1080,700);
        LineBorder SiteBorder = new LineBorder(Color.BLACK,3);
        _pnlChartPanel.setBorder(SiteBorder);
        _pnlChartPanel.setLayout(null);
        this.add(_pnlChartPanel);
    }

    private void setInitLblTime(){
        _lblTime = new JLabel("Renewal time : " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

        _lblTime.setBounds(900,870,200,40);
        _lblTime.setFont(new Font("Verdana", Font.BOLD + Font.PLAIN, 14));
        _lblTime.setBackground(Color.lightGray);
        _lblTime.setHorizontalAlignment(SwingConstants.CENTER);
        _lblTime.setOpaque(true);
        this.add(_lblTime);
    }

    public void addBtnRefreshListener(ActionListener listenForBtnRefresh) {
        _btnRefresh.addActionListener((listenForBtnRefresh));
    }

    public void addBtnMelonListener(ActionListener listenForBtnMelon) {
        _btnSite_Melon.addActionListener((listenForBtnMelon));
    }

    public void addBtnBugsListener(ActionListener listenForBtnBugs) {
        _btnSite_Bugs.addActionListener((listenForBtnBugs));
    }

    public void addBtnGenieListener(ActionListener listenForBtnGenie) {
        _btnSite_Genie.addActionListener((listenForBtnGenie));
    }

    public void addKeyActionListener(KeyListener listenForKey) {
        _txtSearch.addKeyListener((listenForKey));
    }


}