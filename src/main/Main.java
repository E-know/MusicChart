package main;

import javax.swing.*;
import DB.ConnectDB;
import model.ChartData;
import model.DetailData;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {
    static ConnectDB DB = new ConnectDB();

    public static void main(String[] args){
        JFrame frame = new JFrame("The People of Music");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // JVM�� ������ �� Ư�� �۾� ������ �����ϱ�.
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("JVM�� ������...");
                try {
                    DB.getDB();
                    DB.deleteRecentDB(InetAddress.getLocalHost().getHostName());
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        });

        frame.getContentPane().add(AppManager.getS_instance().getPnlPrimaryPanel());
        //ChartData.getS_instance();
        //DetailData.getS_instance();

        frame.pack();
        frame.setVisible(true);
    }
}