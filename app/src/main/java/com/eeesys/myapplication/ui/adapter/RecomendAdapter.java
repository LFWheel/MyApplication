package com.eeesys.myapplication.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eeesys.myapplication.R;
import com.eeesys.myapplication.entity.Recomend;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ASUS on 2016/7/19.
 */
public class RecomendAdapter extends BaseAdapter {
    List<Recomend> data;
    Context context;
    LayoutInflater inflater;
    public RecomendAdapter(List<Recomend> data, Context context){
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null){
            convertView = inflater.inflate(R.layout.item_recomend,null);
            holder = new ViewHolder();
            holder.iv_recImg = (ImageView) convertView.findViewById(R.id.iv_recimg);
            holder.tv_mark = (TextView) convertView.findViewById(R.id.tv_mark);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(context).load(data.get(position).getImgUrl()).resize(120,120).centerCrop().into(holder.iv_recImg);
       // holder.iv_recImg.setImageResource(data.get(position).getImgUrl());
        holder.tv_mark.setText(data.get(position).getMark());
        return convertView;
    }

    class ViewHolder{
        ImageView iv_recImg;
        TextView tv_mark;
    }
}
