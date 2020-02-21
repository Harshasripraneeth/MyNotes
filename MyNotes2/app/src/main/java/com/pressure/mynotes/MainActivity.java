package com.pressure.mynotes;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.pressure.mynotes.R;
import com.pressure.mynotes.UI.AddActivity;
import com.pressure.mynotes.database.Database;
import com.pressure.mynotes.entities.Entity;
import com.pressure.mynotes.methods.Adapter;
import com.pressure.mynotes.methods.Executors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Adapter.itemclicklistener {

    private RecyclerView rcview;
    private Adapter Adapter1;
    private RecyclerView.Adapter Adapter;
    private RecyclerView.LayoutManager layoutManager;
    private EditText etsearch;

    private Database db;
    private List<Entity> list;
    private List<Entity> updatedlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);

            }
        });


        db = Database.getInstance(MainActivity.this);
        rcview = findViewById(R.id.rcview);
        rcview.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        rcview.setLayoutManager(layoutManager);
        etsearch = findViewById(R.id.etsearch);
        updatedlist = new ArrayList<Entity>();

        //text watcher for etsearch
        etsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                textUpdated(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //loading the data from the database.
        Executors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                list = db.taskDao().loadAllTasks();

            }
        });

        if(list == null)
        {
            list = new ArrayList<Entity>();
        }
        Adapter1 = new Adapter(MainActivity.this, list);
        Adapter = Adapter1;
        rcview.setAdapter(Adapter);

        //item touch helper for deleting the note when swiped.
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Here is where you'll implement swipe to delete
                Executors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<Entity> list = Adapter1.get();
                        db.taskDao().deleteTask(list.get(position));
                    }
                });

                db = Database.getInstance(getApplicationContext());
                load();
            }
        }).attachToRecyclerView(rcview);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id)
        {
            case R.id.deleteAllNotes : deleteAllNotes();
                                       break;
            case R.id.sortByTitle    : sortByTitle();
                                       break;
            case R.id.SortbyContent  : sortByContent();
                                        break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        load();
    }

    @Override
    public void onitemclicklistener(int index) {
       Intent intent = new Intent(MainActivity.this,AddActivity.class);
       intent.putExtra("EXTRA_TASK_ID",index);
       startActivity(intent);

    }

    // loading the data into the adapter.
    void load()
    {
        Executors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<Entity> tasks = db.taskDao().loadAllTasks();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Adapter1.set(tasks);
                    }
                });
            }
        });
    }

    //searching the list according the text in etsearch.
    void textUpdated(String s)
    {
        final String search = "%"+s+"%";
        db = Database.getInstance(MainActivity.this);
        Executors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                updatedlist =db.taskDao().loadUpdatedList(search);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Adapter1.set(updatedlist);
                    }
                });
            }
        });

    }

    //deleting all notes in the database.
    void deleteAllNotes()
    {
        db= Database.getInstance(MainActivity.this);
        Executors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                db.taskDao().deleteAllNotes();
            }
        });
        list.clear();
        Adapter1.set(list);
    }

    //sorting the list according to the title of the note.
    void sortByTitle()
    {
        Collections.sort(list, new Comparator<Entity>() {
            @Override
            public int compare(Entity entity, Entity t1) {
                return entity.getTitle().compareTo(t1.getTitle());
            }
        });
        Adapter1.set(list);
        Toast.makeText(this, "Sorted Successfully", Toast.LENGTH_SHORT).show();

    }

    //sorting the list according to the content of the note.
    void sortByContent()
    {
        Collections.sort(list, new Comparator<Entity>() {
            @Override
            public int compare(Entity entity, Entity t1) {
                return entity.getContent().compareTo(t1.getContent());
            }
        });
        Adapter1.set(list);
        Toast.makeText(this, "Sorted Successfully", Toast.LENGTH_SHORT).show();
    }


}
