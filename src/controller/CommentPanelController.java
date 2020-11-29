package controller;

import DB.ConnectDB;
import main.AppManager;
import view.CommentPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.Desktop;
import java.net.URLEncoder;

public class CommentPanelController {

    ConnectDB DB = new ConnectDB();

    private final CommentPanel theCommentPanel;

    public CommentPanelController(CommentPanel TheCommentPanel) {
        this.theCommentPanel = TheCommentPanel;
        this.theCommentPanel.addBtnRegisterListener(new ButtonRegisterListener());
        this.theCommentPanel.addBtnDeleteListener(new ButtonDeleteListener());
        this.theCommentPanel.addBtnBackListener(new ButtonBackListener());
        this.theCommentPanel.addBtnYouTubeListener(new ButtonYouTubeListener());
    }

    private class ButtonRegisterListener implements ActionListener{
        private Component viewLoading;
        public ButtonRegisterListener() { }
        public ButtonRegisterListener(Component parentComponent){
            viewLoading = parentComponent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!theCommentPanel._txtComment.getText().equals("")) {
                DB.getDB();
                DB.insertCommentDB(theCommentPanel._strAlbumId, 6,
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
        }//actionPerformed
    }//ButtonRegisterListener

    private class ButtonDeleteListener implements ActionListener{
        private Component viewLoading;
        public ButtonDeleteListener() { }
        public ButtonDeleteListener(Component parentComponent){
            viewLoading = parentComponent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (Integer.parseInt(theCommentPanel._txtPassword.getText()) == Integer.parseInt(theCommentPanel._arrPassword.get(theCommentPanel._listComment.getSelectedIndex()))) {
                System.out.println("Same Password! At : " + String.valueOf(theCommentPanel._listComment.getSelectedIndex()));
                DB.getDB();
                DB.deleteDB(theCommentPanel._strAlbumId, theCommentPanel._txtPassword.getText());

                theCommentPanel._arrPassword.remove(theCommentPanel._listComment.getSelectedIndex());
                theCommentPanel._arrComment.remove(theCommentPanel._listComment.getSelectedIndex());
                theCommentPanel._modelList.removeElementAt(theCommentPanel._listComment.getSelectedIndex());
            }
            theCommentPanel._txtPassword.setText("");
        }//actionPerformed
    }//ButtonDeleteListener

    private class ButtonBackListener implements ActionListener{
        private Component viewLoading;
        public ButtonBackListener() { }
        public ButtonBackListener(Component parentComponent){
            viewLoading = parentComponent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            theCommentPanel.clearMusicData();
            BackToChartPrimaryPanel();
            System.out.println("Back To ChartPrimary");
        }//actionPerformed
    }//ButtonBackListener

    private class ButtonYouTubeListener implements ActionListener{
        private Component viewLoading;
        public ButtonYouTubeListener() { }
        public ButtonYouTubeListener(Component parentComponent){
            viewLoading = parentComponent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Desktop.getDesktop().browse(new URI("https://www.youtube.com/results?search_query=" + URLEncoder.encode(theCommentPanel._strTitle, "UTF-8")));
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (URISyntaxException ex) {
                ex.printStackTrace();
            }
            System.out.println("Search in Youtube");
        }//actionPerformed
    }//ButtonYoutubeListener

    public void BackToChartPrimaryPanel(){
        AppManager.getS_instance().getPrimaryPanel().repaint();
        AppManager.getS_instance().getPnlCommentPanel().setVisible(false);
        AppManager.getS_instance().getChartPrimaryPanel().setVisible(true);
    }
}
