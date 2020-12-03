package controller;

import controller.commentParser.BugsAlbumCommentParser;
import controller.commentParser.GenieAlbumCommentParser;
import controller.commentParser.MelonAlbumCommentParser;
import controller.panelController.CommentPanelController;
import controller.panelController.SiteChartsPanelController;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import model.DB.InsertDatabase;
import view.CommentPanel;
import view.SiteChartsPanel;

import javax.swing.*;
import java.awt.*;

public class AppManager {
    private static AppManager s_instance;
    private CommentPanel pnlCommentPanel;
    private SiteChartsPanel pnlSiteChartsPanel;
    private JPanel primaryPanel;
    private JFrame _frame;
    InsertDatabase InsertDatabase = new InsertDatabase();

    private AppManager(){
        _frame = new JFrame("The People of Music");
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    	setInitPrimaryPanel();
        setInitSiteChartsPanel();
        setInitCommentPanel();

        primaryPanel.add(pnlCommentPanel);
        pnlCommentPanel.setVisible(false);
        primaryPanel.add(pnlSiteChartsPanel);
        //crawlingComments();

        _frame.pack();
        _frame.setVisible(true);
    }
    
      
    private void crawlingComments(){
        System.setProperty("webdriver.chrome.driver","src/driver/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        InsertDatabase.insertCommentDatabase(new MelonAlbumCommentParser(driver).crawl());
        InsertDatabase.insertCommentDatabase(new BugsAlbumCommentParser(driver).crawl());
        InsertDatabase.insertCommentDatabase(new GenieAlbumCommentParser(driver).crawl());

        driver.quit();
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

        _frame.getContentPane().add(primaryPanel);
    }


    private void setInitCommentPanel(){
        pnlCommentPanel = new CommentPanel();
        new CommentPanelController(pnlCommentPanel);
    }

    public void setInitSiteChartsPanel(){
        pnlSiteChartsPanel = new SiteChartsPanel();
        pnlSiteChartsPanel.setLayout(null);
        new SiteChartsPanelController(pnlSiteChartsPanel);
    }

    public JPanel getPrimaryPanel(){
        if(primaryPanel == null)
            setInitPrimaryPanel();
        return primaryPanel;
    }

    public JPanel getPnlPrimaryPanel(){
        if(primaryPanel == null)
            setInitPrimaryPanel();
        return primaryPanel;
    }

    public CommentPanel getPnlCommentPanel() {
        if(pnlCommentPanel == null)
            setInitCommentPanel();
        return pnlCommentPanel;
    }

    public SiteChartsPanel getChartPrimaryPanel() {
        if(pnlSiteChartsPanel == null)
            setInitSiteChartsPanel();
        return pnlSiteChartsPanel;
    }

    public static AppManager getS_instance() {
        if(s_instance == null)
            s_instance = new AppManager();

        return s_instance;
    }
}