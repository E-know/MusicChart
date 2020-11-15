package controller;

import model.ChartData;
import view.ChartPrimaryPanel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChartPrimaryPanelController {

    private ChartPrimaryPanel the_Chart_Primary_Panel;

    public ChartPrimaryPanelController(ChartPrimaryPanel theChartPrimaryPanel) {
        this.the_Chart_Primary_Panel = theChartPrimaryPanel;
        this.the_Chart_Primary_Panel.addBtnRefreshListener(new ButtonRefreshListener());
        this.the_Chart_Primary_Panel.addBtnMelonListener(new ButtonMelonListener());
        this.the_Chart_Primary_Panel.addBtnBugsListener(new ButtonBugsListener());
        this.the_Chart_Primary_Panel.addBtnGenieListener(new ButtonGenieListener());
        this.the_Chart_Primary_Panel.addKeyActionListener(new KeyActionListener());
    }


    private class ButtonRefreshListener implements ActionListener {
        private Component view_Loading;
        public ButtonRefreshListener() { }
        public ButtonRefreshListener(Component parentComponent){
            view_Loading = parentComponent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            the_Chart_Primary_Panel.current = LocalDateTime.now();
            the_Chart_Primary_Panel.formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            switch (ChartData.getS_instance().getSite_M_B_G()){
                case 1:
                    the_Chart_Primary_Panel.formatted_Melon = the_Chart_Primary_Panel.current.format(the_Chart_Primary_Panel.formatter);
                    the_Chart_Primary_Panel.lblTime.setText("Renewal time : " + the_Chart_Primary_Panel.formatted_Melon);
                    ChartData.getS_instance().setSite_M_B_G(1);
                    ChartData.getS_instance().DataPassing(view_Loading);
                    System.out.println("why?");
                    break;
                case 2:
                    the_Chart_Primary_Panel.formatted_Bugs = the_Chart_Primary_Panel.current.format(the_Chart_Primary_Panel.formatter);
                    the_Chart_Primary_Panel.lblTime.setText("Renewal time : " + the_Chart_Primary_Panel.formatted_Bugs);
                    ChartData.getS_instance().setSite_M_B_G(2);
                    ChartData.getS_instance().DataPassing(view_Loading);
                    break;
                case 3:
                    the_Chart_Primary_Panel.formatted_Genie = the_Chart_Primary_Panel.current.format(the_Chart_Primary_Panel.formatter);
                    the_Chart_Primary_Panel.lblTime.setText("Renewal time : " + the_Chart_Primary_Panel.formatted_Genie);
                    ChartData.getS_instance().setSite_M_B_G(3);
                    ChartData.getS_instance().DataPassing(view_Loading);
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

            if(ChartData.getS_instance().getSite_M_B_G() == 1) return;
            ChartData.getS_instance().setSite_M_B_G(1);
            if(!ChartData.getS_instance().getParser().isParsed()) ChartData.getS_instance().DataPassing(view_Loading);
            System.out.println("Melon");
            the_Chart_Primary_Panel.pnlChartPanel.changeData();
            the_Chart_Primary_Panel.lblTime.setText("Renewal time : " + the_Chart_Primary_Panel.formatted_Melon);
            the_Chart_Primary_Panel.txtSearch.setText("");
            the_Chart_Primary_Panel.pnlChartPanel.filterTitleANDArtist(null,2);
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

            if(ChartData.getS_instance().getSite_M_B_G() == 2) return;
            ChartData.getS_instance().setSite_M_B_G(2);
            if(!ChartData.getS_instance().getParser().isParsed()) ChartData.getS_instance().DataPassing(view_Loading);
            System.out.println("Bugs");
            the_Chart_Primary_Panel.pnlChartPanel.changeData();
            the_Chart_Primary_Panel.lblTime.setText("Renewal time : " + the_Chart_Primary_Panel.formatted_Bugs);
            the_Chart_Primary_Panel.txtSearch.setText("");
            the_Chart_Primary_Panel.pnlChartPanel.filterTitleANDArtist(null,2);
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

            if(ChartData.getS_instance().getSite_M_B_G() == 3) return;
            ChartData.getS_instance().setSite_M_B_G(3);
            if(!ChartData.getS_instance().getParser().isParsed()) ChartData.getS_instance().DataPassing(view_Loading);
            System.out.println("Genie");
            the_Chart_Primary_Panel.pnlChartPanel.changeData();
            the_Chart_Primary_Panel.lblTime.setText("Renewal time : " + the_Chart_Primary_Panel.formatted_Genie);
            the_Chart_Primary_Panel.txtSearch.setText("");
            the_Chart_Primary_Panel.pnlChartPanel.filterTitleANDArtist(null,2);
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

            if(obj == the_Chart_Primary_Panel.txtSearch){
                //strSearchCategory = {"Name", "Artist"};
                if(0 == the_Chart_Primary_Panel.strCombo.getSelectedIndex())//Name
                    the_Chart_Primary_Panel.pnlChartPanel.filterTitleANDArtist(the_Chart_Primary_Panel.txtSearch.getText(),2);
                if(1 == the_Chart_Primary_Panel.strCombo.getSelectedIndex())//Artist
                    the_Chart_Primary_Panel.pnlChartPanel.filterTitleANDArtist(the_Chart_Primary_Panel.txtSearch.getText(),3);
            }//comboBox 0, 1

        }//KeyReleased

    }//KeyListener
}
