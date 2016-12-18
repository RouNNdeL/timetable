package com.roundel.timetable;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Krzysiek on 2016-12-18.
 */

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.ViewHolder>
{
    private String[] data;
    private Context mContext;

    public NavigationDrawerAdapter(String[] data, Context context)
    {
        this.mContext = context;
        this.data = data;
    }

    @Override
    public NavigationDrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        ViewHolder viewHolder;
        /*switch(viewType)
        {
            viewHolder = new ViewHolder();
        }*/
        LinearLayout content = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_drawer_row, parent, false);
        viewHolder = new ViewHolder(content);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NavigationDrawerAdapter.ViewHolder holder, int position)
    {
        TextView title = (TextView) holder.getContent().findViewById(R.id.row_title);
        ImageView icon = (ImageView) holder.getContent().findViewById(R.id.row_icon);
        title.setText(data[position]);
        icon.setColorFilter(mContext.getColor(R.color.icon_light54));
    }

    @Override
    public int getItemCount()
    {
        return data.length;
    }

    @Override
    public int getItemViewType(int position)
    {
        return 0;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private View content;
        public ViewHolder(View itemView)
        {
            super(itemView);
            content = itemView;
        }

        public View getContent()
        {
            return content;
        }
    }
}
