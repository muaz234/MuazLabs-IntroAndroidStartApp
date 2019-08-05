package com.muaz.startscreennavigation;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefenceManager {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;
    Boolean first_time

    public PrefenceManager(Context context){
        editor = context.getSharedPreferences("com.muaz.startscreennavigation", Context.MODE_PRIVATE).edit();
    }

    public  void setFirstTimeLaunch(Boolean first_time){
        editor.putBoolean("first", first_time);
        editor.commit();
    }

    public boolean checkFirstTimeLaunch(){
        return preferences.getBoolean("first", true);
    }

}
