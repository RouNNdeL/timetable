package com.roundel.timetable;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.roundel.timetable.items.Grade;
import com.roundel.timetable.items.GradeGroup;
import com.roundel.timetable.items.HomeItemsGroup;
import com.roundel.timetable.items.LuckyNumber;

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
    private Context mContext;

    public HomeListAdapter(Context context, HomeItemsGroup homeItemsGroup)
    {
        this.mContext = context;
        this.homeItemsGroup = homeItemsGroup;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        int contentPadding = Math.round(mContext.getResources().getDimension(R.dimen.card_contentPadding));

        CardView cardView = new CardView(mContext);
        final RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(contentPadding, contentPadding / 2, contentPadding, contentPadding / 2);
        cardView.setLayoutParams(params);
        cardView.setContentPadding(contentPadding, contentPadding, contentPadding, contentPadding);

        View content;

        switch(viewType)
        {
            case HomeItemsGroup.ITEM_TYPE_LUCKY_NUMBER:
                content = LayoutInflater.from(parent.getContext()).inflate(R.layout.lucky_number_layout, parent, false);
                break;

            case HomeItemsGroup.ITEM_TYPE_GRADE_GROUP:
                content = LayoutInflater.from(parent.getContext()).inflate(R.layout.grade_group_new, parent, false);
                break;

            default:
                content = LayoutInflater.from(parent.getContext()).inflate(R.layout.default_card_content, parent, false);
                break;
        }

        cardView.addView(content);
        ViewHolder viewHolder = new ViewHolder(cardView, viewType);

        return viewHolder;
    }

    @Override
    public int getItemViewType(int position)
    {
        final int itemType = homeItemsGroup.getItemType(position);
        return itemType;
    }

    @Override
    public int getItemCount()
    {
        return homeItemsGroup.size();
    }

    @Override
    public void onBindViewHolder(HomeListAdapter.ViewHolder holder, int position)
    {
        int type = getItemViewType(position);
        Object item = homeItemsGroup.get(position);
        final CardView cardView = holder.getCardView();

        switch(type)
        {
            case HomeItemsGroup.ITEM_TYPE_LUCKY_NUMBER:
                TextView title = (TextView) cardView.findViewById(R.id.luckyNumberTitle);
                TextView number = (TextView) cardView.findViewById(R.id.luckyNumber);
                TextView day = (TextView) cardView.findViewById(R.id.luckyNumberDay);

                final LuckyNumber luckyNumber = (LuckyNumber) item;
                number.setText(Integer.toString(luckyNumber.getNumber()));
                day.setText(dateFormat.format(luckyNumber.getDate()));
                break;
            case HomeItemsGroup.ITEM_TYPE_GRADE_GROUP:
                LinearLayout container = (LinearLayout) cardView.findViewById(R.id.gradeGroupNewGradeContainer);
                TextView subject = (TextView) cardView.findViewById(R.id.gradeGroupNewSubject);
                TextView amount = (TextView) cardView.findViewById(R.id.gradeGroupNewAmount);

                //container.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                final GradeGroup gradeGroup = (GradeGroup) item;

                subject.setText(Integer.toString(gradeGroup.getSubject()));
                amount.setText(String.format(Locale.getDefault(), "%d new", gradeGroup.getGrades().size()));
                for(Grade grade : gradeGroup.getGrades())
                {
                    LinearLayout singleGrade = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.single_grade_layout, null);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    final int contentPadding = Math.round(mContext.getResources().getDimension(R.dimen.card_contentPadding));
                    params.setMargins(0, contentPadding / 2, 0, contentPadding / 2);
                    singleGrade.setLayoutParams(params);

                    TextView gradeType = (TextView) singleGrade.findViewById(R.id.gradeType);
                    TextView gradeNumber = (TextView) singleGrade.findViewById(R.id.gradeNumber);
                    TextView gradeDay = (TextView) singleGrade.findViewById(R.id.gradeDay);

                    Log.d(TAG, Integer.toHexString(mContext.getColor(R.color.colorAccentDark)));

                    gradeNumber.setBackgroundTintList(ColorStateList.valueOf(grade.getColor()));
                    gradeNumber.setText(grade.getGrade());
                    gradeDay.setText(dateFormat.format(grade.getDate()));
                    gradeType.setText(Integer.toString(grade.getType()));

                    container.addView(singleGrade);
                }

                break;

            default:
                TextView message = (TextView) cardView.findViewById(R.id.defaultErrorText);
                message.setText(String.format(Locale.getDefault(), "View type %d not found", type));
                break;
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        // each data item is just a string in this case
        private CardView mCardView;
        private int type;

        public int getType()
        {
            return type;
        }

        public void setType(int type)
        {
            this.type = type;
        }

        public ViewHolder(CardView v, int type)
        {
            super(v);
            this.type = type;
            mCardView = v;
        }

        public CardView getCardView()
        {
            return mCardView;
        }
    }
}
