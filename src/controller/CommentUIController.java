package controller;

import DB.*;
import main.AppManager;
import view.CommentUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

<<<<<<< Updated upstream:src/controller/CommentUIController.java
public class CommentUIController {
    Connection con = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
=======
public class CommentPanelController {
    PreparedStatement _pstmt = null;
>>>>>>> Stashed changes:src/controller/CommentPanelController.java


    private CommentUI theCommentUI;

    public CommentUIController(CommentUI theCommentUI) {
        this.theCommentUI = theCommentUI;
        this.theCommentUI.addBtnRegisterListener(new ButtonRegisterListener());
        this.theCommentUI.addBtnDeleteListener(new ButtonDeleteListener());
        this.theCommentUI.addBtnBackListener(new ButtonBackListener());
    }


    private class ButtonRegisterListener implements ActionListener{
        private Component _viewLoading;
        public ButtonRegisterListener() { }
        public ButtonRegisterListener(Component parentComponent){
            _viewLoading = parentComponent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
<<<<<<< Updated upstream:src/controller/CommentUIController.java
            if (!theCommentUI.txtComment.getText().equals("")) {
                con = ConnectDB.GetDB();
                try {
                    String sql = "INSERT INTO songinfo VALUES (?, ?, ?, ?, ?, ?)";
                    pstmt = con.prepareStatement(sql);
                    pstmt.setString(1, theCommentUI.sqltitle);
                    pstmt.setString(2, theCommentUI.strArtist);
                    pstmt.setString(3, AppManager.getS_instance().getParser().getAlbumName(theCommentUI.strTitle));
                    pstmt.setInt(4, AppManager.getS_instance().getSite_M_B_G());
                    pstmt.setString(5, theCommentUI.txtComment.getText());
                    pstmt.setString(6, theCommentUI.txtPassword.getText());
                    pstmt.executeUpdate();
                    theCommentUI.modelList.addElement(theCommentUI.txtComment.getText());

                    theCommentUI.arrComment.add(theCommentUI.txtComment.getText());
                    if (theCommentUI.txtPassword.getText().equals(""))
                        theCommentUI.arrPassword.add("0000");
                    else
                        theCommentUI.arrPassword.add(theCommentUI.txtPassword.getText());
                    theCommentUI.txtComment.setText("");
                    theCommentUI.txtPassword.setText("");
=======
            if (!theCommentPanel._txtComment.getText().equals("")) {
                try {
                    String sql = "INSERT INTO songinfo VALUES (?, ?, ?, ?, ?, ?)";
                    _pstmt = ConnectDB.GetDB().prepareStatement(sql);
                    _pstmt.setString(1, theCommentPanel._sqlTitle);
                    _pstmt.setString(2, theCommentPanel._strArtist);
                    _pstmt.setString(3, AppManager.getS_instance().getParser().getAlbumName(theCommentPanel._strTitle));
                    _pstmt.setInt(4, AppManager.getS_instance().getSite_M_B_G());
                    _pstmt.setString(5, theCommentPanel._txtComment.getText());
                    _pstmt.setString(6, theCommentPanel._txtPassword.getText());
                    _pstmt.executeUpdate();
                    theCommentPanel._modelList.addElement(theCommentPanel._txtComment.getText());

                    theCommentPanel._arrComment.add(theCommentPanel._txtComment.getText());
                    if (theCommentPanel._txtPassword.getText().equals(""))
                        theCommentPanel._arrPassword.add("0000");
                    else
                        theCommentPanel._arrPassword.add(theCommentPanel._txtPassword.getText());
                    theCommentPanel._txtComment.setText("");
                    theCommentPanel._txtPassword.setText("");
>>>>>>> Stashed changes:src/controller/CommentPanelController.java
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
<<<<<<< Updated upstream:src/controller/CommentUIController.java
            if (Integer.parseInt(theCommentUI.txtPassword.getText()) == Integer.parseInt(theCommentUI.arrPassword.get(theCommentUI.listComment.getSelectedIndex()))) {
                System.out.println("Same Password! At : " + String.valueOf(theCommentUI.listComment.getSelectedIndex()));
                theCommentUI.con = ConnectDB.GetDB();
                try {
                    theCommentUI.arrPassword.remove(theCommentUI.listComment.getSelectedIndex());
                    theCommentUI.arrComment.remove(theCommentUI.listComment.getSelectedIndex());
                    theCommentUI.modelList.removeElementAt(theCommentUI.listComment.getSelectedIndex());
                    String sql = "DELETE FROM songinfo WHERE title = ? AND pwd = ?";
                    pstmt = con.prepareStatement(sql);
                    pstmt.setString(1, theCommentUI.sqltitle);
                    pstmt.setString(2, theCommentUI.txtPassword.getText());
                    int temp = pstmt.executeUpdate();
=======
            if (Integer.parseInt(theCommentPanel._txtPassword.getText()) == Integer.parseInt(theCommentPanel._arrPassword.get(theCommentPanel._listComment.getSelectedIndex()))) {
                System.out.println("Same Password! At : " + String.valueOf(theCommentPanel._listComment.getSelectedIndex()));
                theCommentPanel._con = ConnectDB.GetDB();
                try {
                    theCommentPanel._arrPassword.remove(theCommentPanel._listComment.getSelectedIndex());
                    theCommentPanel._arrComment.remove(theCommentPanel._listComment.getSelectedIndex());
                    theCommentPanel._modelList.removeElementAt(theCommentPanel._listComment.getSelectedIndex());
                    String sql = "DELETE FROM songinfo WHERE title = ? AND pwd = ?";
                    _pstmt = ConnectDB.GetDB().prepareStatement(sql);
                    _pstmt.setString(1, theCommentPanel._sqlTitle);
                    _pstmt.setString(2, theCommentPanel._txtPassword.getText());
                    int temp = _pstmt.executeUpdate();
>>>>>>> Stashed changes:src/controller/CommentPanelController.java
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }

<<<<<<< Updated upstream:src/controller/CommentUIController.java
            theCommentUI.txtPassword.setText("");
=======
            theCommentPanel._txtPassword.setText("");
>>>>>>> Stashed changes:src/controller/CommentPanelController.java
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
            theCommentUI.clearAll();
            AppManager.getS_instance().BackToChartPrimaryPanel();
            System.out.println("Back To ChartPrimary");
        }//actionPerfomed
    }//ButtonBackListener

}
