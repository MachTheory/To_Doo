package com.machtheory.todoo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class InProgress extends Fragment {

    ListView inProgressList;

    public InProgress() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_in_progress, container, false);

        inProgressList = view.findViewById(R.id.inProgressList);
        final ArrayList<String> toDos = new ArrayList<>();

        toDos.add("Do Laundry");
        toDos.add("groceries");
        toDos.add("walk dog");

        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,toDos);

        inProgressList.setAdapter(arrayAdapter);

        inProgressList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), toDos.get(i), Toast.LENGTH_SHORT).show();
            }
        });

        return view;

    }
}
