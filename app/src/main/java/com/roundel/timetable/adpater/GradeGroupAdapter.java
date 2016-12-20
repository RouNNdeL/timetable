package com.roundel.timetable.adpater;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.roundel.timetable.R;
import com.roundel.timetable.librus.GradeGroup;

import java.util.Locale;

/**
 * Created by Krzysiek on 2016-12-20.
 */
public class GradeGroupAdapter extends RecyclerView.Adapter<GradeGroupAdapter.ViewHolder>
{
    public static final String TAG = "GradeGroupAdapter";

    private GradeGroup data;
    private Context mContext;
    private View.OnClickListener mOnClickListener;

    public GradeGroupAdapter(Context mContext, GradeGroup data, View.OnClickListener onClickListener)
    {
        this.data = data;
        this.mContext = mContext;
        this.mOnClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View content = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_grade_layout_expandable, parent, false);

        CardView cardView = new CardView(mContext);

        CardView.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        cardView.setContentPadding(0, 0, 0, 0);
        cardView.setCardElevation(0);
        cardView.setRadius(0);
        cardView.setLayoutParams(layoutParams);

        cardView.addView(content);
        cardView.setOnClickListener(mOnClickListener);

        ViewHolder holder = new ViewHolder(cardView);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        GradeGroup.Grade grade = data.get(position);
        View content = holder.getContent();

        TextView number = (TextView) content.findViewById(R.id.gradeNumber);
        TextView type = (TextView) content.findViewById(R.id.gradeType);

        number.setText(grade.getGrade());
        type.setText(String.format(Locale.getDefault(), "%d", grade.getType()));
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        View content;

        public ViewHolder(View content)
        {
            super(content);
            this.content = content;
        }

        public View getContent()
        {
            return content;
        }

        public void setContent(View content)
        {
            this.content = content;
        }
    }
}
