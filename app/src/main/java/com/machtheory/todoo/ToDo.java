package com.machtheory.todoo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class ToDo extends Fragment {

    ListView toDoList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_to_do, container, false);

        toDoList = view.findViewById(R.id.toDoList);
        final ArrayList<String> toDos = new ArrayList<>();

        toDos.add("Do Laundry");
        toDos.add("groceries");
        toDos.add("walk dog");

        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,toDos);

        toDoList.setAdapter(arrayAdapter);

        toDoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), toDos.get(i), Toast.LENGTH_SHORT).show();
            }
        });

        return view;




    }
}
