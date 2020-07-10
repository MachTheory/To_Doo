package com.machtheory.todoo;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.Html;
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
import android.widget.Toolbar;

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
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menu.clear();
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
                builder.setPositiveButton(Html.fromHtml("<font color='#BE0046'>Yes</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        done.clear();
                        arrayAdapter.notifyDataSetChanged();
                        PrefConfig.listInPref(getActivity(), done);
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
        setHasOptionsMenu(false);
        Log.d("Done", "Paused");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        setHasOptionsMenu(false);
        Log.d("Done", "Destroyed");
    }
}


