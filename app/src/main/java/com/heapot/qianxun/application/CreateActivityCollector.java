package com.heapot.qianxun.application;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 2016/10/8.
 */
public class CreateActivityCollector {
    public static List<Activity> create_activities = new ArrayList<>();
    public static void addActivity(Activity activity){
        create_activities.add(activity);
    }
    public static void removeActivity(Activity activity){
        create_activities.remove(activity);
    }
    public static void finishAll(){
        for (Activity activity : create_activities){
            activity.finish();
        }
    }
}
