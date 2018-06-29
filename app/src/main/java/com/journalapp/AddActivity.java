package com.journalapp;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.journalapp.adapter.JournalAdapter;
import com.journalapp.database.DatabaseHelper;
import com.journalapp.database.model.Journal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddActivity extends AppCompatActivity {
    private DatabaseHelper mHelper;
    private JournalAdapter mAdapter;
    private List<Journal> journalList = new ArrayList<>();
    @BindView(R.id.edit_thought)
    EditText mThought;
    @BindView(R.id.edit_feeling)
    EditText mFeeling;
    @BindView(R.id.btn_add_new)
    Button mAddJournal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);
        mHelper= new DatabaseHelper(this);
    }
    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        if (getSupportActionBar() == null) {
            setSupportActionBar(toolbar);

        }
    }
    /**
     * Inserting new note in db
     * and refreshing the list
     */
    private void createJournal(String thought,String feeling) {
        // inserting note in db and getting
        // newly inserted note id
        long id = mHelper.insertJournal(thought,feeling);

        // get the newly inserted note from db
        Journal n = mHelper.getJournal(id);

        if (n != null) {
            // adding new note to array list at 0 position
            journalList.add(0, n);

            // refreshing the list
          //  mAdapter.notifyDataSetChanged();

            //     toggleEmptyNotes();
        }
    }
    @OnClick(R.id.btn_add_new)
    public void addJournal(){
        String feeling=mFeeling.getText().toString().trim();
        String thought=mThought.getText().toString().trim();

        if(TextUtils.isEmpty(feeling)&& TextUtils.isEmpty(thought)){
            Toast.makeText(getApplicationContext(),"Both fields are Empty",Toast.LENGTH_SHORT).show();
        }
        else {
            createJournal(thought, feeling);
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

}
