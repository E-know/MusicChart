package controller;

import notsort.*;
// TODO: 2020-09-21 It must delete

import view.CommentUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class CommentUIController {

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
            if(!theCommentUI.txtComment.getText().equals("") ){
                File file = new File("comments\\" + theCommentUI.strReadTitle + ".txt");
                try {
                    FileWriter fw = new FileWriter(file,true);
                    fw.write(theCommentUI.txtComment.getText() + "\r");
                    if( theCommentUI.txtPassword.getText().equals("") )
                        fw.write("0000\r");
                    else
                        fw.write(theCommentUI.txtPassword.getText() + "\r");
                    fw.flush();
                    fw.close();
                    theCommentUI.modelList.addElement(theCommentUI.txtComment.getText());

                    theCommentUI.arrComment.add(theCommentUI.txtComment.getText());
                    if( theCommentUI.txtPassword.getText().equals("") )
                        theCommentUI.arrPassword.add("0000");
                    else
                        theCommentUI.arrPassword.add(theCommentUI.txtPassword.getText());

                    theCommentUI.txtComment.setText("");
                    theCommentUI.txtPassword.setText("");
                } catch (IOException ex) {
                    ex.printStackTrace();
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
            if(Integer.parseInt(theCommentUI.txtPassword.getText()) == Integer.parseInt(theCommentUI.arrPassword.get(theCommentUI.listComment.getSelectedIndex()))){
                System.out.println("Same Password! At : " + String.valueOf(theCommentUI.listComment.getSelectedIndex()));
                theCommentUI.arrPassword.remove(theCommentUI.listComment.getSelectedIndex());
                theCommentUI.arrComment.remove(theCommentUI.listComment.getSelectedIndex());
                theCommentUI.removeAtTxt(theCommentUI.listComment.getSelectedIndex());
                theCommentUI.modelList.removeElementAt(theCommentUI.listComment.getSelectedIndex());
            }
            theCommentUI.txtPassword.setText("");
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
