package controller;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

import notsort.AppManager;
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
                Object[] music = theSitePanel.tableModel.getMusicData(table.convertRowIndexToModel(table.getSelectedRow())); //클릭된 열의 위치(숨겨진 항목이 있어도 바뀌지 않는 절대적인 위치)에 있는 곡 선택
                System.out.println(music[2] + music[0].toString()); //테스트
                AppManager.getS_instance().PopUpCommentUI(Integer.parseInt(music[0].toString())); //선택된 곡에 대한 커뮤니티 표시
            }
        }
        @Override
        public void mousePressed(MouseEvent e) { }
        @Override
        public void mouseReleased(MouseEvent e) { }
    } //addClickListener
}
