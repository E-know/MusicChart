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


    private CommentPanel the_Comment_Panel;

    public CommentPanelController(CommentPanel theCommentPanel) {
        this.the_Comment_Panel = theCommentPanel;
        this.the_Comment_Panel.addBtnRegisterListener(new ButtonRegisterListener());
        this.the_Comment_Panel.addBtnDeleteListener(new ButtonDeleteListener());
        this.the_Comment_Panel.addBtnBackListener(new ButtonBackListener());
    }


    private class ButtonRegisterListener implements ActionListener{
        private Component view_Loading;
        public ButtonRegisterListener() { }
        public ButtonRegisterListener(Component parentComponent){
            view_Loading = parentComponent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!the_Comment_Panel.txtComment.getText().equals("")) {
                con = ConnectDB.GetDB();
                try {
                    String sql = "INSERT INTO songinfo VALUES (?, ?, ?, ?, ?, ?)";
                    pstmt = con.prepareStatement(sql);
                    pstmt.setString(1, the_Comment_Panel.sqltitle);
                    pstmt.setString(2, the_Comment_Panel.strArtist);
                    pstmt.setString(3, AppManager.getS_instance().getParser().getAlbumName(the_Comment_Panel.strTitle));
                    pstmt.setInt(4, AppManager.getS_instance().getSite_M_B_G());
                    pstmt.setString(5, the_Comment_Panel.txtComment.getText());
                    pstmt.setString(6, the_Comment_Panel.txtPassword.getText());
                    pstmt.executeUpdate();
                    the_Comment_Panel.modelList.addElement(the_Comment_Panel.txtComment.getText());

                    the_Comment_Panel.arrComment.add(the_Comment_Panel.txtComment.getText());
                    if (the_Comment_Panel.txtPassword.getText().equals(""))
                        the_Comment_Panel.arrPassword.add("0000");
                    else
                        the_Comment_Panel.arrPassword.add(the_Comment_Panel.txtPassword.getText());
                    the_Comment_Panel.txtComment.setText("");
                    the_Comment_Panel.txtPassword.setText("");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }//obj == btnRegister
        }//actionPerfomed
    }//ButtonRegisterListener

    private class ButtonDeleteListener implements ActionListener{
        private Component view_Loading;
        public ButtonDeleteListener() { }
        public ButtonDeleteListener(Component parentComponent){
            view_Loading = parentComponent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (Integer.parseInt(the_Comment_Panel.txtPassword.getText()) == Integer.parseInt(the_Comment_Panel.arrPassword.get(the_Comment_Panel.listComment.getSelectedIndex()))) {
                System.out.println("Same Password! At : " + String.valueOf(the_Comment_Panel.listComment.getSelectedIndex()));
                the_Comment_Panel.con = ConnectDB.GetDB();
                try {
                    the_Comment_Panel.arrPassword.remove(the_Comment_Panel.listComment.getSelectedIndex());
                    the_Comment_Panel.arrComment.remove(the_Comment_Panel.listComment.getSelectedIndex());
                    the_Comment_Panel.modelList.removeElementAt(the_Comment_Panel.listComment.getSelectedIndex());
                    String sql = "DELETE FROM songinfo WHERE title = ? AND pwd = ?";
                    pstmt = con.prepareStatement(sql);
                    pstmt.setString(1, the_Comment_Panel.sqltitle);
                    pstmt.setString(2, the_Comment_Panel.txtPassword.getText());
                    int temp = pstmt.executeUpdate();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }

            the_Comment_Panel.txtPassword.setText("");
        }//actionPerfomed
    }//ButtonDeleteListener

    private class ButtonBackListener implements ActionListener{
        private Component view_Loading;
        public ButtonBackListener() { }
        public ButtonBackListener(Component parentComponent){
            view_Loading = parentComponent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            the_Comment_Panel.clearMusicData();
            AppManager.getS_instance().BackToChartPrimaryPanel();
            System.out.println("Back To ChartPrimary");
        }//actionPerfomed
    }//ButtonBackListener

}
