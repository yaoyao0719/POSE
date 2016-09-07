package com.android.pose;

import android.app.Application;

import com.android.pose.service.FileStoreService;
import com.android.pose.utils.DeviceUtil;
import com.finalteam.galleryfinal.CoreConfig;
import com.finalteam.galleryfinal.FunctionConfig;
import com.finalteam.galleryfinal.GalleryFinal;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;
import java.util.Map;

/**
 * Created by yaoyao on 16/9/5.
 */

public class PoseApplication extends Application {
    private static PoseApplication instance;
    public static PoseApplication getInstance() {
        return instance;
    }
    public void onCreate() {
        super.onCreate();
        instance = this;
        initImageLoader();
        initGalleryPhoto();
    }
    private void initGalleryPhoto() {
        com.finalteam.galleryfinal.ImageLoader imageLoader = new com.finalteam.galleryfinal.utils.UILImageLoader();
        FunctionConfig config = new FunctionConfig.Builder()
                .setEnableEdit(false)
                .setEnableCrop(false)//开启裁剪功能
                .setCropSquare(false)
                .setEnableCamera(true)
                .setEnablePreview(true)
                .build();
        CoreConfig coreConfig = new CoreConfig.Builder(PoseApplication.this,imageLoader,null)
                .setFunctionConfig(config)
                .setNoAnimcation(true)
                .setDebug(true)
                .build();
        GalleryFinal.init(coreConfig);
    }

    public void initImageLoader(){
        //===============初始化图片加载器
        File imageCacheDir = new File(FileStoreService.getCacheDirExternal());
        int maxWidth = getResources().getDisplayMetrics().widthPixels;
        int maxHeight = getResources().getDisplayMetrics().heightPixels;
        int memCacheSize = 0;
        Map<String, Long> memInfo = DeviceUtil.getMemoryInfo();
        if (memInfo != null && memInfo.containsKey("MemTotal:")) {
            memCacheSize = (int) ((memInfo.get("MemTotal:") * 1000) * 0.006);
        }
        memCacheSize = 20 * 1024 * 1024;
        ImageLoaderConfiguration.Builder configBuilder = new ImageLoaderConfiguration
                .Builder(this)
                .memoryCacheExtraOptions(maxWidth, maxHeight) // max wi v   dth, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(3)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCache(new UsingFreqLimitedMemoryCache(memCacheSize == 0 ? 12 * 1024 * 1024 : memCacheSize)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                .diskCache(new UnlimitedDiskCache(imageCacheDir))//自定义缓存路径
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000)); // connectTimeout (5 s), readTimeout (30 s)超时时间
        ImageLoaderConfiguration config = configBuilder.build();
        ImageLoader.getInstance().init(config);

    }
}
