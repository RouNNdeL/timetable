package com.roundel.timetable.adpater;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.roundel.timetable.R;
import com.roundel.timetable.librus.Subject;

import java.util.List;
import java.util.Locale;

/**
 * Created by Krzysiek on 2016-12-20.
 */
public class SubjectListAdapter extends RecyclerView.Adapter<SubjectListAdapter.ViewHolder>
{
    public static final String TAG = "SubjectListAdapter";

    private List<Subject> data;
    private Context mContext;
    private View.OnClickListener mOnClickListener;

    public SubjectListAdapter(Context context, List<Subject> data, View.OnClickListener mOnClickListener)
    {
        this.mContext = context;
        this.data = data;
        this.mOnClickListener = mOnClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        View content = holder.getContent();
        TextView name = (TextView) content.findViewById(R.id.gradeGroupSubject);
        TextView count = (TextView) content.findViewById(R.id.gradeGroupGradeCount);

        name.setText(data.get(position).getName());
        count.setText(String.format(Locale.getDefault(), "%d ocen", data.get(position).getNumber()));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View content = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_row, parent, false);

        CardView cardView = new CardView(mContext);

        cardView.setContentPadding(0, 0, 0, 0);
        cardView.setCardElevation(0);
        cardView.setRadius(0);

        cardView.addView(content);
        cardView.setOnClickListener(mOnClickListener);

        ViewHolder holder = new ViewHolder(cardView);

        return holder;
    }

    @Override
    public int getItemViewType(int position)
    {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private View content;

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
