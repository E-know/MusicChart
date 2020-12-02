package main;

import model.DB.ConnectDB;
import controller.AppManager;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {
    static ConnectDB DB = new ConnectDB();

    public static void main(String[] args){
        // JVM�� ������ �� Ư�� �۾� ������ �����ϱ�.
        DB.driverLoad();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("JVM�� ������...");
                try {
                    DB.connectionDB();
                    DB.deleteRecentDB(InetAddress.getLocalHost().getHostName());
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        });

        AppManager.getS_instance();
    }
}