package controller.panelController;

import controller.AppManager;
import view.CommentPanel;
import model.DB.ConnectDB;
import model.DB.CommentDTO;

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
                DB.connectionDB();
                DB.insertCommentDB(theCommentPanel._strAlbumId, 6,
                        theCommentPanel._txtComment.getText(),
                        theCommentPanel._txtPassword.getText());

                theCommentPanel._modelList.addElement(theCommentPanel._txtComment.getText());

                CommentDTO list = new CommentDTO();
                list.setComment(theCommentPanel._txtComment.getText());

                if (theCommentPanel._txtPassword.getText().equals(""))
                    list.setPassword("0000");
                else
                    list.setPassword(theCommentPanel._txtPassword.getText());

                theCommentPanel._commentListDTO.add(list);
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
            if (Integer.parseInt(theCommentPanel._txtPassword.getText()) == Integer.parseInt(theCommentPanel._commentListDTO.get(theCommentPanel._listComment.getSelectedIndex()).getPassword())) {
                System.out.println("Same Password! At : " + String.valueOf(theCommentPanel._listComment.getSelectedIndex()));

                DB.connectionDB();
                DB.deleteDB(theCommentPanel._strAlbumId, theCommentPanel._txtPassword.getText());

                theCommentPanel._commentListDTO.remove(theCommentPanel._listComment.getSelectedIndex());
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
