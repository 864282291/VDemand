package com.bigdata.base.adapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by Leo on 2018/1/20.
 */

public abstract class RecycleHeadHolder<Data,HeadData> extends RecyclerView.ViewHolder implements View.OnClickListener {
    public OnRecyclerItemClickListener listener;

    public RecycleHeadHolder(View view, OnRecyclerItemClickListener listener) {
        super(view);
        this.listener = listener;
        if (listener != null)
            itemView.setOnClickListener(this);
    }

    public abstract void setData(Data bean, int position);

    public abstract void setHeadData(List<HeadData> headData);

    @Override
    public void onClick(View v) {
        if (listener != null)
            listener.onItemClick(getAdapterPosition());
    }
}