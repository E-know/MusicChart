package model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class ChartModel extends AbstractTableModel {
    // - - - - - �ν��Ͻ� ������ - - - - -
    //������ �� �̸��� �����ϴ� �迭
    private String[] arrColumnName;
    //������ ���� �׸��� �����ϴ� �迭
    private Object[][] chartData;

    // - - - - - ������ - - - - -
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
            chartData[i][0] = Integer.parseInt((String) (obj.get("rank")));
            try {
                ImageIcon loadedImage = new ImageIcon(new URL((String) obj.get("smallImageUrl"))); //������ URL�κ��� �̹����� �޾ƿ�
                chartData[i][1] = new ImageIcon(loadedImage.getImage().getScaledInstance(50, 50, Image.SCALE_FAST)); //���� �̹����� 50 * 50 ũ��� ��ȯ�Ͽ� ���
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