package com.example.dklanucher.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dklanucher.AppItem;
import com.example.dklanucher.R;

import java.util.List;

/**
 * Created by SDK on 2018-9-18.
 */

public class AppsRecyclerAdapter extends RecyclerView.Adapter<AppsRecyclerAdapter.AppHolder> {

    private List<AppItem> mDataList ;
    private Context mContext ;

    public AppsRecyclerAdapter(List<AppItem> dataList , Context context){

        mDataList = dataList ;
        mContext = context ;

    }

    @Override
    public AppHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.app_item,parent,false);
        final AppHolder holder = new AppHolder(view);
        holder.app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Intent intent = mDataList.get(position).getIntent();
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(AppHolder holder, int position) {

        holder.appName.setText(mDataList.get(position).getAppName());
        holder.appIcon.setImageDrawable(mDataList.get(position).getAppIcon());

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class AppHolder extends RecyclerView.ViewHolder {

        private ImageView appIcon ;
        private TextView appName ;
        private View app ;

        public AppHolder(View itemView) {
            super(itemView);

            app = itemView ;
            appIcon = itemView.findViewById(R.id.app_icon);
            appName = itemView.findViewById(R.id.app_name);

        }
    }
}
