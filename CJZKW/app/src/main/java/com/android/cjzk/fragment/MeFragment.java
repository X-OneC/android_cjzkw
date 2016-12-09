package com.android.cjzk.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.cjzk.Api.ApiManager;
import com.android.cjzk.Bean.LoginBean;
import com.android.cjzk.R;
import com.android.cjzk.Utility.Utility;
import com.android.cjzk.activity.CollectionActivity;
import com.android.cjzk.activity.ContactUsActivity;
import com.android.cjzk.activity.ShareActivity;
import com.android.cjzk.activity.loginActivity;
import com.android.cjzk.weight.MyDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeFragment extends MyBaseFragment {

    @BindView(R.id.im_me)
    ImageView imMe;
    @BindView(R.id.rel_sc)
    RelativeLayout relSc;
    @BindView(R.id.rel_share)
    RelativeLayout relShare;
    @BindView(R.id.rel_header)
    RelativeLayout relHeader;
    @BindView(R.id.rel01)
    RelativeLayout rel01;
    @BindView(R.id.rel02)
    RelativeLayout rel02;
    @BindView(R.id.rel03)
    RelativeLayout rel03;
    @BindView(R.id.rel04)
    RelativeLayout rel04;
    @BindView(R.id.rel05)
    RelativeLayout rel05;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;

    public MeFragment() {
    }

    public static MeFragment newInstance(String param1, String param2) {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void onCreateViewT() {
        init();
    }

    private void init() {
        relHeader.getLayoutParams().height = (int) (Utility.screenWidth / 640 * 410);
        rel01.getLayoutParams().height = (int) (Utility.screenWidth / 640 * 85);
        rel02.getLayoutParams().height = (int) (Utility.screenWidth / 640 * 85);
        rel03.getLayoutParams().height = (int) (Utility.screenWidth / 640 * 85);
        rel04.getLayoutParams().height = (int) (Utility.screenWidth / 640 * 85);
        rel05.getLayoutParams().height = (int) (Utility.screenWidth / 640 * 85);
        if (!Utility.getSharedData(getActivity(), "token").equals("")) {
            tvUserName.setText(Utility.getSharedData(getActivity(), "userName"));
        }
    }

    @OnClick({R.id.im_me, R.id.rel_sc, R.id.rel_share, R.id.rel01, R.id.rel02, R.id.rel03, R.id.rel04, R.id.rel05})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_me:
                if (Utility.getSharedData(getActivity(), "token").equals("")) {
                    setIntent(loginActivity.class);
                }
                break;
            case R.id.rel_sc:
                if (Utility.getSharedData(getActivity(), "token").equals("")) {
                    Utility.showLoadingDialog(getActivity(), "您还没有登录...");
                    return;
                }
                setIntent(CollectionActivity.class);
                break;
            case R.id.rel_share:
                if (Utility.getSharedData(getActivity(), "token").equals("")) {
                    Utility.showLoadingDialog(getActivity(), "您还没有登录...");
                    return;
                }
                setIntent(ShareActivity.class);
                break;
            case R.id.rel01:
                break;
            case R.id.rel02:
                if (Utility.getSharedData(getActivity(), "token").equals("")) {
                    Toast.makeText(getActivity(), "您还没有登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                showLoadingDialog(getActivity());
                break;
            case R.id.rel03:
                Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.rel04:
                clearMemoryClick();
                break;
            case R.id.rel05:
                setIntent(ContactUsActivity.class);
                break;
        }
    }

    private void setIntent(Class cla) {
        Intent intent = new Intent(getActivity(), cla);
        startActivity(intent);
    }

    public void clearMemoryClick() {
        Toast.makeText(getActivity(), "清除内存缓存成功", Toast.LENGTH_SHORT).show();
        ImageLoader.getInstance().clearMemoryCache(); // 清除内存缓存
    }

    public void clearDiskClick() {
        Toast.makeText(getActivity(), "清除本地缓存成功", Toast.LENGTH_SHORT).show();
        ImageLoader.getInstance().clearDiskCache(); // 清除本地缓存
    }

    private void getLogout(String token) {
        ApiManager.getInstance().getLogout(token).enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                if (null == response.body()) {
                    Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.body().isSucceed()) {
                    Toast.makeText(getActivity(), "退出成功", Toast.LENGTH_SHORT).show();
                    Utility.putSharedData(getActivity(), "token", "");
                    Utility.putSharedData(getActivity(), "userName", "");
                    tvUserName.setText("登陆  | 注册");
                } else {
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //    @Override
//    public void changeText(String text) {
//        tvUserName.setText(text);
//    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

    }

    public void showLoadingDialog(final Context context) {
        View view = View.inflate(context, R.layout.activity_popu_login, null);
        final MyDialog loadingDialog1 = new MyDialog(context, R.style.loading_dialog);
        view.findViewById(R.id.bt_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog1.dismiss();
                getLogout(Utility.getSharedData(context, "token"));
            }
        });
        view.findViewById(R.id.bt_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog1.dismiss();
            }
        });
        ((TextView) view.findViewById(R.id.tv_content)).setText("确定退出！！！");
        LinearLayout.LayoutParams dialoglp = new LinearLayout.LayoutParams(
                (int) (Utility.screenWidth * 3 / 4), (int) (Utility.screenWidth / 3));
        loadingDialog1.addContentView(view, dialoglp);
        loadingDialog1.setCancelable(true);
        loadingDialog1.show();
    }

}
