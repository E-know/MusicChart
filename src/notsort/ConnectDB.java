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

        try{
            stmt = con.createStatement();
            String sql = "SELECT table_name FROM information_schema.tables where table_schema = 'charts_db' and table_name = 'songinfo'";
            rs = stmt.executeQuery(sql);

            if(!rs.next()){
                System.out.println("데이터베이스 테이블 생성");
                //테이블이 없다면 생성

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
