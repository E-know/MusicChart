package main;

import javax.swing.*;
import model.DB.ConnectDB;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {
    static ConnectDB DB = new ConnectDB();

    public static void main(String[] args){
        JFrame frame = new JFrame("The People of Music");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // JVM이 종료할 때 특정 작업 무조건 수행하기.
        DB.driverLoad();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("JVM은 종료중...");
                try {
                    DB.connectionDB();
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