package view;

import controller.*;
import controller.commentParser.BugsAlbumCommentParser;
import controller.commentParser.GenieAlbumCommentParser;
import controller.commentParser.MelonAlbumCommentParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import model.DB.InsertDatabase;

import javax.swing.*;
import java.awt.*;

public class ViewManager {
    private static ViewManager s_instance;
    private CommentPanel pnlCommentPanel;
    private SiteChartsPanel pnlSiteChartsPanel;
    private JPanel primaryPanel;
    InsertDatabase InsertDatabase = new InsertDatabase();

    private ViewManager(){
    	setInitPrimaryPanel();
        setInitSiteChartsPanel();
        setInitCommentPanel();

        primaryPanel.add(pnlCommentPanel);
        pnlCommentPanel.setVisible(false);
        primaryPanel.add(pnlSiteChartsPanel);
        //crawlingComments();
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

    public static ViewManager getS_instance() {
        if(s_instance == null)
            s_instance = new ViewManager();

        return s_instance;
    }
}