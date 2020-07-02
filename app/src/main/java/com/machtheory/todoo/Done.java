package com.machtheory.todoo;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.machtheory.todoo.interfaces.PassDataInterface;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Done extends Fragment {

    ListView doneList;
    ArrayList<String> done;
    ArrayAdapter arrayAdapter;
    ArrayList<String> archive;

    public Done() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }


    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.menus, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.clear_text:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Delete All Items in Done");
                builder.setMessage("*Note, items will not be archived.");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        done.clear();
                        arrayAdapter.notifyDataSetChanged();
                        PrefConfig.listInPref(getActivity(), done);
                    }
                });

                builder.setNegativeButton("No, don't clear items", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.show();

                break;
        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_done, container, false);

        doneList = view.findViewById(R.id.doneList);
        done = PrefConfig.readDoneFromPref(getActivity());
        archive = PrefConfig.readArchiveFromPref(getActivity());

        if(done == null) {
            done = new ArrayList<>();
        }
        if(archive == null){
            archive = new ArrayList<>();
        }

        arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, done);

        doneList.setAdapter(arrayAdapter);

       Bundle bundle = this.getArguments();
        if (bundle != null) {
            String s = bundle.getString("Key2", "received");
            Log.d("Fragment Done", "Received data!");
            done.add(s);
            PrefConfig.doneListInPref(getContext(), done);
            arrayAdapter.notifyDataSetChanged();
        }



        doneList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    archive.add(done.get(i));
                    PrefConfig.archiveListInPref(getActivity(), archive);
                    Toast.makeText(getActivity(), "Sent to Archive", Toast.LENGTH_SHORT).show();

                    done.remove(i);
                    arrayAdapter.notifyDataSetChanged();
                    PrefConfig.doneListInPref(getActivity(), done);
                    if(done.size() == 0){
                        done.clear();
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
                });

        if(done.size() == 0){
            done.clear();
            arrayAdapter.notifyDataSetChanged();
        }
            return view;
        }



    @Override
    public void onPause() {
        super.onPause();
        Log.d("Done", "Paused");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Done", "Destroyed");
    }
}


