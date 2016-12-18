package com.roundel.timetable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by Krzysiek on 2016-12-18.
 */

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.ViewHolder>
{
    private NavigationDrawerItems data;
    private Context mContext;
    private View.OnClickListener mOnClickListener;

    public NavigationDrawerAdapter(NavigationDrawerItems items, Context context, View.OnClickListener onClickListener)
    {
        this.mContext = context;
        this.data = items;
        this.mOnClickListener = onClickListener;
    }

    @Override
    public NavigationDrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        ViewHolder viewHolder;
        View content;
        switch(viewType)
        {
            case NavigationDrawerItem.TYPE_ITEM:
                content = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_drawer_row, parent, false);
                break;
            case NavigationDrawerItem.TYPE_SUB_HEADER:
                content = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_drawer_sub_header, parent, false);
                break;
            case NavigationDrawerItem.TYPE_DIVIDER:
                content = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_drawer_divider, parent, false);
                break;
            case NavigationDrawerItem.TYPE_EMPTY_SPACE:
                content = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_drawer_empty_space, parent, false);
                break;
            default:
                content = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_drawer_empty_layout, parent, false);
                break;
        }

        content.setOnClickListener(mOnClickListener);
        viewHolder = new ViewHolder(content);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NavigationDrawerAdapter.ViewHolder holder, int position)
    {
        int type = getItemViewType(position);
        NavigationDrawerItem item = data.get(position);
        View content = holder.getContent();
        switch(type)
        {
            case NavigationDrawerItem.TYPE_ITEM:
                //LinearLayout container = (LinearLayout)  content.findViewById(R.id.row_container);
                TextView title = (TextView) content.findViewById(R.id.row_title);
                ImageView icon = (ImageView) content.findViewById(R.id.row_icon);

                title.setText(item.getText());
                icon.setImageDrawable(item.getIcon());

                if(data.getEnabled() == position)
                {
                    content.setBackgroundColor(mContext.getColor(R.color.light6));
                    title.setTextColor(mContext.getColor(R.color.colorPrimaryDark));
                    icon.setColorFilter(mContext.getColor(R.color.colorPrimaryDark));
                }
                else
                {
                    int[] attrs = new int[] { android.R.attr.selectableItemBackground /* index 0 */};
                    TypedArray ta = mContext.obtainStyledAttributes(attrs);
                    Drawable drawableFromTheme = ta.getDrawable(0 /* index */);
                    ta.recycle();

                    content.setBackground(drawableFromTheme);
                    title.setTextColor(mContext.getColor(R.color.text_light87));
                    icon.setColorFilter(mContext.getColor(R.color.icon_light54));
                }
                break;

            case NavigationDrawerItem.TYPE_SUB_HEADER:
                TextView sectionName = (TextView) content.findViewById(R.id.sub_header_title);
                sectionName.setText(item.getText());
                break;

            case NavigationDrawerItem.TYPE_DIVIDER:
                break;

            case NavigationDrawerItem.TYPE_EMPTY_SPACE:
                break;

            default:
                break;
        }
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        return data.get(position).getType();
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
