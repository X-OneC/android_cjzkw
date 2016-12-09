package com.android.cjzk.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.cjzk.Api.ApiManager;
import com.android.cjzk.Bean.DataPositionBean;
import com.android.cjzk.R;
import com.android.cjzk.Utility.DateClick;
import com.android.cjzk.Utility.Utility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import calendar.AbStrUtil;
import calendar.CalendarCell;
import calendar.CalendarView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Second_date_item_Fragment extends Fragment {

    private SecondPageFragmentTwo instance;
    private DateClick dateClick;
    private LinearLayout main_layout;
    private LinearLayout date_layout;
    private CalendarView mCalendarView = null;
    private int isClick = -011;
    private String currentMonth = null;
    private List<String> monthList = null;
    private int currentMonthIndex = 0;
    private Calendar calendar;

    public static Second_date_item_Fragment newInstance(int currentMonthIndex) {
        Second_date_item_Fragment itmeFragment = null;
        itmeFragment = new Second_date_item_Fragment();
        itmeFragment.currentMonthIndex = currentMonthIndex;
        return itmeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (main_layout != null) {
            ViewGroup parent = (ViewGroup) main_layout.getParent();
            if (parent != null) {
                parent.removeView(main_layout);
            }
            return main_layout;
        }
        main_layout = (LinearLayout) inflater.inflate(R.layout.fragment_second_date_item_, container, false);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        instance = (SecondPageFragmentTwo) fm.findFragmentByTag("secondPageFragmentTwo");
        dateClick = (DateClick) instance;
        date_layout = (LinearLayout) main_layout.findViewById(R.id.date_layout1);
        mCalendarView = new CalendarView(getActivity());
        date_layout.addView(mCalendarView);
        mCalendarView.setHeaderHeight(50);
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int screenWidth = displayMetrics.widthPixels;
//        int screenHeight = displayMetrics.heightPixels;
        float ratioWidth = (float) Utility.screenWidth / 720;
        float ratioHeight = (float) Utility.screenHeight / 1280;
        float RATIO = Math.min(ratioWidth, ratioHeight);
        mCalendarView.setHeaderTextSize(Math.round(24 * RATIO));
        mCalendarView.setBackgroundResource(R.color.list_cache_color_hint);
        mCalendarView.setHeaderBackgroundResource(R.color.list_cache_color_hint);
        mCalendarView.setOnItemClickListener(new CalendarView.AbOnItemClickListener() {
            @Override
            public void onClick(int position) {
                String date = mCalendarView.getStrDateAtPosition(position);
                if (isClick != position) {
                    isClick = position;
                    dateClick.dateClick(date, position);
                }
            }
        });
        new MyAsyncTask().execute();

        return main_layout;
    }

    class MyAsyncTask extends AsyncTask<Void, Integer, List<String>> {

        @Override
        protected List<String> doInBackground(Void... params) {
            monthList = new ArrayList<String>();
            calendar = Calendar.getInstance();
            int curYear = calendar.get(Calendar.YEAR); // 得到系统年份
            int curMonth = calendar.get(Calendar.MONTH) + 1; // 得到系统月份
            int preYear = curYear - 1;
            for (int i = 7; i <= 12; i++) {
                monthList.add(preYear + "-" + AbStrUtil.strFormat2(String.valueOf(i)));
            }
            for (int i = 1; i <= 12; i++) {
                monthList.add(curYear + "-" + AbStrUtil.strFormat2(String.valueOf(i)));
            }
            for (int i = 1; i <= 6; i++) {
                monthList.add((curYear + 1) + "-" + AbStrUtil.strFormat2(String.valueOf(i)));
            }
            return monthList;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            currentMonth = monthList.get(currentMonthIndex);
            String[] yearmonth = currentMonth.split("-");
            Calendar cal_select = Calendar.getInstance();
            cal_select.set(Calendar.YEAR, Integer.parseInt(yearmonth[0]));
            cal_select.set(Calendar.MONTH, Integer.parseInt(yearmonth[1]) - 1);
            cal_select.set(Calendar.DAY_OF_MONTH, 1);
            mCalendarView.rebuildCalendar(cal_select);
            getDateDataT(cal_select.get(Calendar.YEAR), cal_select.get(Calendar.MONTH) + 1,
                    cal_select.get(Calendar.DAY_OF_WEEK), cal_select.getActualMaximum(Calendar.DATE));
            super.onPostExecute(result);
        }
    }

    private void getDateDataT(int YEAR, int MONTH, final int DAY_OF_WEEK, final int Maximum) {
        ApiManager.getInstance().getPositionData(getReleaseDate(YEAR, MONTH)).enqueue(new Callback<DataPositionBean>() {
            @Override
            public void onResponse(Call<DataPositionBean> call, Response<DataPositionBean> response) {
                if (null == response.body()) {
                    Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Integer> list = response.body().getData();
                ArrayList<CalendarCell> mCalendarCell = mCalendarView.getCalendarCells();
                for (int i = 0; i < list.size(); i++) {
                    CalendarCell cc = mCalendarCell.get((DAY_OF_WEEK - 2) + list.get(i));
                    cc.setHasRecord(true);
                }
            }

            @Override
            public void onFailure(Call<DataPositionBean> call, Throwable t) {
                Toast.makeText(getActivity(), "shibai", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getReleaseDate(int year, int month) {
        StringBuffer buffer = null;
        buffer = new StringBuffer();
        buffer.append(String.valueOf(year));
        buffer.append("-");
        buffer.append(String.valueOf(month));
        return buffer.toString();
    }

}

