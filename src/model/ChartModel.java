package model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class ChartModel extends AbstractTableModel {
    // - - - - - 인스턴스 데이터 - - - - -
    //각각의 행 이름을 저장하는 배열
    private String[] arrColumnName;
    //각각의 셀의 항목을 저장하는 배열
    private Object[][] chartData;

    // - - - - - 생성자 - - - - -
    public ChartModel(JSONArray musics) {
        arrColumnName = new String[5];
        arrColumnName[0] = "Rank";
        arrColumnName[1] = "Album Image";
        arrColumnName[2] = "Title";
        arrColumnName[3] = "Singer";
        arrColumnName[4] = "Album Title";

        chartData = new Object[musics.size()][5];
        for (int i = 0; i < musics.size(); i++) chartData[i] = new Object[5];
        setContents(musics);
    } //생성자 끝

    /*
    Name: setContents
    Parameter: (JSONArray) 항목이 들어있음
    Returns: -
    Description: 표에서 표시할 항목 설정
    */
    public void setContents(JSONArray musics) {
        for (int i = 0; i < musics.size(); i++) {
            JSONObject obj = (JSONObject) musics.get(i);
            chartData[i][0] = Integer.parseInt((String) (obj.get("rank")));
            try {
                ImageIcon loadedImage = new ImageIcon(new URL((String) obj.get("smallImageUrl"))); //지정된 URL로부터 이미지를 받아옴
                chartData[i][1] = new ImageIcon(loadedImage.getImage().getScaledInstance(50, 50, Image.SCALE_FAST)); //받은 이미지를 50 * 50 크기로 변환하여 사용
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            chartData[i][2] = (String) obj.get("title");
            chartData[i][3] = (String) obj.get("artist");
            chartData[i][4] = (String) obj.get("albumName");
        }
    }

    public Object[][] getChartData() {
        return chartData;
    }

    public Object[] getMusicData(int index) {
        return chartData[index];
    }

    @Override
    public int getColumnCount() {
        return arrColumnName.length;
    }

    @Override
    public int getRowCount() {
        return chartData.length;
    }

    @Override
    public Object getValueAt(int row, int column) {
        return chartData[row][column];
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        chartData[row][column] = value;
    }

    @Override
    public String getColumnName(int column) {
        return arrColumnName[column];
    }

    @Override
    public Class<?> getColumnClass(int column) {
        switch (column) {
            case 0: //순위
                return Integer.class;
            case 1: //이미지
                return ImageIcon.class;
            case 2: //제목
            case 3: //가수
            case 4: //앨범
                return String.class;
            default:
                return Object.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
} //ChartModel 클래스 끝