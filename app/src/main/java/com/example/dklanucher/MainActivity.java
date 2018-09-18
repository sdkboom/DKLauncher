package com.example.dklanucher;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.LinearInterpolator;

import com.example.dklanucher.adapter.AppsRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView ;
    private List<AppItem> mAppsData ;
    private Context mContext ;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        queryAllApps();
        initAdapter();

    }

    private void initAdapter() {
        AppsRecyclerAdapter adapter = new AppsRecyclerAdapter(mAppsData , mContext);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext,4,GridLayoutManager.HORIZONTAL,false);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void queryAllApps() {
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
        recyclerView = findViewById(R.id.recycler_apps);
        mAppsData = new ArrayList<>();
        mContext = this ;
    }
}
