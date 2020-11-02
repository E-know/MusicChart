package view;

import DB.*;
import main.AppManager;

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

public class CommentPanel extends JPanel {

    private JPanel pnlComment, pnlMusicInfo;
    private JButton btnRegister, btnDelete, btnBack;

    public JTextField txtComment, txtPassword;
    public ArrayList<String> arrComment;
    public ArrayList<String> arrPassword;

    public JList<String> listComment;
    public DefaultListModel<String> modelList;
    public String strTitle, strArtist, sqltitle;

    private JLabel lblTitle, lblArtist, lblImage;

    public Connection con = null;
    public ResultSet rs = null;

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

    public CommentPanel() {
        setPreferredSize(new Dimension(1024, 768));
        setBackground(new Color(0, 0, 0, 25));
        setLayout(null);
        setBounds(128, 96, 1024, 768);
        setLayout(null);

        setInitializationPnlMusicInfo();

        setInitializationPnlComment();

        setInitializationBtnBack();
    }//Constructor

    /*
     *Description of Method addMusicInfo
     *   pnlMusicInfo 위에 올라가는 이미지와 String을 정해주는 메소드
     * */
    private void setInitializationBtnBack() {
        btnBack = new JButton("Back");
        btnBack.setBounds(964, 0, 60, 30);
        btnBack.setFont(new Font("배달의민족 을지로체 TTF", Font.PLAIN, 12));
        btnBack.setBackground(Color.WHITE);
        add(btnBack);
    }

    // PnlMusicInfo에 대한 초기 설정 = 밑에 함수들은 PnlMusicInfo에 붙은 것들이다=======================================
    private void setInitializationPnlMusicInfo() { //Called by Constructor
        pnlMusicInfo = new JPanel();
        pnlMusicInfo.setBackground(new Color(255, 255, 255, 50));
        pnlMusicInfo.setBounds(32, 32, 960, 160);
        pnlMusicInfo.setLayout(null);
        this.add(pnlMusicInfo);

        setInitializationLblTitle();
        setInitializationLblArtist();
        setInitializationLblImage();
    }

    private void setInitializationLblArtist() { // Called by setInitializationPnlMusicInfo
        lblArtist = new JLabel();
        lblArtist.setBounds(10, 90, 700, 60);
        lblArtist.setFont(new Font("서울남산체 B", Font.PLAIN, 40));
        lblArtist.setHorizontalAlignment(SwingConstants.LEFT);
        pnlMusicInfo.add(lblArtist);
    }

    private void setInitializationLblTitle() { // Called by setInitializationPnlMusicInfo
        lblTitle = new JLabel();
        lblTitle.setBounds(10, 10, 700, 60);
        lblTitle.setHorizontalAlignment(SwingConstants.LEFT);
        lblTitle.setFont(new Font("서울남산체 B", Font.PLAIN, 40));
        lblTitle.setBackground(Color.black);
        pnlMusicInfo.add(lblTitle);
    }

    private void setInitializationLblImage() {
        lblImage = new JLabel();
        lblImage.setBounds(800, 0, 160, 160);
        pnlMusicInfo.add(lblImage);
    }

    // PnlComment에 대한 초기 설정 = 밑 함수들은 PnlComment에 붙은 것들이다.============================================
    private void setInitializationPnlComment() { // Called by Constructor
        pnlComment = new JPanel();
        pnlComment.setBounds(32, 260, 960, 640);
        pnlComment.setBackground(new Color(0, 0, 0, 0));
        pnlComment.setLayout(null);
        add(pnlComment);

        setInitializationListComment();
        setInitializationTxtComment();
        setInitializationTxtPassword();
        setInitializationBtnRegister();
        setInitializationBtnDelete();
    }

    private void setInitializationListComment() { //Called by setInitializationPnlComment
        arrComment = new ArrayList<>();
        arrPassword = new ArrayList<>();
        modelList = new DefaultListModel<String>();

        listComment = new JList<String>();
        listComment.setFont(new Font("서울한강체 M", Font.PLAIN, 20));
        listComment.setBounds(0, 0, 960, 400);
        pnlComment.add(listComment);
    }

