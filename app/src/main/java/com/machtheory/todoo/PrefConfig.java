package com.machtheory.todoo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;

public class PrefConfig {

    public static final String LIST_KEY = "list_key";
    public static final String PROGRESS_KEY = "progress_key";
    public static final String DONE_KEY = "done_key";

    public static void listInPref(Context context, ArrayList list){
        Gson gson = new Gson();
        String jsonString = gson.toJson(list);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LIST_KEY, jsonString);
        editor.apply();
        //preferences.edit().clear().commit();
    }

    public static ArrayList readListFromPref (Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = pref.getString(LIST_KEY,"");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        ArrayList list = gson.fromJson(jsonString, type);
        return list;
    }

    public static void removeToDoFromPref(Context context, String s){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(LIST_KEY);
        editor.apply();
    }

    public static void removeInProgFromPref(Context context, String s){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(PROGRESS_KEY);
        editor.apply();
    }

    public static void removeDoneFromPref(Context context, String s){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(DONE_KEY);
        editor.apply();
    }

    public static void progressListInPref(Context context2, ArrayList list2){
        Gson gson = new Gson();
        String jsonString = gson.toJson(list2);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context2);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PROGRESS_KEY, jsonString);
        editor.apply();
    }

    public static ArrayList readProgFromPref (Context context2){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context2);
        String jsonString = pref.getString(PROGRESS_KEY,"");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        ArrayList list2 = gson.fromJson(jsonString, type);
        return list2;
    }

    public static void doneListInPref(Context context3, ArrayList list3){
        Gson gson = new Gson();
        String jsonString = gson.toJson(list3);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context3);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(DONE_KEY, jsonString);
        editor.apply();
    }

    public static ArrayList readDoneFromPref (Context context3){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context3);
        String jsonString = pref.getString(DONE_KEY,"");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        ArrayList list3 = gson.fromJson(jsonString, type);
        return list3;
    }

    public static void registerPref(Context context, SharedPreferences.OnSharedPreferenceChangeListener listener){
        SharedPreferences preferences = context.getSharedPreferences(LIST_KEY, Context.MODE_PRIVATE);
        preferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public static void unregisterPref(Context context, SharedPreferences.OnSharedPreferenceChangeListener listener){
        SharedPreferences preferences = context.getSharedPreferences(LIST_KEY, Context.MODE_PRIVATE);
        preferences.unregisterOnSharedPreferenceChangeListener(listener);
    }

}
