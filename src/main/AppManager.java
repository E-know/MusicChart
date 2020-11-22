package main;

import controller.*;
import view.SiteChartsPanel;
import view.CommentPanel;

import javax.swing.*;
import java.awt.*;

public class AppManager {
    private static AppManager s_instance;
    private CommentPanel pnlCommentPanel;
    private SiteChartsPanel pnlSiteChartsPanel;
    private JPanel primaryPanel;

    private AppManager(){
    	s_instance = this;
    }

    private void setInitPrimaryPanel(){
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


    public JPanel getPrimaryPanel(){
        if(primaryPanel == null)
            setInitPrimaryPanel();
        return primaryPanel;
    }

    public CommentPanel getPnlCommentUI() {
        return pnlCommentPanel;
    }
    public SiteChartsPanel getChartPrimaryPanel() {
        return pnlSiteChartsPanel;
    }

    public void setPnlCommentPanel(){
        pnlCommentPanel = new CommentPanel();
        new CommentPanelController(pnlCommentPanel);
    }

    public void setPnlSiteChartsPanel(){
        pnlSiteChartsPanel = new SiteChartsPanel();
        new ChartPrimaryPanelController(pnlSiteChartsPanel);
    }

    public void PrimaryPanel(){
        if(primaryPanel == null){
            setInitPrimaryPanel();
        }
        setPnlCommentPanel();
        setPnlSiteChartsPanel();
        addToPrimaryPanel(pnlCommentPanel);
        addToPrimaryPanel(pnlSiteChartsPanel);
        pnlSiteChartsPanel.setLayout(null);
        pnlSiteChartsPanel.setVisible(true);
    }

    public void addToPrimaryPanel(JPanel pnlAdd){
        pnlAdd.setVisible(false);
        primaryPanel.add(pnlAdd);
    }

    public static AppManager getS_instance() {
        if(s_instance == null)
            s_instance = new AppManager();
        return s_instance;
    }
}