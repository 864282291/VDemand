package com.bigdata.base.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by 通子 on 2017/12/18.
 */

public abstract class ListViewBaseAdapter<Data> extends BaseAdapter {
    private List<Data> datas;
    private LayoutInflater inflater;

    @Override
    public int getCount() {
        if (datas == null)
            return 0;
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(getLayout(), null);
            convertView.setTag(getHolder(convertView));
        }
        RecyclerHolder<Data> holder = (RecyclerHolder<Data>) convertView.getTag();
        Data data = datas.get(position);
        holder.setData(data, position);
        return convertView;
    }

    public abstract RecyclerHolder<Data> getHolder(View convertView);

    public abstract int getLayout();
}
