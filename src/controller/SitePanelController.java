package controller;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Comparator;
import javax.swing.*;
import javax.swing.table.*;

import notsort.AppManager;
import notsort.MusicChartParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import view.SitePanel;

public class SitePanelController {

    //음악 차트를 담는 표
    private JTable tableChart;

    //표의 모델(셀의 크기, 개수, 표시 자료형 등을 결정)
    private SitePanel.ChartModel tableModel;

    private class ClickListener implements MouseListener {
        @Override
        public void mouseEntered(MouseEvent e) { }
        @Override
        public void mouseExited(MouseEvent e) { }
        @Override
        public void mouseClicked(MouseEvent e) {
            Object obj = e.getSource();
            if(obj == tableChart) {
                JTable table = (JTable) obj;
                Object[] music = tableModel.getMusicData(table.convertRowIndexToModel(table.getSelectedRow())); //클릭된 열의 위치(숨겨진 항목이 있어도 바뀌지 않는 절대적인 위치)에 있는 곡 선택
                System.out.println(music[2] + music[0].toString()); //테스트
                AppManager.getS_instance().PopUpCommentUI(Integer.parseInt(music[0].toString())); //선택된 곡에 대한 커뮤니티 표시
            }
        }
        @Override
        public void mousePressed(MouseEvent e) { }
        @Override
        public void mouseReleased(MouseEvent e) { }
    } //ClickListener 클래스 끝
}
