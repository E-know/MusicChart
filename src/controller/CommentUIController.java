package controller;

import notsort.*;
// TODO: 2020-09-21 It must delete

import view.CommentUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class CommentUIController {

    private CommentUI the_Comment_UI;

    public CommentUIController(CommentUI the_Comment_UI) {
        this.the_Comment_UI = the_Comment_UI;
        this.the_Comment_UI.addBtnRegisterListener(new ButtonRegisterListener());
        this.the_Comment_UI.addBtnDeleteListener(new ButtonDeleteListener());
        this.the_Comment_UI.addBtnBackListener(new ButtonBackListener());
    }

    private class ButtonRegisterListener implements ActionListener{
        private Component view_Loading;
        public ButtonRegisterListener() { }
        public ButtonRegisterListener(Component parentComponent){
            view_Loading = parentComponent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(!the_Comment_UI.txtComment.getText().equals("") ){
                File file = new File("comments\\" + the_Comment_UI.strReadTitle + ".txt");
                try {
                    FileWriter fw = new FileWriter(file,true);
                    fw.write(the_Comment_UI.txtComment.getText() + "\r");
                    if( the_Comment_UI.txtPassword.getText().equals("") )
                        fw.write("0000\r");
                    else
                        fw.write(the_Comment_UI.txtPassword.getText() + "\r");
                    fw.flush();
                    fw.close();
                    the_Comment_UI.modelList.addElement(the_Comment_UI.txtComment.getText());

                    the_Comment_UI.arrComment.add(the_Comment_UI.txtComment.getText());
                    if( the_Comment_UI.txtPassword.getText().equals("") )
                        the_Comment_UI.arrPassword.add("0000");
                    else
                        the_Comment_UI.arrPassword.add(the_Comment_UI.txtPassword.getText());

                    the_Comment_UI.txtComment.setText("");
                    the_Comment_UI.txtPassword.setText("");
                } catch (IOException ex) {
                    ex.printStackTrace();
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
            if(Integer.parseInt(the_Comment_UI.txtPassword.getText()) == Integer.parseInt(the_Comment_UI.arrPassword.get(the_Comment_UI.listComment.getSelectedIndex()))){
                System.out.println("Same Password! At : " + String.valueOf(the_Comment_UI.listComment.getSelectedIndex()));
                the_Comment_UI.arrPassword.remove(the_Comment_UI.listComment.getSelectedIndex());
                the_Comment_UI.arrComment.remove(the_Comment_UI.listComment.getSelectedIndex());
                the_Comment_UI.removeAtTxt(the_Comment_UI.listComment.getSelectedIndex());
                the_Comment_UI.modelList.removeElementAt(the_Comment_UI.listComment.getSelectedIndex());
            }
            the_Comment_UI.txtPassword.setText("");
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
            the_Comment_UI.clearAll();
            AppManager.getS_instance().BackToChartPrimaryPanel();
            System.out.println("Back To ChartPrimary");
        }//actionPerfomed
    }//ButtonBackListener

}
