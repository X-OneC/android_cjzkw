package imageShow;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.cjzk.R;
import com.android.cjzk.Utility.Utility;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

/**
 * 图片浏览的PagerAdapter
 */
public class ImagePagerAdapter extends PagerAdapter {
    Context context;
    ArrayList<String> imgsUrl;
    LayoutInflater inflater = null;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options;
    //view内控件
    TouchImageView full_image;

    public ImagePagerAdapter(Context context, ArrayList<String> imgsUrl) {
        this.context = context;
        this.imgsUrl = imgsUrl;
        inflater = LayoutInflater.from(context);
//        options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.mipmap.background).showImageForEmptyUri(R.mipmap.text)
//                .showImageOnFail(R.mipmap.text).cacheInMemory(true)
//                .cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).build();
        options = Options.getListOptions();
    }

    /**
     * 动态加载数据
     */
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        ((ImageShowViewPager) container).mCurrentView = ((TouchImageView) ((View) object).findViewById(R.id.full_image));
    }

    @Override
    public int getCount() {
        return imgsUrl == null ? 0 : imgsUrl.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = inflater.from(context).inflate(R.layout.details_imageshow_item, null);
        full_image = (TouchImageView) view.findViewById(R.id.full_image);
       final ProgressBar bar = (ProgressBar) view.findViewById(android.R.id.empty);
        Log.d("strings", "instantiateItem: ---"+ Utility.DefPicUrl + imgsUrl.get(position));
        imageLoader.displayImage(Utility.DefPicUrl + imgsUrl.get(position), full_image, options, new ImageLoadingListener() {

            @Override
            public void onLoadingStarted(String imageUri, View view) {
//                full_image.setVisibility(View.GONE);
                bar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view,FailReason failReason) {
//                full_image.setVisibility(View.GONE);
                bar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                full_image.setVisibility(View.VISIBLE);
                bar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
//                full_image.setVisibility(View.GONE);
                bar.setVisibility(View.VISIBLE);
            }
        });
        ((ViewPager) container).addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }
}

