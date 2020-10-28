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

public class CommentPanel extends JPanel {

    private JPanel pnlCommentField, pnlMusicInfo;
    private JButton btnRegister, btnDelete, btnBack;

    public JTextField txtComment, txtPassword;
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

    private void setPnlMusicInfo(){
        pnlMusicInfo = new JPanel();
        pnlMusicInfo.setBackground(new Color(255, 255, 255, 50));
        pnlMusicInfo.setBounds(32, 32, 960, 160);
        pnlMusicInfo.setLayout(null);
        this.add(pnlMusicInfo);

        setLblTitle();
    }


    private void setLblTitle(){
        lblTitle = new JLabel();
        lblTitle.setBounds(110, 10, 700, 60);
        lblTitle.setHorizontalAlignment(SwingConstants.LEFT);
        lblTitle.setFont(new Font("서울남산체 B", Font.PLAIN, 40));
        lblTitle.setBackground(Color.black);
        pnlMusicInfo.add(lblTitle);
    }

    public CommentPanel() {
        setPreferredSize(new Dimension(1024, 768));
        setBackground(new Color(0, 0, 0, 25));
        setLayout(null);

        setBounds(128, 96, 1024, 768);
        setLayout(null);

        setPnlMusicInfo();

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

        btnBack = new JButton("Back");
        btnBack.setBounds(964, 0, 60, 30);
        btnBack.setFont(new Font("배달의민족 을지로체 TTF", Font.PLAIN, 12));
        btnBack.setBackground(Color.WHITE);
        btnBack.addActionListener(new ButtonListener());
        add(btnBack);

        lblArtist = new JLabel();
        lblArtist.setBounds(110, 90, 700, 60);
        lblArtist.setFont(new Font("서울남산체 B", Font.PLAIN, 40));
        lblArtist.setHorizontalAlignment(SwingConstants.LEFT);
        pnlMusicInfo.add(lblArtist);

        Font fnt1 = new Font("한강남산체 M", Font.BOLD, 30);

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
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lblImage.setIcon(new ImageIcon(image));

    }//addMusicInfo

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
    public void clearAll() {
        txtPassword.setText("");
        txtComment.setText("");
        lblArtist.setText("");
        lblTitle.setText("");

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


    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object obj = e.getSource();
            if (obj == btnRegister && !txtComment.getText().equals("")) {
                /*
                File file = new File("comments\\" + strReadTitle + ".txt");
                try {
                    FileWriter fw = new FileWriter(file,true);
                    fw.write(txtComment.getText() + "\r");
                    if( txtPassword.getText().equals("") )
                        fw.write("0000\r");
                    else
                        fw.write(txtPassword.getText() + "\r");
                    fw.flush();
                    fw.close();
                    modelList.addElement(txtComment.getText());

                    arrComment.add(txtComment.getText());
                    if( txtPassword.getText().equals("") )
                        arrPassword.add("0000");
                    else
                        arrPassword.add(txtPassword.getText());

                    txtComment.setText("");
                    txtPassword.setText("");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                */
            }//obj == btnRegister
            if (obj == btnDelete) {
                /*
                if(Integer.parseInt(txtPassword.getText()) == Integer.parseInt(arrPassword.get(listComment.getSelectedIndex()))){
                    System.out.println("Same Password! At : " + String.valueOf(listComment.getSelectedIndex()));
                    arrPassword.remove(listComment.getSelectedIndex());
                    arrComment.remove(listComment.getSelectedIndex());
                    removeAtTxt(listComment.getSelectedIndex());
                    modelList.removeElementAt(listComment.getSelectedIndex());
                }
                txtPassword.setText("");
                */
            }
            if (obj == btnBack) {
                /*
                clearAll();
                AppManager.getS_instance().BackToChartPrimaryPanel();
                System.out.println("Back To ChartPrimary");
                */
            }
        }//actionPerfomed
    }//ButtonRegister


}//CommentUI

