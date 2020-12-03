package controller.panelController;

import model.ChartData;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SiteChartsPanelController {

    private view.SiteChartsPanel theSiteChartsPanel;

    public SiteChartsPanelController(view.SiteChartsPanel TheSiteChartsPanel) {
        this.theSiteChartsPanel = TheSiteChartsPanel;
        this.theSiteChartsPanel.addBtnRefreshListener(new ButtonRefreshListener());
        this.theSiteChartsPanel.addBtnMelonListener(new ButtonMelonListener());
        this.theSiteChartsPanel.addBtnBugsListener(new ButtonBugsListener());
        this.theSiteChartsPanel.addBtnGenieListener(new ButtonGenieListener());
        this.theSiteChartsPanel.addRecentListener(new ButtonRecentListener());
        this.theSiteChartsPanel.addKeyActionListener(new KeyActionListener());
    }

    private class ButtonRefreshListener implements ActionListener {
        private Component viewLoading;
        public ButtonRefreshListener() { }
        public ButtonRefreshListener(Component parentComponent){
            viewLoading = parentComponent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setChartTime();
            theSiteChartsPanel._lblTime.setText("Renewal time : " + getChartTime());
            ChartData.getS_instance().DataPassing(viewLoading);
        }
    }//ButtonRefreshListener

    public void setChartTime() {
        switch (ChartData.getS_instance().getSiteMBG()) {
            case 1:
                theSiteChartsPanel._formatted_Melon = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                break;
            case 2:
                theSiteChartsPanel._formatted_Bugs = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                break;
            case 3:
                theSiteChartsPanel._formatted_Genie = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                break;
        }
    }

    public String getChartTime() {
        switch (ChartData.getS_instance().getSiteMBG()) {
            case 1:
                return theSiteChartsPanel._formatted_Melon;
            case 2:
                return theSiteChartsPanel._formatted_Bugs;
            case 3:
                return theSiteChartsPanel._formatted_Genie;
            default:
                return null;
        }
    }

    private void renewalChartTime() {
        theSiteChartsPanel._pnlChartPanel.changeData();
        theSiteChartsPanel._lblTime.setText("Renewal time : " + getChartTime());
        theSiteChartsPanel._txtSearch.setText("");
        theSiteChartsPanel._pnlChartPanel.filterTitleANDArtist(null,2);
    }

    private class ButtonMelonListener implements ActionListener {
        private Component viewLoading;
        public ButtonMelonListener() { }
        public ButtonMelonListener(Component parentComponent){
            viewLoading = parentComponent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if(ChartData.getS_instance().getSiteMBG() == 1) return;
            ChartData.getS_instance().setSiteMBG(1);
            if(!ChartData.getS_instance().getParser().isParsed()) ChartData.getS_instance().DataPassing(viewLoading);
            //System.out.println("Melon");
            renewalChartTime();
        }
    }//ButtonMelonListener

    private class ButtonBugsListener implements ActionListener {
        private Component viewLoading;
        public ButtonBugsListener() { }
        public ButtonBugsListener(Component parentComponent){
            viewLoading = parentComponent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if(ChartData.getS_instance().getSiteMBG() == 2) return;
            ChartData.getS_instance().setSiteMBG(2);
            if(!ChartData.getS_instance().getParser().isParsed()) ChartData.getS_instance().DataPassing(viewLoading);
            //System.out.println("Bugs");
            renewalChartTime();
        }
    }//ButtonBugsListener

    private class ButtonGenieListener implements ActionListener {
        private Component viewLoading;
        public ButtonGenieListener() { }
        public ButtonGenieListener(Component parentComponent){
            viewLoading = parentComponent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(ChartData.getS_instance().getSiteMBG() == 3) return;
            ChartData.getS_instance().setSiteMBG(3);
            if(!ChartData.getS_instance().getParser().isParsed()) ChartData.getS_instance().DataPassing(viewLoading);
            //System.out.println("Genie");
            renewalChartTime();
        }
    }//ButtonGenieListener

    private class ButtonRecentListener implements ActionListener {
        private Component viewLoading;
        public ButtonRecentListener() { }
        public ButtonRecentListener(Component parentComponent){
            viewLoading = parentComponent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Recent");
            theSiteChartsPanel._pnlChartPanel.recentData();
            theSiteChartsPanel._lblTime.setText("Renewal time : ");
            theSiteChartsPanel._txtSearch.setText("");
            theSiteChartsPanel._pnlChartPanel.filterTitleANDArtist(null,2);
        }
    }//ButtonRecentListener

    private class KeyActionListener implements KeyListener{
        private Component viewLoading;
        public KeyActionListener() { }
        public KeyActionListener(Component parentComponent){
            viewLoading = parentComponent;
        }

        @Override
        public void keyTyped(KeyEvent e) { }
        @Override
        public void keyPressed(KeyEvent e) { }
        @Override
        public void keyReleased(KeyEvent e) {
            Object obj = e.getSource();

            if(obj == theSiteChartsPanel._txtSearch){
                //strSearchCategory = {"Name", "Artist"};
                if(0 == theSiteChartsPanel._strCombo.getSelectedIndex())//Name
                    theSiteChartsPanel._pnlChartPanel.filterTitleANDArtist(theSiteChartsPanel._txtSearch.getText(),2);
                if(1 == theSiteChartsPanel._strCombo.getSelectedIndex())//Artist
                    theSiteChartsPanel._pnlChartPanel.filterTitleANDArtist(theSiteChartsPanel._txtSearch.getText(),3);
            }//comboBox 0, 1
        }//KeyReleased
    }//KeyListener
}
