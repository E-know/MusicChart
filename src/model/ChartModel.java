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
    private String[] _arrColumnName;
    //각각의 셀의 항목을 저장하는 배열
    private Object[][] _chartData;

    // - - - - - 생성자 - - - - -
    public ChartModel(JSONArray musics) {
        _arrColumnName = new String[5];
        _arrColumnName[0] = "Rank";
        _arrColumnName[1] = "Album Image";
        _arrColumnName[2] = "Title";
        _arrColumnName[3] = "Singer";
        _arrColumnName[4] = "Album Title";

        _chartData = new Object[musics.size()][5];
        for (int i = 0; i < musics.size(); i++) _chartData[i] = new Object[5];
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
            _chartData[i][0] = Integer.parseInt((String) (obj.get("rank")));
            try {
                ImageIcon loadedImage = new ImageIcon(new URL((String) obj.get("smallImageUrl"))); //지정된 URL로부터 이미지를 받아옴
                _chartData[i][1] = new ImageIcon(loadedImage.getImage().getScaledInstance(50, 50, Image.SCALE_FAST)); //받은 이미지를 50 * 50 크기로 변환하여 사용
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            _chartData[i][2] = (String) obj.get("title");
            _chartData[i][3] = (String) obj.get("artist");
            _chartData[i][4] = (String) obj.get("albumName");
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