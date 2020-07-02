package com.machtheory.todoo;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ToDo extends Fragment  {

    ListView toDoList;
    FloatingActionButton addButton;
    String addText = "";
    EditText input;
    ArrayList<String> toDos;
    ArrayAdapter arrayAdapter;

    public ToDo() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //setHasOptionsMenu(true);
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
                builder.setTitle("Delete All Items in To Do");
                builder.setMessage("Are you sure you want to clear all items?");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        toDos.clear();
                        arrayAdapter.notifyDataSetChanged();
                        PrefConfig.listInPref(getActivity(), toDos);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewInflater = inflater.inflate(R.layout.fragment_to_do, container, false);



        toDoList = viewInflater.findViewById(R.id.toDoList);

        toDos = PrefConfig.readListFromPref(getActivity());
        addButton = viewInflater.findViewById(R.id.addButton);

        if(toDos == null) {
            toDos = new ArrayList<>();
        }

        arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,toDos);
        toDoList.setAdapter(arrayAdapter);

        toDoList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //send data to next fragment
                Bundle bundle = new Bundle();
                InProgress fragB = new InProgress();
                bundle.putString("Key", toDos.get(i));
                fragB.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frame_progress, fragB)
                        .commit();

                //set alert dialog to ask if user wants to delete item from list
                Toast.makeText(getActivity(), "Moved to In Progress!", Toast.LENGTH_SHORT).show();
                toDos.remove(i);
                arrayAdapter.notifyDataSetChanged();

                // add code to remove from shared prefs
                PrefConfig.listInPref(getActivity(), toDos);

                return true;
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(), "Button Selected", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setTitle("Add new item");
                builder.setView(input);
                //add select if urgent option to

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addText = input.getText().toString();
                        toDos.add(addText);
                        PrefConfig.listInPref(getContext(), toDos);
                        arrayAdapter.notifyDataSetChanged();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
                return viewInflater;
        }


// This was an attempt at fixing data loss between to do and done
// Update! fixed as of now by adding a removePrefFromList method

    @Override
    public void onPause() {
        super.onPause();
        Log.d("To Do", "Paused");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("To Do", "Destroyed");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("To Do", "Resumed");
        //PrefConfig.registerPref(getContext(),l);
    }
/* Removing for now as I seem to have fixed the issue of deleting items from the array
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

    }

 */
}


