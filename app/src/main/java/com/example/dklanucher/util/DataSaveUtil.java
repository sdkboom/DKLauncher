package com.example.dklanucher.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.dklanucher.AppItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SDK on 2018-9-19.
 */

public class DataSaveUtil {

    private static String TAG = "DKLauncher";
    private static SharedPreferences.Editor editor ;
    private static SharedPreferences preferences ;

    public DataSaveUtil(Context context , String preferencesName){
        Log.e(TAG, "DataSaveUtil------DataSaveUtil: " );
        preferences = context.getSharedPreferences(preferencesName,Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    /**
     * 将AppList中的单个id顺序存入SharedPreferences中
     * @param datalist   app集合
     */
    public static void setData(List<AppItem> datalist){
        Log.e(TAG, "DataSaveUtil------setData: start!" );
        if(datalist == null || datalist.size() <= 0)
            return;
        editor.clear();
        editor.putInt("size",datalist.size());
        Log.e(TAG, "setData: SIZE-----"+datalist.size() );
        for(int i = 0 ; i < datalist.size() ; i ++ ){
            editor.putInt("position"+i,datalist.get(i).getId());
            Log.e(TAG, "setData: putInt---------"+datalist.get(i).getId());
        }
        editor.apply();
        Log.e(TAG, "setData: "+datalist.get(0).getAppName());
        Log.e(TAG, "DataSaveUtil------setData: end!" );
    }

    public static List<Integer> getData(){
        Log.e(TAG, "DataSaveUtil------getData: start!" );
        int size = preferences.getInt("size",0);
        Log.e(TAG, "getData: SIZE-----"+size );
        List<Integer> mOrderList = new ArrayList<>();
        if(size == 0)
            return null;
        for(int i = 0 ; i < size ; i ++ ){
            int order = preferences.getInt("position"+i,0);
            mOrderList.add(order);
        }
        Log.e(TAG, "getData: "+mOrderList );
        Log.e(TAG, "DataSaveUtil------getData: end!" );
        return mOrderList;
    }

}
