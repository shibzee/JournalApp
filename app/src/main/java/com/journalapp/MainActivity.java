package com.journalapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.journalapp.adapter.JournalAdapter;
import com.journalapp.database.DatabaseHelper;
import com.journalapp.database.model.Journal;
import com.journalapp.utils.MyDividerItemDecoration;
import com.journalapp.utils.RecyclerTouchListener;
import com.journalapp.utils.itemdecorators.ShadowVerticalSpaceItemDecorator;
import com.journalapp.utils.itemdecorators.VerticalSpaceItemDecorator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper mHelper;
    private JournalAdapter mAdapter;
    private List<Journal> journalList = new ArrayList<>();
    @BindView(R.id.recycler_view)
     RecyclerView recyclerView;
    @BindView(R.id.text_empty_journals)
    TextView mEmptyJournals;
    @BindView(R.id.fab_add_journal)
    FloatingActionButton mFabAddButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();
        ButterKnife.bind(this);

        //for Adding new Journal
        mFabAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent mIntent= new Intent(getApplicationContext(),AddActivity.class);
               startActivity(mIntent);

            }
        });
        mHelper= new DatabaseHelper(this);
        journalList.addAll(mHelper.getAllJournals());


        mAdapter = new JournalAdapter(this, journalList);

        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        VerticalSpaceItemDecorator itemDecorator = new VerticalSpaceItemDecorator((int) getResources().getDimension(R.dimen.spacer_20));
        ShadowVerticalSpaceItemDecorator shadowItemDecorator = new ShadowVerticalSpaceItemDecorator(this, R.drawable.drop_shadow);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(shadowItemDecorator);
        recyclerView.addItemDecoration(itemDecorator);
        recyclerView.setItemAnimator(itemAnimator);
        recyclerView.setAdapter(mAdapter);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
//        recyclerView.setAdapter(mAdapter);
//
//        journalList.add(new Journal("My First Day At school","How was my First Day At School"));
//        journalList.add(new Journal("Programming is my passion","writing few lines of code can change your life"));
//        journalList.add(new Journal("My First Day At school","How was my First Day At School"));
//        journalList.add(new Journal("Programming is my passion","writing few lines of code can change your life"));
//        journalList.add(new Journal("My First Day At school","How was my First Day At School"));
//        journalList.add(new Journal("Programming is my passion","writing few lines of code can change your life"));

        // notifyAll();
       // mAdapter.addItemAll(journalList);
//        mAdapter.notifyDataSetChanged();
        // toggleEmptyNotes();

        /**
         * On long press on RecyclerView item, open alert dialog
         * with options to choose
         * Edit and Delete
         * */
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
            startActivity(new Intent(getApplicationContext(),JournalDetail.class));
            }

            @Override
            public void onLongClick(View view, int position) {
               // showActionsDialog(position);
            }
        }));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.add,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_logout:
              //  startActivity(i);
                break;
        }
        return true;
    }

    /**
     * Updating note in db and updating
     * item in the list by its position
     */
    private void updateNote(String thought,String feeling, int position) {
        Journal n = journalList.get(position);
        // updating note text
        n.setThought(thought);
        n.setFeeling(feeling);

        // updating note in db
        mHelper.updateJournal(n);

        // refreshing the list
        journalList.set(position, n);
        mAdapter.notifyItemChanged(position);

       // toggleEmptyNotes();
    }

    /**
     * Deleting note from SQLite and removing the
     * item from the list by its position
     */
    private void deleteNote(int position) {
        // deleting the note from db
        mHelper.deleteJournal(journalList.get(position));

        // removing the note from the list
        journalList.remove(position);
        mAdapter.notifyItemRemoved(position);

        //toggleEmptyNotes();
    }

    /**
     * Opens dialog with Edit - Delete options
     * Edit - 0
     * Delete - 0
     */

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        if (getSupportActionBar() == null) {
            setSupportActionBar(toolbar);
        }
    }

}
