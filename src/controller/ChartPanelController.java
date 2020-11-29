package controller;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

import main.AppManager;
import view.ChartPanel;

public class ChartPanelController {

    private ChartPanel theChartPanel;

    public ChartPanelController(ChartPanel TheChartPanel) {
        this.theChartPanel = TheChartPanel;
        this.theChartPanel.addClickListener(new addClickListener());
    }

    private class addClickListener implements MouseListener {
        private Component viewLoading;
        public addClickListener() { }
        public addClickListener(Component parentComponent){
            viewLoading = parentComponent;
        }

        @Override
        public void mouseEntered(MouseEvent e) { }
        @Override
        public void mouseExited(MouseEvent e) { }
        @Override
        public void mouseClicked(MouseEvent e) {

            Object obj = e.getSource();

            if(obj == theChartPanel._tableChart) {
                JTable table = (JTable) obj;
                Object[] music = theChartPanel._tableModel.getMusicData(table.convertRowIndexToModel(table.getSelectedRow())); //클릭된 열의 위치(숨겨진 항목이 있어도 바뀌지 않는 절대적인 위치)에 있는 곡 선택
                System.out.println(music[2] + music[0].toString()); //테스트
                PopUpCommentUI(Integer.parseInt(music[0].toString())); //선택된 곡에 대한 커뮤니티 표시
            }
        }
        @Override
        public void mousePressed(MouseEvent e) { }
        @Override
        public void mouseReleased(MouseEvent e) { }
    } //addClickListener

    public void PopUpCommentUI(int rank){
        AppManager.getS_instance().getPnlCommentPanel().popUpCommentPanel(rank);
        AppManager.getS_instance().getPnlCommentPanel().setVisible(true);
        AppManager.getS_instance().getChartPrimaryPanel().setVisible(false);
    }
}
