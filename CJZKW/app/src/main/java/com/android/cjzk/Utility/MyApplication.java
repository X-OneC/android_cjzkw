package com.android.cjzk.Utility;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.util.LinkedList;
import java.util.List;

public class MyApplication extends Application {
    private static MyApplication instance;
    private PushAgent mPushAgent;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }
    //onCreate


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initImageLoader(getApplicationContext());
        mPushAgent = PushAgent.getInstance(this);
        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            @Override
            public void dealWithCustomMessage(final Context context, final UMessage msg) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        boolean isClickOrDismissed = true;
                        if (isClickOrDismissed) {
                            UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
                        } else {
                            UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
                        }
                    }
                });
            }
        };
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                Intent var3 = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
                var3.setPackage((String) null);
                var3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                var3.putExtra("ArtCtId", msg.custom);
                context.startActivity(var3);
                if (msg.custom.equals("更新")) {
                    Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
//                else {
//                    var3.setClass(context, WebViewActivity.class);
//                    var3.putExtra("ArtCtId", msg.custom);
//                    var3.putExtra("isCollection", false);
//                    context.startActivity(var3);
//                }
                super.dealWithCustomAction(context, msg);
            }
        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);
        UMShareAPI.get(this);
    }

    private List<Activity> activityList = new LinkedList<Activity>();

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void exit() {
        try {
            for (Activity activity : activityList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    public void finishAll() {
        try {
            for (Activity activity : activityList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(480, 800)
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 1)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new WeakMemoryCache())
                .memoryCacheSize(10 * 1024 * 1024)
                .memoryCacheSizePercentage(13)
                .diskCacheSize(50 * 1024 * 1024).diskCacheFileCount(1000)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(context))
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .build();
        ImageLoader.getInstance().init(config);
    }

    //各个平台的配置，建议放在全局Application或者程序入口
    {
        //微信 wx12342956d1cab4f9,a5ae111de7d9ea137e88a5e02c07c94d
        PlatformConfig.setWeixin("wx1cf179a72642f7e4", "0619169db0bbbf3ba8f38c568aa56788");
        PlatformConfig.setQQZone("1104792315", "MLCc0s5oCAWTyzZm");
    }
}
