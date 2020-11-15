package controller;

import DB.ConnectDB;
import main.AppManager;
import model.ChartData;
import view.CommentPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CommentPanelController {
    ConnectDB DB = new ConnectDB();

    private final CommentPanel theCommentPanel;

    public CommentPanelController(CommentPanel theCommentPanel) {
        this.theCommentPanel = theCommentPanel;
        this.theCommentPanel.addBtnRegisterListener(new ButtonRegisterListener());
        this.theCommentPanel.addBtnDeleteListener(new ButtonDeleteListener());
        this.theCommentPanel.addBtnBackListener(new ButtonBackListener());
    }

    private class ButtonRegisterListener implements ActionListener{
        private Component _viewLoading;
        public ButtonRegisterListener() { }
        public ButtonRegisterListener(Component parentComponent){
            _viewLoading = parentComponent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!theCommentPanel._txtComment.getText().equals("")) {
                DB.insertDB(theCommentPanel._sqlTitle,
                        theCommentPanel._strArtist,
                        ChartData.getS_instance().getParser().getAlbumName(theCommentPanel._strTitle),
                        ChartData.getS_instance().getSite_M_B_G(),
                        theCommentPanel._txtComment.getText(),
                        theCommentPanel._txtPassword.getText());

                theCommentPanel._modelList.addElement(theCommentPanel._txtComment.getText());
                theCommentPanel._arrComment.add(theCommentPanel._txtComment.getText());

                if (theCommentPanel._txtPassword.getText().equals(""))
                    theCommentPanel._arrPassword.add("0000");
                else
                    theCommentPanel._arrPassword.add(theCommentPanel._txtPassword.getText());

                theCommentPanel.clearPanelTxt();
            }//obj == btnRegister
        }//actionPerfomed
    }//ButtonRegisterListener

    private class ButtonDeleteListener implements ActionListener{
        private Component _viewLoading;
        public ButtonDeleteListener() { }
        public ButtonDeleteListener(Component parentComponent){
            _viewLoading = parentComponent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (Integer.parseInt(theCommentPanel._txtPassword.getText()) == Integer.parseInt(theCommentPanel._arrPassword.get(theCommentPanel._listComment.getSelectedIndex()))) {
                System.out.println("Same Password! At : " + String.valueOf(theCommentPanel._listComment.getSelectedIndex()));
                DB.deleteDB(theCommentPanel._sqlTitle,theCommentPanel._txtPassword.getText());
                
                theCommentPanel._arrPassword.remove(theCommentPanel._listComment.getSelectedIndex());
                theCommentPanel._arrComment.remove(theCommentPanel._listComment.getSelectedIndex());
                theCommentPanel._modelList.removeElementAt(theCommentPanel._listComment.getSelectedIndex());
            }
            theCommentPanel._txtPassword.setText("");
        }//actionPerfomed
    }//ButtonDeleteListener

    private class ButtonBackListener implements ActionListener{
        private Component _viewLoading;
        public ButtonBackListener() { }
        public ButtonBackListener(Component parentComponent){
            _viewLoading = parentComponent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            theCommentPanel.clearMusicData();
            BackToChartPrimaryPanel();
            System.out.println("Back To ChartPrimary");
        }//actionPerfomed
    }//ButtonBackListener

    public void BackToChartPrimaryPanel(){
        AppManager.getS_instance().getPrimaryPanel().repaint();
        AppManager.getS_instance().getPnlCommentUI().setVisible(false);
        AppManager.getS_instance().getChartPrimaryPanel().setVisible(true);
    }
}
