package com.journalapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.journalapp.adapter.JournalAdapter;
import com.journalapp.database.DatabaseHelper;
import com.journalapp.database.model.Journal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JournalDetail extends AppCompatActivity {
    private static final String JOURNAL="journal";
    @BindView(R.id.text_thought_detail)
    TextView mThought;
    @BindView(R.id.text_feeling_detail)
    TextView mFeeling;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_detail);
        setUpToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setTitle("meat");
        ButterKnife.bind(this);
       i= getIntent();


        Typeface ts= Typeface.createFromAsset(getApplicationContext().getAssets(), "font/Ultima.ttf");
        Typeface th= Typeface.createFromAsset(getApplicationContext().getAssets(), "font/HelveticaNeue.otf");

        Journal mJournal = (Journal) getIntent().getSerializableExtra(JOURNAL);
//        mThought.setText(i.getIntExtra("stud",0));
        mThought.setText(mJournal.getThought());
        mThought.setTypeface(th);
        mFeeling.setText(mJournal.getFeeling());
        mFeeling.setTypeface(ts);
    }

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        if (getSupportActionBar() == null) {
            setSupportActionBar(toolbar);

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit_journal:
                i=getIntent();
                Intent mIntent=new Intent(this,EditActivity.class);
                mIntent.putExtra(JOURNAL,i.getSerializableExtra(JOURNAL));
                startActivity(mIntent);
                break;
            case android.R.id.home:
                startActivity(new Intent(this,MainActivity.class));
                finish();
                break;
        }
        return true;
    }
}
