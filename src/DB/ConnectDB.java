package DB;

import main.AppManager;
import model.ChartData;
import view.CommentPanel;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import org.json.simple.JSONArray;

public class ConnectDB {
    PreparedStatement _pstmt = null;
    ResultSet _rs = null;
    Statement _stmt = null;
    Connection _con = null;

    public void getDB() {
        String url = "jdbc:mysql://database-2.cqcrpm8zqs1t.ap-northeast-2.rds.amazonaws.com:3306/charts_db?&serverTimezone=Asia/Seoul&useSSL=false";
        String userid = "admin";
        String pwd = "refactoring";

        // 1.드라이버 로딩
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("드라이버 로드 성공");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // 2.연결
        try {
            System.out.println("데이터베이스 연결 준비...");
            _con = DriverManager.getConnection(url, userid, pwd);
            System.out.println("데이터베이스 연결 성공");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertDB(String title, String artist, String albumName, int siteNum, String comment, String pwd){
        try {
            String sql = "INSERT INTO chartInfo VALUES (?, ?, ?, ?, ?, ?)";
            _pstmt = _con.prepareStatement(sql);
            _pstmt.setString(1, title);
            _pstmt.setString(2, artist);
            _pstmt.setString(3, albumName);
            _pstmt.setInt(4, siteNum);
            _pstmt.setString(5, comment);
            _pstmt.setString(6, pwd);
            _pstmt.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
    public void deleteDB(String title, String pwd){
        try {
            String sql = "DELETE FROM songinfo WHERE title = ? AND pwd = ?";
            _pstmt = _con.prepareStatement(sql);
            _pstmt.setString(1, title);
            _pstmt.setString(2, pwd);
            _pstmt.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
//    public ArrayList<String> readCommentDB(String title){
//        ArrayList<String> comment = new ArrayList<String>();
//        try {
//            _stmt = _con.createStatement();
//            String sql = "SELECT comment FROM songinfo WHERE title = '" + title + "'";
//            _rs = _stmt.executeQuery(sql);
//            while (_rs.next()) {
//                comment.add(_rs.getString("comment"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return comment;
//    }
//    public ArrayList<String> readPwdDB(String title){
//        ArrayList<String> password = new ArrayList<String>();
//        try {
//            _stmt = _con.createStatement();
//            String sql = "SELECT pwd FROM songinfo WHERE title = '" + title + "'";
//            _rs = _stmt.executeQuery(sql);
//            while (_rs.next()) {
//                password.add(_rs.getString("pwd"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return password;
//    }


    ////////////////////////////////////////////////////////////////////////////////////
    public void insertRecentListDB(String title, String hostName){
        try {
            String sql = "INSERT INTO recentList VALUES (?, ?)";
            _pstmt = _con.prepareStatement(sql);
            _pstmt.setString(1, title);
            _pstmt.setString(2, hostName);
            _pstmt.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
    public ArrayList<Integer> readRecentList(String hostName){
        ArrayList<Integer> songList = new ArrayList<>();
        try {
            _stmt = _con.createStatement();
            String sql = "SELECT title FROM recentList WHERE hostName = '" + hostName + "'";
            _rs = _stmt.executeQuery(sql);
            while (_rs.next()) {
                songList.add(_rs.getInt("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return songList;
    }
    public void deleteRecentDB(String hostName){
        try {
            String sql = "DELETE FROM recentList WHERE hostName = ?";
            _pstmt = _con.prepareStatement(sql);
            _pstmt.setString(1, hostName);
            _pstmt.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public void insertCommentDB(String albumID, int order, String comment, String pwd){
        try {
            String sql = "INSERT INTO commentList VALUES (?, ?, ?, ?)";
            _pstmt = _con.prepareStatement(sql);
            _pstmt.setString(1, albumID);
            _pstmt.setInt(2, order);
            _pstmt.setString(3, comment);
            _pstmt.setString(4, pwd);
            _pstmt.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
    public void deleteCommentDB(String albumID){
        try {
            String sql = "DELETE FROM commentList WHERE albumID = ?";
            _pstmt = _con.prepareStatement(sql);
            _pstmt.setString(1, albumID);
            _pstmt.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public ResultSet getCommentInfo(String albumID){
        try {
            _stmt = _con.createStatement();
            String sql = "SELECT * FROM commentList WHERE albumID = '" + albumID + "'";
            _rs = _stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return _rs;
    }
    public ResultSet getSongInfo(String title, int siteNum){
        try {
            _stmt = _con.createStatement();
            String sql = "SELECT * FROM chartInfo WHERE title = '" + title + "' AND siteNum = " + siteNum + "";
            _rs = _stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return _rs;
    }
    public void insertChartDB(String title, String artist, String albumName, int siteNum, String albumId){
        try {
            String sql = "INSERT INTO chartInfo VALUES (?, ?, ?, ?, ?)";
            _pstmt = _con.prepareStatement(sql);
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
    public ArrayList<String> getAlbumId(String title){
        ArrayList<String> albumId = new ArrayList<String>();
        try {
            _stmt = _con.createStatement();
            String sql = "SELECT albumId FROM chartInfo WHERE title = '" + title + "'";
            _rs = _stmt.executeQuery(sql);
            while (_rs.next()) {
                albumId.add(_rs.getString("albumId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return albumId;
    }
    public ArrayList<String> readCommentDB(String albumId){
        ArrayList<String> comment = new ArrayList<String>();
        try {
            _stmt = _con.createStatement();
            System.out.println("albumID : "+albumId);
            String sql = "SELECT comment FROM commentList WHERE albumId = '" + albumId + "'";
            _rs = _stmt.executeQuery(sql);
            while (_rs.next()) {
                comment.add(_rs.getString("comment"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comment;
    }
    public ArrayList<String> readPwdDB(String albumId){
        ArrayList<String> password = new ArrayList<String>();
        try {
            _stmt = _con.createStatement();
            String sql = "SELECT pwd FROM commentList WHERE albumId = '" + albumId + "'";
            _rs = _stmt.executeQuery(sql);
            while (_rs.next()) {
                password.add(_rs.getString("pwd"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return password;
    }

}