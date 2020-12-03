package main;

import model.DB.ConnectDB;
import controller.AppManager;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {
    static ConnectDB DB = new ConnectDB();

    public static void main(String[] args){
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

        AppManager.getS_instance();
    }
}