package com.example.dklanucher;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.example.dklanucher.adapter.AppsRecyclerAdapter;
import com.example.dklanucher.helper.RecyclerCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String TAG = "DKLauncher";

    private RecyclerView recyclerView ;
    private List<AppItem> mAppsData ;
    private Context mContext ;
    private ItemTouchHelper itemTouchHelper ;
    private AppsRecyclerAdapter adapter ;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "MainActivity--onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        loadApps();
        initAdapter();

    }

    private void loadApps() {
        Log.e(TAG, "MainActivity--loadApps: ");
        if(null == mAppsData || mAppsData.size() <= 0){
            queryAllApps();
        }
    }

    private void initAdapter() {
        Log.e(TAG, "MainActivity--initAdapter: " );
        adapter = new AppsRecyclerAdapter(mAppsData , mContext);
        itemTouchHelper = new ItemTouchHelper(new RecyclerCallback(adapter.getListener()));
        GridLayoutManager layoutManager = new GridLayoutManager(mContext,4,GridLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void queryAllApps() {
        Log.e(TAG, "MainActivity--queryAllApps: " );
        PackageManager manager = mContext.getPackageManager() ;
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> list = manager.queryIntentActivities(intent,0);
        for(ResolveInfo info : list){
            AppItem item = new AppItem() ;
            item.setAppIcon(info.loadIcon(manager));
            item.setAppName((String) info.loadLabel(manager));
            item.setIntent(new Intent().setComponent(new ComponentName(info.activityInfo.packageName,info.activityInfo.name)));
            item.setPkgName(info.activityInfo.packageName);
            mAppsData.add(item);
        }
    }

    private void initViews() {
        Log.e(TAG, "MainActivity--initViews: " );
        recyclerView = findViewById(R.id.recycler_apps);
        mAppsData = new ArrayList<>();
        mContext = this ;
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "MainActivity--onDestroy: " );
        super.onDestroy();
    }
}
