package com.android.pose.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.customrecyclerview.ptr.CustomRecyclerView;
import com.android.pose.R;
import com.android.pose.adapter.PicGridRecyclerAdapter;
import com.android.pose.model.PosePic;
import com.finalteam.galleryfinal.GalleryFinal;
import com.finalteam.galleryfinal.model.PhotoInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.recyclerView)
    CustomRecyclerView mRecyclerView;

    private Context mContext;
    private PicGridRecyclerAdapter mAdapter;
    private List<PosePic> mPics=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        initWidget();
    }
    private void initWidget() {
        mAdapter = new PicGridRecyclerAdapter(mContext);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext,2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setRefreshEnable(false);
        mRecyclerView.setRefreshing(false);
        PosePic posePic=new PosePic();
        posePic.imgUrl="http://scimg.jb51.net/allimg/160815/103-160Q509544OC.jpg";
        mPics.add(posePic);
        mPics.add(posePic);
        mPics.add(posePic);
        mPics.add(posePic);
        mPics.add(posePic);
        mPics.add(posePic);
        mAdapter.setList(mPics);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_camera) {
            openGallery();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openGallery() {

        GalleryFinal.openGallerySingle(1111, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                if (resultList != null && resultList.get(0) != null) {
                    PhotoInfo info = resultList.get(0);
                    if (TextUtils.isEmpty(info.getPhotoPath())) {
                        //    ToastUtil.showToast(MyInfoActivity.this,"上传图片失败");
                    } else {
                        //   uploadImage(info.getPhotoPath());
                    }
                }
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {

            }
        });

    }
}
