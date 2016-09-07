package com.android.pose.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 手机相关工具类，用户获取手机相关信息
 */
public final class DeviceUtil {
    private static DeviceUtil phoneUtil;
    private Context mContext;
    private TelephonyManager telephonyManager;

    /**
     * 获取手机内存信息<br/>
     * 实际上就是读取 /proc/meminfo 文件的内容<br/>
     * 返回的 Map 中，Key 是文件中的字段名，Value 为对应的数值，单位是：KB<br/>
     * 包含的字段及其含义：<br/>
     * <pre>
     *      MemTotal：所有可用RAM大小
     *      MemFree：LowFree与HighFree的总和，被系统留着未使用的内存
     *      Buffers：用来给文件做缓冲大小
     *      Cached：被高速缓冲存储器（cache memory）用的内存的大小（等于diskcache minus SwapCache）
     *      SwapCached：被高速缓冲存储器（cache memory）用的交换空间的大小。已经被交换出来的内存，仍然被存放在swapfile中，用来在需要的时候很快的被替换而不需要再次打开I/O端口
     *      Active：在活跃使用中的缓冲或高速缓冲存储器页面文件的大小，除非非常必要，否则不会被移作他用
     *      Inactive：在不经常使用中的缓冲或高速缓冲存储器页面文件的大小，可能被用于其他途径
     *      SwapTotal：交换空间的总大小
     *      SwapFree：未被使用交换空间的大小
     *      Dirty：等待被写回到磁盘的内存大小
     *      Writeback：正在被写回到磁盘的内存大小
     *      AnonPages：未映射页的内存大小
     *      Mapped：设备和文件等映射的大小
     *      Slab：内核数据结构缓存的大小，可以减少申请和释放内存带来的消耗
     *      SReclaimable：可收回Slab的大小
     *      SUnreclaim：不可收回Slab的大小（SUnreclaim+SReclaimable＝Slab）
     *      PageTables：管理内存分页页面的索引表的大小
     *      NFS_Unstable：不稳定页表的大小
     * </pre>
     *
     * @return 包含手机内存信息的 Map
     */
    public static Map<String, Long> getMemoryInfo() {
        Map<String, Long> result = new HashMap<String, Long>();
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;

        String str1 = "/proc/meminfo";
        String line = null;
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            while ((line = localBufferedReader.readLine()) != null) {
                String[] arrayOfLine = line.split("\\s+");
                if (arrayOfLine.length == 3) {
                    result.put(arrayOfLine[0], Long.parseLong(arrayOfLine[1]));
                }
            }
            localBufferedReader.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 获取 CPU 信息<br/>
     * 实际上就是读取 /proc/cpuinfo 文件的内容
     *
     * @return 包含 CPU 信息的 Map
     */
    public static Map<String, String> getCPUInfo() {
        Map<String, String> cpuInfo = new HashMap<String, String>();
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec("cat /proc/cpuinfo");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] strs = line.split(":");
                if (strs.length == 2) {
                    cpuInfo.put(strs[0].trim(), strs[1].trim());
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cpuInfo;
    }

    /**
     * 获取 PhoneUtil 类的实例
     *
     * @param context 上下文
     * @return 唯一的 PhoneUtil 实例
     */
    public synchronized static DeviceUtil getInstance(Context context) {
        if (phoneUtil == null) {
            phoneUtil = new DeviceUtil(context);
        }
        return phoneUtil;
    }

    private DeviceUtil(Context context) {
        mContext = context.getApplicationContext();
        telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
    }


    /**
     * 获取手机外部存储器的可用空间大小<br/>
     * 单位：byte
     *
     * @return 外部存储器不存在，则返回 -1
     */
    public static long getAvailableExternalMemorySize(Context context) {
        if (!isHaveSDCard()) {
            return -1;
        }
//        File path = Environment.getExternalStorageDirectory();
//        StatFs stat = new StatFs(path.getFilePathFromUri());
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
        long blockSize = stat.getBlockSize();
        long availableBlock = stat.getAvailableBlocks();
        return blockSize * availableBlock;
    }

    public static long getTotalExternalMemorySize(Context context) {
        if (!isHaveSDCard()) {
            return -1;
        }
        StatFs sFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
        long blockSize = sFs.getBlockSize();
        long totalBlocks = sFs.getBlockCount();
        return blockSize * totalBlocks;
    }

    /**
     * 判断是否有SD卡
     *
     * @return
     */
    public static boolean isHaveSDCard() {
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获得手机内存的可用空间大小<br/>
     * 单位：byte
     *
     * @return 内存可用空间大小
     */
    public static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return blockSize * availableBlocks;
    }

    /**
     * 获取手机的电子串号
     *
     * @return 手机电子串号
     */
    public String getEsn() {
        return telephonyManager.getDeviceId();
    }

    /**
     * 获取客户端系统版本
     *
     * @return 客户端系统版本
     */
    public String getClientOSVersion() {
        return "android " + Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public String getModel() {
        String model = Build.MODEL;
        if ("sdk".equals(model)) {
            model = "XT800";
        }
        return model;
    }

    /**
     * 获取手机的品牌
     *
     * @return 手机品牌，没有则返回空串
     */
    public String geteBrand() {
        return Build.BRAND == null ? "" : Build.BRAND;
    }


    /**
     * 获取手机的 IMSI 号<br/>
     * IMSI是国际移动用户识别码的简称(International Mobile Subscriber Identity)<br/>
     * IMSI共有15位，其结构如下：<br/>
     * MCC+MNC+MIN<br/>
     * MCC：Mobile Country Code，移动国家码，共3位，中国为460;<br/>
     * MNC:Mobile NetworkCode，移动网络码，共2位<br/>
     * 在中国，移动的代码为电00和02，联通的代码为01，电信的代码为03<br/>
     * 合起来就是（也是Android手机中APN配置文件中的代码）：<br/>
     * 中国移动：46000 46002<br/>
     * 中国联通：46001<br/>
     * 中国电信：46003<br/>
     * 举例，一个典型的IMSI号码为460030912121001<br/>
     *
     * @return IMSI 号
     */
    public String getIMSI() {
        String imsi = null;
//        int state = telephonyManager.getSimState();
//        if(state == TelephonyManager.SIM_STATE_ABSENT || state == TelephonyManager.SIM_STATE_UNKNOWN){
//            return null;
//        }
        try {
            imsi = telephonyManager.getSubscriberId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (imsi == null || imsi.length() == 0) {
            imsi = "00000000000000";
        }
        return imsi;
    }

    /**
     * 获取手机 SDK 版本
     *
     * @return 手机 SDK 版本
     */
    public int getPhoneSDK() {
        return Integer.parseInt(Build.VERSION.SDK);
    }

    /**
     * 获取手机内置 CONFIG_UA
     *
     * @return CONFIG_UA
     */
    public String getPhoneUA() {
        return Build.MODEL;
    }

    /**
     * 获取手机屏幕信息
     *
     * @return 手机屏幕信息，如尺寸等
     */
    public DisplayMetrics getScreenInfo() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    /**
     * 获取标题栏高度
     *
     * @param activity Activity 对象
     * @return 标题栏高度
     */
    public int getTitleBarHeight(Activity activity) {
        return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop() - getStatusBarHeight(activity);
    }

    /**
     * 获取状态栏高度
     *
     * @param activity Activity 对象
     * @return 状态栏高度
     */
    public int getStatusBarHeight(Activity activity) {
        return getPhoneDisplaySize(activity).top;
    }

    /**
     * 获取手机屏幕显示区域大小，包括标题栏，不包括状态栏
     *
     * @param activity Activity 对象
     * @return 手机屏幕显示区域大小
     */
    public Rect getPhoneDisplaySize(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect;
    }

    /**
     * 获取手机外部存储器的总空间大小<br/>
     * 单位：byte
     *
     * @return 外部存储器不存在，则返回 -1
     */
    public long getTotalExternalMemorySize() {
        if (!isHaveSDCard()) {
            return -1;
        }
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long blockCount = stat.getBlockCount();
        return blockSize * blockCount;
    }

    /**
     * 获取手机内存的总空间大小<br/>
     * 单位：byte
     *
     * @return 手机内存总大小
     */
    public long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }




    public static boolean isAndroidVersionOlderThan4_0(){
        return Build.VERSION.SDK_INT <= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1;
    }

}
