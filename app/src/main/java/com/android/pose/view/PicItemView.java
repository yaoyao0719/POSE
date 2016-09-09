package com.android.pose.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android.pose.R;
import com.android.pose.model.PosePic;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.wangyeming.simplecamera.TakePhotoUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yaoyao on 16/9/5.
 */
public class PicItemView extends FrameLayout {

    private static final int REQUEST_CODE_FOR_CAMERA = 1;
    @Bind(R.id.image)
    ImageView mImage;

    public PicItemView(Context context) {
        super(context);
        init();
    }

    public PicItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PicItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View contentView =  LayoutInflater.from(getContext()).inflate(R.layout.pic_item_view, this);
        ButterKnife.bind(this,contentView);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TakePhotoUtils.takePhotoByCamera2((Activity) getContext(), REQUEST_CODE_FOR_CAMERA,mPosePic.imgUrl);
            }
        });
    }

    private PosePic mPosePic;

    public void setImage(PosePic posePic) {
        this.mPosePic = posePic;
        ImageLoader.getInstance().displayImage(mPosePic.imgUrl, mImage, displayImageOptions);
     //   ImageLoader.getInstance().displayImage("file:///mnt/sdcard/pose/1.png" , mImage, displayImageOptions);
    }


    DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.mipmap.ic_launcher)          // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.ic_launcher)    // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.ic_launcher)         // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true)                          // 设置下载的图片是否缓存在SD卡中
            .displayer(new RoundedBitmapDisplayer(4))  // 设置成圆角图片
            .build();                                   // 创建配置过得DisplayImageOption对象

}
