package com.example.momoleague;

import android.app.Application;

public class MyApp extends Application {
    private static String uid;
    private static boolean isAdmin = false;

    public static String getUid(){
        return uid;
    }

    public static void setUid(String uid) {
        MyApp.uid = uid;
    }

    public static boolean getIsAdmin() {
        return isAdmin;
    }

    public static void setIsAdmin(boolean isAdmin) {
        MyApp.isAdmin = isAdmin;
    }
}
