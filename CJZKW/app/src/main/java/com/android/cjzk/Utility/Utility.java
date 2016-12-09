package com.android.cjzk.Utility;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.cjzk.R;
import com.android.cjzk.activity.loginActivity;
import com.android.cjzk.weight.MyDialog;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Utility {

    private static Map<String, SoftReference<Bitmap>> mBitmapRefs = new HashMap<String, SoftReference<Bitmap>>();
    public static final String PREFERENCES_NAME = "SP-EDC";
    public static final String DefPicUrl = "http://www.cjzkw.cn";
    public static float screenWidth = 640;
    public static float screenHeight = 1136;

    // 最小消耗加载本地图片，加入软引用，内存不足时释放图片资源
    public static BitmapDrawable getBitMapDrawableFromId(int redIs, Context context) {
        BitmapDrawable bd = null;
        Bitmap bitmap = null;
        if (mBitmapRefs != null
                && mBitmapRefs.get(String.valueOf(redIs)) != null
                && mBitmapRefs.get(String.valueOf(redIs)).get() != null
                && !mBitmapRefs.get(String.valueOf(redIs)).get().isRecycled()) {
            bitmap = mBitmapRefs.get(String.valueOf(redIs)).get();
        } else {
            try {
                BitmapFactory.Options opt = new BitmapFactory.Options();
                opt.inPreferredConfig = Bitmap.Config.RGB_565;
                opt.inPurgeable = true;
                opt.inInputShareable = true;

                InputStream is = context.getResources().openRawResource(redIs);
                bitmap = BitmapFactory.decodeStream(is, null, opt);
                is.close();
            } catch (Exception oom) {
                Log.e("getBitMapDrawableFromId", "getBitMapDrawableFromId");
            } finally {
            }
        }
        if (bitmap != null && !bitmap.isRecycled()) {
            bd = new BitmapDrawable(context.getResources(), bitmap);
            mBitmapRefs.put(String.valueOf(redIs), new SoftReference<Bitmap>(
                    bitmap));
        }
        return bd;
    }

    // 最小消耗加载本地图片
    public static Bitmap readBitMap(Context context, int resId) {
        Bitmap bitmap = null;
        try {
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
            opt.inPurgeable = true;
            opt.inInputShareable = true;
            // 获取资源图片
            InputStream is = context.getResources().openRawResource(resId);
            bitmap = BitmapFactory.decodeStream(is, null, opt);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    //  取数据
    public static String getSharedData(Context context, String str) {
        String data1 = null;
        try {
            SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
            data1 = pref.getString(str, "");
        } catch (Exception e) {
        }
        return data1;
    }

    //   存数据
    public static void putSharedData(Context context, String name, String data) {
        SharedPreferences preferences = context.getSharedPreferences(
                PREFERENCES_NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        try {
            editor.putString(name, data);
        } catch (Exception e) {
        }
        editor.commit();
    }

    //	清除缓存
    public static void clearData(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                PREFERENCES_NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.clear().commit();
    }

    //读取清单文件里的内容
    public static String getMetaValue(Context context, String metaKey) {
        Bundle metaData = null;
        String apiKey = null;
        if (context == null || metaKey == null) {
            return null;
        }
        try {
            ApplicationInfo ai = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                apiKey = metaData.getString(metaKey);
            }
        } catch (NameNotFoundException e) {

        }
        return apiKey;
    }

    public static MyDialog loadingDialog1;

    public static void showLoadingDialog(final Context context, String text) {
        View view = View.inflate(context, R.layout.activity_popu_login, null);
        loadingDialog1 = new MyDialog(context, R.style.loading_dialog);
        view.findViewById(R.id.bt_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog1.dismiss();
                context.startActivity(new Intent(context, loginActivity.class));
                loadingDialog1 = null;
            }
        });
        view.findViewById(R.id.bt_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog1.dismiss();
                loadingDialog1 = null;
            }
        });
        ((TextView) view.findViewById(R.id.tv_content)).setText(text);
        LinearLayout.LayoutParams dialoglp = new LinearLayout.LayoutParams(
                (int) (screenWidth * 3 / 4), (int) (screenWidth / 3));
        loadingDialog1.addContentView(view, dialoglp);
        loadingDialog1.setCancelable(true);
        loadingDialog1.show();
    }

    /**
     * 关闭键盘事件.
     *
     * @param context the context
     */
    public static void closeSoftInput(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && ((Activity) context).getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(((Activity) context).getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 打开键盘.
     *
     * @param context the context
     */
    public static void showSoftInput(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 描述：判断网络是否有效.
     *
     * @param context the context
     * @return true, if is network available
     */
    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * 显示沉浸状态栏 xml在其主体加android:fitsSystemWindows="true"
     *
     * @param color 颜色资源
     * @return 其他设置通过返回对象设置
     */
    @SuppressWarnings("ResourceType")
    public static SystemBarTintManager showImmerseStatusBar(int color, Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true, context);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(context);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(color);
        return tintManager;
    }

    @TargetApi(19)
    private static void setTranslucentStatus(boolean on, Activity context) {
        Window win = context.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     *设置状态栏黑色字体图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     * @param activity
     * @return 1:MIUUI 2:Flyme 3:android6.0
     */
    public static int StatusBarLightMode(Activity activity){
        int result=0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if(MIUISetStatusBarLightMode(activity.getWindow(), true)){
                result=1;
            }else if(FlymeSetStatusBarLightMode(activity.getWindow(), true)){
                result=2;
            }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                result=3;
            }
        }
        return result;
    }
    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     * @param window 需要设置的窗口
     * @param dark 是否把状态栏字体及图标颜色设置为深色
     * @return  boolean 成功执行返回true
     *
     */
    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     * @param window 需要设置的窗口
     * @param dark 是否把状态栏字体及图标颜色设置为深色
     * @return  boolean 成功执行返回true
     *
     */
    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field  field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if(dark){
                    extraFlagField.invoke(window,darkModeFlag,darkModeFlag);//状态栏透明且黑色字体
                }else{
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result=true;
            }catch (Exception e){

            }
        }
        return result;
    }


    public static String getTimeDesc(Date _date) {
        Date _now = new Date();
        long diff = _now.getTime() - _date.getTime();
        StringBuffer bufferTime = null;
        bufferTime = new StringBuffer();
        long _day = diff / 24L / 3600000L;
        long _hour = diff % (24L * 3600000L) / 3600000L;
        long _mini = diff % 3600000L / 60000L;
        long _second = diff % 60000L / 1000L;
        if (_day >= 7) {
            bufferTime.append(DateUtil.formatToYYYYMMDD(_date));
        } else {
            if (_day > 0) {
                bufferTime.append(String.valueOf(_day) + "天");
            }
            if (_hour > 0 && _day == 0) {
                bufferTime.append(String.valueOf(_hour) + "小时");
            }
            if (_mini > 0 && _day == 0 && _hour == 0) {
                bufferTime.append(String.valueOf(_mini) + "分钟");
            }
            if (_day == 0 && _hour == 0 && _mini == 0 && _second >= 10) {
                bufferTime.append(String.valueOf(_second + 1) + "秒");
            }
            if (_day <= 0 && _hour <= 0 && _mini <= 0 && _second < 10) {
                bufferTime.append("刚刚");
            } else {
                bufferTime.append("前");
            }
        }
        _now = null;
        return bufferTime.toString();
    }

    public static int getTimeDate(String time) {
        String time1 = time.substring(time.lastIndexOf("T") + 1, time.lastIndexOf(":"));
        String time2 = time1.substring(0, time1.lastIndexOf(":"));
        return Integer.parseInt(time2);
    }

    public static int setRatio(int ratio) {
        float ratioWidth = screenWidth / 640;
        float ratioHeight = screenHeight / 1136;
        float RATIO = Math.min(ratioWidth, ratioHeight);
        return (Math.round(ratio * RATIO));
    }
}

