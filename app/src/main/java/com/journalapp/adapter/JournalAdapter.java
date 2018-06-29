package com.journalapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.journalapp.R;
import com.journalapp.database.model.Journal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 26/06/18.
 */

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.JournalHolder> {

    private Context mContext;
    private List<Journal> journalsList;

    public JournalAdapter(Context context, List<Journal> journalsList) {
        this.mContext = context;
        this.journalsList = journalsList;
    }
    @NonNull
    @Override
    public JournalHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.journal_list_row,viewGroup, false);
        return new JournalHolder(itemView);
    }
    public void addItemAll(List<Journal> journalModel) {
        journalsList.addAll(journalModel);
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull JournalHolder journalHolder, int position) {
        journalHolder.bind(journalsList.get(position));
    }

    @Override
    public int getItemCount() {
        return journalsList.size();
    }

     class JournalHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_thought)
        TextView mThought;
        @BindView(R.id.text_feeling)
        TextView mFeeling;
        @BindView(R.id.text_date)
        TextView mDate;
        JournalHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        void bind(Journal journal){
            mThought.setText(journal.getThought());
            mFeeling.setText(journal.getFeeling());
            mDate.setText(formatDate(journal.getTimestamp()));
           // mDate.setText("12th, June 2018");
        }
    }
    private String formatDate(String dateStr) {
        try {

            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
            fmt.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = fmt.parse(dateStr);

            SimpleDateFormat fmtOut = new SimpleDateFormat("h:mm a   yyyy-MM-dd",Locale.US);
            return fmtOut.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }
}
