package com.bigdata.base.adapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by 通子 on 2017/8/25.
 */

public abstract class RecyclerHolder<Data> extends RecyclerView.ViewHolder implements View.OnClickListener {
    public OnRecyclerItemClickListener listener;

    public RecyclerHolder(View view, OnRecyclerItemClickListener listener) {
        super(view);
        this.listener = listener;
        if (listener != null)
            itemView.setOnClickListener(this);
    }

    public abstract void setData(Data bean, int position);

    @Override
    public void onClick(View v) {
        if (listener != null)
            listener.onItemClick(getAdapterPosition());
    }
}
