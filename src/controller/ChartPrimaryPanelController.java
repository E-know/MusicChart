package controller;

import notsort.AppManager;
import view.ChartPrimaryPanel;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChartPrimaryPanelController {

    private ChartPrimaryPanel the_Chart_PrimaryPanel;

    public ChartPrimaryPanelController(ChartPrimaryPanel theChartPrimaryPanel) {
        this.the_Chart_PrimaryPanel = theChartPrimaryPanel;
        this.the_Chart_PrimaryPanel.addBtnRefreshListener(new ButtonRefreshListener());
        this.the_Chart_PrimaryPanel.addBtnMelonListener(new ButtonMelonListener());
        this.the_Chart_PrimaryPanel.addBtnBugsListener(new ButtonBugsListener());
        this.the_Chart_PrimaryPanel.addBtnGenieListener(new ButtonGenieListener());
        this.the_Chart_PrimaryPanel.addKeyActionListener(new KeyActionListener());
    }


    private class ButtonRefreshListener implements ActionListener {
        private Component view_Loading;
        public ButtonRefreshListener() { }
        public ButtonRefreshListener(Component parentComponent){
            view_Loading = parentComponent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            the_Chart_PrimaryPanel.current = LocalDateTime.now();
            the_Chart_PrimaryPanel.formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            switch (AppManager.getS_instance().getSite_M_B_G()){
                case 1:
                    the_Chart_PrimaryPanel.formatted_Melon = the_Chart_PrimaryPanel.current.format(the_Chart_PrimaryPanel.formatter);
                    the_Chart_PrimaryPanel.lblTime.setText("Renewal time : " + the_Chart_PrimaryPanel.formatted_Melon);
                    AppManager.getS_instance().setSite_M_B_G(1);
                    AppManager.getS_instance().DataPassing(view_Loading);
                    System.out.println("why?");
                    break;
                case 2:
                    the_Chart_PrimaryPanel.formatted_Bugs = the_Chart_PrimaryPanel.current.format(the_Chart_PrimaryPanel.formatter);
                    the_Chart_PrimaryPanel.lblTime.setText("Renewal time : " + the_Chart_PrimaryPanel.formatted_Bugs);
                    AppManager.getS_instance().setSite_M_B_G(2);
                    AppManager.getS_instance().DataPassing(view_Loading);
                    break;
                case 3:
                    the_Chart_PrimaryPanel.formatted_Genie = the_Chart_PrimaryPanel.current.format(the_Chart_PrimaryPanel.formatter);
                    the_Chart_PrimaryPanel.lblTime.setText("Renewal time : " + the_Chart_PrimaryPanel.formatted_Genie);
                    AppManager.getS_instance().setSite_M_B_G(3);
                    AppManager.getS_instance().DataPassing(view_Loading);
                    break;
            }
        }
    }//ButtonRefreshListener

    private class ButtonMelonListener implements ActionListener {
        private Component view_Loading;
        public ButtonMelonListener() { }
        public ButtonMelonListener(Component parentComponent){
            view_Loading = parentComponent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if(AppManager.getS_instance().getSite_M_B_G() == 1) return;
            AppManager.getS_instance().setSite_M_B_G(1);
            if(!AppManager.getS_instance().getParser().isParsed()) AppManager.getS_instance().DataPassing(view_Loading);
            System.out.println("Melon");
            the_Chart_PrimaryPanel.pnlSitePanel.changeData();
            the_Chart_PrimaryPanel.lblTime.setText("Renewal time : " + the_Chart_PrimaryPanel.formatted_Melon);
            the_Chart_PrimaryPanel.txtSearch.setText("");
            the_Chart_PrimaryPanel.pnlSitePanel.filter(null,2);
        }
    }//ButtonMelonListener

    private class ButtonBugsListener implements ActionListener {
        private Component view_Loading;
        public ButtonBugsListener() { }
        public ButtonBugsListener(Component parentComponent){
            view_Loading = parentComponent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if(AppManager.getS_instance().getSite_M_B_G() == 2) return;
            AppManager.getS_instance().setSite_M_B_G(2);
            if(!AppManager.getS_instance().getParser().isParsed()) AppManager.getS_instance().DataPassing(view_Loading);
            System.out.println("Bugs");
            the_Chart_PrimaryPanel.pnlSitePanel.changeData();
            the_Chart_PrimaryPanel.lblTime.setText("Renewal time : " + the_Chart_PrimaryPanel.formatted_Bugs);
            the_Chart_PrimaryPanel.txtSearch.setText("");
            the_Chart_PrimaryPanel.pnlSitePanel.filter(null,2);
        }
    }//ButtonBugsListener

    private class ButtonGenieListener implements ActionListener {
        private Component view_Loading;
        public ButtonGenieListener() { }
        public ButtonGenieListener(Component parentComponent){
            view_Loading = parentComponent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if(AppManager.getS_instance().getSite_M_B_G() == 3) return;
            AppManager.getS_instance().setSite_M_B_G(3);
            if(!AppManager.getS_instance().getParser().isParsed()) AppManager.getS_instance().DataPassing(view_Loading);
            System.out.println("Genie");
            the_Chart_PrimaryPanel.pnlSitePanel.changeData();
            the_Chart_PrimaryPanel.lblTime.setText("Renewal time : " + the_Chart_PrimaryPanel.formatted_Genie);
            the_Chart_PrimaryPanel.txtSearch.setText("");
            the_Chart_PrimaryPanel.pnlSitePanel.filter(null,2);
        }
    }//ButtonGenieListener

    private class KeyActionListener implements KeyListener{
        private Component view_Loading;
        public KeyActionListener() { }
        public KeyActionListener(Component parentComponent){
            view_Loading = parentComponent;
        }

        @Override
        public void keyTyped(KeyEvent e) { }

        @Override
        public void keyPressed(KeyEvent e) { }

        @Override
        public void keyReleased(KeyEvent e) {
            Object obj = e.getSource();

            if(obj == the_Chart_PrimaryPanel.txtSearch){
                //strSearchCategory = {"Name", "Artist"};
                if(0 == the_Chart_PrimaryPanel.strCombo.getSelectedIndex())//Name
                    the_Chart_PrimaryPanel.pnlSitePanel.filter(the_Chart_PrimaryPanel.txtSearch.getText(),2);
                if(1 == the_Chart_PrimaryPanel.strCombo.getSelectedIndex())//Artist
                    the_Chart_PrimaryPanel.pnlSitePanel.filter(the_Chart_PrimaryPanel.txtSearch.getText(),3);
            }//comboBox 0, 1일때 sitepanel의 filter에서 검색

        }//KeyReleased

    }//KeyListener
}
