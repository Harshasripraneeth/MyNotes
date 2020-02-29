package com.pressure.mynotes;

import android.content.Intent;
import android.os.Bundle;

import com.pressure.mynotes.UI.AddActivity;
import com.pressure.mynotes.databinding.ActivityMainBinding;
import com.pressure.mynotes.model.Entity;
import com.pressure.mynotes.methods.Adapter;
import com.pressure.mynotes.viewmodel.MainActivityViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Adapter.itemclicklistener {

    private Adapter adapter;
    private  MainActivityViewModel mainActivityViewModel;
    private ActivityMainBinding mainBinding;
    private ClickHandlers clickHandlers;
    private int ADD_NOTE_REQUEST_CODE=1;
    private int EDIT_NOTE_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        clickHandlers = new ClickHandlers();
        mainBinding.setClickhandlers(clickHandlers);
        mainActivityViewModel = new ViewModelProvider(MainActivity.this).get(MainActivityViewModel.class);
        mainBinding.rcview.setHasFixedSize(true);
        mainBinding.rcview.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        //text watcher for etsearch
        mainBinding.etsearch.addTextChangedListener(new TextWatcher() {
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
        loadData();

        adapter = new Adapter(MainActivity.this);
        mainBinding.rcview.setAdapter(adapter);

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
                int position = viewHolder.getAdapterPosition();
                List<Entity> list = adapter.get();

                mainActivityViewModel.delete(list.get(position));
            }
        }).attachToRecyclerView(mainBinding.rcview);
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
            case R.id.deleteAllNotes : mainActivityViewModel.deleteAllNotes();
                                       break;
            case R.id.sortByTitle    : adapter.sortByTitle();
                                       break;
            case R.id.SortbyContent  : adapter.sortByContent();
                                        break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onitemclicklistener(int index) {
       Intent intent = new Intent(MainActivity.this,AddActivity.class);
       intent.putExtra("EXTRA_TASK_ID",index);
       startActivityForResult(intent,EDIT_NOTE_REQUEST_CODE);

    }

    // loading the data into the adapter.
    void loadData()
    {
        //MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        MainActivityViewModel viewModel = new ViewModelProvider(MainActivity.this).get(MainActivityViewModel.class);
        viewModel.getNotes().observe(this, new Observer<List<Entity>>() {
            @Override
            public void onChanged(List<Entity> entities) {
                if(entities == null)
                    entities = new ArrayList<Entity>();
                adapter.set(entities);
            }
        });

    }

    //searching the list according the text in etsearch.
    void textUpdated(String s)
    {
        final String search = s;
        adapter.set(mainActivityViewModel.getSearchList(s));
        Log.d("mainactivity","loaded list");

    }
    public class ClickHandlers
    {
        public void onFabClicked(View view)
        {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivityForResult(intent,ADD_NOTE_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_NOTE_REQUEST_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                mainActivityViewModel.insert(new Entity(data.getStringExtra("title"),data.getStringExtra("desc")));
            }
        }
        else if(requestCode == EDIT_NOTE_REQUEST_CODE && resultCode == RESULT_OK)
        {
                mainActivityViewModel.update(new Entity(data.getIntExtra("taskid",-1),data.getStringExtra("title"),data.getStringExtra("desc")));
        }
    }
}
