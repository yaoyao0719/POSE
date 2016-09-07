package com.android.customrecyclerview.listener;

import android.support.v7.widget.RecyclerView;

/**
 * Created by yaoyao on 16/6/12.
 */
public interface OnViewClick {

    void onClick(RecyclerView.ViewHolder holder, int position);

    boolean onLongClick(RecyclerView.ViewHolder holder, int position);
}
