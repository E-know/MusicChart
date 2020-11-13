package main;

import controller.*;
import view.ChartPrimaryPanel;
import view.CommentPanel;

import javax.swing.*;
import java.awt.*;

public class AppManager {
    private static AppManager s_instance;
    private CommentPanel pnlCommentPanel;
    private ChartPrimaryPanel pnlChartPrimary;
    private JPanel primaryPanel;
    private ChartPrimaryPanelController theChartPrimaryPanelController;
    private CommentPanelController theCommentPanelController;

    private AppManager(){
    	s_instance = this;
    }

    public JPanel getPrimaryPanel(){
        if(primaryPanel == null) {
            primaryPanel = new JPanel(){
                public void paintComponent(Graphics g){
                    ImageIcon icon = new ImageIcon("Image\\background.jpg");
                    g.drawImage(icon.getImage(),0,0,null);
                    setOpaque(false);
                    super.paintComponent(g);
                }
            };
            primaryPanel.setLayout(null);
            primaryPanel.setPreferredSize(new Dimension(1280,960));
            primaryPanel.setBackground(Color.BLACK);
        }
        return primaryPanel;
    }

    public CommentPanel getPnlCommentUI() {
        return pnlCommentPanel;
    }
    public ChartPrimaryPanel getPnlChartPrimary() {
        if (pnlChartPrimary == null) {
            pnlChartPrimary = new ChartPrimaryPanel();
            theChartPrimaryPanelController = new ChartPrimaryPanelController(pnlChartPrimary);
            pnlChartPrimary.setVisible(true);
            pnlChartPrimary.setLayout(null);
        }
        return pnlChartPrimary;
    }

    public void addToPrimaryPanel(JPanel pnlAdd){
        if(primaryPanel == null){
            primaryPanel = new JPanel();
            primaryPanel.setVisible(true);
            primaryPanel.setLayout(null);
        }
        pnlAdd.setVisible(true);
        primaryPanel.add(pnlAdd);
    }

    public void PopUpCommentUI(int rank){
        if(pnlCommentPanel == null) {
            pnlCommentPanel = new CommentPanel();
            theCommentPanelController = new CommentPanelController(pnlCommentPanel);
            primaryPanel.add(pnlCommentPanel);
        }
        pnlCommentPanel.popUpCommentPanel(rank);
        pnlCommentPanel.setVisible(true);
        pnlChartPrimary.setVisible(false);
    }

    public void BackToChartPrimaryPanel(){
        primaryPanel.repaint();
        pnlCommentPanel.setVisible(false);
        pnlChartPrimary.setVisible(true);
    }

    public static AppManager getS_instance() {
        if(s_instance == null) s_instance = new AppManager();
        return s_instance;
    }
}