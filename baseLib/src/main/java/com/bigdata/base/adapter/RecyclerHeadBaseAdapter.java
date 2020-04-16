package com.bigdata.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 通子 on 2017/9/7.
 */

public abstract class RecyclerHeadBaseAdapter<Data, HeadData> extends RecyclerView.Adapter<RecycleHeadHolder<Data, HeadData>> {
    public List<HeadData> headData;
    public List<Data> datas;
    public Context mContext;
    public LayoutInflater inflater;
    public OnRecyclerItemClickListener itemListener;

    public RecyclerHeadBaseAdapter(List<HeadData> headData, List<Data> datas, Context mContext, OnRecyclerItemClickListener itemListener) {
        this.headData = headData;
        this.datas = datas;
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
        this.itemListener = itemListener;
    }


    @Override
    public RecycleHeadHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            //头布局
            View headView = inflater.inflate(getHeadLayout(), parent, false);
            headView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return getHeadHolder(headView);
        } else{
            View childView = inflater.inflate(getLayout(), parent, false);
            childView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return getHolder(childView,itemListener);
        }
    }

    @Override
    public void onBindViewHolder(RecycleHeadHolder<Data,HeadData> holder, int position) {
        if (position == 0) {
            holder.setHeadData(headData);
        } else {
            position--;
            if (datas != null && datas.size() > 0) {
                Data data = datas.get(position);
                holder.setData(data, position);
            }
        }
    }

    public abstract RecycleHeadHolder getHeadHolder(View headView);

    public abstract int getHeadLayout();

    public abstract RecycleHeadHolder getHolder(View view, OnRecyclerItemClickListener itemListener);

    public abstract int getLayout();


    public void setHeadData(List<HeadData> headData) {
        this.headData = headData;
        notifyDataSetChanged();
    }


    public void setDatas(List<Data> dataList) {
        this.datas = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(datas != null){
            return datas.size() + 1;
        }
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 0;
        else
            return 1;
    }
}
