package DB;

import java.sql.*;

public class ConnectDB {
    public static Connection GetDB() {
        Connection con = null;
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet rs;
        String url = "jdbc:mysql://database-2.cqcrpm8zqs1t.ap-northeast-2.rds.amazonaws.com:3306/charts_db?&serverTimezone=Asia/Seoul&useSSL=false";
        String userid = "admin";
        String pwd = "refactoring";
        // 1.����̹� �ε�
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("����̹� �ε� ����");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 2.����
        try {
            System.out.println("�����ͺ��̽� ���� �غ�...");
            con = DriverManager.getConnection(url, userid, pwd);
            System.out.println("�����ͺ��̽� ���� ����");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return con;
    }
}
