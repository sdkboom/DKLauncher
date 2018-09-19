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
import com.example.dklanucher.helper.RecyclerCallback;

import java.util.Collections;
import java.util.List;

/**
 * Created by SDK on 2018-9-18.
 */

public class AppsRecyclerAdapter extends RecyclerView.Adapter<AppsRecyclerAdapter.AppHolder> {

    private static List<AppItem> mDataList ;
    private Context mContext ;
    private RecyclerAdapterListener mListener ;

    public AppsRecyclerAdapter(List<AppItem> dataList , Context context ){

        mDataList = dataList ;
        mContext = context ;
        mListener = new RecyclerAdapterListener();

    }

    public RecyclerAdapterListener getListener(){
        return mListener ;
    }

    @Override
    public AppHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.app_item,parent,false);
        final AppHolder holder = new AppHolder(view);
        holder.appItem.setOnClickListener(new View.OnClickListener() {
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
        public View appItem;

        public AppHolder(View itemView) {
            super(itemView);

            appItem = itemView ;
            appIcon = itemView.findViewById(R.id.app_icon);
            appName = itemView.findViewById(R.id.app_name);

        }
    }

    public class RecyclerAdapterListener implements RecyclerCallback.RecyclerTouchListener{

        @Override
        public boolean onMove(int fromPosition, int toPosition) {
            if(fromPosition > toPosition){
                for(int i = fromPosition ; i > toPosition ; i --){
                    Collections.swap(mDataList,i,i-1);
                }
            }else {
                for(int i = fromPosition ; i < toPosition ; i ++){
                    Collections.swap(mDataList,i,i+1);
                }
            }
            notifyItemMoved(fromPosition,toPosition);
            return true;
        }

        @Override
        public void onSwiped(int position) {
            mDataList.remove(position);
        }
    }

    public static List<AppItem> getDataList(){
        return mDataList ;
    }

}
