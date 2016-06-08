package com.eeesys.myapplication.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eeesys.myapplication.R;
import com.eeesys.myapplication.entity.RecentNews;

import java.util.List;

/**
 * Created by ASUS on 2016/6/7.
 */
public class RecentNewsAdapter extends RecyclerView.Adapter<RecentNewsAdapter.MyViewHolder>{
    private List<RecentNews> data;
    private LayoutInflater inflater;
    private Context context;
    private OnItemClickListener mlistener;

    public RecentNewsAdapter(Context context, List<RecentNews> data){
        this.data=data;
        this.context=context;
        inflater=LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mlistener=listener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.item_recent_news,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.iv_news_img.setImageResource(data.get(position).getNews_img());
        holder.tv_content.setText(data.get(position).getContent());

        if(mlistener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=holder.getLayoutPosition();
                    mlistener.onItemClick(holder.itemView,pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos=holder.getLayoutPosition();
                    mlistener.onItemLongClick(holder.itemView,pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_content;
        ImageView iv_news_img;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_content= (TextView) itemView.findViewById(R.id.tv_news_content);
            iv_news_img = (ImageView) itemView.findViewById(R.id.iv_news_img);
        }
    }

    public interface OnItemClickListener{
        public void onItemClick(View view,int postion);
        public  void onItemLongClick(View view,int postion);
    }
}
