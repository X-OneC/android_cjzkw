package com.android.cjzk.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.cjzk.Bean.SiftingBean;
import com.android.cjzk.R;
import com.android.cjzk.activity.NewsMoreActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XOne on 2016/10/10.
 */

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.vh> {

    private Context mContext;
    private LayoutInflater inflater;
    private List<SiftingBean.DataBean> bean;

    public FilterAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
        bean = new ArrayList<>();
    }

    @Override
    public vh onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_filter, parent, false);
        return new vh(view);
    }

    @Override
    public void onBindViewHolder(vh holder, int position) {
        final SiftingBean.DataBean be = bean.get(position);
        holder.bt.setText(be.getName());
        holder.bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIntent(NewsMoreActivity.class, be.getName(), be.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return bean.size();
    }

    class vh extends RecyclerView.ViewHolder {

        private Button bt;

        public vh(View itemView) {
            super(itemView);
            bt = (Button) itemView.findViewById(R.id.bt);
        }
    }

    public void getListData(List<SiftingBean.DataBean> listbo) {
        this.bean = listbo;
        notifyDataSetChanged();
    }

    private void setIntent(Class cla, String name, int Type) {
        Intent intent = new Intent(mContext, cla);
        intent.putExtra("name", name);
        intent.putExtra("Type", Type);
        mContext.startActivity(intent);
    }
}
