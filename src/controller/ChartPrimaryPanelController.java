package controller;

import model.ChartData;
import view.ChartPrimaryPanel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChartPrimaryPanelController {

    private ChartPrimaryPanel theChartPrimaryPanel;

    public ChartPrimaryPanelController(ChartPrimaryPanel theChartPrimaryPanel) {
        this.theChartPrimaryPanel = theChartPrimaryPanel;
        this.theChartPrimaryPanel.addBtnRefreshListener(new ButtonRefreshListener());
        this.theChartPrimaryPanel.addBtnMelonListener(new ButtonMelonListener());
        this.theChartPrimaryPanel.addBtnBugsListener(new ButtonBugsListener());
        this.theChartPrimaryPanel.addBtnGenieListener(new ButtonGenieListener());
        this.theChartPrimaryPanel.addKeyActionListener(new KeyActionListener());
    }


    private class ButtonRefreshListener implements ActionListener {
        private Component _viewLoading;
        public ButtonRefreshListener() { }
        public ButtonRefreshListener(Component parentComponent){
            _viewLoading = parentComponent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            theChartPrimaryPanel.current = LocalDateTime.now();
            theChartPrimaryPanel.formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            switch (ChartData.getS_instance().getSite_M_B_G()){
                case 1:
                    theChartPrimaryPanel.formatted_Melon = theChartPrimaryPanel.current.format(theChartPrimaryPanel.formatter);
                    theChartPrimaryPanel.lblTime.setText("Renewal time : " + theChartPrimaryPanel.formatted_Melon);
                    ChartData.getS_instance().setSite_M_B_G(1);
                    ChartData.getS_instance().DataPassing(_viewLoading);
                    System.out.println("why?");
                    break;
                case 2:
                    theChartPrimaryPanel.formatted_Bugs = theChartPrimaryPanel.current.format(theChartPrimaryPanel.formatter);
                    theChartPrimaryPanel.lblTime.setText("Renewal time : " + theChartPrimaryPanel.formatted_Bugs);
                    ChartData.getS_instance().setSite_M_B_G(2);
                    ChartData.getS_instance().DataPassing(_viewLoading);
                    break;
                case 3:
                    theChartPrimaryPanel.formatted_Genie = theChartPrimaryPanel.current.format(theChartPrimaryPanel.formatter);
                    theChartPrimaryPanel.lblTime.setText("Renewal time : " + theChartPrimaryPanel.formatted_Genie);
                    ChartData.getS_instance().setSite_M_B_G(3);
                    ChartData.getS_instance().DataPassing(_viewLoading);
                    break;
            }
        }
    }//ButtonRefreshListener

    private class ButtonMelonListener implements ActionListener {
        private Component _viewLoading;
        public ButtonMelonListener() { }
        public ButtonMelonListener(Component parentComponent){
            _viewLoading = parentComponent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if(ChartData.getS_instance().getSite_M_B_G() == 1) return;
            ChartData.getS_instance().setSite_M_B_G(1);
            if(!ChartData.getS_instance().getParser().isParsed()) ChartData.getS_instance().DataPassing(_viewLoading);
            System.out.println("Melon");
            theChartPrimaryPanel.pnlChartPanel.changeData();
            theChartPrimaryPanel.lblTime.setText("Renewal time : " + theChartPrimaryPanel.formatted_Melon);
            theChartPrimaryPanel.txtSearch.setText("");
            theChartPrimaryPanel.pnlChartPanel.filterTitleANDArtist(null,2);
        }
    }//ButtonMelonListener

    private class ButtonBugsListener implements ActionListener {
        private Component _viewLoading;
        public ButtonBugsListener() { }
        public ButtonBugsListener(Component parentComponent){
            _viewLoading = parentComponent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if(ChartData.getS_instance().getSite_M_B_G() == 2) return;
            ChartData.getS_instance().setSite_M_B_G(2);
            if(!ChartData.getS_instance().getParser().isParsed()) ChartData.getS_instance().DataPassing(_viewLoading);
            System.out.println("Bugs");
            theChartPrimaryPanel.pnlChartPanel.changeData();
            theChartPrimaryPanel.lblTime.setText("Renewal time : " + theChartPrimaryPanel.formatted_Bugs);
            theChartPrimaryPanel.txtSearch.setText("");
            theChartPrimaryPanel.pnlChartPanel.filterTitleANDArtist(null,2);
        }
    }//ButtonBugsListener

    private class ButtonGenieListener implements ActionListener {
        private Component _viewLoading;
        public ButtonGenieListener() { }
        public ButtonGenieListener(Component parentComponent){
            _viewLoading = parentComponent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if(ChartData.getS_instance().getSite_M_B_G() == 3) return;
            ChartData.getS_instance().setSite_M_B_G(3);
            if(!ChartData.getS_instance().getParser().isParsed()) ChartData.getS_instance().DataPassing(_viewLoading);
            System.out.println("Genie");
            theChartPrimaryPanel.pnlChartPanel.changeData();
            theChartPrimaryPanel.lblTime.setText("Renewal time : " + theChartPrimaryPanel.formatted_Genie);
            theChartPrimaryPanel.txtSearch.setText("");
            theChartPrimaryPanel.pnlChartPanel.filterTitleANDArtist(null,2);
        }
    }//ButtonGenieListener

    private class KeyActionListener implements KeyListener{
        private Component _viewLoading;
        public KeyActionListener() { }
        public KeyActionListener(Component parentComponent){
            _viewLoading = parentComponent;
        }

        @Override
        public void keyTyped(KeyEvent e) { }

        @Override
        public void keyPressed(KeyEvent e) { }

        @Override
        public void keyReleased(KeyEvent e) {
            Object obj = e.getSource();

            if(obj == theChartPrimaryPanel.txtSearch){
                //strSearchCategory = {"Name", "Artist"};
                if(0 == theChartPrimaryPanel.strCombo.getSelectedIndex())//Name
                    theChartPrimaryPanel.pnlChartPanel.filterTitleANDArtist(theChartPrimaryPanel.txtSearch.getText(),2);
                if(1 == theChartPrimaryPanel.strCombo.getSelectedIndex())//Artist
                    theChartPrimaryPanel.pnlChartPanel.filterTitleANDArtist(theChartPrimaryPanel.txtSearch.getText(),3);
            }//comboBox 0, 1ÀÏ¶§ sitepanelÀÇ filter¿¡¼­ °Ë»ö

        }//KeyReleased

    }//KeyListener
}
