package controller;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

import notsort.AppManager;
import view.SitePanel;

public class SitePanelController {

    private SitePanel the_Site_Panel;

    public SitePanelController(SitePanel theSitePanel) {
        this.the_Site_Panel = theSitePanel;
        this.the_Site_Panel.addClickListener(new addClickListener());
    }
    private class addClickListener implements MouseListener {
        private Component view_Loading;
        public addClickListener() { }
        public addClickListener(Component parentComponent){
            view_Loading = parentComponent;
        }

        @Override
        public void mouseEntered(MouseEvent e) { }
        @Override
        public void mouseExited(MouseEvent e) { }
        @Override
        public void mouseClicked(MouseEvent e) {
            Object obj = e.getSource();
            if(obj == the_Site_Panel.tableChart) {
                JTable table = (JTable) obj;
                Object[] music = the_Site_Panel.tableModel.getMusicData(table.convertRowIndexToModel(table.getSelectedRow())); //Ŭ���� ���� ��ġ(������ �׸��� �־ �ٲ��� �ʴ� �������� ��ġ)�� �ִ� �� ����
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
