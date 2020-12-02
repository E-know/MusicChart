package model.DB;

import java.sql.*;
import java.util.ArrayList;

public class ConnectDB {
    private PreparedStatement _pstmt = null;
    private ResultSet _rs = null;
    private Statement _stmt = null;
    private Connection _con = null;
    private String _sql;

    final private String _url = "jdbc:mysql://database-2.cqcrpm8zqs1t.ap-northeast-2.rds.amazonaws.com:3306/charts_db?&serverTimezone=Asia/Seoul&useSSL=false";
    final private String _userId = "admin";
    final private String _password = "refactoring";

    public void driverLoad(){//드라이버 로딩
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("드라이버 로드 성공");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void connectionDB() {//연결
        try {
            System.out.println("데이터베이스 연결 준비...");
            _con = DriverManager.getConnection(_url, _userId, _password);
            System.out.println("데이터베이스 연결 성공");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //////////////////////////////최근 본 목록 관련 메소드
    public void insertRecentListDB(String title, int siteNum, int rank, String hostName){
        java.util.Date utilDate = new java.util.Date();
        java.sql.Time sqlTime = new java.sql.Time(utilDate.getTime());
        try {
            _sql = "INSERT INTO recentList VALUES (?, ?, ?, ?, ?)";
            _pstmt = _con.prepareStatement(_sql);
            _pstmt.setString(1, title);
            _pstmt.setInt(2, siteNum);
            _pstmt.setInt(3, rank);
            _pstmt.setString(4, hostName);
            _pstmt.setTime(5, sqlTime);
            _pstmt.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
    public void deleteRecentDB(String hostName){
        try {
            _sql = "DELETE FROM recentList WHERE hostName = ?";
            _pstmt = _con.prepareStatement(_sql);
            _pstmt.setString(1, hostName);
            _pstmt.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
    public ArrayList<RecentListDTO> readRecentList(String hostName){
        ArrayList<RecentListDTO> recentListDTO = new ArrayList<RecentListDTO>();
        try {
            _stmt = _con.createStatement();
            _sql = "SELECT * FROM recentList WHERE hostName = '" + hostName + "' ORDER BY clickTime DESC";
            _rs = _stmt.executeQuery(_sql);
            while (_rs.next()) {
                RecentListDTO list = new RecentListDTO();
                list.setTitle(_rs.getString("title"));
                list.setSiteNum(_rs.getInt("siteNum"));
                list.setRank(_rs.getInt("rankNum"));
                recentListDTO.add(list);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recentListDTO;
    }
    public ArrayList<String> readRecentListl(String hostName, String title){
        ArrayList<String> recentListSite = new ArrayList<>();
        try {
            _stmt = _con.createStatement();
            _sql = "SELECT title  FROM recentList WHERE hostName = '" + hostName + "' ORDER BY clickTime DESC";
            _rs = _stmt.executeQuery(_sql);
            while (_rs.next()) {
                recentListSite.add(_rs.getString("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recentListSite;
    }

    //////////////////////////////크롤링한 댓글들 DB에 넣는 관련 메소드
    public void insertCommentDB(String albumID, int order, String comment, String pwd){
        try {
            _sql = "INSERT INTO commentList VALUES (?, ?, ?, ?)";
            _pstmt = _con.prepareStatement(_sql);
            _pstmt.setString(1, albumID);
            _pstmt.setInt(2, order);
            _pstmt.setString(3, comment);
            _pstmt.setString(4, pwd);
            _pstmt.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
    public void deleteDB(String albumId, String pwd){
        try {
            _sql = "DELETE FROM commentList WHERE albumId = ? AND pwd = ?";
            _pstmt = _con.prepareStatement(_sql);
            _pstmt.setString(1, albumId);
            _pstmt.setString(2, pwd);
            _pstmt.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
    public void deleteCommentDB(String albumID){
        try {
            _sql = "DELETE FROM commentList WHERE albumID = ?";
            _pstmt = _con.prepareStatement(_sql);
            _pstmt.setString(1, albumID);
            _pstmt.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
    public ResultSet getCommentInfo(String albumID){
        try {
            _stmt = _con.createStatement();
            _sql = "SELECT * FROM commentList WHERE albumID = '" + albumID + "'";
            _rs = _stmt.executeQuery(_sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return _rs;
    }

    //////////////////////////////차트를 DB에 넣는 관련 메소드
    public ResultSet getSongInfo(String title, int siteNum){
        try {
            _stmt = _con.createStatement();
            _sql = "SELECT * FROM chartInfo WHERE title = '" + title + "' AND siteNum = " + siteNum + "";
            _rs = _stmt.executeQuery(_sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return _rs;
    }
    public void insertChartDB(String title, String artist, String albumName, int siteNum, String albumId){
        try {
            _sql = "INSERT INTO chartInfo VALUES (?, ?, ?, ?, ?)";
            _pstmt = _con.prepareStatement(_sql);
            _pstmt.setString(1, title);
            _pstmt.setString(2, artist);
            _pstmt.setString(3, albumName);
            _pstmt.setInt(4, siteNum);
            _pstmt.setString(5, albumId);
            _pstmt.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    //////////////////////////////CommentDB를 읽어오는 관련 메소드
    public ArrayList<String> getAlbumId(String title){
        ArrayList<String> albumId = new ArrayList<String>();
        try {
            _stmt = _con.createStatement();
            _sql = "SELECT albumId FROM chartInfo WHERE title = '" + title + "'";
            _rs = _stmt.executeQuery(_sql);
            while (_rs.next()) {
                albumId.add(_rs.getString("albumId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return albumId;
    }
    public ArrayList<CommentDTO> readCommentDB(ArrayList<String> albumIdList){
        ArrayList<CommentDTO> commentDTO = new ArrayList<CommentDTO>();
        for (String albumId : albumIdList) {
            try {
                _stmt = _con.createStatement();
                _sql = "SELECT * FROM commentList WHERE albumId = '" + albumId + "'";
                _rs = _stmt.executeQuery(_sql);
                while (_rs.next()) {
                    CommentDTO list = new CommentDTO();
                    list.setComment(_rs.getString("comment"));
                    list.setPassword(_rs.getString("pwd"));
                    commentDTO.add(list);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return commentDTO;
    }
}