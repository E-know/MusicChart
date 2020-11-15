package DB;

import main.AppManager;
import view.CommentPanel;

import java.sql.*;
import java.util.ArrayList;

public class ConnectDB {
    PreparedStatement _pstmt = null;
    ResultSet _rs = null;
    Statement _stmt = null;

    public static Connection getDB() {
        Connection con = null;

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
            con = DriverManager.getConnection(url, userid, pwd);
            System.out.println("데이터베이스 연결 성공");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return con;
    }

    public void insertDB(String title, String artist, String albumName, int siteNum, String comment, String pwd){
        try {
            String sql = "INSERT INTO songinfo VALUES (?, ?, ?, ?, ?, ?)";
            _pstmt = getDB().prepareStatement(sql);
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
            _pstmt = getDB().prepareStatement(sql);
            _pstmt.setString(1, title);
            _pstmt.setString(2, pwd);
            _pstmt.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
    public ArrayList<String> readCommentDB(String title){
        ArrayList<String> comment = new ArrayList<String>();
        try {
            _stmt = getDB().createStatement();
            String sql = "SELECT comment FROM songinfo WHERE title = '" + title + "'";
            _rs = _stmt.executeQuery(sql);
            while (_rs.next()) {
                comment.add(_rs.getString("comment"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comment;
    }
    public ArrayList<String> readPwdDB(String title){
        ArrayList<String> password = new ArrayList<String>();
        try {
            _stmt = getDB().createStatement();
            String sql = "SELECT pwd FROM songinfo WHERE title = '" + title + "'";
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