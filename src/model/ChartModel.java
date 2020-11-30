package model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.net.MalformedURLException;
import java.net.URL;

import model.DB.RecentListDTO;

public class ChartModel extends AbstractTableModel {
    // - - - - - �ν��Ͻ� ������ - - - - -
    //������ �� �̸��� �����ϴ� �迭
    private String[] _arrColumnName;
    //������ ���� �׸��� �����ϴ� �迭
    private Object[][] _chartData;

    // - - - - - ������ - - - - -
    public ChartModel(JSONArray musics) {
        _arrColumnName = new String[5];
        _arrColumnName[0] = "Rank";
        _arrColumnName[1] = "Album Image";
        _arrColumnName[2] = "Title";
        _arrColumnName[3] = "Singer";
        _arrColumnName[4] = "Album Title";

        _chartData = new Object[musics.size()][5];
        for (int i = 0; i < musics.size(); i++)
            _chartData[i] = new Object[5];
        setContents(musics);
    } //������ ��

    /*
    Name: setContents
    Parameter: (JSONArray) �׸��� �������
    Returns: -
    Description: ǥ���� ǥ���� �׸� ����
    */
    public void setContents(JSONArray musics) {
        for (int i = 0; i < musics.size(); i++) {
            JSONObject obj = (JSONObject) musics.get(i);
            _chartData[i][0] = Integer.parseInt((String) (obj.get("rank")));
            try {
                Image loadedImage = new ImageIcon(new URL((String) obj.get("smallImageUrl"))).getImage().getScaledInstance(50, 50, Image.SCALE_FAST); //������ URL�κ��� �̹����� �޾ƿ�
                _chartData[i][1] = new ImageIcon(loadedImage); //���� �̹����� 50 * 50 ũ��� ��ȯ�Ͽ� ���
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            _chartData[i][2] = obj.get("title").toString();
            _chartData[i][3] = obj.get("artist").toString();
            _chartData[i][4] = obj.get("albumName").toString();
        }
    }
//    public void setRecentContents(ArrayList<Integer> rankNum, ArrayList<Integer> siteNum) {
//        for(int i = 0; i < rankNum.size(); i++) {
//            ChartData.getS_instance().setSite_M_B_G(siteNum.get(i));
//            int rank = rankNum.get(i);
//            _chartData[i][0] = rank;
//            try {
//                ImageIcon loadedImage = new ImageIcon(new URL(ChartData.getS_instance().getParser().getImageUrl(rank))); //������ URL�κ��� �̹����� �޾ƿ�
//                _chartData[i][1] = new ImageIcon(loadedImage.getImage().getScaledInstance(50, 50, Image.SCALE_FAST)); //���� �̹����� 50 * 50 ũ��� ��ȯ�Ͽ� ���
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//            _chartData[i][2] = (String) ChartData.getS_instance().getParser().getTitle(rank);
//            _chartData[i][3] = (String) ChartData.getS_instance().getParser().getArtistName(rank);
//            _chartData[i][4] = (String) ChartData.getS_instance().getParser().getAlbumName(rank);
//        }
//    }

    public void setRecentContents(ArrayList<RecentListDTO> recentListDTO) {
        for(int i = 0; i < recentListDTO.size(); i++) {            ;
            ChartData.getS_instance().setSite_M_B_G(recentListDTO.get(i).getSiteNum());
            int rank = recentListDTO.get(i).getRank();
            _chartData[i][0] = rank;
            try {
                ImageIcon loadedImage = new ImageIcon(new URL(ChartData.getS_instance().getParser().getImageUrl(rank))); //������ URL�κ��� �̹����� �޾ƿ�
                _chartData[i][1] = new ImageIcon(loadedImage.getImage().getScaledInstance(50, 50, Image.SCALE_FAST)); //���� �̹����� 50 * 50 ũ��� ��ȯ�Ͽ� ���
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            _chartData[i][2] = (String) ChartData.getS_instance().getParser().getTitle(rank);
            _chartData[i][3] = (String) ChartData.getS_instance().getParser().getArtistName(rank);
            _chartData[i][4] = (String) ChartData.getS_instance().getParser().getAlbumName(rank);
        }
    }


    public Object[][] get_chartData() {
        return _chartData;
    }

    public Object[] getMusicData(int index) {
        return _chartData[index];
    }

    @Override
    public int getColumnCount() {
        return _arrColumnName.length;
    }

    @Override
    public int getRowCount() {
        return _chartData.length;
    }

    @Override
    public Object getValueAt(int row, int column) {
        return _chartData[row][column];
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        _chartData[row][column] = value;
    }

    @Override
    public String getColumnName(int column) {
        return _arrColumnName[column];
    }

    @Override
    public Class<?> getColumnClass(int column) {
        switch (column) {
            case 0: //����
                return Integer.class;
            case 1: //�̹���
                return ImageIcon.class;
            case 2: //����
            case 3: //����
            case 4: //�ٹ�
                return String.class;
            default:
                return Object.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
} //ChartModel Ŭ���� ��