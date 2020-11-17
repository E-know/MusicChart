package view;

import controller.SitePanelController;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class SiteChartsPanel extends JPanel{
    public ChartPanel pnlChartPanel;
    private JButton btnRefresh, btnSite_Melon, btnSite_Bugs, btnSite_Genie, btnSearch;
    public JLabel lblTime;
    public JComboBox<String> strCombo;

    private ButtonListener  ButtonRefresh, ButtonSearch,
            ButtonMelon, ButtonBugs, ButtonGenie;

    private String[] strSearchCategory = {"Name", "Artist"};

    public JTextField txtSearch;


    public LocalDateTime current = LocalDateTime.now();
    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public String formatted_Melon = current.format(formatter);
    public String formatted_Bugs = current.format(formatter);
    public String formatted_Genie = current.format(formatter);
    //refreshTime

    private SitePanelController theSitePanelController;


    public SiteChartsPanel(){

        setBackground(new Color(255, 255, 255, 0));
        setBounds(1,0,1278,960);
        setLayout(null);

        ButtonRefresh = new ButtonListener(this);
        ButtonMelon = new ButtonListener(this);
        ButtonBugs = new ButtonListener(this);
        ButtonGenie = new ButtonListener(this);
        ButtonSearch = new ButtonListener();

        setInitBtnRefresh();

        setInitStrCombo();

        setInitTxtSearch();

        setInitBtnSearch();

        setInitBtnSites();

        setInitPnlChartPanel();

        LocalDateTime current = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formatted = current.format(formatter);
        //현재시간을 정해진 표기데로 출력해주는 메소드
        lblTime = new JLabel("Renewal time : " + formatted);
        System.out.println("Renewal time : " + formatted);

        lblTime.setBounds(900,870,200,40);
        lblTime.setFont(new Font("Verdana", Font.BOLD + Font.PLAIN, 14));
        lblTime.setBackground(Color.lightGray);
        lblTime.setHorizontalAlignment(SwingConstants.CENTER);
        lblTime.setOpaque(true);
        add(lblTime);

    }//constructor

    private void setInitBtnRefresh(){
        btnRefresh = new JButton(new ImageIcon("Image/Refresh.png"));
        btnRefresh.setBounds(30,30,40,40);
        btnRefresh.setForeground(Color.DARK_GRAY);
        btnRefresh.setBackground(Color.lightGray);
        btnRefresh.addActionListener(ButtonRefresh);
        this.add(btnRefresh);
    }

    private void setInitBtnSearch(){
        btnSearch = new JButton("Search");
        btnSearch.setBounds(1090,30,150,40);
        btnSearch.setForeground(Color.DARK_GRAY);
        btnSearch.setBackground(Color.lightGray);
        btnSearch.addActionListener(ButtonSearch);
        this.add(btnSearch);
    }

    private void setInitStrCombo(){
        strCombo = new JComboBox<String>(strSearchCategory);
        strCombo.setBounds(100, 30, 150,40);
        strCombo.setEditable(false);
        this.add(strCombo);
    }

    private void setInitTxtSearch(){
        txtSearch = new JTextField();
        txtSearch.setBounds(250,30,840,40);
        txtSearch.setFont(new Font("SansSerif", Font.PLAIN, 25));
        txtSearch.addKeyListener(new KeyActionListener());
        this.add(txtSearch);
    }

    private void setInitBtnSites(){
        btnSite_Melon = new JButton(new ImageIcon("Image/logo_Melon.png"));
        btnSite_Melon.setBounds(100,100,150,40);
        btnSite_Melon.setBackground(Color.WHITE);
        btnSite_Melon.addActionListener(ButtonMelon);
        this.add(btnSite_Melon);

        btnSite_Bugs = new JButton(new ImageIcon("Image/logo_Bugs.png"));
        btnSite_Bugs.setBounds(250,100,150,40);
        btnSite_Bugs.setBackground(Color.WHITE);
        btnSite_Bugs.addActionListener(ButtonBugs);
        this.add(btnSite_Bugs);

        btnSite_Genie = new JButton(new ImageIcon("Image/logo_Genie.png"));
        btnSite_Genie.setBounds(400,100,150,40);
        btnSite_Genie.setBackground(Color.WHITE);
        btnSite_Genie.addActionListener(ButtonGenie);
        this.add(btnSite_Genie);
    }

    private void setInitPnlChartPanel(){
        pnlChartPanel = new ChartPanel();
        theSitePanelController = new SitePanelController(pnlChartPanel); //사이트 패널 컨트롤러 추가 선언
        pnlChartPanel.setBounds(100,140,1080,700);
        LineBorder SiteBorder = new LineBorder(Color.BLACK,3);
        pnlChartPanel.setBorder(SiteBorder);
        pnlChartPanel.setLayout(null);
        this.add(pnlChartPanel);
    }

    public void addBtnRefreshListener(ActionListener listenForBtnRefresh) {
        btnRefresh.addActionListener((listenForBtnRefresh));
    }

    public void addBtnMelonListener(ActionListener listenForBtnMelon) {
        btnSite_Melon.addActionListener((listenForBtnMelon));
    }

    public void addBtnBugsListener(ActionListener listenForBtnBugs) {
        btnSite_Bugs.addActionListener((listenForBtnBugs));
    }

    public void addBtnGenieListener(ActionListener listenForBtnGenie) {
        btnSite_Genie.addActionListener((listenForBtnGenie));
    }

    public void addKeyActionListener(KeyListener listenForKey) {
        txtSearch.addKeyListener((listenForKey));
    }

    private class ButtonListener implements ActionListener {
        private Component _viewLoading;
        public ButtonListener() { }
        public ButtonListener(Component parentComponent){
            _viewLoading = parentComponent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Object obj = e.getSource();
            if (obj == btnRefresh) {

                /*
                current = LocalDateTime.now();
                formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                switch (AppManager.getS_instance().getSite_M_B_G()){
                    case 1:
                        formatted_Melon = current.format(formatter);
                        lblTime.setText("Renewal time : " + formatted_Melon);
                        AppManager.getS_instance().setSite_M_B_G(1);
                        AppManager.getS_instance().DataPassing(_viewLoading);
                        System.out.println("why?");
                        break;
                    case 2:
                        formatted_Bugs = current.format(formatter);
                        lblTime.setText("Renewal time : " + formatted_Bugs);
                        AppManager.getS_instance().setSite_M_B_G(2);
                        AppManager.getS_instance().DataPassing(_viewLoading);
                        break;
                    case 3:
                        formatted_Genie = current.format(formatter);
                        lblTime.setText("Renewal time : " + formatted_Genie);
                        AppManager.getS_instance().setSite_M_B_G(3);
                        AppManager.getS_instance().DataPassing(_viewLoading);
                        break;
                }
                */

            }//refresh 새로 파싱해옴//파싱시간도 갱신
            if (obj == btnSite_Melon) {
                /*
                if(AppManager.getS_instance().getSite_M_B_G() == 1) return;
                AppManager.getS_instance().setSite_M_B_G(1);
                if(!AppManager.getS_instance().getParser().isParsed()) AppManager.getS_instance().DataPassing(_viewLoading);
                System.out.println("Melon");
                pnlSitePanel.changeData();
                lblTime.setText("Renewal time : " + formatted_Melon);
                txtSearch.setText("");
                pnlSitePanel.filter(null,2);
                */
            }
            if (obj == btnSite_Bugs) {
                /*
                if(AppManager.getS_instance().getSite_M_B_G() == 2) return;
                AppManager.getS_instance().setSite_M_B_G(2);
                if(!AppManager.getS_instance().getParser().isParsed()) AppManager.getS_instance().DataPassing(_viewLoading);
                System.out.println("Bugs");
                pnlSitePanel.changeData();
                lblTime.setText("Renewal time : " + formatted_Bugs);
                txtSearch.setText("");
                pnlSitePanel.filter(null,2);
                */
            }
            if (obj == btnSite_Genie) {
                /*
                if(AppManager.getS_instance().getSite_M_B_G() == 3) return;
                AppManager.getS_instance().setSite_M_B_G(3);
                if(!AppManager.getS_instance().getParser().isParsed()) AppManager.getS_instance().DataPassing(_viewLoading);
                System.out.println("Genie");
                pnlSitePanel.changeData();
                lblTime.setText("Renewal time : " + formatted_Genie);
                txtSearch.setText("");
                pnlSitePanel.filter(null,2);
                */
            }
            //멜론, 벅스, 지니 버튼에 따라 저장해 놓은 데이터를 새로 갖고옴
            //저장되있던 불러온 시간도 같이 갖고옴
        }
    }//ButtonListener

    private class KeyActionListener implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) { }

        @Override
        public void keyPressed(KeyEvent e) { }

        @Override
        public void keyReleased(KeyEvent e) {
            Object obj = e.getSource();

            if(obj == txtSearch){
                /*
                //strSearchCategory = {"Name", "Artist"};
                if(0 == strCombo.getSelectedIndex())//Name
                    pnlSitePanel.filter(txtSearch.getText(),2);
                if(1 == strCombo.getSelectedIndex())//Artist
                    pnlSitePanel.filter(txtSearch.getText(),3);
                */
            }//comboBox 0, 1일때 sitepanel의 filter에서 검색

        }//KeyReleased

    }//KeyListener
}