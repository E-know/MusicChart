package model;

import DB.ConnectDB;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class InsertDatabase {
    ConnectDB DB = new ConnectDB();
    public void insertChartDatabase(Component parentComponent){
        DB.getDB();
        String title, artist, albumName, sqltitle, albumId;

        for (int i = 1; i <= 3; i++){
            ChartData.getS_instance().setSite_M_B_G(i);
            ChartData.getS_instance().DataPassing(parentComponent);
            for (int k = 1; k <= 100; k++) {
                title = ChartData.getS_instance().getParser().getTitle(k);
                artist = ChartData.getS_instance().getParser().getArtistName(k);
                albumName = ChartData.getS_instance().getParser().getAlbumName(k);
                albumId = ChartData.getS_instance().getParser().getAlbumID(k);
                if(title == "������ �뷡"){
                    System.out.println(title);
                    System.out.println(artist);
                    System.out.println(albumName);
                    System.out.println(albumId);
                }
                sqltitle = title;

                if(sqltitle.contains("'")){//�뷡 ���� '�� �� ��� sql���������� ���� ó���� �������
                    //System.out.println("sqltitle1: "+sqltitle);
                    sqltitle = sqltitle.replace("'",":");
                    //System.out.println("sqltitle2: "+sqltitle);
                }
                try {
                    if(!DB.getSongInfo(sqltitle,i).next()){
                        DB.insertChartDB(sqltitle, artist, albumName, i, albumId);
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
                        //����� �̹� ����Ǿ��ִ�???
                        DB.deleteCommentDB(albumId);//�� ����
                        DB.insertCommentDB(albumId,order,comment,"0000");
                    }
                    else{
                        DB.insertCommentDB(albumId,order,comment,"0000");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}
