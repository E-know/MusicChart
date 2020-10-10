package controller;

import DB.*;
import notsort.*;
// TODO: 2020-09-21 It must delete

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import view.ChartPrimaryPanel;
import view.CommentUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.stream.events.Comment;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.sql.*;

public class CommentUIController {
    Connection con = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;


    private CommentUI theCommentUI;

    public CommentUIController(CommentUI theCommentUI) {
        this.theCommentUI = theCommentUI;
        this.theCommentUI.addBtnRegisterListener(new ButtonRegisterListener());
        this.theCommentUI.addBtnDeleteListener(new ButtonDeleteListener());
        this.theCommentUI.addBtnBackListener(new ButtonBackListener());
    }

    private class ButtonRegisterListener implements ActionListener {
        private Component _viewLoading;

        public ButtonRegisterListener() {
        }

        public ButtonRegisterListener(Component parentComponent) {
            _viewLoading = parentComponent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
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
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
//                File file = new File("comments\\" + theCommentUI.strReadTitle + ".txt");
//                try {
//                    FileWriter fw = new FileWriter(file,true);
//                    fw.write(theCommentUI.txtComment.getText() + "\r");
//                    if( theCommentUI.txtPassword.getText().equals("") )
//                        fw.write("0000\r");
//                    else
//                        fw.write(theCommentUI.txtPassword.getText() + "\r");
//                    fw.flush();
//                    fw.close();
//                    theCommentUI.modelList.addElement(theCommentUI.txtComment.getText());
//
//                    theCommentUI.arrComment.add(theCommentUI.txtComment.getText());
//                    if( theCommentUI.txtPassword.getText().equals("") )
//                        theCommentUI.arrPassword.add("0000");
//                    else
//                        theCommentUI.arrPassword.add(theCommentUI.txtPassword.getText());
//
//                    theCommentUI.txtComment.setText("");
//                    theCommentUI.txtPassword.setText("");
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
            }//obj == btnRegister
        }//actionPerfomed
    }//ButtonRegisterListener

    private class ButtonDeleteListener implements ActionListener {
        private Component _viewLoading;

        public ButtonDeleteListener() {
        }

        public ButtonDeleteListener(Component parentComponent) {
            _viewLoading = parentComponent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (Integer.parseInt(theCommentUI.txtPassword.getText()) == Integer.parseInt(theCommentUI.arrPassword.get(theCommentUI.listComment.getSelectedIndex()))) {
//                System.out.println("Same Password! At : " + String.valueOf(theCommentUI.listComment.getSelectedIndex()));
//                theCommentUI.arrPassword.remove(theCommentUI.listComment.getSelectedIndex());
//                theCommentUI.arrComment.remove(theCommentUI.listComment.getSelectedIndex());
//                theCommentUI.removeAtTxt(theCommentUI.listComment.getSelectedIndex());
//                theCommentUI.modelList.removeElementAt(theCommentUI.listComment.getSelectedIndex());
            }
            System.out.println("Same Password! At : " + String.valueOf(theCommentUI.listComment.getSelectedIndex()));
//                    arrPassword.remove(listComment.getSelectedIndex());
//                    arrComment.remove(listComment.getSelectedIndex());
//                    removeAtTxt(listComment.getSelectedIndex());
//                    modelList.removeElementAt(listComment.getSelectedIndex());


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
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            theCommentUI.txtPassword.setText("");
        }//actionPerfomed
    }//ButtonDeleteListener

    private class ButtonBackListener implements ActionListener {
        private Component _viewLoading;

        public ButtonBackListener() { }

        public ButtonBackListener(Component parentComponent) { _viewLoading = parentComponent; }

        @Override
        public void actionPerformed(ActionEvent e) {
            theCommentUI.clearAll();
            AppManager.getS_instance().BackToChartPrimaryPanel();
            System.out.println("Back To ChartPrimary");
        }//actionPerfomed
    }//ButtonBackListener

}
