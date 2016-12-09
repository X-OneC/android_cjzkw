//package com.android.cjzk.Adapter;
//
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.android.cjzk.Bean.LiveBean;
//import com.android.cjzk.R;
//import com.eowise.recyclerview.stickyheaders.StickyHeadersAdapter;
//
//import java.util.List;
//
//public class BigramHeaderAdapter implements StickyHeadersAdapter<BigramHeaderAdapter.ViewHolder> {
//
//    private List<LiveBean.LiveArticleList> newsArticleLists;
//
//    public BigramHeaderAdapter() {
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent) {
//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.head_item_view, parent, false);
//        return new ViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder headerViewHolder, int position) {
//        headerViewHolder.title.setText(setTime(newsArticleLists.get(position).getReleaseDate().substring(0, 10)));
//    }
//
//    @Override
//    public long getHeaderId(int position) {
//        return newsArticleLists.get(position).getReleaseDate().subSequence(9, 10).hashCode();
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//
//        TextView title;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            title = (TextView) itemView.findViewById(R.id.tv_header_item);
//        }
//    }
//
//    public void getListData(List<LiveBean.LiveArticleList> listbo) {
//        this.newsArticleLists = listbo;
//    }
//
//    public void getAllListData(List<LiveBean.LiveArticleList> listbo) {
//        this.newsArticleLists.addAll(listbo);
//    }
//
//    private CharSequence setTime(String time) {
//        String[] str = time.split("-");
//        return str[1] + "月" + str[2] + "日";
//    }
//}
