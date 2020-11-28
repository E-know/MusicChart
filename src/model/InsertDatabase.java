package model;

import DB.ConnectDB;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class InsertDatabase {
    ConnectDB DB = new ConnectDB();
    public void insertChartDatabase(Component parentComponent){
        DB.getDB();
        String title, artist, albumName, albumId;

        for (int i = 1; i <= 3; i++){
            ChartData.getS_instance().setSite_M_B_G(i);
            ChartData.getS_instance().DataPassing(parentComponent);
            for (int k = 1; k <= 100; k++) {
                title = ChartData.getS_instance().getParser().getTitle(k);
                artist = ChartData.getS_instance().getParser().getArtistName(k);
                albumName = ChartData.getS_instance().getParser().getAlbumName(k);
                albumId = ChartData.getS_instance().getParser().getAlbumID(k).replaceAll("[^0-9]", "");

                if(title.contains("'")){//노래 제목에 '가 들어간 경우 sql쿼리문에게 따로 처리를 해줘야함
                    title = title.replace("'",":");
                }
                if (title.contains(" ")) {//노래 제목에 공백이 들어간 경우 통일을 위해 따로 처리를 해줘야함
                    title = title.replace(" ", "");
                }
                if (title.contains("by")) {//노래 제목에 by가 들어간 경우 통일을 위해 따로 처리를 해줘야함
                    title = title.replace("by", "");
                }
                try {
                    if(!DB.getSongInfo(title, i).next()){
                        DB.insertChartDB(title, artist, albumName, i, albumId);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }//for(k)
        }//for(i)
    }

    public void insertCommentDatabase(Map<String, List<String>> albumAndComment){
        DB.getDB();

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