    private void setInitializationTxtComment() {
        txtComment = new JTextField();
        txtComment.setBounds(0, 435, 800, 40);
        pnlComment.add(txtComment);
    }

    private void setInitializationTxtPassword() {
        txtPassword = new JTextField();
        txtPassword.setBounds(800, 415, 80, 20);
        pnlComment.add(txtPassword);
    }

    private void setInitializationBtnRegister() {
        btnRegister = new JButton("Register");
        btnRegister.setBounds(800, 435, 160, 40);
        btnRegister.setBackground(Color.WHITE);
        pnlComment.add(btnRegister);
    }

    private void setInitializationBtnDelete() {
        btnDelete = new JButton("Delete");
        btnDelete.setBounds(880, 415, 80, 20);
        btnDelete.setBackground(Color.WHITE);
        btnDelete.setFont(new Font("한강남산체 M", Font.PLAIN, 13));
        pnlComment.add(btnDelete);
    }
    
    //==================================================================================================================

    private void inputMusicInfoToPnlMusicInfo(int rank) {
        lblTitle.setText("Title : " + AppManager.getS_instance().getParser().getTitle(rank));
        lblArtist.setText("Artist : " + AppManager.getS_instance().getParser().getArtistName(rank));

        try {
            AppManager.getS_instance().detailDataPassing(rank,AppManager.getS_instance().getParser().getChartList(),this);
            URL url = new URL(AppManager.getS_instance().getDetailParser().getImageUrl(rank));
            Image image = ImageIO.read(url).getScaledInstance(160,160,Image.SCALE_SMOOTH);
            lblImage.setIcon(new ImageIcon(image));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void inputCommentToListComment(int rank) {
        readCommentFromDB(rank);
        for (String ptr : arrComment) {
            modelList.addElement(ptr);
        }
        listComment.setModel(modelList);
    }

    public void popUpCommentPanel(int rank) {
        this.setVisible(true);

        inputMusicInfoToPnlMusicInfo(rank);
        inputCommentToListComment(rank);
    }

    /*Description of Method readComment
     *   덧글과 각 비밀번호가 적혀있는 txt 파일을 읽어와 각각의 ArrayList에 저장하는 메소드
     * */
    private void readCommentFromDB(int rank) {
        Connection con = ConnectDB.GetDB();
        sqltitle = AppManager.getS_instance().getParser().getTitle(rank);
        if (sqltitle.contains("'")) {
            sqltitle = sqltitle.replace("'", ":");
        }
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT comment, pwd FROM songinfo WHERE title = '" + sqltitle + "'";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String comment = rs.getString("comment");
                String pwd = rs.getNString("pwd");
                System.out.println("comment " + comment + ", pwd" + pwd);
                arrComment.add(comment);
                arrPassword.add(pwd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//readComment

    /*
     *Description of Method clearAll
     *   btnBack(ChartPrimaryPanel로 돌아가는 버튼)이 일어나면 싱글톤 패턴이기 때문에 원래 있던 정보는 모두다
     *  삭제가 되어야한다. 그러므로 모든 정보를 초기화 해주는 메소드드     * */
    public void clearMusicData() {
        txtPassword.setText("");
        txtComment.setText("");

        modelList.clear();
        arrComment.clear();
        arrPassword.clear();
    }

    public void addBtnRegisterListener(ActionListener listenForBtnRegister) {
        btnRegister.addActionListener((listenForBtnRegister));
    }

    public void addBtnDeleteListener(ActionListener listenForBtnDelete) {
        btnDelete.addActionListener((listenForBtnDelete));
    }

    public void addBtnBackListener(ActionListener listenForBtnBack) {
        btnBack.addActionListener((listenForBtnBack));
    }

}//CommentUI

