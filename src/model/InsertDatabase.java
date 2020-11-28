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

                if(title.contains("'")){//�뷡 ���� '�� �� ��� sql���������� ���� ó���� �������
                    title = title.replace("'",":");
                }
                if (title.contains(" ")) {//�뷡 ���� ������ �� ��� ������ ���� ���� ó���� �������
                    title = title.replace(" ", "");
                }
                if (title.contains("by")) {//�뷡 ���� by�� �� ��� ������ ���� ���� ó���� �������
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
                        //����� �̹� ����Ǿ��ִ�
                        DB.deleteCommentDB(albumId);//����
                        DB.insertCommentDB(albumId, order, comment, makePassword());
                    }//�ֽ� ��۵鸸 �����ϱ� ���� ���
                    else{
                        DB.insertCommentDB(albumId, order, comment, makePassword());
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }//ũ�Ѹ��� ��۵� ����

    private String makePassword(){
        Random rand = new Random();
        String passwd = "";
        for (int p = 0; p < 4; p++) {
            //0~9 ���� ���� ����
            String ran = Integer.toString(rand.nextInt(10));
            passwd += ran;
        }//4�ڸ� ��й�ȣ ����
        return passwd;
    }
}
