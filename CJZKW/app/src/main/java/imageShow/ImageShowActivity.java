package imageShow;

import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.TextView;

import com.android.cjzk.R;
import com.android.cjzk.Utility.Utility;
import com.android.cjzk.activity.MyBaseActivity;

import java.util.ArrayList;


/*
 * ͼƬչʾ
 */
public class ImageShowActivity extends MyBaseActivity {

    private ImageShowViewPager image_pager;
    private TextView page_number;

    private ArrayList<String> imgsUrl;

    private ImagePagerAdapter mAdapter;

    @Override
    protected void onCreateT(Bundle savedInstanceState) {
        Utility.showImmerseStatusBar(R.color.StatusBarColor, this);
        initData1();
        initView();
        initViewPager();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.details_imageshow;
    }

    private void initData1() {
    }

    @SuppressWarnings("deprecation")
    private void initView() {
        imgsUrl = getIntent().getStringArrayListExtra("infos");
        image_pager = (ImageShowViewPager) findViewById(R.id.image_pager);
        page_number = (TextView) findViewById(R.id.page_number);
        image_pager.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                page_number.setText((arg0 + 1) + "/" + imgsUrl.size());
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        page_number.setText("1" + "/" + imgsUrl.size());
    }

    private void initViewPager() {
        if (imgsUrl != null && imgsUrl.size() != 0) {
            mAdapter = new ImagePagerAdapter(getApplicationContext(), imgsUrl);
            image_pager.setAdapter(mAdapter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}
