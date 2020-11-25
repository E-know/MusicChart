package main;

import controller.*;
import model.BugsAlbumCommentParser;
import model.GenieAlbumCommentParser;
import model.MelonAlbumCommentParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import view.SiteChartsPanel;
import view.CommentPanel;

import javax.swing.*;
import java.awt.*;

public class AppManager {
    private static AppManager s_instance;
    private CommentPanel pnlCommentPanel;
    private SiteChartsPanel pnlChartPrimary;
    private JPanel primaryPanel;
    private ChartPrimaryPanelController theChartPrimaryPanelController;
    private CommentPanelController theCommentPanelController;

    private AppManager() {
        s_instance = this;

        crawlingComments();
    }

    private void crawlingComments(){
        System.setProperty("webdriver.chrome.driver","src/driver/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        new MelonAlbumCommentParser(driver).crawl();

        new BugsAlbumCommentParser(driver).crawl();

        new GenieAlbumCommentParser(driver).crawl();

        driver.close();
    }

    public JPanel getPrimaryPanel() {
        if (primaryPanel == null) {
            primaryPanel = new JPanel() {
                public void paintComponent(Graphics g) {
                    ImageIcon icon = new ImageIcon("Image\\background.jpg");
                    g.drawImage(icon.getImage(), 0, 0, null);
                    setOpaque(false);
                    super.paintComponent(g);
                }
            };
            primaryPanel.setLayout(null);
            primaryPanel.setPreferredSize(new Dimension(1280, 960));
            primaryPanel.setBackground(Color.BLACK);
        }
        return primaryPanel;
    }

    public CommentPanel getPnlCommentUI() {
        return pnlCommentPanel;
    }

    public SiteChartsPanel getChartPrimaryPanel() {
        return pnlChartPrimary;
    }

    public void setPnlCommentPanel() {
        pnlCommentPanel = new CommentPanel();
        theCommentPanelController = new CommentPanelController(pnlCommentPanel);
    }

    public void setPnlChartPrimary() {
        pnlChartPrimary = new SiteChartsPanel();
        theChartPrimaryPanelController = new ChartPrimaryPanelController(pnlChartPrimary);
    }

    public void PrimaryPanel() {
        if (primaryPanel == null) {
            primaryPanel = new JPanel();
            primaryPanel.setVisible(true);
            primaryPanel.setLayout(null);
        }
        setPnlCommentPanel();
        setPnlChartPrimary();
        addToPrimaryPanel(pnlCommentPanel);
        addToPrimaryPanel(pnlChartPrimary);
        //pnlChartPrimary.setLayout(null);
        pnlChartPrimary.setVisible(true);
    }

    public void addToPrimaryPanel(JPanel pnlAdd) {
        pnlAdd.setVisible(false);
        primaryPanel.add(pnlAdd);
    }

    public static AppManager getS_instance() {
        if (s_instance == null) s_instance = new AppManager();
        return s_instance;
    }
}