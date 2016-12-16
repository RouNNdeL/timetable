package com.roundel.timetable;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Krzysiek on 2016-12-16.
 */

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.ViewHolder>
{
    private final String TAG = getClass().getSimpleName();
    private HomeItemsGroup homeItemsGroup;
    private DateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy", Locale.getDefault());

    public HomeListAdapter(HomeItemsGroup homeItemsGroup)
    {
        this.homeItemsGroup = homeItemsGroup;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        CardView v = null;

        Log.d(TAG, Integer.toString(viewType));

        if(viewType == HomeItemsGroup.ITEM_TYPE_LUCKY_NUMBER)
        {
            v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.lucky_number_card, parent, false);
        }

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public int getItemViewType(int position)
    {
        return homeItemsGroup.getItemType(position);
    }

    @Override
    public int getItemCount()
    {
        return homeItemsGroup.getItemCount();
    }

    @Override
    public void onBindViewHolder(HomeListAdapter.ViewHolder holder, int position)
    {
        int type = getItemViewType(position);
        Object item = homeItemsGroup.getItemAtPosition(position);

        if(type == HomeItemsGroup.ITEM_TYPE_LUCKY_NUMBER)
        {
            TextView title = (TextView) holder.getCardView().findViewById(R.id.luckyNumberTitle);
            TextView number = (TextView) holder.getCardView().findViewById(R.id.luckyNumber);
            TextView day = (TextView) holder.getCardView().findViewById(R.id.luckyNumberDay);

            number.setText(Integer.toString(((LuckyNumber) item).getNumber()));
            day.setText(dateFormat.format(((LuckyNumber) item).getDate()));
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        // each data item is just a string in this case
        private CardView mCardView;

        public ViewHolder(CardView v)
        {
            super(v);
            mCardView = v;
        }

        public CardView getCardView()
        {
            return mCardView;
        }
    }
}
