package com.android.customrecyclerview.listener;

/**
 * Created by yaoyao on 16/6/12.
 */
public interface IHeaderView {

    void pullToRefresh();

    void releaseToRefresh();

    void onRefresh();

    void onReset(float distance, float fraction);

    void onPull(float distance, float fraction);
}
