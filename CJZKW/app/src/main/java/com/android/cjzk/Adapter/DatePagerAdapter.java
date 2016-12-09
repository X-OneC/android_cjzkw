package com.android.cjzk.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.android.cjzk.fragment.Second_date_item_Fragment;


public class DatePagerAdapter extends FragmentStatePagerAdapter {// FragmentPagerAdapter

    public DatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // int index = position % adsItems.size();
        // int _position = (index == 0 ? (adsItems.size() - 1) : (index - 1));
        return Second_date_item_Fragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 24;// adsItems.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }

    //	@Override
//	public int getIconResId(int index) {
//		return 0;
//	}
    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

}