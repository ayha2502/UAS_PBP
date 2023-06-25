package com.syzlnnuro.fmodul1.adapters;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.syzlnnuro.fmodul1.R;
import com.syzlnnuro.fmodul1.models.Diary;

import java.util.ArrayList;

import utils.DateUtil;

public class DiaryRecyclerViewAdapter extends RecyclerView.Adapter<DiaryRecyclerViewAdapter.ViewHolder> {
    private ArrayList<Diary> mDiary;
    private OnDiaryListener mOnDiaryListener;
    //private Context context;

    public DiaryRecyclerViewAdapter(ArrayList<Diary> mDiary, OnDiaryListener mOnDiaryListener) {
        //this.context = context;
        this.mDiary = mDiary;
        this.mOnDiaryListener = mOnDiaryListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView timestamp;
        OnDiaryListener onDiaryListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.diary_title);
            timestamp = itemView.findViewById(R.id.diary_timestamp);
            this.onDiaryListener = onDiaryListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnDiaryListener.onDiaryClick(getAdapterPosition());
        }
    }

    public interface OnDiaryListener {
        void onDiaryClick(int position);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_diary, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            String month = mDiary.get(position).getTimestamp().substring(0,2);
            month = DateUtil.getMonthFromNumber(month);
            String year = mDiary.get(position).getTimestamp().substring(3);
            String timestamp = month + " " + year;
            holder.title.setText(mDiary.get(position).getTitle());
            holder.timestamp.setText(timestamp);
        }catch (NullPointerException e){
            Log.e(TAG, "onBindViewHolde :"+e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return mDiary.size();
    }

}
