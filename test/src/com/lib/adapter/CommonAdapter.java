package com.lib.adapter;

import java.util.List;


import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class CommonAdapter<T> extends BaseAdapter {
    List<T> list;
    RowFactory rowFactory;
    public CommonAdapter(List<T> list, RowFactory rowFactory) {
        this.list=list;
        this.rowFactory=rowFactory;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Row row;
        if (convertView == null) {
            row=rowFactory.newRow();
            row.init(position, getItem(position));
        }else{
            row=(Row)convertView;
        }
        row.update(position, getItem(position));
        return (View)row;
    }


}
