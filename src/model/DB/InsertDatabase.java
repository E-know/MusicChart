package model.DB;

import model.ChartData;
import model.DB.ConnectDB;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class InsertDatabase {
    ConnectDB DB = new ConnectDB();
    public void insertChartDatabase(Component parentComponent){
        String title, artist, albumName, albumId;
        DB.connectionDB();
        System.out.println("1");

        for (int i = 1; i <= 3; i++){
            ChartData.getS_instance().setSite_M_B_G(i);
            ChartData.getS_instance().DataPassing(parentComponent);
            for (int k = 1; k <= 100; k++) {
                title = ChartData.getS_instance().getParser().getTitle(k);
                artist = ChartData.getS_instance().getParser().getArtistName(k);
                albumName = ChartData.getS_instance().getParser().getAlbumName(k);
                albumId = ChartData.getS_instance().getParser().getAlbumID(k).replaceAll("[^0-9]", "");

                try {
                    if(!DB.getSongInfo(replaceTitle(title), i).next()){
                        DB.insertChartDB(replaceTitle(title), artist, albumName, i, albumId);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }//for(k)
        }//for(i)
    }
    private String replaceTitle(String strTitle){
        //노래 제목이 사이트마다 다른 기호들의 경우 처리해줌
        if (strTitle.contains("'")) {
            strTitle = strTitle.replace("'", ":");
        }
        if (strTitle.contains(" ")) {
            strTitle = strTitle.replace(" ", "");
        }
        if (strTitle.contains("by")) {
            strTitle = strTitle.replace("by", "");
        }
        if (strTitle.contains(",")) {
            strTitle = strTitle.replace(",", "");
        }
        if (strTitle.contains("&")) {
            strTitle = strTitle.replace("&", "");
        }
        return strTitle;
    }

    public void insertCommentDatabase(Map<String, List<String>> albumAndComment){
        DB.connectionDB();
        for (String albumId : albumAndComment.keySet()){
            int order = 0;
            for (String comment : albumAndComment.get(albumId)){
                //System.out.println("key : " + key +" / value : " + str);
                try {
                    order++;
                    if(DB.getCommentInfo(albumId).next() && order == 1){
                        //댓글이 이미 저장되어있다
                        DB.deleteCommentDB(albumId);//삭제
                        DB.insertCommentDB(albumId, order, comment, makePassword());
                    }//최신 댓글들만 저장하기 위한 방법
                    else{
                        DB.insertCommentDB(albumId, order, comment, makePassword());
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }//크롤링한 댓글들 저장

    private String makePassword(){
        Random rand = new Random();
        String passwd = "";
        for (int p = 0; p < 4; p++) {
            //0~9 까지 난수 생성
            String ran = Integer.toString(rand.nextInt(10));
            passwd += ran;
        }//4자리 비밀번호 설정
        return passwd;
    }
}
