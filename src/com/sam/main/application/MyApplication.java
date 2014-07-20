package com.sam.main.application;

import android.app.Activity;
import android.app.Application;
import android.os.*;
import android.os.Process;

import java.util.ArrayList;

/**
 * 作为全局容器，实现直接退出应用程序
 */
public class MyApplication extends Application {
    public static ArrayList<Activity> activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity)
    {
        activities.add(activity);
    }
    public static void finishActivity()
    {
        for (int i = 0; i < activities.size(); i++)
        {
            activities.get(i).finish();
        }
        Process.killProcess(Process.myPid()); //所有activity关闭后，结束进程
    }
}
