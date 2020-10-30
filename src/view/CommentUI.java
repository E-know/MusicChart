package view;

import DB.*;
import main.AppManager;

import org.json.simple.JSONArray;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.sql.*;

<<<<<<< Updated upstream:src/view/CommentUI.java
public class CommentUI extends JPanel {

    private JPanel pnlCommentField, pnlMusicInfo;
    public JTextField txtComment, txtPassword;
    private JButton btnRegister, btnDelete, btnBack;
    public ArrayList<String> arrComment;
    public ArrayList<String> arrPassword;
    public JList listComment;
    public DefaultListModel modelList;
    public String strTitle, strArtist, sqltitle;
    private JLabel lblStrTitle, lblStrArtist;
    private JLabel lblTitle, lblArtist, lblImage;

    public Connection con = null;
    public Statement stmt = null;
    public PreparedStatement pstmt = null;
    public ResultSet rs = null;
=======
public class CommentPanel extends JPanel {

    private JPanel _pnlComment, _pnlMusicInfo;
    private JButton _btnRegister, _btnDelete, _btnBack;

    public JTextField _txtComment, _txtPassword;
    public ArrayList<String> _arrComment;
    public ArrayList<String> _arrPassword;

    public JList<String> _listComment;
    public DefaultListModel<String> _modelList;
    public String _strTitle, _strArtist, _sqlTitle;

    private JLabel _lblTitle, _lblArtist, _lblImage;

    public Connection _con = null;
    public ResultSet _rs = null;
>>>>>>> Stashed changes:src/view/CommentPanel.java

    /*
     * Description of Class
     *   음악 정보를 Paser에 AppManager를 통하여 직접 접근하여서 노래를 받아온다.
     *   노래를 받아오는 rank 는 SitePanel 에서 몇번 째 노래를 클릭했는지 받아온다.
     * */


