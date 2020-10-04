package notsort;

import java.sql.*;

public class ConnectDB {
    public static Connection GetDB() {
        Connection con = null;
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet rs;
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

        try{
            stmt = con.createStatement();
            String sql = "SELECT table_name FROM information_schema.tables where table_schema = 'charts_db' and table_name = 'songinfo'";
            rs = stmt.executeQuery(sql);

            if(!rs.next()){
                System.out.println("�����ͺ��̽� ���̺� ����");
                //���̺��� ���ٸ� ����

                sql = "create table songinfo ("
                        + "`title` varchar(100) COLLATE utf8_bin NOT NULL,"
                        + "`artist` varchar(100) COLLATE utf8_bin NOT NULL,"
                        + "`albumname` varchar(100) COLLATE utf8_bin NOT NULL,"
                        + "`siteid` int NOT NULL,"
                        + "`comment` varchar(100) COLLATE utf8_bin NOT NULL,"
                        + "`pwd` varchar(8) COLLATE utf8_bin NOT NULL"
                        + ")";
                boolean rs2 = stmt.execute(sql);
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return con;
    }
}
