package utils;

import android.app.Activity;
import android.widget.Toast;

import com.android.cjzk.Bean.ShareBean;
import com.android.cjzk.Utility.Utility;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * Created by XOne on 2016/8/17.
 */
public class UmengOneKeyShare {

    private static UmengOneKeyShare oneShare;
    private ShareAction shareAction;
    private Activity mContext;
    private String content;
    private String url;
    private String title;
    private ShareBean bean;

    public UmengOneKeyShare(Activity context) {
        this.mContext = context;
        shareAction = new ShareAction(context);
        shareAction.setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE);
    }

    public void setOneShareBean(ShareBean bean) {
        this.bean = bean;
        setData(bean);
        setShareContent();
    }

    private void setData(ShareBean bean) {
        content = bean.getTitle() == null ? "财经智库网" : bean.getTitle();
        url = bean.getURL();
        title = bean.getTitle() == null ? "财经智库网" : bean.getTitle();
    }

    private void setShareContent() {
        UMImage urlImage = new UMImage(mContext, Utility.DefPicUrl + bean.getImg());
        shareAction.withTitle(title);
        shareAction.withText(content);
        shareAction.withMedia(urlImage);
        shareAction.withTargetUrl(url);
        shareAction.setCallback(umShareListener);
    }

    private void configPlatforms() {
        PlatformConfig.setWeixin("wx1cf179a72642f7e4", "0619169db0bbbf3ba8f38c568aa56788");
        PlatformConfig.setQQZone("1104792315", "MLCc0s5oCAWTyzZm");
    }

    public void share() {
        shareAction.open();
    }

    public void cleanListeners() {
        shareAction = null;
        if (null != oneShare) {
            oneShare = null;
        }
    }

    public void addQQPlatforms(SHARE_MEDIA platform) {
        shareAction.setPlatform(platform)
                .share();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(mContext, platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(mContext, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(mContext, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };
}
