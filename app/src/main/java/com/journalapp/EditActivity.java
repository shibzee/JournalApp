package com.journalapp;

import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.journalapp.adapter.JournalAdapter;
import com.journalapp.database.DatabaseHelper;
import com.journalapp.database.model.Journal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditActivity extends AppCompatActivity {
    private static final String JOURNAL="journal";
    private DatabaseHelper mHelper;
@BindView(R.id.edit_thought)
    EditText mThought;
@BindView(R.id.edit_feeling)
    EditText mFeeling;
@BindView(R.id.btn_update_journal)
    MaterialButton mUpdate;
    Journal mJournal;

Intent mIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);
        mIntent=getIntent();
        mHelper=new DatabaseHelper(this);
       mJournal = (Journal) getIntent().getSerializableExtra(JOURNAL);
          mThought.setText(mJournal.getThought());
          mFeeling.setText(mJournal.getFeeling());

    }

    @OnClick(R.id.btn_update_journal)
    public void updateJournal(){
       mHelper.updateData(String.valueOf(mJournal.getId()),mThought.getText().toString(),mFeeling.getText().toString());
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
