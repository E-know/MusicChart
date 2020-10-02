package controller;

import notsort.AppManager;
import notsort.MakeComment;
import view.ChartPrimaryPanel;
import view.SitePanel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChartPrimaryPanelController {

    private SitePanel pnlSitePanel;
    private JButton btnRefresh, btnSite_M, btnSite_B, btnSite_G;
    private JLabel lblTime;
    private JComboBox<String> strCombo;

    private String[] strSearchCategory = {"Name", "Artist"};

    private JTextField txtSearch;

    LocalDateTime current = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    String formatted_Melon = current.format(formatter);
    String formatted_Bugs = current.format(formatter);
    String formatted_Genie = current.format(formatter);
    //refreshTime


    private class ButtonListener implements ActionListener {
        private Component _viewLoading;
        public ButtonListener() { }
        public ButtonListener(Component parentComponent){
            _viewLoading = parentComponent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Object obj = e.getSource();
            if (obj == btnRefresh) {
                current = LocalDateTime.now();
                formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                switch (AppManager.getS_instance().getSite_M_B_G()){
                    case 1:
                        formatted_Melon = current.format(formatter);
                        lblTime.setText("Renewal time : " + formatted_Melon);
                        AppManager.getS_instance().setSite_M_B_G(1);
                        AppManager.getS_instance().DataPassing(_viewLoading);
                        System.out.println("why?");
                        break;
                    case 2:
                        formatted_Bugs = current.format(formatter);
                        lblTime.setText("Renewal time : " + formatted_Bugs);
                        AppManager.getS_instance().setSite_M_B_G(2);
                        AppManager.getS_instance().DataPassing(_viewLoading);
                        break;
                    case 3:
                        formatted_Genie = current.format(formatter);
                        lblTime.setText("Renewal time : " + formatted_Genie);
                        AppManager.getS_instance().setSite_M_B_G(3);
                        AppManager.getS_instance().DataPassing(_viewLoading);
                        break;
                }
            }//refresh 새로 파싱해옴//파싱시간도 갱신
            if (obj == btnSite_M) {
                if(AppManager.getS_instance().getSite_M_B_G() == 1) return;
                AppManager.getS_instance().setSite_M_B_G(1);
                if(!AppManager.getS_instance().getParser().isParsed()) AppManager.getS_instance().DataPassing(_viewLoading);
                System.out.println("Melon");
                pnlSitePanel.changeData();
                lblTime.setText("Renewal time : " + formatted_Melon);
                txtSearch.setText("");
                pnlSitePanel.filter(null,2);
            }
            if (obj == btnSite_B) {
                if(AppManager.getS_instance().getSite_M_B_G() == 2) return;
                AppManager.getS_instance().setSite_M_B_G(2);
                if(!AppManager.getS_instance().getParser().isParsed()) AppManager.getS_instance().DataPassing(_viewLoading);
                System.out.println("Bugs");
                pnlSitePanel.changeData();
                lblTime.setText("Renewal time : " + formatted_Bugs);
                txtSearch.setText("");
                pnlSitePanel.filter(null,2);
            }
            if (obj == btnSite_G) {
                if(AppManager.getS_instance().getSite_M_B_G() == 3) return;
                AppManager.getS_instance().setSite_M_B_G(3);
                if(!AppManager.getS_instance().getParser().isParsed()) AppManager.getS_instance().DataPassing(_viewLoading);
                System.out.println("Genie");
                pnlSitePanel.changeData();
                lblTime.setText("Renewal time : " + formatted_Genie);
                txtSearch.setText("");
                pnlSitePanel.filter(null,2);
            }
            //멜론, 벅스, 지니 버튼에 따라 저장해 놓은 데이터를 새로 갖고옴
            //저장되있던 불러온 시간도 같이 갖고옴
        }
    }//ButtonListener

    private class KeyActionListener implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) { }

        @Override
        public void keyPressed(KeyEvent e) { }

        @Override
        public void keyReleased(KeyEvent e) {
            Object obj = e.getSource();

            if(obj == txtSearch){
                //strSearchCategory = {"Name", "Artist"};
                if(0 == strCombo.getSelectedIndex())//Name
                    pnlSitePanel.filter(txtSearch.getText(),2);
                if(1 == strCombo.getSelectedIndex())//Artist
                    pnlSitePanel.filter(txtSearch.getText(),3);
            }//comboBox 0, 1일때 sitepanel의 filter에서 검색

        }//KeyReleased

    }//KeyListener
}