    /*
     *Description of Constructor
     *   사용된 폰트
     *      한강남산체 M
     *      배달의민족 을지로체 TTF
     *      서울남산체 B / M
     *  기본적인 UI에 대한 기본 설정을 해준다.
     *  투명 패널을 지니고 있다.
     * */
    public CommentUI() {
        setPreferredSize(new Dimension(1024, 768));
        setBackground(new Color(0, 0, 0, 25));
        setLayout(null);

        setBounds(128, 96, 1024, 768);
        setLayout(null);

        pnlMusicInfo = new JPanel();
        pnlMusicInfo.setBackground(new Color(255, 255, 255, 50));
        pnlMusicInfo.setBounds(32, 32, 960, 160);
        pnlMusicInfo.setLayout(null);
        add(pnlMusicInfo);

        pnlCommentField = new JPanel();
        pnlCommentField.setBackground(Color.white);
        pnlCommentField.setBounds(32, 224, 960, 440);
        pnlCommentField.setLayout(null);
        add(pnlCommentField);

        txtComment = new JTextField();
        txtComment.setBounds(32, 696, 800, 40);
        add(txtComment);

        txtPassword = new JTextField();
        txtPassword.setBounds(832, 676, 80, 20);
        add(txtPassword);

        btnRegister = new JButton("Register");
        btnRegister.setBounds(832, 696, 160, 40);
        btnRegister.setBackground(Color.WHITE);
        btnRegister.addActionListener(new ButtonListener());
        add(btnRegister);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(912, 676, 80, 20);
        btnDelete.setBackground(Color.WHITE);
        btnDelete.setFont(new Font("한강남산체 M", Font.PLAIN, 13));
        btnDelete.addActionListener(new ButtonListener());
        add(btnDelete);

<<<<<<< Updated upstream:src/view/CommentUI.java
        btnBack = new JButton("Back");
        btnBack.setBounds(964, 0, 60, 30);
        btnBack.setFont(new Font("배달의민족 을지로체 TTF", Font.PLAIN, 12));
        btnBack.setBackground(Color.WHITE);
        btnBack.addActionListener(new ButtonListener());
        add(btnBack);

=======
    /*
     *Description of Method addMusicInfo
     *   pnlMusicInfo 위에 올라가는 이미지와 String을 정해주는 메소드
     * */
    private void setInitializationBtnBack() {
        _btnBack = new JButton("Back");
        _btnBack.setBounds(964, 0, 60, 30);
        _btnBack.setFont(new Font("배달의민족 을지로체 TTF", Font.PLAIN, 12));
        _btnBack.setBackground(Color.WHITE);
        _btnBack.addActionListener(new ButtonListener());
        add(_btnBack);
    }

    // PnlMusicInfo에 대한 초기 설정 = 밑에 함수들은 PnlMusicInfo에 붙은 것들이다=======================================
    private void setInitializationPnlMusicInfo() { //Called by Constructor
        _pnlMusicInfo = new JPanel();
        _pnlMusicInfo.setBackground(new Color(255, 255, 255, 50));
        _pnlMusicInfo.setBounds(32, 32, 960, 160);
        _pnlMusicInfo.setLayout(null);
        this.add(_pnlMusicInfo);
>>>>>>> Stashed changes:src/view/CommentPanel.java

        lblTitle = new JLabel();
        lblTitle.setBounds(110, 10, 700, 60);
        lblTitle.setHorizontalAlignment(SwingConstants.LEFT);
        lblTitle.setFont(new Font("서울남산체 B", Font.PLAIN, 40));
        pnlMusicInfo.add(lblTitle);

<<<<<<< Updated upstream:src/view/CommentUI.java
        lblArtist = new JLabel();
        lblArtist.setBounds(110, 90, 700, 60);
        lblArtist.setFont(new Font("서울남산체 B", Font.PLAIN, 40));
        lblArtist.setHorizontalAlignment(SwingConstants.LEFT);
        pnlMusicInfo.add(lblArtist);

        Font fnt1 = new Font("한강남산체 M", Font.BOLD, 30);

        lblStrArtist = new JLabel("Artist : ");
        lblStrArtist.setFont(fnt1);
        lblStrArtist.setBounds(10, 90, 160, 60);
        lblStrArtist.setHorizontalAlignment(SwingConstants.LEFT);
        pnlMusicInfo.add(lblStrArtist);

        lblStrTitle = new JLabel("Title : ");
        lblStrTitle.setFont(fnt1);
        lblStrTitle.setBounds(10, 10, 100, 60);
        lblStrTitle.setHorizontalAlignment(SwingConstants.LEFT);
        pnlMusicInfo.add(lblStrTitle);

        lblImage = new JLabel();
        lblImage.setBounds(800, 0, 160, 160);
        pnlMusicInfo.add(lblImage);

        arrComment = new ArrayList<>();
        arrPassword = new ArrayList<>();
        listComment = new JList();

        modelList = new DefaultListModel();
    }//Constructor

    /*
     *Description of Method addMusicInfo
     *   pnlMusicInfo 위에 올라가는 이미지와 String을 정해주는 메소드
     * */
    private void addMusicInfo(int rank) {
        String strRefinedTitle = strTitle;
        if (strRefinedTitle.indexOf("(") != -1) {
            strRefinedTitle = strRefinedTitle.substring(0, strRefinedTitle.indexOf("("));
        }
        lblTitle.setText(strRefinedTitle);

        String strRefinedArtist = strArtist;
        if (strRefinedArtist.indexOf("(") != -1) {
            strRefinedArtist = strRefinedArtist.substring(0, strRefinedArtist.indexOf("("));
        }
        lblArtist.setText(strRefinedArtist);
        Image image = null;
        URL url;

        try {
            url = new URL(AppManager.getS_instance().getDetailParser().getImageUrl());
            System.out.println(url);
            image = ImageIO.read(url);
            image = image.getScaledInstance(160, 160, Image.SCALE_SMOOTH);
=======
    private void setInitializationLblArtist() { // Called by setInitializationPnlMusicInfo
        _lblArtist = new JLabel();
        _lblArtist.setBounds(10, 90, 700, 60);
        _lblArtist.setFont(new Font("서울남산체 B", Font.PLAIN, 40));
        _lblArtist.setHorizontalAlignment(SwingConstants.LEFT);
        _pnlMusicInfo.add(_lblArtist);
    }

    private void setInitializationLblTitle() { // Called by setInitializationPnlMusicInfo
        _lblTitle = new JLabel();
        _lblTitle.setBounds(10, 10, 700, 60);
        _lblTitle.setHorizontalAlignment(SwingConstants.LEFT);
        _lblTitle.setFont(new Font("서울남산체 B", Font.PLAIN, 40));
        _lblTitle.setBackground(Color.black);
        _pnlMusicInfo.add(_lblTitle);
    }

    private void setInitializationLblImage() {
        _lblImage = new JLabel();
        _lblImage.setBounds(800, 0, 160, 160);
        _pnlMusicInfo.add(_lblImage);
    }

    // PnlComment에 대한 초기 설정 = 밑 함수들은 PnlComment에 붙은 것들이다.============================================
    private void setInitializationPnlComment() { // Called by Constructor
        _pnlComment = new JPanel();
        _pnlComment.setBounds(32, 260, 960, 640);
        _pnlComment.setBackground(new Color(0, 0, 0, 0));
        _pnlComment.setLayout(null);
        add(_pnlComment);

        setInitializationListComment();
        setInitializationTxtComment();
        setInitializationTxtPassword();
        setInitializationBtnRegister();
        setInitializationBtnDelete();
    }

    private void setInitializationListComment() { //Called by setInitializationPnlComment
        _arrComment = new ArrayList<>();
        _arrPassword = new ArrayList<>();
        _modelList = new DefaultListModel<String>();

        _listComment = new JList<String>();
        _listComment.setFont(new Font("서울한강체 M", Font.PLAIN, 20));
        _listComment.setBounds(0, 0, 960, 400);
        _pnlComment.add(_listComment);
    }

    private void setInitializationTxtComment() {
        _txtComment = new JTextField();
        _txtComment.setBounds(0, 435, 800, 40);
        _pnlComment.add(_txtComment);
    }

    private void setInitializationTxtPassword() {
        _txtPassword = new JTextField();
        _txtPassword.setBounds(800, 415, 80, 20);
        _pnlComment.add(_txtPassword);
    }

    private void setInitializationBtnRegister() {
        _btnRegister = new JButton("Register");
        _btnRegister.setBounds(800, 435, 160, 40);
        _btnRegister.setBackground(Color.WHITE);
        _btnRegister.addActionListener(new ButtonListener());
        _pnlComment.add(_btnRegister);
    }

    private void setInitializationBtnDelete() {
        _btnDelete = new JButton("Delete");
        _btnDelete.setBounds(880, 415, 80, 20);
        _btnDelete.setBackground(Color.WHITE);
        _btnDelete.setFont(new Font("한강남산체 M", Font.PLAIN, 13));
        _btnDelete.addActionListener(new ButtonListener());
        _pnlComment.add(_btnDelete);
    }
    
    //==================================================================================================================

    private void inputMusicInfoToPnlMusicInfo(int rank) {
        _lblTitle.setText("Title : " + AppManager.getS_instance().getParser().getTitle(rank));
        _lblArtist.setText("Artist : " + AppManager.getS_instance().getParser().getArtistName(rank));

        try {
            AppManager.getS_instance().detailDataPassing(rank,AppManager.getS_instance().getParser().getChartList(),this);
            URL url = new URL(AppManager.getS_instance().getDetailParser().getImageUrl(rank));
            Image image = ImageIO.read(url).getScaledInstance(160,160,Image.SCALE_SMOOTH);
            _lblImage.setIcon(new ImageIcon(image));
>>>>>>> Stashed changes:src/view/CommentPanel.java
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lblImage.setIcon(new ImageIcon(image));

    }//addMusicInfo

<<<<<<< Updated upstream:src/view/CommentUI.java
    /*
     *Description of Method addList
     *   Txt 파일에서 읽어온 ArrryList - Comment 를 JList에 올려주는 함수
     * */
    private void addList() {
        for (String ptr : arrComment) {
            modelList.addElement(ptr);
        }
        listComment.setModel(modelList);
        listComment.setFont(new Font("서울한강체 M", Font.PLAIN, 20));
        listComment.setBounds(0, 0, 960, 400);
        pnlCommentField.add(listComment);
=======
    private void inputCommentToListComment(int rank) {
        readCommentFromDB(rank);
        for (String ptr : _arrComment) {
            _modelList.addElement(ptr);
        }
        _listComment.setModel(_modelList);
>>>>>>> Stashed changes:src/view/CommentPanel.java
    }

    /*Description of Method reNewalInfo
     *  Site Panel에서 받아온 rank를 기반으로 Parser에 직접접근하여 정보를 업데이트 해준다.
     * */
    public void reNewalInfo(int rank) {
        this.setVisible(true);
        strTitle = AppManager.getS_instance().getParser().getTitle(rank);
        strArtist = AppManager.getS_instance().getParser().getArtistName(rank);

        JSONArray chartListData = AppManager.getS_instance().getParser().getChartList();
        System.out.println("Detail Parsing is Start");

        AppManager.getS_instance().detailDataPassing(rank, chartListData, this);

        System.out.println("Detail Parsing is End");

        readComment();
        addList();
        addMusicInfo(rank);
    }

    /*Description of Method readComment
     *   덧글과 각 비밀번호가 적혀있는 txt 파일을 읽어와 각각의 ArrayList에 저장하는 메소드
     * */
<<<<<<< Updated upstream:src/view/CommentUI.java
    private void readComment() {
        con = ConnectDB.GetDB();
        sqltitle = strTitle;
        if (sqltitle.contains("'")) {
            sqltitle = sqltitle.replace("'", ":");
        }
        try {
            stmt = con.createStatement();
            String sql = "SELECT comment, pwd FROM songinfo WHERE title = '" + sqltitle + "'";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String comment = rs.getString("comment");
                String pwd = rs.getNString("pwd");
=======
    private void readCommentFromDB(int rank) {
        Connection con = ConnectDB.GetDB();
        _sqlTitle = AppManager.getS_instance().getParser().getTitle(rank);
        _strTitle = AppManager.getS_instance().getParser().getTitle(rank);
        _strArtist = AppManager.getS_instance().getParser().getArtistName(rank);
        if (_sqlTitle.contains("'")) {
            _sqlTitle = _sqlTitle.replace("'", ":");
        }
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT comment, pwd FROM songinfo WHERE title = '" + _sqlTitle + "'";
            _rs = stmt.executeQuery(sql);
            while (_rs.next()) {
                String comment = _rs.getString("comment");
                String pwd = _rs.getNString("pwd");
>>>>>>> Stashed changes:src/view/CommentPanel.java
                System.out.println("comment " + comment + ", pwd" + pwd);
                _arrComment.add(comment);
                _arrPassword.add(pwd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }//readComment

    /*
     *Description of Method clearAll
     *   btnBack(ChartPrimaryPanel로 돌아가는 버튼)이 일어나면 싱글톤 패턴이기 때문에 원래 있던 정보는 모두다
     *  삭제가 되어야한다. 그러므로 모든 정보를 초기화 해주는 메소드드     * */
<<<<<<< Updated upstream:src/view/CommentUI.java
    public void clearAll() {
        txtPassword.setText("");
        txtComment.setText("");
        lblArtist.setText("");
        lblTitle.setText("");
=======
    public void clearMusicData() {
        _txtPassword.setText("");
        _txtComment.setText("");
>>>>>>> Stashed changes:src/view/CommentPanel.java

        _modelList.clear();
        _arrComment.clear();
        _arrPassword.clear();
    }

    public void addBtnRegisterListener(ActionListener listenForBtnRegister) {
        _btnRegister.addActionListener((listenForBtnRegister));
    }
    public void addBtnDeleteListener(ActionListener listenForBtnDelete) {
        _btnDelete.addActionListener((listenForBtnDelete));
    }
    public void addBtnBackListener(ActionListener listenForBtnBack) {
        _btnBack.addActionListener((listenForBtnBack));
    }


    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object obj = e.getSource();
//            if (obj == btnRegister && !txtComment.getText().equals("")) {
//            }//obj == btnRegister
//            if (obj == btnDelete) {
//            }
//            if (obj == btnBack) {
//            }
        }//actionPerfomed
    }//ButtonRegister


}//CommentUI

