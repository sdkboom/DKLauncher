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
import com.example.dklanucher.util.DataSaveUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String TAG = "DKLauncher";

    private RecyclerView recyclerView ;
    private List<AppItem> mAppsList;
    private Context mContext ;
    private ItemTouchHelper itemTouchHelper ;
    private AppsRecyclerAdapter adapter ;
    private List<Integer> mOrderList ;
    private List<AppItem> mAppsOrderList;
    private DataSaveUtil dataSaveUtil ;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "MainActivity--onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        loadApps();
        initAdapter();

    }

    /**
     * 加载已安装的app
     */
    private void loadApps() {
        Log.e(TAG, "MainActivity--loadApps: ");
        mOrderList = getOrderList();
        queryAllApps();
        if(null == mOrderList || mOrderList.size() <= 0){
            mAppsOrderList = mAppsList ;
            return;
        }
        for(int i = 0 ; i < mOrderList.size(); i ++ ){
            mAppsOrderList.add(mAppsList.get(mOrderList.get(i)));
        }
    }

    /**
     * 从SharedPreferences中取出App拖动后的顺序列表mOrderList。
     * @return
     */
    private List<Integer> getOrderList() {
        return DataSaveUtil.getData();
    }

    private void initAdapter() {
        Log.e(TAG, "MainActivity--initAdapter: " );
        adapter = new AppsRecyclerAdapter(mAppsOrderList, mContext);
        itemTouchHelper = new ItemTouchHelper(new RecyclerCallback(adapter.getListener()));
        GridLayoutManager layoutManager = new GridLayoutManager(mContext,4,GridLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    /**
     * 查找本机内所有已安装APP，并将单个应用信息封装进AppItem类存入集合mAppList中
     */
    private void queryAllApps() {
        Log.e(TAG, "MainActivity--queryAllApps: " );
        PackageManager manager = mContext.getPackageManager() ;
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> list = manager.queryIntentActivities(intent,0);
        int i = 0 ;
        for(ResolveInfo info : list){
            AppItem item = new AppItem() ;
            item.setAppIcon(info.loadIcon(manager));
            item.setAppName((String) info.loadLabel(manager));
            item.setIntent(new Intent().setComponent(new ComponentName(info.activityInfo.packageName,info.activityInfo.name)));
            item.setPkgName(info.activityInfo.packageName);
            item.setId(i);
            mAppsList.add(item);
            i++ ;
        }
    }

    private void initViews() {
        Log.e(TAG, "MainActivity--initViews: " );
        recyclerView = findViewById(R.id.recycler_apps);
        mAppsList = new ArrayList<>();
        mAppsOrderList = new ArrayList<>();
        mOrderList = new ArrayList<>();
        mContext = this ;
        dataSaveUtil = new DataSaveUtil(mContext,"apps");
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "MainActivity--onDestroy: " );
        dataSaveUtil.setData(mAppsOrderList);
        super.onDestroy();
    }
}
