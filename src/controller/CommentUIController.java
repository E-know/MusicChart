package controller;

import notsort.*;
// TODO: 2020-09-21 It must delete

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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

public class CommentUIController {

    private JTextField          txtComment,txtPassword;
    private JButton             btnRegister,btnDelete,btnBack;
    private ArrayList<String>   arrComment;
    private ArrayList<String>   arrPassword;
    private JList               listComment;
    private DefaultListModel    modelList;
    private String              strTitle,strArtist,strReadTitle;

    private class ButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Object obj = e.getSource();
            if(obj == btnRegister && !txtComment.getText().equals("") ){
                File file = new File("comments\\" + strReadTitle + ".txt");
                try {
                    FileWriter fw = new FileWriter(file,true);
                    fw.write(txtComment.getText() + "\r");
                    if( txtPassword.getText().equals("") )
                        fw.write("0000\r");
                    else
                        fw.write(txtPassword.getText() + "\r");
                    fw.flush();
                    fw.close();
                    modelList.addElement(txtComment.getText());

                    arrComment.add(txtComment.getText());
                    if( txtPassword.getText().equals("") )
                        arrPassword.add("0000");
                    else
                        arrPassword.add(txtPassword.getText());

                    txtComment.setText("");
                    txtPassword.setText("");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }//obj == btnRegister
            if(obj == btnDelete){
                if(Integer.parseInt(txtPassword.getText()) == Integer.parseInt(arrPassword.get(listComment.getSelectedIndex()))){
                    System.out.println("Same Password! At : " + String.valueOf(listComment.getSelectedIndex()));
                    arrPassword.remove(listComment.getSelectedIndex());
                    arrComment.remove(listComment.getSelectedIndex());
                    CommentUI.removeAtTxt(listComment.getSelectedIndex());
                    modelList.removeElementAt(listComment.getSelectedIndex());
                }
                txtPassword.setText("");
            }
            if(obj == btnBack){
                CommentUI.clearAll();
                AppManager.getS_instance().BackToChartPrimaryPanel();
                System.out.println("Back To ChartPrimary");
            }
        }//actionPerfomed
    }//ButtonRegister
}
