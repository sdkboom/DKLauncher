package com.example.dklanucher.helper;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by SDK on 2018-9-19.
 */

public class RecyclerCallback extends ItemTouchHelper.Callback {

    private RecyclerTouchListener mListener ;

    public RecyclerCallback(RecyclerTouchListener listener){
        mListener = listener ;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if(recyclerView.getLayoutManager() instanceof GridLayoutManager |recyclerView
                .getLayoutManager() instanceof StaggeredGridLayoutManager){
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT ;
            int swipeFlags = 0 ;
            return makeMovementFlags(dragFlags,swipeFlags);
        }else {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN ;
            int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT ;
            return makeMovementFlags(dragFlags,swipeFlags);
        }
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition() ;
        int toPosition = target.getAdapterPosition() ;
        return mListener.onMove(fromPosition,toPosition);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition() ;
        mListener.onSwiped(position);
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if(actionState != ItemTouchHelper.ACTION_STATE_IDLE){
            viewHolder.itemView.setBackgroundColor(Color.GRAY);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setBackgroundColor(0);
    }

    public interface RecyclerTouchListener {

        boolean onMove(int fromPosition, int toPosition);
        void onSwiped(int position);

    }
}
