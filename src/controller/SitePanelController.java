package controller;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

import main.AppManager;
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
                Object[] music = the_Site_Panel.tableModel.getMusicData(table.convertRowIndexToModel(table.getSelectedRow())); //클릭된 열의 위치(숨겨진 항목이 있어도 바뀌지 않는 절대적인 위치)에 있는 곡 선택
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
