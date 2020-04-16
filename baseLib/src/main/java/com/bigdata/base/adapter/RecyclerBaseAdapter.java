package com.bigdata.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 通子 on 2017/8/25.
 */

public abstract class RecyclerBaseAdapter<Data> extends RecyclerView.Adapter<RecyclerHolder<Data>> {
    public List<Data> datas;
    public Context mContext;
    public LayoutInflater inflater;
    public OnRecyclerItemClickListener itemListener;

    public RecyclerBaseAdapter(Context mContext, List<Data> datas, OnRecyclerItemClickListener itemListener) {
        this.mContext = mContext;
        this.datas = datas;
        this.itemListener = itemListener;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder<Data> holder, int position) {
        if (datas != null && datas.size() > 0) {
            Data data = datas.get(position);
            holder.setData(data, position);
        }
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(getLayout(), null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        return getHolder(view, itemListener);
    }

    public void setDatas(List<Data> dataList) {
        this.datas = dataList;
        notifyDataSetChanged();
    }

    public void addDatas(List<Data> datas) {
        if (this.datas != null && datas != null)
            this.datas.addAll(datas);
        else
            this.datas = datas;
        notifyDataSetChanged();
    }

    public abstract RecyclerHolder getHolder(View view, OnRecyclerItemClickListener itemListener);

    public abstract int getLayout();

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (datas == null)
            return 0;
        return datas.size();
    }
}
