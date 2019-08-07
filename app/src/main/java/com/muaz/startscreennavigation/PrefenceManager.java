package com.muaz.startscreennavigation;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefenceManager {


    SharedPreferences.Editor editor;
    Context context;
    Boolean first_time = true;
//    editor = context.getSharedPreferences("com.muaz.startscreennavigation", Context.MODE_PRIVATE).edit();

    SharedPreferences preferences;

    public PrefenceManager(Context context){
        preferences = context.getSharedPreferences("launch", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public  void setFirstTimeLaunch(boolean first_time){
        editor.putBoolean("first", first_time);
        editor.commit();
    }

    public boolean checkFirstTimeLaunch(){
//        preferences = context.getSharedPreferences("com.muaz.startscreennavigation", Context.MODE_PRIVATE);
        return preferences.getBoolean("first", true);
    }

}
