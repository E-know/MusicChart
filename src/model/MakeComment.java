package model;

import DB.ConnectDB;
import org.json.simple.JSONArray;

import java.sql.*;
import notsort.AppManager;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class MakeComment {
    Connection con = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;

    public MakeComment(Component parentComponent) {
        makeRandomCommentDB(parentComponent);
    }//MakeComment | Constructor

    private void makeRandomCommentDB(Component parentComponent) {
        con = ConnectDB.GetDB();
        String singer, title, albumName, sqltitle;
        String comment = "";
        String passwd = "";
        int Orig = AppManager.getS_instance().getSite_M_B_G();

            AppManager.getS_instance().setSite_M_B_G(j);
            AppManager.getS_instance().DataPassing(parentComponent);
            for (int k = 1; k <= 100; k++) {
                title = AppManager.getS_instance().getParser().getTitle(k);
                singer = AppManager.getS_instance().getParser().getArtistName(k);
                albumName = AppManager.getS_instance().getParser().getAlbumName(k);
                sqltitle = title;
                if(sqltitle.contains("'")){
                    sqltitle = sqltitle.replace("'",":");
                }
                try {
                    stmt = con.createStatement();
                    String sql = "SELECT * FROM songinfo WHERE title = '" + sqltitle + "' AND siteid = " + j + "";
                    ResultSet rs = stmt.executeQuery(sql);
                    if (!rs.next()) {
                        int rndNum1 = (int) (Math.random() * 5) + 1;
                        int rndNum2;
                        // rndNum1 = How many make comment
                        // rndNum2 = What comment

                        for (int i = 0; i < rndNum1; i++) {
                            rndNum2 = (int) (Math.random() * 6) + 1;
                            switch (rndNum2 % 10) {
                                case 1: {
                                    comment = singer + "는 역시 믿고 들어야지";
                                    break;
                                }//case 1
                                case 2: {
                                    comment = "이번 " + title + " 너무 좋은 것 같아요";
                                    break;
                                }//case 2
                                case 3: {
                                    comment = "이번 앨범 " + albumName + " 너무 좋아요";
                                    break;
                                }
                                case 4: {
                                    comment = "앨범 발매일만 기다렸습니다!";
                                    break;
                                }
                                case 5: {
                                    comment = "5252~" + singer + " 기다렸다구";
                                    break;
                                }
                                case 6: {
                                    comment = "'" + albumName + "'앨범 수록된 노래 다 좋은 것 같아요.";
                                    break;
                                }
                            }//switch
                            Random rand = new Random();
                            passwd = "";
                            for (int p = 0; p < 4; p++) {
                                //0~9 까지 난수 생성
                                String ran = Integer.toString(rand.nextInt(10));
                                passwd += ran;
                            }//4자리 비밀번호 설정
                            try {
                                sql = "INSERT INTO songinfo VALUES (?, ?, ?, ?, ?, ?)";
                                pstmt = con.prepareStatement(sql);
                                pstmt.setString(1, sqltitle);
                                pstmt.setString(2, singer);
                                pstmt.setString(3, albumName);
                                pstmt.setInt(4, j);
                                pstmt.setString(5, comment);
                                pstmt.setString(6, passwd);
                                pstmt.executeUpdate();
                            } catch (Exception e1) {
                                System.out.println("쿼리 읽기 실패 :" + e1);
                            }
                        }//for
                    }
                } catch (Exception e) {
                    System.out.println(title + singer + albumName);
                    System.out.println("쿼리 읽기 실패 :" + e);
                }
            }//for(k)
        }//for(j)
        AppManager.getS_instance().setSite_M_B_G(Orig);
    }
}//MakeComment
