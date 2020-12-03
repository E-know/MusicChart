package view;

import model.DB.ConnectDB;
import model.DB.CommentDTO;
import model.DB.InsertDatabase;
import model.ChartData;
import model.DetailData;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.sql.Array;
import java.util.ArrayList;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class CommentPanel extends JPanel {
    private JPanel _pnlComment, _pnlMusicInfo;
    private JButton _btnRegister, _btnDelete, _btnBack, _btnYouTube;
    private JScrollPane _scrollBar;

    public JTextField _txtComment, _txtPassword;

    public ArrayList<CommentDTO> _commentListDTO;

    public JList<String> _listComment;
    public DefaultListModel<String> _modelList;
    public String _strTitle, _strArtist, _sqlTitle, _strAlbumId;

    private JLabel _lblTitle, _lblArtist, _lblImage;

    ConnectDB DB = new ConnectDB();
    public int _commmentPanelRank;

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
        _btnBack = new JButton("Back");
        _btnBack.setBounds(964, 0, 60, 30);
        _btnBack.setFont(new Font("배달의민족 을지로체 TTF", Font.PLAIN, 12));
        _btnBack.setBackground(Color.WHITE);
        add(_btnBack);
    }

    // PnlMusicInfo에 대한 초기 설정 = 밑에 함수들은 PnlMusicInfo에 붙은 것들이다=======================================
    private void setInitializationPnlMusicInfo() { //Called by Constructor
        _pnlMusicInfo = new JPanel();
        _pnlMusicInfo.setBackground(new Color(255, 255, 255, 50));
        _pnlMusicInfo.setBounds(32, 32, 960, 190);
        _pnlMusicInfo.setLayout(null);
        this.add(_pnlMusicInfo);

        setInitializationLblTitle();
        setInitializationLblArtist();
        setInitializationLblImage();
        setInitializationBtnYouTube();
    }

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

    private void setInitializationBtnYouTube() {
        _btnYouTube = new JButton("Search in Youtube");
        _btnYouTube.setBounds(800, 160, 160, 30);
        _btnYouTube.setBackground(Color.WHITE);
        _pnlMusicInfo.add(_btnYouTube);
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
        setInitializationScroll();
    }

    private void setInitializationListComment() { //Called by setInitializationPnlComment
        _commentListDTO = new ArrayList<CommentDTO>();
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
        _pnlComment.add(_btnRegister);
    }

    private void setInitializationBtnDelete() {
        _btnDelete = new JButton("Delete");
        _btnDelete.setBounds(880, 415, 80, 20);
        _btnDelete.setBackground(Color.WHITE);
        _btnDelete.setFont(new Font("한강남산체 M", Font.PLAIN, 13));
        _pnlComment.add(_btnDelete);
    }

    private void setInitializationScroll() {
        _scrollBar = new JScrollPane(_listComment, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);  // 스크롤패널을 선언
        _scrollBar.setBounds(32, 260, 960, 400);
        add(_scrollBar);
    }
    //==================================================================================================================

    private void inputMusicInfoToPnlMusicInfo(int rank) {
        _lblTitle.setText("Title : " + ChartData.getS_instance().getParser().getTitle(rank));
        _lblArtist.setText("Artist : " + ChartData.getS_instance().getParser().getArtistName(rank));

        try {
            DetailData.getS_instance().detailDataPassing(rank, ChartData.getS_instance().getParser().getChartList(), this);
            Image image = ImageIO.read(new URL(DetailData.getS_instance().getDetailParser().getImageUrl(rank))).getScaledInstance(160, 160, Image.SCALE_SMOOTH);
            _lblImage.setIcon(new ImageIcon(image));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void inputCommentToListComment(int rank) {
        readCommentFromDB(rank);
        for (CommentDTO list : _commentListDTO) {
            System.out.println("comment " + list.getComment() + ", pwd" + list.getPassword());
            _modelList.addElement(list.getComment());
        }
        _listComment.setModel(_modelList);
    }

    private void inputRecentList(int rank){
        _sqlTitle = ChartData.getS_instance().getParser().getTitle(rank);
        _sqlTitle = replaceTitle(_strTitle);
        DB.connectionDB();
        try {
            DB.insertRecentListDB(_sqlTitle, ChartData.getS_instance().getSiteMBG(), rank, InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void popUpCommentPanel(int rank) {
        this.setVisible(true);

        inputMusicInfoToPnlMusicInfo(rank);
        inputCommentToListComment(rank);
        inputRecentList(rank);
        setCommnetPanelRank(rank);
    }

    /*Description of Method readComment
     *   덧글과 각 비밀번호가 적혀있는 db 파일을 읽어와 각각의 ArrayList에 저장하는 메소드
     * */
    private void readCommentFromDB(int rank) {
        _strAlbumId = ChartData.getS_instance().getParser().getAlbumID(rank).replaceAll("[^0-9]", "");
        _strTitle = ChartData.getS_instance().getParser().getTitle(rank);
        _strArtist = ChartData.getS_instance().getParser().getArtistName(rank);

        _sqlTitle = replaceTitle(_strTitle);

        DB.connectionDB();
        ArrayList<String> albumIdList = DB.getAlbumId(_sqlTitle);
        System.out.println("albumIdList : "+albumIdList);
        _commentListDTO = DB.readCommentDB(albumIdList);
    }//readComment

    private String replaceTitle(String strTitle){
        String[] strArray = new String[] {"'", " ", "by", ",", "&"};
        //노래 제목이 사이트마다 다른 기호들의 경우 처리해줌
        for (String needReplace : strArray){
            if (strTitle.contains(needReplace)) {
                strTitle = strTitle.replace(needReplace, "");
            }
        }
        return strTitle;
    }

    /*
     *Description of Method clearAll
     *   btnBack(ChartPrimaryPanel로 돌아가는 버튼)이 일어나면 싱글톤 패턴이기 때문에 원래 있던 정보는 모두다
     *  삭제가 되어야한다. 그러므로 모든 정보를 초기화 해주는 메소드드     * */
    public void clearMusicData() {
        clearPanelTxt();
        _modelList.clear();
        _commentListDTO.clear();
    }

    public void clearPanelTxt() {
        _txtPassword.setText("");
        _txtComment.setText("");
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

    public void addBtnYouTubeListener(ActionListener listenForBtnYouTube) {
        _btnYouTube.addActionListener((listenForBtnYouTube));
    }

    public void setCommnetPanelRank(int rank) {
        _commmentPanelRank = rank;
    }

    public int getCommentPanelRank() {
        return _commmentPanelRank;
    }
}//CommentUI
