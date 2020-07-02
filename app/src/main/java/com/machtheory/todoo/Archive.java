package com.machtheory.todoo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    //private ArrayList<String> dates; To be added later

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Nav);
        setTitle("Archive");
        setContentView(R.layout.activity_archive);
        archiveList = findViewById(R.id.listView);

        tasks = PrefConfig.readArchiveFromPref(this);
        if(tasks == null) {
            tasks = new ArrayList<>();
        }
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, tasks);
        archiveList.setAdapter(arrayAdapter);
/*
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String data = extras.getString("done");
            //The key argument here must match that used in the other activity
            tasks.add(data);
            arrayAdapter.notifyDataSetChanged();
            PrefConfig.archiveListInPref(this, tasks);
            Log.d("Done", data);
        }

 */


        archiveList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), tasks.get(i), Toast.LENGTH_SHORT).show();
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



    //dates = new ArrayList<>();
/*
        recyclerView = findViewById(R.id.recyclerView);

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this, tasks);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

 */





    }


