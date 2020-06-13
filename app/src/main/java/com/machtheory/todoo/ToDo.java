package com.machtheory.todoo;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ToDo extends Fragment {

    ListView toDoList;
    FloatingActionButton addButton;
    String addText = "";
    EditText input;
    ArrayList<String> toDos;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewInflater = inflater.inflate(R.layout.fragment_to_do, container, false);

        toDoList = viewInflater.findViewById(R.id.toDoList);
        toDos = PrefConfig.readListFromPref(getActivity());

        if(toDos == null) {
            toDos = new ArrayList<>();
        }
        addButton = viewInflater.findViewById(R.id.addButton);

        final ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,toDos);

        toDoList.setAdapter(arrayAdapter);

        toDoList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //send data to next fragment
                Bundle bundle = new Bundle();
                InProgress fragB = new InProgress();
                bundle.putString("Key", toDos.get(i));
                fragB.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frame, fragB).commit();

                //set alert dialog to ask if user wants to delete item from list
                Toast.makeText(getActivity(), "Moved to In Progress!", Toast.LENGTH_SHORT).show();
                toDos.remove(i);
                arrayAdapter.notifyDataSetChanged();
                return false;
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(), "Button Selected", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
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
    }


