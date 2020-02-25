package com.pressure.mynotes.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.pressure.mynotes.databinding.ActivityAddBinding;
import com.pressure.mynotes.methods.Executors;
import com.pressure.mynotes.R;
import com.pressure.mynotes.database.Database;
import com.pressure.mynotes.entities.Entity;

public class AddActivity extends AppCompatActivity  {
    private ActivityAddBinding addBinding;
    private Database db;
    private EditText etdesc;
    private EditText etTitle;
    private int DEFAULT_TASK_ID = -1;
    //if TASK_ID is not equal to DEFAULT_ID then it is in update mode.
    private int TASK_ID = DEFAULT_TASK_ID;
    private boolean b =true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addBinding = DataBindingUtil.setContentView(this,R.layout.activity_add);
        Intent intent = getIntent();
        TASK_ID = intent.getIntExtra("EXTRA_TASK_ID",-1);

        //getting the previous data.
        if(TASK_ID != -1)
        {
            load();
        }
    }

    @Override
    public void onBackPressed() {
        if(TASK_ID == -1)
        {
            insert();
        }
        else
        {
            update();
        }
        if(b)
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    void insert()
    {
        String title = addBinding.etTitle.getText().toString();
        if(title.isEmpty()) {
            showDialogBox();
        }
        else {
        insertingNote();
        }
    }

    //inserting note to the database.
    void insertingNote()
    {
        String title = addBinding.etTitle.getText().toString();
        String desc = addBinding.etDesc.getText().toString();

        final Entity et = new Entity(title, desc);
        db = Database.getInstance(AddActivity.this);
        Executors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                db.taskDao().insertTask(et);
            }
        });
        finish();
    }

    //loading the data when the activity is in update mode.
    void load()
    {
        db = Database.getInstance(AddActivity.this);
        Executors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
               final Entity e = db.taskDao().loadById(TASK_ID);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        addBinding.setNote(e);
                    }
                });
            }
        });
    }

    //updating the note.
    void update()
    {   String title = etTitle.getText().toString();
        String desc = etdesc.getText().toString();
        final Entity et = new Entity(TASK_ID,title,desc);

        db= Database.getInstance(AddActivity.this);
        Executors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                db.taskDao().updateTask(et);
            }
        });
    }

     //showing the dialog box if the title is empty.
    void showDialogBox()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
         builder.setTitle("Attention")
                 .setMessage("do you want to save the note without title")
                 .setPositiveButton("save", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialogInterface, int i) {
                     insertingNote();
                     }
                 })
                 .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialogInterface, int i) {
                         dialogInterface.cancel();
                     }
                 });
          AlertDialog dialog = builder.create();
          dialog.show();
          if(dialog.isShowing())
          {
              b=false;
          }
    }

}

