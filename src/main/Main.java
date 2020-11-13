package main;

import javax.swing.*;
import model.ChartData;
public class Main {
    public static void main(String[] args){
        JFrame frame = new JFrame("The People of Music");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(AppManager.getS_instance().getPrimaryPanel());
        AppManager.getS_instance().addToPrimaryPanel(AppManager.getS_instance().getPnlChartPrimary());
        ChartData.getS_instance();

        frame.pack();
        frame.setVisible(true);
    }
}