package controller;

import DB.*;
import main.AppManager;
import view.CommentPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CommentPanelController {
    Connection con = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;


    private CommentPanel theCommentPanel;

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
            if (!theCommentPanel.txtComment.getText().equals("")) {
                con = ConnectDB.GetDB();
                try {
                    String sql = "INSERT INTO songinfo VALUES (?, ?, ?, ?, ?, ?)";
                    pstmt = con.prepareStatement(sql);
                    pstmt.setString(1, theCommentPanel.sqltitle);
                    pstmt.setString(2, theCommentPanel.strArtist);
                    pstmt.setString(3, AppManager.getS_instance().getParser().getAlbumName(theCommentPanel.strTitle));
                    pstmt.setInt(4, AppManager.getS_instance().getSite_M_B_G());
                    pstmt.setString(5, theCommentPanel.txtComment.getText());
                    pstmt.setString(6, theCommentPanel.txtPassword.getText());
                    pstmt.executeUpdate();
                    theCommentPanel.modelList.addElement(theCommentPanel.txtComment.getText());

                    theCommentPanel.arrComment.add(theCommentPanel.txtComment.getText());
                    if (theCommentPanel.txtPassword.getText().equals(""))
                        theCommentPanel.arrPassword.add("0000");
                    else
                        theCommentPanel.arrPassword.add(theCommentPanel.txtPassword.getText());
                    theCommentPanel.txtComment.setText("");
                    theCommentPanel.txtPassword.setText("");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
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
            if (Integer.parseInt(theCommentPanel.txtPassword.getText()) == Integer.parseInt(theCommentPanel.arrPassword.get(theCommentPanel.listComment.getSelectedIndex()))) {
                System.out.println("Same Password! At : " + String.valueOf(theCommentPanel.listComment.getSelectedIndex()));
                theCommentPanel.con = ConnectDB.GetDB();
                try {
                    theCommentPanel.arrPassword.remove(theCommentPanel.listComment.getSelectedIndex());
                    theCommentPanel.arrComment.remove(theCommentPanel.listComment.getSelectedIndex());
                    theCommentPanel.modelList.removeElementAt(theCommentPanel.listComment.getSelectedIndex());
                    String sql = "DELETE FROM songinfo WHERE title = ? AND pwd = ?";
                    pstmt = con.prepareStatement(sql);
                    pstmt.setString(1, theCommentPanel.sqltitle);
                    pstmt.setString(2, theCommentPanel.txtPassword.getText());
                    int temp = pstmt.executeUpdate();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }

            theCommentPanel.txtPassword.setText("");
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
            AppManager.getS_instance().BackToChartPrimaryPanel();
            System.out.println("Back To ChartPrimary");
        }//actionPerfomed
    }//ButtonBackListener

}
