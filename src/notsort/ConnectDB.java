package notsort;

import java.sql.*;

public class ConnectDB {
    public Statement stmt;
    public PreparedStatement pstmt = null;

    public ResultSet rs;

    public static Connection GetDB() {
        Connection con = null;
        String url = "jdbc:mysql://127.0.0.1:3306/charts_db?&serverTimezone=Asia/Seoul&useSSL=false";
        String userid = "root";
        String pwd = "root";
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
