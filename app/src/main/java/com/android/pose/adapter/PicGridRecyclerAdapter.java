package com.android.pose.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.pose.model.PosePic;
import com.android.pose.view.PicItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaoyao on 16/8/1.
 * description: 投资人列表适配器
 */
public class PicGridRecyclerAdapter extends RecyclerView.Adapter {


    private List<PosePic> mPics;
    private Context mContext;

    public PicGridRecyclerAdapter(Context context) {
        mContext = context;
        mPics = new ArrayList<>();
    }

    public void setList(List<PosePic> data) {
        mPics.clear();
        mPics.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = new PicItemView(mContext);
        return new ItemHolder(itemView);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindListHolder((ItemHolder) holder, position);
    }

    private void onBindListHolder(ItemHolder holder, int parentPosition) {
        if (holder.itemView instanceof PicItemView) {
            ((PicItemView) holder.itemView).setImage(mPics.get(parentPosition));
        }
    }

    @Override
    public int getItemCount() {
        if (mPics != null) {
            return mPics.size();
        }
        return 0;
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        public ItemHolder(View itemView) {
            super(itemView);
        }
    }
}
