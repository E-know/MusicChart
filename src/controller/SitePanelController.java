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
import view.ChartPrimaryPanel;
import view.SitePanel;

public class SitePanelController {

    private SitePanel theSitePanel;

    public SitePanelController(SitePanel theSitePanel) {
        this.theSitePanel = theSitePanel;
        this.theSitePanel.addClickListener(new addClickListener());
    }
    private class addClickListener implements MouseListener {
        private Component _viewLoading;
        public addClickListener() { }
        public addClickListener(Component parentComponent){
            _viewLoading = parentComponent;
        }

        @Override
        public void mouseEntered(MouseEvent e) { }
        @Override
        public void mouseExited(MouseEvent e) { }
        @Override
        public void mouseClicked(MouseEvent e) {
            Object obj = e.getSource();
            if(obj == theSitePanel.tableChart) {
                JTable table = (JTable) obj;
                Object[] music = theSitePanel.tableModel.getMusicData(table.convertRowIndexToModel(table.getSelectedRow())); //Ŭ���� ���� ��ġ(������ �׸��� �־ �ٲ��� �ʴ� �������� ��ġ)�� �ִ� �� ����
                System.out.println(music[2] + music[0].toString()); //�׽�Ʈ
                AppManager.getS_instance().PopUpCommentUI(Integer.parseInt(music[0].toString())); //���õ� � ���� Ŀ�´�Ƽ ǥ��
            }
        }
        @Override
        public void mousePressed(MouseEvent e) { }
        @Override
        public void mouseReleased(MouseEvent e) { }
    } //addClickListener
}
