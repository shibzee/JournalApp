package com.journalapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.journalapp.adapter.JournalAdapter;
import com.journalapp.database.DatabaseHelper;
import com.journalapp.database.model.Journal;
import com.journalapp.utils.RecyclerTouchListener;
import com.journalapp.utils.itemdecorators.ShadowVerticalSpaceItemDecorator;
import com.journalapp.utils.itemdecorators.VerticalSpaceItemDecorator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity{
    private static final String JOURNAL="journal";
    private DatabaseHelper mHelper;
    private JournalAdapter mAdapter;
    private List<Journal> journalList = new ArrayList<>();

    // Firebase Auth Object.
    public FirebaseAuth firebaseAuth;
    @BindView(R.id.recycler_view)
     RecyclerView recyclerView;
    @BindView(R.id.text_empty_journals)
    TextView mEmptyJournals;
    @BindView(R.id.fab_add_journal)
    FloatingActionButton mFabAddButton;

    //Action Mode for toolbar
    private ActionMode mActionMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();
        ButterKnife.bind(this);


        // Getting Firebase Auth Instance into firebaseAuth object.
        firebaseAuth = FirebaseAuth.getInstance();
        
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
        registerForContextMenu(recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(shadowItemDecorator);
        recyclerView.addItemDecoration(itemDecorator);
        recyclerView.setItemAnimator(itemAnimator);
        recyclerView.setAdapter(mAdapter);




        /**
         * On long press on RecyclerView item, open alert dialog
         * with options to choose
         * Edit and Delete
         * */
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new RecyclerTouchListener.ClickListener() {

                @Override
            public void onClick(View view, int position) {
                Journal journal=journalList.get(position);
                Intent i=new Intent(getApplicationContext(),JournalDetail.class);
                i.putExtra(JOURNAL, journal);
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        FirebaseUser user = firebaseAuth.getCurrentUser();
        getMenuInflater().inflate(R.menu.main_menu,menu);
     //   MenuItem mUserName = menu.findItem(R.id.user_name);
     //   mUserName.setTitle(user.getDisplayName());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_logout:
                FirebaseInstanceId FirebaseInstanceID = FirebaseInstanceId.getInstance();
                firebaseAuth.signOut();
               startActivity(new Intent(getApplicationContext(),LoginActivity.class));
               // FirebaseInstanceID.getInstance(getApplicationContext()).deleteInstanceID();
               finishAffinity();
                break;
        }
        return true;
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


    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
//         //           showNoteDialog(true, journalList.get(position), position);
//                    FragmentManager fragmentManager = getSupportFragmentManager();
//                    Journal journal=journalList.get(position);
//                  //  Log.d("this is the id",journal.getId());
//                    FullscreenDialogFragment newFragment = new FullscreenDialogFragment(journal.getId());
//                    Bundle mBundle=new Bundle();
//                    mBundle.putString(JOURNAL_THOUGHT,journal.getThought());
//                    mBundle.putString(JOURNAL_FEELING,journal.getFeeling());
//                  //  mBundle.putInt(JOURNAL_POSITION,position);
//                    newFragment.setArguments(mBundle);FragmentTransaction transaction = fragmentManager.beginTransaction();
//                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//                    transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
//                } else {
                    deleteNote(position);
                }
            }
        });
        builder.show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.notifyDataSetChanged();
    }
}
