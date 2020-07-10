package com.machtheory.todoo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.machtheory.todoo.interfaces.PassDataInterface;

import java.util.ArrayList;

public class Archive extends AppCompatActivity {

    //RecyclerView recyclerView; to be added back when recyclerview is back

    private ArrayList<String> tasks;
    ArrayAdapter arrayAdapter;
    ListView archiveList;
    Toolbar archiveToolBar;

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus, menu);
        return true;
    }

 */




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);
        archiveList = findViewById(R.id.listView);
        archiveToolBar = findViewById(R.id.archiveToolBar);

        archiveToolBar.inflateMenu(R.menu.menus);
        archiveToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                switch(id){
                    case R.id.clear_text:
                        AlertDialog.Builder builder = new AlertDialog.Builder(Archive.this);
                        builder.setTitle("Delete All Items in Archive");
                        builder.setMessage("This cannot be undone");
                        builder.setPositiveButton(Html.fromHtml("<font color='#BE0046'>Yes</font>"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                tasks.clear();
                                arrayAdapter.notifyDataSetChanged();
                                PrefConfig.archiveListInPref(Archive.this, tasks);
                            }
                        });

                        builder.setNegativeButton(Html.fromHtml("<font color='#BE0046'>No, don't clear items</font>"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                        builder.show();

                        break;
                }
                return true;
            }
        });

        tasks = PrefConfig.readArchiveFromPref(this);
        if(tasks == null) {
            tasks = new ArrayList<>();
        }
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, tasks);
        archiveList.setAdapter(arrayAdapter);


        archiveList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "Item deleted", Toast.LENGTH_SHORT).show();
                tasks.remove(i);
                arrayAdapter.notifyDataSetChanged();
                PrefConfig.archiveListInPref(getApplicationContext(), tasks);

            }
        });

        if(tasks.size() == 0){
            tasks.clear();
            arrayAdapter.notifyDataSetChanged();
        }

    }










    }


