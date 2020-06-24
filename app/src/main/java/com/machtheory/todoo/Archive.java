package com.machtheory.todoo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class Archive extends AppCompatActivity {

    RecyclerView recyclerView;

    private ArrayList<String> tasks;
    //private ArrayList<String> dates; To be added later




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Nav);
        setTitle("Archive");
        setContentView(R.layout.activity_archive);

        recyclerView = findViewById(R.id.recyclerView);

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this, tasks);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tasks = PrefConfig.archiveDoneFromPref(getApplicationContext());
        if(tasks == null) {
            tasks = new ArrayList<>();
        }
        //dates = new ArrayList<>();





    }

}
